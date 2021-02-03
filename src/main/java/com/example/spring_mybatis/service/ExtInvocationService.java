package com.example.spring_mybatis.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_mybatis.connection.ESClient;
import com.example.spring_mybatis.mapper.ExtInvocationMapper;
import com.example.spring_mybatis.model.ExtInvocation;

class U {
	String name;

	public U(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

@Service
public class ExtInvocationService {
	@Autowired
	private ExtInvocationMapper extInvocationMapper;

	public List<ExtInvocation> getExtInvocation(int featureId, String startDate, String endDate) {
		return extInvocationMapper.getExtInvocation(featureId, startDate, endDate);
	}

	/**
	 * <获取当日日期> 00:00:00.sss时需要对前一天日志做最后一次计算,返回的仍然是前一天的日期
	 * 
	 * @return 格式yyyy-MM-dd
	 */
	private String getCreateDate() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		// 每天0点时,取前一天的日志计算,是对前一天日志的最后一次计算
		if (0 == hour) {
			cal.set(Calendar.HOUR_OF_DAY, hour - 1);
		}
		cal.get(Calendar.DAY_OF_MONTH);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}

	private String getUpdateTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return format.format(new Date());
	}

	/**
	 * 计算
	 */
	public void aggExtInvocation() {
		String createDate = getCreateDate();
		String updateTime = getUpdateTime();
		// 该更新
		List<ExtInvocation> updateList = extInvocationMapper.selectByDate(createDate);
		Map<String, ExtInvocation> updateMap = updateList.stream()
				.collect(Collectors.toMap(ExtInvocation::getKey, Function.identity()));
		// 待插入
		List<ExtInvocation> insertList = new ArrayList<>();
		try {
			// 创建 Bool 查询构建器
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			// 构建查询条件
			boolQueryBuilder.must(QueryBuilders.wildcardQuery("rests.logType.keyword", "*EXT*")).filter()
					.add(QueryBuilders.rangeQuery("beginDate").format("yyyy-MM-dd HH:mm:ss.SSS")
							.gte(createDate + " 00:00:00.000").lte(createDate + " 23:59:59.999"));

			AggregationBuilder my_agg1 = AggregationBuilders.terms("my_agg1").field("rests.restUrl.keyword")
					.size(10000);
			AggregationBuilder my_agg2 = AggregationBuilders.terms("my_agg2").field("busiFeatureID.keyword").size(10);
			my_agg1.subAggregation(my_agg2);
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(0);
			searchSourceBuilder.aggregation(my_agg1);
			// 创建查询请求对象，将查询条件配置到其中
			SearchRequest request = new SearchRequest("busidetails");
			request.source(searchSourceBuilder);
			// 执行请求
			SearchResponse response = ESClient.getClient().search(request, RequestOptions.DEFAULT);
			// 获取响应中的聚合信息
			Aggregations aggregations = response.getAggregations();
			// 输出内容
			if (RestStatus.OK.equals(response.status())) {
				// 分桶
				Terms byCompanyAggregation = aggregations.get("my_agg1");
				List<? extends Terms.Bucket> buckets = byCompanyAggregation.getBuckets();
				// 输出各个桶的内容

				for (Terms.Bucket bucket : buckets) {
					String url = bucket.getKeyAsString();
					Aggregations aggregations2 = bucket.getAggregations();
					Terms byCompanyAggregation2 = aggregations2.get("my_agg2");
					List<? extends Terms.Bucket> buckets2 = byCompanyAggregation2.getBuckets();
					for (Terms.Bucket bucket2 : buckets2) {
						int featureId = Integer.parseInt(bucket2.getKeyAsString().toString());
						int invocationCount = (int) bucket2.getDocCount();
						// 据featureId和url判定表中是否有该记录,对应ExtInvocation.getKey()
						String key = url + "_" + featureId;
						ExtInvocation extInvocation = updateMap.get(key);
						if (null == extInvocation) {
							insertList.add(new ExtInvocation(featureId, createDate, updateTime, url, invocationCount));
						} else {
							extInvocation.setUpdateTime(updateTime);
							extInvocation.setInvocationCount(invocationCount);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		// 更新,插入
		if (!updateList.isEmpty()) {
			extInvocationMapper.updateBatch(updateList);
		}
		if (!insertList.isEmpty()) {
			extInvocationMapper.insertBatch(insertList);
		}

	}
}
