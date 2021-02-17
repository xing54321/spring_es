package com.example.spring_es.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ESService8 {
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public void aggrBucketTerms() {
		try {
			AggregationBuilder aggr = AggregationBuilders.terms("level_bucket").field("level");
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(10);
			searchSourceBuilder.aggregation(aggr);
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
					log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
				}
				log.info("-------------------------------------------");
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}

	public void aggrBucketRange() {
		try {
			AggregationBuilder aggr = AggregationBuilders.range("cost_range_bucket").field("cost")
					.addUnboundedTo("低耗时", 500).addRange("中间耗时", 500, 1500).addUnboundedFrom("高耗时", 1500);
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(0);
			searchSourceBuilder.aggregation(aggr);
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
				Range byCompanyAggregation = aggregations.get("cost_range_bucket");
				List<? extends Range.Bucket> buckets = byCompanyAggregation.getBuckets();
				// 输出各个桶的内容
				log.info("-------------------------------------------");
				log.info("聚合信息:");
				for (Range.Bucket bucket : buckets) {
					log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
				}
				log.info("-------------------------------------------");
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}

	public void aggrBucketDateRange() {
		try {
			AggregationBuilder aggr = AggregationBuilders.dateRange("timestamp_range_bucket").field("timestamp")
					.format("8uuuu-MM-dd").addRange("2021-01-01~2021-01-31", "2021-01-01", "2021-01-31")
					.addRange("2021-02-01~2021-02-28", "2021-02-01", "2021-02-28");
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(0);
			searchSourceBuilder.aggregation(aggr);
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
				Range byCompanyAggregation = aggregations.get("timestamp_range_bucket");
				List<? extends Range.Bucket> buckets = byCompanyAggregation.getBuckets();
				// 输出各个桶的内容
				log.info("-------------------------------------------");
				log.info("聚合信息:");
				for (Range.Bucket bucket : buckets) {
					log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
				}
				log.info("-------------------------------------------");
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}

	public void aggrBucketHistogram() {
		try {
			AggregationBuilder aggr = AggregationBuilders.histogram("cost_histogram").field("cost")
					.extendedBounds(0, 3000).interval(1000);
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(0);
			searchSourceBuilder.aggregation(aggr);
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
				Histogram byCompanyAggregation = aggregations.get("cost_histogram");
				List<? extends Histogram.Bucket> buckets = byCompanyAggregation.getBuckets();
				// 输出各个桶的内容
				log.info("-------------------------------------------");
				log.info("聚合信息:");
				for (Histogram.Bucket bucket : buckets) {
					log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
				}
				log.info("-------------------------------------------");
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}

	public void aggrBucketDateHistogram() {
		try {
			AggregationBuilder aggr = AggregationBuilders.dateHistogram("day_histogram").field("day").interval(1)
					.dateHistogramInterval(DateHistogramInterval.DAY).format("8uuuu-MM-dd");
			// 查询源构建器
			SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
			searchSourceBuilder.size(0);
			searchSourceBuilder.aggregation(aggr);
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
				Histogram byCompanyAggregation = aggregations.get("day_histogram");

				List<? extends Histogram.Bucket> buckets = byCompanyAggregation.getBuckets();
				// 输出各个桶的内容
				log.info("-------------------------------------------");
				log.info("聚合信息:");
				for (Histogram.Bucket bucket : buckets) {
					log.info("桶名：{} | 总数：{}", bucket.getKeyAsString(), bucket.getDocCount());
				}
				log.info("-------------------------------------------");
			}
		} catch (IOException e) {
			log.error("", e);
		}
	}
}
