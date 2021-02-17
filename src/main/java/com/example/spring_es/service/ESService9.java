package com.example.spring_es.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ESService9 {
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public void aggregationTopHits() {
        try {
            AggregationBuilder testTop = AggregationBuilders.topHits("cost_max_user")
                    .size(1)
                    .sort("cost", SortOrder.DESC);
            AggregationBuilder levelBucket = AggregationBuilders.terms("level_bucket")
                    .field("level")
                    .size(10);
            levelBucket.subAggregation(testTop);
            // 查询源构建器
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.size(0);
            searchSourceBuilder.aggregation(levelBucket);
            // 创建查询请求对象，将查询条件配置到其中
            SearchRequest request = new SearchRequest("logs_20210210");
            request.source(searchSourceBuilder);
            // 执行请求
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            // 获取响应中的聚合信息
            Aggregations aggregations = response.getAggregations();
            // 输出内容
            if (RestStatus.OK.equals(response.status())) {
                // 分桶
                Terms byCompanyAggregation = aggregations.get("level_bucket");
                List<? extends Terms.Bucket> buckets = byCompanyAggregation.getBuckets();
                // 输出各个桶的内容
                log.info("-------------------------------------------");
                log.info("聚合信息:");
                for (Terms.Bucket bucket : buckets) {
                    log.info("桶名：{}", bucket.getKeyAsString());
                    ParsedTopHits topHits = bucket.getAggregations().get("cost_max_user");
                    for (SearchHit hit:topHits.getHits()){
                        log.info(hit.getSourceAsString());
                    }
                }
                log.info("-------------------------------------------");
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
