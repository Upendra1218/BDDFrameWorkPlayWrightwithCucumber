package com.automation.baseline.testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features={"src/test/resources/Feature"},
		glue = { "com/automation/baseline/steps"  ,"com/automation/baseline/hooks" },
		tags= "@CreateAccountonStoreFront",
	
		plugin={"pretty",
				"json:target/cucumber-reports/Cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:test-output1"
				},
		monochrome=true,
		dryRun = false
		)
public class TestRunner {

}
