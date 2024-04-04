package com.exercises.utils;


import java.io.FileOutputStream;

import java.io.PrintStream;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;
	
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	
	/*************** Reporting Functions ***************/
	public RequestSpecification requestSpecification(){
		
		if(req==null) {
			try {
				String logName = DateUtils.getTimeStamp() + ".txt";
				
				PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"//target//Logs//log_"+logName));
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
	
	/*************** Get value of a JSON Field ***************/
	public String getJsonPath(Response response, String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
	}
	
	/*************** Reporting Functions ***************/
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}
	
	public void reportInfo(String reportString) {
		logger.log(Status.INFO, reportString);
	}
	
	/*************** Reporting Functions ***************/
	public void accesAPILog() {
		reportInfo("Opening Reqres API");
		
	}
}
