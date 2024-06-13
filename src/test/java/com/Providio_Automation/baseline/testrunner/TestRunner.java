package com.Providio_Automation.baseline.testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features={"src/test/resources/Feature"},
		glue = { "com/Providio_Automation/baseline/steps"  ,"com/Providio_Automation/baseline/hooks" },
		tags= "@Verify_random_footer_links_navigation",
	
		plugin={"pretty",
				"json:target/cucumber-reports/Cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:test-output1"
				},
		monochrome=true,
		dryRun = false
		)
public class TestRunner {

}
