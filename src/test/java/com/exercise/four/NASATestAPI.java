package com.exercise.four;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.exercises.utils.Utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NASATestAPI extends Utils{

	@Test
	public void EPICApiTest() {
		RestAssured.baseURI = "https://api.nasa.gov/EPIC/archive/natural";
		Response response = given().log().all().pathParams("date", "2019/05/30")
								.pathParams("type","png")
								.pathParams("file","epic_1b_20190530011359.png")
								.queryParam("api_key", getNasaValue("api_key"))
							.when().get("/{date}/{type}/{file}")
							.then().log().all().extract().response();
		Assert.assertEquals(response.getStatusCode(), 200);
		if(response.getStatusCode()==200) {
			byte[] content = response.body().asByteArray();
			
			try(FileOutputStream file = new FileOutputStream("NASA.png")){
				file.write(content);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static String getNasaValue(String key) {
		
		Properties prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(System.getProperty("user.dir") +"\\src\\test\\resources\\PropertiesFile\\NASA.properties");
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	
}
