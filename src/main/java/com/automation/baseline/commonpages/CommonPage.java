package com.automation.baseline.commonpages;

import org.junit.Assert;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.exception.CustomException;
import com.automation.baseline.managers.FileReaderManager;
import com.automation.baseline.utils.APIUtils;
import com.automation.baseline.utils.CommonUtils;
import com.automation.baseline.utils.PlaywrightUtils;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page.NavigateOptions;

/**
 * @Author: RafterOne QA
 */
public class CommonPage {

	/**
	 * Method for increasing the Quantity
	 */
	public static void increaseQuantityOnCart() {
		try {
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Quantity Cart", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Increase Quantity Cart", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Increase Quantity Cart", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			Constant.result = "Quantity increased by 1 on Cart";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for decreasing the quantity
	 */
	public static void decreaseQuantityOnCart() {
		try {
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Quantity Cart", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Decrease Quantity Cart", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Decrease Quantity Cart", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			Constant.result = "Quantity decreased by 1 on Cart";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for Selecting Category based on Category Name
	 * @param category - Category Name
	 */
	public static void selectCategory(String category) {
		try {
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(CommonUtils.findCategoryByText(category, Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.findCategoryByText(category, Constant.PAGE));
			Constant.result = "'" + category + "' Category selected successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for searching Contact in Salesforce
	 * @param name - Contact Name
	 */
	public static void searchContactInSalesforce(String name) {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Box Salesforce", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.click(PlaywrightUtils.getElement("Search Box Salesforce", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Box Input Salesforce", Constant.PAGE));
			PlaywrightUtils.clearAndSetValueUsingKeyboard(name, PlaywrightUtils.getElement("Search Box Input Salesforce", Constant.PAGE));
			PlaywrightUtils.keyPress("Enter", Constant.PAGE);
			selectContactInSalesforce(name);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for Selecting Contact
	 * @param name - Contact Name
	 */
	public static void selectContactInSalesforce(String name) {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Contact Option", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Contact Option", Constant.PAGE));			
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(CommonUtils.getEntityByName(name, Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getEntityByName(name, Constant.PAGE));
			Constant.result = "'" + name + "' Contact selected successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	/**
	 * Method for Salesforce Login
	 */
	public static void salesforcelogin() {
		try {
			Constant.PAGE.navigate(FileReaderManager.getInstance().getConfigReader().getSfdcUrl());
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Username Salesforce", Constant.PAGE));
			String Username = FileReaderManager.getInstance().getConfigReader().getSfdcUsername();
			String Password = FileReaderManager.getInstance().getConfigReader().getSfdcPassword();
			PlaywrightUtils.clearAndSetValueUsingKeyboard(Username, PlaywrightUtils.getElement("Username Salesforce", Constant.PAGE));
			PlaywrightUtils.clearAndSetValueUsingKeyboard(Password, PlaywrightUtils.getElement("Password Salesforce", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Login Salesforce", Constant.PAGE));
			Constant.result = "Logged In to Salesforce sucessfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method to switch to an App using App Launcher
	 * @param appName - App Name 
	 */
	public static void switchToApp(String appName) {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("App Name", Constant.PAGE));
			if(!PlaywrightUtils.getText(PlaywrightUtils.getElement("App Name", Constant.PAGE)).equals(appName)) {
				PlaywrightUtils.click(PlaywrightUtils.getElement("Nine Dots", Constant.PAGE));
				PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Apps And Item", Constant.PAGE));
				PlaywrightUtils.setValue(appName, PlaywrightUtils.getElement("Search Apps And Item", Constant.PAGE));
				PlaywrightUtils.waitForAnElement(CommonUtils.getSearchedAppLocator(appName));
				PlaywrightUtils.click(CommonUtils.getSearchedAppLocator(appName));
				PlaywrightUtils.waitForMoreSec(2);
				Constant.result = "Switched to '" +  appName + "' App successfully";
			}
			else {
				Constant.result = "Already on '" + appName + "' App. No need to switch.";
			}
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Switching to Salesforce User
	 * @param User's Name- Salesforce User whom do you want to switch
	 */
	public static void switchToUser(String user){
		try {
			PlaywrightUtils.click(PlaywrightUtils.getElement("Gear Icon", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Setup", Constant.PAGE));
			Constant.PAGE = PlaywrightUtils.clickThenSwitchToTab("Setup", "SetupOneHome", Constant.PAGE);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Setup", Constant.PAGE));
			PlaywrightUtils.setValue(user, PlaywrightUtils.getElement("Search Setup", Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getDivTagWebElementByExactText(user, Constant.PAGE));
			FrameLocator scrollingFrame = PlaywrightUtils.getFrame("Scrolling Frame", Constant.PAGE);
			PlaywrightUtils.click(PlaywrightUtils.getElement("Login User", scrollingFrame));
			PlaywrightUtils.waitForMoreSec(3);
			Constant.result = "Switched to '" +  user + "' User successfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Switching to Salesforce User
	 * @param User's Name- Salesforce User whom do you want to switch
	 */
	public static void switchToUserByNormalSearch(String user){
		try {
			PlaywrightUtils.waitForPageToLoad(Constant.PAGE);
			PlaywrightUtils.waitForMoreSec(2);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Box Salesforce", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Search Box Salesforce", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search Box Input Salesforce", Constant.PAGE));
			PlaywrightUtils.setValue(user, PlaywrightUtils.getElement("Search Box Input Salesforce", Constant.PAGE));
			PlaywrightUtils.keyPress("Enter", Constant.PAGE);
			PlaywrightUtils.waitForAnElement(CommonUtils.getSpanTagWebElementByExactText("People", Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getSpanTagWebElementByExactText("People", Constant.PAGE));
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(CommonUtils.getAnchorTagWebElementByExactText(user, Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getAnchorTagWebElementByExactText(user, Constant.PAGE));
			PlaywrightUtils.waitForAnElement(CommonUtils.getDivTagWebElementByExactText("User Detail", Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getDivTagWebElementByExactText("User Detail", Constant.PAGE));
			FrameLocator scrollingFrame = PlaywrightUtils.getFrame("Scrolling Frame", Constant.PAGE);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Login User", scrollingFrame));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Login User", scrollingFrame));
			PlaywrightUtils.waitForMoreSec(3);
			PlaywrightUtils.waitForAnElement(CommonUtils.getUserLogoutButton(user, Constant.PAGE));
			Constant.result = "Switched to '" + user + "' User Successfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for Switching to Salesforce User
	 * @param User's Name - Salesforce User whom do you want to switch
	 */
	public static void switchToUserByAPI(String user){
		try {
			PlaywrightUtils.waitForPageToLoad(Constant.PAGE);
			PlaywrightUtils.waitForMoreSec(2);
			String Id = APIUtils.getSoqlResult(String.format("Select Id from User where Name = '%s'", user), "Id");
			PlaywrightUtils.navigateToURL(Constant.baseURL+"/lightning/setup/ManageUsers/page?address=%2F"+Id+"%3Fnoredirect%3D1%26isUserEntityOverride%3D1", Constant.PAGE);
			FrameLocator scrollingFrame = PlaywrightUtils.getFrame("Scrolling Frame", Constant.PAGE);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Login User", scrollingFrame));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Login User", scrollingFrame));
			PlaywrightUtils.waitForMoreSec(3);
			PlaywrightUtils.waitForAnElement(CommonUtils.getUserLogoutButton(user, Constant.PAGE));
			Constant.result = "Switched to '" + user + "' User Successfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Store Login
	 */
	public static void storelogin() {
		try {
			NavigateOptions options = new NavigateOptions();
			options.timeout = (double) 300000;
			Constant.PAGE.navigate(FileReaderManager.getInstance().getConfigReader().getStoreUrl(), options);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Username Store", Constant.PAGE));
			String Username = FileReaderManager.getInstance().getConfigReader().getStoreUsername();
			String Password = FileReaderManager.getInstance().getConfigReader().getStorePassword();
			PlaywrightUtils.clearAndSetValueUsingKeyboard(Username, PlaywrightUtils.getElement("Username Store", Constant.PAGE));
			PlaywrightUtils.clearAndSetValueUsingKeyboard(Password, PlaywrightUtils.getElement("Password Store", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Login Store", Constant.PAGE));
			Constant.result = "Logged In to Store successfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for increasing the quantity
	 */
	public static void increaseQuantityOnPDP() {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Quantity PDP", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Increase Quantity PDP", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Increase Quantity PDP", Constant.PAGE));
			Constant.result = "Quantity increased by 1 on PDP";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for decreasing the quantity
	 */
	public static void decreaseQuantityOnPDP() {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Quantity PDP", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Decrease Quantity PDP", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Decrease Quantity PDP", Constant.PAGE));
			Constant.result = "Quantity decreased by 1 on PDP";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for Product Verify
	 * @param product - Product Name
	 */
	public static void verifyProduct(String product) {
		try {
			PlaywrightUtils.waitForAnElement(CommonUtils.verifyProductOnDetailPage(product, Constant.PAGE));
			Assert.assertTrue(CommonUtils.verifyProductOnDetailPage(product, Constant.PAGE).isVisible());
			Constant.result = "'" + product + "' Product displayed successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	/**
	 * Method selecting the Product by Given text
	 * @param product - Product Name
	 */
	public static void productSelect(String product) {
		try {
			PlaywrightUtils.waitForAnElement(CommonUtils.findProductByText(product, Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.findProductByText(product, Constant.PAGE));
			Constant.result = "'" + product + "' Product selected successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}

	/**
	 * Method for Search Product by Name
	 * @param product - Product Name
	 */
	public static void searchProduct(String product) {
		try {
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Search", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Search", Constant.PAGE));
			PlaywrightUtils.clearAndSetValueUsingKeyboard(product, PlaywrightUtils.getElement("Search", Constant.PAGE));
			PlaywrightUtils.keyPress("Enter", Constant.PAGE);
			PlaywrightUtils.waitForMoreSec(3);
			Constant.result = "'" + product + "' Product searched successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
}
