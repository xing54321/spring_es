package com.example.spring_es.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Log {
	private String id;
	private String msg;
	private long cost;
	private String level;
	private String logger;
	private Date day;
	private float threadNo;
	private Date timestamp;
}
