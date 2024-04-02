package com.exercise.one;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.exercises.utils.Utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class TestNGRestAssured extends Utils{
	
	RequestSpecification res;
	
	String userId;
	
	@Test
	public void listUsers() {
		
		String page = "2";
		Response response = res.queryParam("page", page)
							.when().get("/users")
							.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(getJsonPath(response, "page"), page);
		
	}
	
	@Test(priority = 1)
	public void createUser() {
		String userName = "Nicolas";
		String userJob = "Developer";
		
		Response response = res.body("{\"name\":\""+userName+"\",\"job\":\""+userJob+"\"}")
							.when().post("/users")
							.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertEquals(getJsonPath(response, "name"), userName);		
		
		userId = getJsonPath(response, "id");
	}
	
	@Test(priority=2)
	public void updateUser() {
		String userName = "Natalia";
		String userJob = "Designer";
		
		Response response = res.pathParam("id", userId).body("{\"name\":\""+userName+"\",\"job\":\""+userJob+"\"}")
				.when().put("/users/{id}")
				.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(getJsonPath(response, "name"), userName);	
		Assert.assertEquals(getJsonPath(response, "job"), userJob);	
	}
	
	@Test(priority = 3)
	public void getUser() {
		Response response = res.pathParam("id", userId)
				.when().delete("/users/{id}")
				.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 204);
		
	}
	
	
	@BeforeMethod
	public void createRestSpecification() {
		res = given().log().all().spec(requestSpecification());
	}
	

}
