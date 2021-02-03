package com.example.spring_mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_mybatis.model.ExtInvocation;
import com.example.spring_mybatis.service.ExtInvocationService;

@RestController
@RequestMapping("/aaa")
public class ExtInvocationController {
	@Autowired
	ExtInvocationService extInvocationService;
	
    @GetMapping("/getExtInvocation")
    public List<ExtInvocation> getExtInvocation(int featureId,String startDate,String endDate){
    	return extInvocationService.getExtInvocation(featureId, startDate, endDate);
    }
}
