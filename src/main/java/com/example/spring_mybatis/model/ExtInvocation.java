package com.example.spring_mybatis.model;

public class ExtInvocation {
	private int id;
	private int featureId;
	private String createDate;
	private String updateTime;
	private String url;
	private int invocationCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureId) {
		this.featureId = featureId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getInvocationCount() {
		return invocationCount;
	}

	public void setInvocationCount(int invocationCount) {
		this.invocationCount = invocationCount;
	}

	public ExtInvocation(int id, int featureId, String createDate, String updateTime, String url, int invocationCount) {
		super();
		this.id = id;
		this.featureId = featureId;
		this.createDate = createDate;
		this.updateTime = updateTime;
		this.url = url;
		this.invocationCount = invocationCount;
	}

	public ExtInvocation(int featureId, String createDate, String updateTime, String url, int invocationCount) {
		super();
		this.featureId = featureId;
		this.createDate = createDate;
		this.updateTime = updateTime;
		this.url = url;
		this.invocationCount = invocationCount;
	}

	public ExtInvocation() {
		super();
	}

	public String getKey() {
		return this.url + "_" + this.featureId;
	}

}
