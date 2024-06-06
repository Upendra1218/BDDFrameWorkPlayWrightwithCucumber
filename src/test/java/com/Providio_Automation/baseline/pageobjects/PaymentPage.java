package com.Providio_Automation.baseline.pageobjects;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.managers.FileReaderManager;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;

/**
 * @author RafterOne
 *
 */
public class PaymentPage {

	/**
	 * Method to fill all the Credit Card fields on Payment Page
	 */
	public void enterCreditCardDetails() {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Credit Card Option", Constant.PAGE));
		PlaywrightUtils.scrollVertically(Constant.PAGE);
		PlaywrightUtils.scrollVertically(Constant.PAGE);
		PlaywrightUtils.click(PlaywrightUtils.getElement("Credit Card Option", Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(FileReaderManager.getInstance().getConfigReader().getCardNumber(), 
				PlaywrightUtils.getElement("Credit Card Number", PlaywrightUtils.getFrame("Credit Card Number iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(FileReaderManager.getInstance().getConfigReader().getCardExpireDateMonth(), 
				PlaywrightUtils.getElement("Expiry", PlaywrightUtils.getFrame("Credit Card Number iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(FileReaderManager.getInstance().getConfigReader().getCardCVV(), 
				PlaywrightUtils.getElement("CVV", PlaywrightUtils.getFrame("Credit Card Number iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(FileReaderManager.getInstance().getConfigReader().getZipCode(), 
				PlaywrightUtils.getElement("Zip Code Credit Card", PlaywrightUtils.getFrame("Credit Card Number iFrame", Constant.PAGE)));
	}
	
	/**
	 * Method to fill all the Credit Card fields on Payment Page
	 */
	public void enterCreditCardDetailsWithGivenValues(String name, String cardNumber, String cvv, String expiry) {
		PlaywrightUtils.scrollVertically(Constant.PAGE);
		PlaywrightUtils.scrollVertically(Constant.PAGE);
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Name on Card", PlaywrightUtils.getFrame("Name on Card iFrame", Constant.PAGE)));
		PlaywrightUtils.setValue(name, PlaywrightUtils.getElement("Name on Card", PlaywrightUtils.getFrame("Name on Card iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(cardNumber, PlaywrightUtils.getElement("Card Number", PlaywrightUtils.getFrame("Card Number iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(cvv, PlaywrightUtils.getElement("CVV", PlaywrightUtils.getFrame("CVV iFrame", Constant.PAGE)));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.setValue(expiry, PlaywrightUtils.getElement("Expiration Date", PlaywrightUtils.getFrame("Expiration Date iFrame", Constant.PAGE)));
	}
}
