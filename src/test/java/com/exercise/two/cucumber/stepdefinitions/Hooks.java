package com.exercise.two.cucumber.stepdefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@UpdateUser")
	public void beforeScenario() throws IOException {
		//write a code that will give you place id
		
		StepDefinitions m = new StepDefinitions();
		if(m.userId==null) {
			m.the_and_for_user_payload("Andrés", "Mechanic");
			m.user_calls_with_a_request("APIPath", "POST");
		}

	}
	
}
