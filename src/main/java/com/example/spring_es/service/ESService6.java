package com.example.spring_es.service;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.spring_es.model.Log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ESService6 {
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public void boolQuery() {
		try {
			// 创建 Bool 查询构建器
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			// 构建查询条件
			boolQueryBuilder
					.must(QueryBuilders.termsQuery("logger", "c.f.s.controller.IndexController",
							"c.f.s.controller.IndexService"))
					.filter().add(QueryBuilders.rangeQuery("timestamp").format("8uuuu-MM-dd").gte("2021-01-10")
							.lte("2021-01-17"));
			// 构建查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(boolQueryBuilder);
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
					Log l = JSON.parseObject(hit.getSourceAsString(), Log.class);
					// 输出查询信息
					log.info(l.toString());
				}
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
