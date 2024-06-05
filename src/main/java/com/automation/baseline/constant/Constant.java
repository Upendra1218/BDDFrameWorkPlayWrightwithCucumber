package com.automation.baseline.constant;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import io.cucumber.java.Scenario;

/**
 * @Author: RafterOne QA
 */
public class Constant {
	
	private Constant() {}
	
	/** 
	 * Constant having Playwright Reference
	 */
	public static Playwright PLAYWRIGHT;
	
	/** 
	 * Constant having BrowserContext Reference
	 */
	public static BrowserContext BROWSERCONTEXT;
	
	/** 
	 * Constant having Page Reference
	 */
	public static Page PAGE;
	
	/** 
	 * Constant having FrameLocator Reference
	 */
	public static FrameLocator FRAME;
	
	/** 
	 * Constant having configuration properties file path
	 */
	public static String ConfigPropertiesFilePath = "src/test/resources/TestData/config.properties";
	
	/** 
	 * Constant having TestData excel file path
	 */
	public static String TestDataExcelFilePath = "src/test/resources/TestData/TestData.xlsx";
	
	/**
	 * Constant having configuration properties json file path
	 */
	public static String JsonData = "src/test/resources/TestData/Data.json";
	
	/** 
	 * Constant having Locators properties file path
	 */
	public static String LocatorsJSONFilePath = "src/test/resources/Locators/locators.properties";
	
	/** 
	 * Constant Map having Locators keys and values
	 */
	public static HashMap<String, String> locatorsMap = new HashMap<>();
	
	/** 
	 * Constant having Base URL used after Trimming RecordID from current URL
	 */
	public static String baseURL;
	
	/** 
	 * Constant used to store runtime Records
	 */
	public static String recordId, giftVoucherAmount, shippingCharges;
	
	/**
	 * Constant used to Store Values in Map of Maps for Comparison
	 */
	public static Map<String, Map<String, String>> comparisonMap = new HashMap<String, Map<String, String>>();
	
	/**
	 * Constant used to Store API Response
	 */
	public static List<Map<String, Object>> recordsFromAPI;
	
	/** 
	 * Constant used to store format used for pricing
	 */
	public static final DecimalFormat decfor = new DecimalFormat("0.00");
	
	/** 
	 * Constant having expected field values in Map
	 */
	public static HashMap<String, String> expectedValuesMap = new HashMap<>();

	/** 
	 * Constant having expected and actual value to add in report
	 */
	public static String actualResult, expectedResult, result;
	
	/** 
	 * Constant having expected and actual value to add in report
	 */
	public static List<String> resultList = new ArrayList<>();
	
	/**
	 * Constant having API Values in JSON Object
	 */
	public static JSONObject recordsJSON;
	
	/**
	 * Constant having API Values in JSON Array
	 */
	public static JSONArray recordsJSONArray;
	
	/**
	 * Constant used to store Execution Environment
	 */
	public static String env;
	
	/**
	 * Constant used to Product Store Prices
	 */
	public static Map<String, Map<String, Object>> productSpecs = new HashMap<String, Map<String, Object>>();
	
	/**
	 * Constant used to Email
	 */
	public static String email;
	
	/**
	 * Flag Constant used to runtime flows
	 */
	public static boolean giftVoucherApplied;
	
	/**
	 * Constant having current Scenario Name
	 */
	public static Scenario currentScenario;
	
	/**
	 * Constant having Products after Reading from Products.json
	 */
	public static org.json.simple.JSONObject productsJsonObj;
	
	/** 
	 * Constant used to store runtime Records
	 */
	public static String endUserName ,accountName, currencyISOCode, cartName, accountCountry, accountStreet, 
		accountCity, accountPostalCode, accountState, accountFullCountryName, accountFullStateName, flowName, 
		discount, quoteNumber;
	
	/**
	 * Constant used to Product Store Prices
	 */
	public static Map<String, Map<String, Object>> cpqQuoteFields = new HashMap<String, Map<String, Object>>();
	
	/**
	 * Constant used to Product Store Prices
	 */
	public static Map<String, Map<String, Object>> contractFields = new HashMap<String, Map<String, Object>>();
	
	/**
	 * Constants used to store prices of Products based on tiers
	 */
	public static Map<String, Map<String, String>> tierPricing = new HashMap<String, Map<String, String>>();
	
	/**
	 * Constants used to store prices of Products based on tiers
	 */
	public static Map<String, Map<String, String>> oneDayPricing = new HashMap<String, Map<String, String>>();
	
	/**
	 * Constant used to store CPQ Quote Field Values
	 */
	public static String cpqRegularAmount = "0", cpqNetAmount = "0", cpqPartnerDiscount = "0", 
			cpqPartnerDiscAmount = "0",	cpqTotalDiscount = "0", cpqTotalDiscountAmount = "0", cpqMRR = "0", 
			cpqARR = "0", cpqACV = "0", cpqNetNewACV = "0", cpqTCVOneTime = "0", cpqTCVRecurring = "0", 
			cpqTCV = "0";
	
	/**
	 * Constant used to store Contract Fields
	 */
	public static String contractAccountId, contractBillTo, contractSoldTo, contract, contractEndUser,
						 contractDealId, contractRenewalOpp, contractCurrency, contractPriceBookId,
						 contractStartDate, contractEndDate, contractShipTo, contractTerm;
}
