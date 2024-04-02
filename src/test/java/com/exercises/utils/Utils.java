package com.exercises.utils;


import java.io.FileOutputStream;

import java.io.PrintStream;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;

	public RequestSpecification requestSpecification(){
		
		if(req==null) {
			try {
				PrintStream log = new PrintStream(new FileOutputStream("loggin.txt"));
				req = new RequestSpecBuilder().setBaseUri("https://reqres.in/api")
						.addFilter(RequestLoggingFilter.logRequestTo(log))
						.addFilter(ResponseLoggingFilter.logResponseTo(log))
						.setContentType(ContentType.JSON).build();

				return req;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return req;
	}
	
	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}

}
