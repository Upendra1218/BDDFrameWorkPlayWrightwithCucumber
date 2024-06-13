package com.Providio_Automation.baseline.pageobjects;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class PageValidation {
	
	/**
	 * Method for Compare the current page with  Home page and redirect to the Home page
	 * @param pageName
	 */
	public static void home_validation(String pageName) {
		if(PlaywrightUtils.no_of_elements_present_on_page(PlaywrightUtils.getElement(pageName, Constant.PAGE))) {
			System.out.println("user already on the home page");
			ExtentCucumberAdapter.addTestStepLog("Already '" +pageName+ "' is loaded Succesfully");
		}else {
			System.out.println("user not on the desired page");
			PlaywrightUtils.click(PlaywrightUtils.getElement("homepage logo", Constant.PAGE));
			PlaywrightUtils.waitForMoreSec(2);
			ExtentCucumberAdapter.addTestStepLog("Redirected to '" +pageName+ "' Form current page successfully");
		}
	}



}
