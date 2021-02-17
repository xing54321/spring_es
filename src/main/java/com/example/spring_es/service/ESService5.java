package com.example.spring_es.service;

import com.alibaba.fastjson.JSON;
import com.example.spring_es.model.Log;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
public class ESService5 {
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	/**
	 * 查询所有以 “三” 结尾的姓名
	 *
	 * *：表示多个字符（0个或多个字符） ?：表示单个字符
	 */
	public void wildcardQuery() {
		try {
			// 构建查询条件
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.query(QueryBuilders.wildcardQuery("msg.keyword", "*message"));
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
