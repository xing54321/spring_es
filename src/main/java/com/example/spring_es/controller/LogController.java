package com.example.spring_es.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_es.service.ESService1;
import com.example.spring_es.service.ESService2;
import com.example.spring_es.service.ESService3;
import com.example.spring_es.service.ESService4;
import com.example.spring_es.service.ESService5;
import com.example.spring_es.service.ESService6;
import com.example.spring_es.service.ESService7;
import com.example.spring_es.service.ESService8;
import com.example.spring_es.service.ESService9;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LogController {
	@Autowired
	ESService1 eSService1;

	@Autowired
	ESService2 eSService2;

	@Autowired
	ESService3 eSService3;

	@Autowired
	ESService4 eSService4;

	@Autowired
	ESService5 eSService5;

	@Autowired
	ESService6 eSService6;

	@Autowired
	ESService7 eSService7;

	@Autowired
	ESService8 eSService8;
	
	@Autowired
	ESService9 eSService9;
	
	@RequestMapping(value = "/eSService1", method = RequestMethod.POST)
	public void termQuery() {
		log.info("termQuery");
		eSService1.termQuery();
		log.info("termsQuery");
		eSService1.termsQuery();
	}

	@RequestMapping(value = "/eSService2", method = RequestMethod.POST)
	public void eSService2() {
		log.info("matchQuery");
		eSService2.matchQuery();
		log.info("matchAllQuery");
		eSService2.matchAllQuery();
		log.info("matchPhraseQuery");
		eSService2.matchPhraseQuery();
		log.info("matchMultiQuery");
		eSService2.matchMultiQuery();
	}

	@RequestMapping(value = "/eSService3", method = RequestMethod.POST)
	public void eSService3() {
		log.info("fuzzyQuery");
		eSService3.fuzzyQuery();
	}

	@RequestMapping(value = "/eSService4", method = RequestMethod.POST)
	public void eSService4() {
		log.info("rangeQuery");
		eSService4.rangeQuery();
	}

	@RequestMapping(value = "/eSService5", method = RequestMethod.POST)
	public void eSService5() {
		log.info("wildcardQuery");
		eSService5.wildcardQuery();
	}

	@RequestMapping(value = "/eSService6", method = RequestMethod.POST)
	public void eSService6() {
		log.info("boolQuery");
		eSService6.boolQuery();
	}

	@RequestMapping(value = "/eSService7", method = RequestMethod.POST)
	public void eSService7() {
		log.info("aggregationAvg");
		eSService7.aggregationAvg();
		log.info("aggregationCount");
		eSService7.aggregationCount();
		log.info("aggregationMax");
		eSService7.aggregationMax();
		log.info("aggregationMin");
		eSService7.aggregationMin();
		log.info("aggregationPercentiles");
		eSService7.aggregationPercentiles();
		log.info("aggregationStats");
		eSService7.aggregationStats();
		log.info("aggregationSum");
		eSService7.aggregationSum();
	}
	
	@RequestMapping(value = "/eSService8", method = RequestMethod.POST)
	public void eSService8() {
		log.info("aggrBucketDateHistogram");
		eSService8.aggrBucketDateHistogram();
		log.info("aggrBucketDateRange");
		eSService8.aggrBucketDateRange();
		log.info("aggrBucketHistogram");
		eSService8.aggrBucketHistogram();
		log.info("aggrBucketRange");
		eSService8.aggrBucketRange();
		log.info("aggrBucketTerms");
		eSService8.aggrBucketTerms();
	}
	
	@RequestMapping(value = "/eSService9", method = RequestMethod.POST)
	public void eSService9() {
		log.info("aggregationTopHits");
		eSService9.aggregationTopHits();
	}
}
