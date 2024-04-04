package com.exercise.three;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.exercises.pojo.User;

import com.exercises.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RequestExternalJSON extends Utils {

	RequestSpecification res;

	String userId;

	@Test(dataProvider = "UserInfo")
	public void createUser(User user) {

		Response response = res.body(user)
					.when().post("/users")
					.then().log().all().extract().response();
		verifyStatusCode(response, 201);
		verifyResponseField(response, "name", user.getName());
		verifyResponseField(response, "job", user.getJob());
	}

	@BeforeMethod
	public void createRestSpecification() {
		res = given().log().all().spec(requestSpecification());
	}

	@DataProvider(name = "UserInfo")
	public Object[][] dataProvider() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File JSONUserCreation = new File(
					System.getProperty("user.dir") + "//src//test//resources//JSON-Files//CreateUser.json");
			List<User> userList = objectMapper.readValue(JSONUserCreation,
					objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

			return userList.stream()
							.map(user -> new Object[] {user})
							.toArray(Object[][]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Object[0][0];

	}

	/************* General Functions **************/
	private void verifyStatusCode(Response response, int statusCode) {
		int actualStatusCode = response.getStatusCode();
		Assert.assertEquals(actualStatusCode, statusCode);
	}

	private void verifyResponseField(Response response, String responseField, String expectedValue) {
		String actualFieldValue = getJsonPath(response, responseField);
		Assert.assertEquals(actualFieldValue, expectedValue);
	}

}
