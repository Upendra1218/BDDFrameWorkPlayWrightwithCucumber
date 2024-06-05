package com.automation.baseline.hooks;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.dataprovider.LocatorsFileReader;
import com.automation.baseline.managers.PlaywrightManager;
import com.automation.baseline.utils.EmailUtils;
import com.automation.baseline.utils.ExcelUtils;
import com.automation.baseline.utils.PlaywrightUtils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void beforeScenario(Scenario scenario) throws IOException, ParseException {
		Constant.currentScenario = scenario;
		LocatorsFileReader.readLocatorProperties();
		ExcelUtils.openStream();
		new PlaywrightManager();
		Constant.PAGE = PlaywrightManager.intializePlaywright();
	}

	@After
	public void afterScenario(Scenario scenario) throws IOException {
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
		PlaywrightManager.closePlaywright();
		EmailUtils.sendreport();
	}
}
