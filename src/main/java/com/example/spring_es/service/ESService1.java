package com.example.spring_es.service;

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
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.spring_es.model.Log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ESService1 {
	// private static final org.slf4j.Logger log =
	// org.slf4j.LoggerFactory.getLogger(ESService1.class);

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public void termQuery() {
		try {
			// 构建查询条件（注意：termQuery 支持多种格式查询，如 boolean、int、double、string 等，这里使用的是 string 的查询）
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.termQuery("id", "da579fb935524fe387ab6c9483c82bdc"));
			// 创建查询请求对象，将查询对象配置到其中
			SearchRequest searchRequest = new SearchRequest("logs_20210210");
			searchRequest.source(searchSourceBuilder);
			// 执行查询，然后处理响应结果
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			// 根据状态和数据条数验证是否返回了数据
			if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
				SearchHits hits = searchResponse.getHits();
				for (SearchHit hit : hits) {
					// 将 JSON 转换成对象
					Log userInfo = JSON.parseObject(hit.getSourceAsString(), Log.class);
					// // 输出查询信息
					log.info(userInfo.toString());
				}
			}
		} catch (IOException e) {
			System.out.print(e);
		}
	}

	/**
	 * 多个内容在一个字段中进行查询
	 */
	public void termsQuery() {
		try {
			// 构建查询条件（注意：termsQuery 支持多种格式查询，如 boolean、int、double、string 等，这里使用的是 string
			// 的查询）
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.termsQuery("id", "da579fb935524fe387ab6c9483c82bdc",
					"0bb205917188487383ac5c310a377d00"));
			// 创建查询请求对象，将查询对象配置到其中
			SearchRequest searchRequest = new SearchRequest("logs_20210210");
			searchRequest.source(searchSourceBuilder);
			// 执行查询，然后处理响应结果
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			// 根据状态和数据条数验证是否返回了数据
			if (RestStatus.OK.equals(searchResponse.status()) && searchResponse.getHits().totalHits > 0) {
				SearchHits hits = searchResponse.getHits();
				for (SearchHit hit : hits) {
					// 将 JSON 转换成对象
					Log userInfo = JSON.parseObject(hit.getSourceAsString(), Log.class);
					// 输出查询信息
					log.info(userInfo.toString());
				}
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
