package com.exercises.utils;

public enum APIResources {
	
	APIPath("/users"),
	GetUser("/users/{id}"),
	UpdateUser("/users/{id}");

	private String resource;
	APIResources(String resource){
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}

}
