package com.example.spring_mybatis.model;

public class User {
	private int id;
	private String userName;
	private int userAge;
	private String userAddress;

	public User() {
		super();
	}

	public User(int id, String userName, int userAge, String userAddress) {
		super();
		this.id = id;
		this.userName = userName;
		this.userAge = userAge;
		this.userAddress = userAddress;
	}
	
	public User( String userName, int userAge, String userAddress) {
		super();
		this.userName = userName;
		this.userAge = userAge;
		this.userAddress = userAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	@Override
	public String toString() {
		return this.userName + " " + this.userAge + " " + this.userAddress;
	}

}