package com.Providio_Automation.baseline.hooks;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.dataprovider.LocatorsFileReader;
import com.Providio_Automation.baseline.managers.FileReaderManager;
import com.Providio_Automation.baseline.managers.PlaywrightManager;
import com.Providio_Automation.baseline.utils.ExcelUtils;
import com.Providio_Automation.baseline.utils.FeatureFileReader;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	private static boolean isBrowserInitialized = false;
	private static int totalScenarios = 0;
	private static int completedScenarios = 0;


	@Before
	public void beforeScenario(Scenario scenario) throws IOException, ParseException {
		Constant.currentScenario = scenario;
		LocatorsFileReader.readLocatorProperties();
		ExcelUtils.openStream();

		if (!isBrowserInitialized) {
			// Initialize the browser and pass the URL here
			new PlaywrightManager();
			Constant.PAGE = PlaywrightManager.intializePlaywright();
			PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getStoreUrl(), Constant.PAGE);
			ExtentCucumberAdapter.addTestStepLog("Navigated to '" +FileReaderManager.getInstance().getConfigReader().getStoreUrl()+ "' URL successfully");	
			isBrowserInitialized = true;
			// Dynamically get the total number of scenarios
			totalScenarios = FeatureFileReader.countScenariosWithTag(
					FileReaderManager.getInstance().getConfigReader().getFeatureFileLocation(), 
					FileReaderManager.getInstance().getConfigReader().getTagName()); // Update the path as needed
		}
		System.out.println("Total No of scenatios: "+totalScenarios);
	}

	@After
	public void afterScenario(Scenario scenario) throws IOException {
		try {
			Thread.sleep(2000); // 5000 milliseconds = 5 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			if (scenario.isFailed()) {
				scenario.log("Test Case Failed");
				//adding screen shot to the Report
				PlaywrightUtils.addScreenshotToReport();
				//Basic report
				byte[] screenshot = Constant.PAGE.screenshot();
				scenario.attach(screenshot, "image/png", screenshotName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		completedScenarios++;
		System.out.println("Completed No of scenatios: "+completedScenarios);

		if (completedScenarios == totalScenarios) {
			System.out.println("coming here");
			// Close the browser after all scenarios have completed
			// e.g., browser.close(); 
			PlaywrightManager.closePlaywright();
			//EmailUtils.sendreport();
		}
	}

}
