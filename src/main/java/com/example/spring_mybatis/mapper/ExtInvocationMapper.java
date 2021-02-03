package com.example.spring_mybatis.mapper;

import java.util.List;

import com.example.spring_mybatis.model.ExtInvocation;

public interface ExtInvocationMapper {
	public List<ExtInvocation> getExtInvocation(int featureId,String startDate,String endDate);
	
	public List<ExtInvocation> selectByDate(String currentDate);
	
	public void updateBatch(List<ExtInvocation> list);
	
	public void insertBatch(List<ExtInvocation> list);
}