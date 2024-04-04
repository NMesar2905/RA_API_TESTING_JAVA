package com.exercise.two.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/exercise/two/cucumber/features",
				plugin = "json:target/jsonReports/cucumber-report.json",
				glue = "com.exercise.two.cucumber.stepdefinitions")
public class TestRunner {

}
