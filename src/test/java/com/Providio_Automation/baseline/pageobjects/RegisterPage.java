package com.Providio_Automation.baseline.pageobjects;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;

public class RegisterPage {
	
	/**
	 * method to fill the Register fields
	 */
	
	public void enterRegiesterformValueswithgivenValues(String fname, String lname, String phone, String email, String comformemil, String pwd, String conformPwd) {
		
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Register FirstName", Constant.PAGE));
		
		PlaywrightUtils.setValue(fname, PlaywrightUtils.getElement("Register FirstName", Constant.PAGE));
		PlaywrightUtils.setValue(lname, PlaywrightUtils.getElement("Register LastName", Constant.PAGE));
		PlaywrightUtils.setValue(phone, PlaywrightUtils.getElement("Register Phone", Constant.PAGE));
		PlaywrightUtils.setValue(email, PlaywrightUtils.getElement("Register Email", Constant.PAGE));
		PlaywrightUtils.setValue(comformemil, PlaywrightUtils.getElement("Register Conform Email", Constant.PAGE));
		PlaywrightUtils.setValue(pwd, PlaywrightUtils.getElement("Register Password", Constant.PAGE));
		PlaywrightUtils.setValue(conformPwd, PlaywrightUtils.getElement("Register Conform Password", Constant.PAGE));
	}

}
