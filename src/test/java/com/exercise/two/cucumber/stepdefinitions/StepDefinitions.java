package com.exercise.two.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;

import org.testng.Assert;

import com.exercises.utils.APIResources;
import com.exercises.utils.TestDataBuild;
import com.exercises.utils.Utils;

public class StepDefinitions extends Utils{
	
	RequestSpecification request = given().log().all().spec(requestSpecification());
	ResponseSpecification responseSpec;
	Response response;
	TestDataBuild dataPayload = new TestDataBuild(); 
	static String userId;
	
	@Given("The {string} {string} as query param")
	public void the_as_query_param(String queryParam, String valueParam)  {
	    request.queryParam(queryParam, valueParam);
	}
	
	@Given("The {string} {string} as path param")
	public void the_as_path_param(String pathParam, String valueParam) {
		request.pathParam(pathParam, valueParam);
	}
	
	@Given("The {string} and {string} for user payload")
	public void the_and_for_user_payload(String name, String job) {
	    request.body(dataPayload.addUpdateUser(name, job));
	}
	
	@When("User calls {string} with a {string} request")
	public void user_calls_with_a_request(String resource, String method) {
	    
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		if(method.equalsIgnoreCase("POST")) {
			response = request.when().post(resourceAPI.getResource())
					.then().log().all().extract().response();
			userId = getJsonPath(response, "id");
		}else if(method.equalsIgnoreCase("GET")) {
			response = request.when().get(resourceAPI.getResource())
					.then().log().all().extract().response();
		}else if(method.equalsIgnoreCase("PUT")) {
			response = request.pathParam("id", userId).when().put(resourceAPI.getResource())
					.then().log().all().extract().response();
		}else if(method.equalsIgnoreCase("DELETE")) {
			response = request.when().delete(resourceAPI.getResource())
					.then().log().all().extract().response();
		}
	}
	
	@Then("The API got success with status code {int}")
	public void the_api_got_success_with_status_code(Integer expectedStatusCode) {
	    
		Assert.assertEquals(response.getStatusCode(), expectedStatusCode);
		
	}
	
	@Then("Verify if {string} have the correct value {string}")
	public void verify_if_have_the_correct_value(String field, String expectedValue) {
	    
		JsonPath js = new JsonPath(response.asString());
		if(js.get("data")!=null) {
			Assert.assertEquals(getJsonPath(response, "data."+field), expectedValue);
		}else {
			Assert.assertEquals(getJsonPath(response, field), expectedValue);
		}
		
	}
	@Then("Verify if userId maps to {string} using {string}")
	public void verify_if_user_id_maps_to_using(String expectedValue, String resource) {
	    userId = getJsonPath(response, "id");
	    	    
	    request = given().log().all().spec(requestSpecification()).pathParam("id", userId);
	    user_calls_with_a_request(resource, "GET");
	    
	    Assert.assertEquals(getJsonPath(response, "name"), expectedValue);
	}




}
