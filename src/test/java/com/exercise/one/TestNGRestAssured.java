package com.exercise.one;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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
		logger = report.createTest("[TC-01] : List Users"); 
		accesAPILog();
		reportInfo("Defining the Request Specification with path: 'https://reqres.in/api' ");
		String page = "2";
		reportInfo("Setting query param 'page' with: '"+page+"'");
		Response response = res.queryParam("page", page)
							.when().get("/users")
							.then().log().all().extract().response();
		
		//Crear log para mostrar endpoint
		verifyStatusCode(response,200);
		verifyResponseField(response, "page", page);	
		reportPass("Users Listed Succesfully");
	}


	
	@Test(priority = 1)
	public void createUser() {
		logger = report.createTest("[TC-02] : Create User");
		accesAPILog();
		reportInfo("Defining the Request Specification with path: 'https://reqres.in/api' ");
		String userName = "Nicolas";
		String userJob = "Developer";
		Response response = res.body(createUpdateUserPayload(userName, userJob))
							.when().post("/users")
							.then().log().all().extract().response();
		verifyStatusCode(response, 201);
		verifyResponseField(response, "name", userName);
		
		userId = getJsonPath(response, "id");
		reportInfo("Getting new user Id: '"+userId+"'");
		reportPass("User Created Succesfully");
	}
	
	@Test(priority=2)
	public void updateUser() {
		logger = report.createTest("[TC-03] : Update User");
		accesAPILog();
		reportInfo("Defining the Request Specification with path: 'https://reqres.in/api' ");
		String userName = "Natalia";
		String userJob = "Designer";
		reportInfo("Setting path param 'id' with: '"+userId+"'");
		Response response = res.pathParam("id", userId).body(createUpdateUserPayload(userName, userJob))
				.when().put("/users/{id}")
				.then().log().all().extract().response();
		verifyStatusCode(response, 200);
		verifyResponseField(response, "name", userName);
		verifyResponseField(response, "job", userJob);
		reportPass("User Updated Succesfully");
	}
	
	@Test(priority = 3)
	public void deleteUser() {
		logger = report.createTest("[TC-04] : Delete User");
		accesAPILog();
		reportInfo("Defining the Request Specification with path: 'https://reqres.in/api' ");
		reportInfo("Setting path param 'id' with: '"+userId+"'");
		Response response = res.pathParam("id", userId)
				.when().delete("/users/{id}")
				.then().log().all().extract().response();
		verifyStatusCode(response, 204);
		reportPass("User Deleted Succesfully");
		
	}
	
	
	@BeforeMethod
	public void createRestSpecification() {
		res = given().log().all().spec(requestSpecification());
	}
	
	
	@AfterMethod
	public void flushReports() {
		System.out.println("Se aplicó el Flush");
		report.flush();
	}
	
	
	/************* General Functions **************/
	private void verifyStatusCode(Response response, int statusCode) {
		reportInfo("Verify the correct Status Code");
		try {
			int actualStatusCode = response.getStatusCode();
			Assert.assertEquals(actualStatusCode, statusCode);
			reportPass(
					"The expected Status Code is: '" + statusCode + "' and the actual is: '" + actualStatusCode + "'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	private void verifyResponseField(Response response, String responseField, String expectedValue) {
		reportInfo("Verify the response '"+responseField+"' field");
		try {
			String actualFieldValue = getJsonPath(response, responseField);
			Assert.assertEquals(actualFieldValue, expectedValue);
			reportPass("The expected Field Value is: '"+expectedValue+"' and the actual is: '"+actualFieldValue+"'");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	private String createUpdateUserPayload(String userName, String userJob) {
		reportInfo("Setting values name: '"+userName+"' and job: '"+userJob+"' in the JSON Payload");
		String jsonPayload = "{\"name\":\""+userName+"\",\"job\":\""+userJob+"\"}";
		reportPass("Correct creation of the JSON:\n"+jsonPayload);
		return jsonPayload;
	}

}
