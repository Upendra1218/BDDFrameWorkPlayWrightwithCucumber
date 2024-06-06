package com.Providio_Automation.baseline.pageobjects;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.managers.FileReaderManager;
import com.Providio_Automation.baseline.utils.CommonUtils;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;
import com.microsoft.playwright.Locator;

/**
 * @author RafterOne
 */
public class CheckoutPage {

	/**
	 * Method to fill all the details under Shipping Method on Checkout Page
	 */
	public void enterShippingDetails() {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("First Name", Constant.PAGE));
		Constant.shippingCharges = PlaywrightUtils.getText(PlaywrightUtils.
				getElement("Shipping Charges", Constant.PAGE)).replace("$", "");
		String fName = "FN"+RandomStringUtils.random(5, true, false).toUpperCase();
		String lName = "LN"+RandomStringUtils.random(5, true, false).toUpperCase();
		Constant.recordId = fName + " " + lName;
		PlaywrightUtils.setValueUsingKeyboard(fName, PlaywrightUtils.getElement("First Name", Constant.PAGE));
		PlaywrightUtils.setValueUsingKeyboard(lName, PlaywrightUtils.getElement("Last Name", Constant.PAGE));
		String countryValue = FileReaderManager.getInstance().getConfigReader().getCountry();
		if(!countryValue.equals("United States")) {
			List<Locator> options =  Constant.PAGE.locator(Constant.locatorsMap.get("country_options")).all();
			int index = -1;
			for(int i=0; i<options.size(); i++) {
				if(options.get(i).textContent().contains(countryValue)) {
					index = i + 1;
					break;
				}
			}
			PlaywrightUtils.click(PlaywrightUtils.getElement("Country", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			for(int j=1; j<index; j++) {
				PlaywrightUtils.keyPress("ArrowDown", Constant.PAGE);
			}
		}
		PlaywrightUtils.keyPress("Enter", Constant.PAGE);
		List<Locator> options1 =  Constant.PAGE.locator(Constant.locatorsMap.get("state_options")).all();
		int index1 = -1;
		for(int i=0; i<options1.size(); i++) {
			if(options1.get(i).textContent().contains(
					FileReaderManager.getInstance().getConfigReader().getState())) {
				index1 = i + 1;
				break;
			}
		}
		PlaywrightUtils.click(PlaywrightUtils.getElement("State", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		for(int j=1; j<index1; j++) {
			PlaywrightUtils.keyPress("ArrowDown", Constant.PAGE);
		}
		PlaywrightUtils.keyPress("Enter", Constant.PAGE);
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getAddress1(), 
				PlaywrightUtils.getElement("Address 1", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getAddress2(), 
				PlaywrightUtils.getElement("Address 2", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getCity(), 
				PlaywrightUtils.getElement("City", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getZipCode(), 
				PlaywrightUtils.getElement("Zip Code", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getPhoneNumber(), 
				PlaywrightUtils.getElement("Phone Number", Constant.PAGE));
	}
	
	/**
	 * Method to search and fill all the details under Shipping Method on Checkout Page
	 */
	public void searchAndEnterShippingDetails() {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("First Name", Constant.PAGE));
		Constant.shippingCharges = PlaywrightUtils.getText(PlaywrightUtils.
				getElement("Shipping Charges", Constant.PAGE)).replace("$", "");
		String fName = "FN"+RandomStringUtils.random(5, true, false).toUpperCase();
		String lName = "LN"+RandomStringUtils.random(5, true, false).toUpperCase();
		Constant.recordId = fName + " " + lName;
		PlaywrightUtils.setValueUsingKeyboard(fName, PlaywrightUtils.getElement("First Name", Constant.PAGE));
		PlaywrightUtils.setValueUsingKeyboard(lName, PlaywrightUtils.getElement("Last Name", Constant.PAGE));
		PlaywrightUtils.scrollToElement(PlaywrightUtils.getElement("Address 1", Constant.PAGE));
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getAddressSearchValue(), 
				PlaywrightUtils.getElement("Address 1", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.click(CommonUtils.getSpanTagWebElementByExactText
				(FileReaderManager.getInstance().getConfigReader().getAddressSelectValue(), Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.scrollToElement(PlaywrightUtils.getElement("Phone Number", Constant.PAGE));
		PlaywrightUtils.setValueUsingKeyboard(FileReaderManager.getInstance().getConfigReader().getPhoneNumber(), 
				PlaywrightUtils.getElement("Phone Number", Constant.PAGE));
	}
}
