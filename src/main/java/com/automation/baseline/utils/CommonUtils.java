package com.automation.baseline.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.exception.CustomException;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * @Author: RafterOne QA
 */
public class CommonUtils {
	
	/**
     * Method to get Products List from Products.json file
     * @return - List of Products
     */
    public static List<String> getProducts(){
        String name = "Name";
        List<String> prodList = new ArrayList<>(); 
        for(Object productObj : (JSONArray)Constant.productsJsonObj.get(Constant.flowName)) {
            JSONObject productJSONObj = (JSONObject) productObj;
            prodList.add((String) productJSONObj.get(name));
        }
        return prodList;
    }
    
    /**
     * Method to get Quantity of given Product from Products.json
     * @param productName - Name of the Products
     * @return - quantity of the given product
     */
    public static String getProductQuantity(String quoteLineNumber){
        String name = "QuoteLineItemNumber";
        String quantity = "Quantity";
        for(Object productObj : (JSONArray)Constant.productsJsonObj.get(Constant.flowName)) {
            JSONObject productJSONObj = (JSONObject) productObj;
            if(quoteLineNumber.equals((String) productJSONObj.get(name)))
                if(((String) productJSONObj.get(quantity)).matches("[0-9]+") || Integer.parseInt((String) productJSONObj.get(quantity)) > 0)
                    return(String) productJSONObj.get(quantity);
                else
                    throw new CustomException("You must define Quantity as a valid digit in Products.json for '" + quoteLineNumber +"' QuoteLine Item");
        }
        throw new CustomException("For '"+ quoteLineNumber +"' QuoteLine Item Quantity is not defined in Products.json file");
    }
    
    /**
     * Method to get Quantity of given Product from Products.json
     * @param productName - Name of the Products
     * @return - quantity of the given product
     */
    public static String getProductTerm(String quoteLineNumber){
        String name = "QuoteLineItemNumber";
        String term = "Term";
        for(Object productObj : (JSONArray)Constant.productsJsonObj.get(Constant.flowName)) {
            JSONObject productJSONObj = (JSONObject) productObj;
            if(quoteLineNumber.equals((String) productJSONObj.get(name)))
                if(((String) productJSONObj.get(term)).matches("[0-9]+") || ((String) productJSONObj.get(term)).equals("NA"))
                    return(String) productJSONObj.get(term);
                else
                    throw new CustomException("You must define Term as a digit or 'NA' in Products.json for '" + quoteLineNumber +"' QuoteLine Item");
        }
        throw new CustomException("For '"+ quoteLineNumber +"' QuoteLine Item Term is not defined in Products.json file");
    }
    
    /**
     * Method to get Trade Discount of given Product from Products.json
     * @param productName - Name of the Products
     * @return - TradeDiscount of the given product
     */
    public static String getProductTradeDiscount(String quoteLineNumber){
        String name = "QuoteLineItemNumber";
        String tradeDiscount = "LineLevelTD";
        for(Object productObj : (JSONArray)Constant.productsJsonObj.get(Constant.flowName)) {
            JSONObject productJSONObj = (JSONObject) productObj;
            if(quoteLineNumber.equals((String) productJSONObj.get(name)))
                if(((String) productJSONObj.get(tradeDiscount)).matches("[0-9]+") || ((String) productJSONObj.get(tradeDiscount)).equals("NA"))
                    return(String) productJSONObj.get(tradeDiscount);
                else
                    throw new CustomException("You must define LineLevelTD as a digit or 'NA' in Products.json for '" + quoteLineNumber +"' QuoteLine Item");
        }
        throw new CustomException("For '"+ quoteLineNumber +"' QuoteLine Item LineLevelTD is not defined in Products.json file");
    }
    
    /**
     * Method to get difference b/w Dates(in Months)
     * @param earlier - Earlier Date in YYYY-MM-DD
     * @param later - Later Date in YYYY-MM-DD
     * @return - Difference in Months and Days(Months#Days)
     */
    public static String getDifferenceBetweenDates(String earlier, String later) {

        later = CommonUtils.addDays(later, 1);
        LocalDate earlierFullDate = LocalDate.parse(earlier);
        LocalDate laterFullDate = LocalDate.parse(later);
        Period d = Period.between(earlierFullDate, laterFullDate);
        if(d.getYears() <= 0 && d.getMonths() <= 0 && d.getDays() <= 0) 
            throw new CustomException("Invalid Dates(Required format: 'yyyy-MM-dd') '" + earlier + "' and '" + later + "'");
        int months = 0;
        if(d.getYears() > 0)
            months = d.getYears() * 12 + d.getMonths();
        else
            months = d.getMonths();
        return months+"#"+d.getDays();
    }

	/**
	 * Method for Returning Searched App Locator by Given App Name 
	 * @param value - App Name
	 * @return - Element Locator
	 */
	public static Locator getSearchedAppLocator(String value) {
		return Constant.PAGE.locator(String.format("//a[@data-label='%s']//lightning-formatted-rich-text/span", value));
	}
	
	/**
	 * Method for Opening Salesforce Object in Browser by given Record Id
	 * @param recordId - Record Id from Salesforce
	 * @param page - Page on which you are navigating to URL
	 */
	public static void getRecordInSalesforce(String recordId, Page page) {
		try {
			if(Constant.baseURL != null) {
				page.navigate(Constant.baseURL + recordId);
				PlaywrightUtils.waitForPageToLoad(page);
			}
			else {
				throw new CustomException("Base URL not Intialized");
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	/**
	 * Method for Returning Div Tag Locator By Given Text
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getDivTagWebElementByExactText(String value, Page page) {
		return page.locator(String.format("//div[text()='%s']", value));
	}
	
	/**
	 * Method for Returning button for User logout
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getUserLogoutButton(String value, Page page) {
		return page.locator(String.format("//a[text()='Log out as %s']", value));
	}
	
	/**
	 * Method for Returning Div Tag Locator By Given Text from Locator
	 * @param value - Dynamic Text value
	 * @param locator - Locator on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getDivTagWebElementByExactText(String value, Locator locator) {
		return locator.locator(String.format("//div[text()='%s']", value));
	}
	
	/**
	 * Method for Returning Div Tag Locator from FrameLocator By Given Text
	 * @param value - Dynamic Text value
	 * @param frame - FrameLocator in which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getDivTagWebElementByExactText(String value, FrameLocator frame) {
		return frame.locator(String.format("//div[text()='%s']", value));
	}
	
	/**
	 * Method for Returning Locator for Account Option
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getAccountNameForOpportunity(String value, Page page){
		return page.locator(String.format("(//lightning-base-combobox-formatted-text[@title='%s'])[1]", value));
	}
	
	/**
	 * Method to add days in the given date then returns the new date in String
	 * @param date - Date in which Days should be added
	 * @param days - No. of Days to add
	 * @return - New Date
	 */
	public static String addDays(String date, int days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.DAY_OF_MONTH, days);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Method to add months in the given date then returns the new date in String
	 * @param date - Date in which Months should be added
	 * @param days - No. of Months to add
	 * @return - New Date
	 */
	public static String addMonths(String date, int months) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cal.add(Calendar.MONTH, months);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Method for Returning Locator of Field Value for Opportunity And Quote
	 * @param value - Field Name
	 * @param page - Browser Page
	 * @return Element Locator
	 */
	public static Locator getFieldValueOnOpportunityAndQuotePage(String value, Page page){
		return page.locator(String.format("//records-record-layout-block//span[text()='%s']/../..//a//span", value));
	}
	
	/**
	 * Method for adding key-value in given map
	 * @param key - Key which should be added in map
	 * @param value - Value which should be added in map
	 * @param mapName = Map Name to add Key & Value
	 */
	public static void addKeyValueInMap(String key, String value, String mapName) {
		if(Constant.comparisonMap.containsKey(mapName))
			Constant.comparisonMap.get(mapName).put(key, value);
		else {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(key, value);
			Constant.comparisonMap.put(mapName, map);
		}
	}
	
	/**
	 * Method for adding key-value in given map
	 * @param key - Key which should be added in map
	 * @param value - Value which should be added in map
	 * @param mapName = Map Name to add Key & Value
	 * @param map - Map(Map of Maps) in which you want to add map of key value
	 */
	public static void addKeyValueInGivenMap(String key, Object value, String mapName, Map<String ,Map<String, Object>> map) {
		if(map.containsKey(mapName))
			map.get(mapName).put(key, value);
		else {
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put(key, value);
			map.put(mapName, temp);
		}
	}
	
	/**
	 * Method to Compare All Maps stored in ComparisonMap(Map of Maps)
	 * @param condition - Matches or Unmatches
	 */
	public static void compareMaps(String condition) {
		List<String> keys = new ArrayList<String>(Constant.comparisonMap.keySet());
		String mapString = keys.get(0);
		for(int i=1; i<keys.size(); i++) {
			if(condition.equals("mismatches"))
				Assert.assertNotSame(Constant.comparisonMap.get(mapString), Constant.comparisonMap.get(keys.get(i)));
			else
				Assert.assertEquals(Constant.comparisonMap.get(mapString), Constant.comparisonMap.get(keys.get(i)));
		}
	}
	
	/**
	 * Method for Returning Locator for Edit Button for a Field in Salesforce by Given Text
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return Element Locator
	 */
	public static Locator getEditButtonUsingFieldName(String value, Page page) {
		return page.locator(String.format("//span[text()='%s']/../..//button[contains(@title,'Edit')]", value));
	}
	
	/**
	 * Method for Returning Locator for Opening Edit Dropdown for Field
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return Element Locator
	 */
	public static Locator openDropdown(String value, Page page){
		return page.locator(String.format("(//button[contains(@aria-label,'%s')])[1]", value));
	}
	
	/**
	 * Method for Returning Span Tag WebElement By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getSpanTagWebElementByExactText(String value, Page page){
		return page.locator(String.format("//span[text()='%s']", value));
	}
	
	/**
	 * Method for Returning Span Tag WebElement By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getOptionValueWithSpanTagWebElementByExactText(String value, Page page){
		return page.locator(String.format("//lightning-base-combobox-item//span[text()='%s']",value));
	}
	
	/**
	 * Method for Returning Anchor Tag WebElement By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getAnchorTagWebElementByExactText(String value, Page page){
		page.locator(String.format("//a[text()='%s']",value)).all();
		System.out.println(page.locator(String.format("//a[text()='%s']",value)).count());
		return page.locator(String.format("//a[text()='%s']",value));
	}
	
	/**
	 * Method for Returning Anchor Tag WebElement By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getAnchorTagWebElementByExactTextRandomly(String value, Page page){
		return page.locator(String.format("//a[text()='%s']",value));
	}
	
	
	
	/**
	 * Method for Returning Eud User Option by Given Name 
	 * @param value - Dynamic Text value
	 * @param page - Locator on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getEndUserOption(String value, Locator locator){
		return locator.locator(String.format("div[data-label='%s']",value));
	}
	
	/**
	 * Method for Returning Span Tag WebElement By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getSpanTagWebElementByContainsText(String value, Page page){
		return page.locator(String.format("//span[contains(text(),'%s')]",value));
	}
	
	/**
	 * Method for Returning Locator for Opening Edit Dropdown for Field
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getOptionAfterSearch(String value, Page page){
		return page.locator(String.format("(//lightning-base-combobox-formatted-text[@title='%s'])[1]", value));
	}
	
	/**
	 * Method for Returning Field's Value Locator By Given Text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getFieldValueLocator(String value, Page page) {
		return page.locator(String.format("(//span[text()='%s'])[1]/following::lightning-formatted-text[1]", value));
	}
	
	/**
	 * Method for Returning Type of Opportunity by given text 
	 * @param value - Dynamic Text value
	 * @param page - Page on which you are looking for Element
	 * @return - Element Locator
	 */
	public static Locator getTypeOfOpportunity(String value, Page page) {
		return page.locator(String.format("//div[@class='changeRecordTypeOptionRightColumn']/span[text()='%s']/preceding::span[1]", value));
	}
	
	/**
	 * Method for Verifying Field Value
	 * @param value - Expected Value
	 * @param fieldName - Field Name having Actual Value
	 * @param page - Page having the Field
	 */
	public static void verifyFieldValue(String value, String fieldName, Page page) {
		try {
			PlaywrightUtils.waitForAnElement(getFieldValueLocator(fieldName, Constant.PAGE));
			Assert.assertEquals(value, PlaywrightUtils.getText(getFieldValueLocator(fieldName, Constant.PAGE)));
			Constant.result = "Value for '" + fieldName + "' field is '" + value + "' verified successfully";
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method to get SOQL Field Value from Constant
	 * @param fieldName - Field Name you want extract from API Response
	 * @return Object having the value
	 */
	public static Object getSOQLFieldValueFromAPIConstant(String fieldName) {
		if(Constant.recordsFromAPI.get(0).containsKey(fieldName))
			return Constant.recordsFromAPI.get(0).get(fieldName);
		else
			throw new CustomException(fieldName + " Field Value not returned from API");
	}
	
	/**
	 * Method to Set Value on Multiple Element(Input Fields)
	 * @param values - Values Array
	 * @param fieldNames - Field Names Array
	 */
	public static void setValueInMultipleFields(String[] values, String[] fieldNames) {
		try {
			if(values.length != fieldNames.length)
				throw new CustomException("Values and Fields count mismatch");
			for(int i=0; i<values.length; i++) {
				PlaywrightUtils.setValue(values[i].trim(), PlaywrightUtils.getElement(fieldNames[i].trim(), Constant.PAGE));
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method to Select Multiple Dropdowns
	 * @param options - Option Array
	 * @param fieldNames - Dropdown Names Array
	 */
	public static void selectMultipleDropdowns(String[] options, String[] fieldNames) {
		try {
			if(options.length != fieldNames.length)
				throw new CustomException("Options and Fields count mismatch");
			for(int i=0; i<options.length; i++) {
				PlaywrightUtils.selectByLabelFromDropdown(options[i].trim(), PlaywrightUtils.getElement(fieldNames[i].trim(), Constant.PAGE));
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Search and Select from Dropdown
	 * @param value - Value to Select
	 * @param field - Field Name
	 */
	public static void searchAndSelect(String value, String field) {
		try {
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.setValueUsingKeyboard(value, PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.waitForSec();
			PlaywrightUtils.keyPress("Backspace", Constant.PAGE);
			try {
				PlaywrightUtils.click(CommonUtils.getAccountNameForOpportunity(value, Constant.PAGE));			
			} catch (Exception e) {
				PlaywrightUtils.waitForSec();
				PlaywrightUtils.keyPress("Backspace", Constant.PAGE);
				PlaywrightUtils.click(CommonUtils.getAccountNameForOpportunity(value, Constant.PAGE));
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Search and Select Multiple Dropdown
	 * @param values - Values to Select
	 * @param fields - Field Names
	 */
	public static void searchAndSelectMultipleDropdown(String[] values, String[] fields) {
		try {
			if(values.length != fields.length)
				throw new CustomException("Options and Fields count mismatch");
			for(int i=0; i<values.length; i++) {
				searchAndSelect(values[i].trim(), fields[i].trim());
			}
		}catch(Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method to round off float to 2 decimal places
	 * @param number - input number
	 * @return rounded off number
	 */
	public static float roundOffFloatToTwoDecPlace(float number) {
		return Float.parseFloat(new DecimalFormat("0.00").format(number));
	}
	
	/**
	 * Method to round off float to 2 decimal places
	 * @param number - input number
	 * @return rounded off number
	 */
	public static String roundOffFloatToTwoDecPlaceToString(float number) {
		return String.valueOf(new DecimalFormat("0.00").format(number));
	}
	
	/**
	 * Method for Testing Connection with Salesforce through API
	 */
	public static boolean testConnection() {
		
		int statusCode = APIUtils.getStatusCode();
		
		if(statusCode == 200) {
			System.out.println("Connection succesfull with Status Code " + statusCode);
			return true;
		}else {
			System.out.println("Connection unsuccesfull with Status Code " + statusCode);
			return false;
		}
	}
	
	/**
	 * Method for Getting Field Value Salesforce through API by Given Table Fields and there Value
	 * @param fields - Field Array to be used in SOQL Select Clause
	 * @param tableName - Table Name
	 * @param tableField - Field Names to be used in SOQL Where Clause
	 * @param tableValue - Field Values to be used in SOQL Where Clause
	 */
	public static void getSOQLResultsFromAPIByGivenFieldsAndValuesInJSON(String[] fields, String tableName, String[] tableField, String[] tableValue) {
		
		if(tableField.length != tableValue.length)
			throw new CustomException("Table Fields and Table Values Count mismatch");
		else {
			String whereClause = "", fieldClause = "", temp = "";
			for(int i=0; i<tableField.length-1; i++) {
				temp = tableField[i] + " = '" + tableValue[i] + "' and ";
				whereClause += temp;
			}
			whereClause += tableField[tableField.length-1] + " = '" + tableValue[tableField.length-1] + "'";
			for(int j=0; j<fields.length-1; j++) {
				temp = fields[j] + ", ";
				fieldClause += temp;
			}
			fieldClause += fields[fields.length-1];
			
			if(CommonUtils.testConnection()) 
				APIUtils.getSoqlResultInJSON("SELECT "+ fieldClause +" from "+ tableName +" WHERE "+ whereClause);
			else
				throw new CustomException("Connection unsuccesfull with Salesforce");
		}
	}
	
	/**
	 * Method for Verifying Field Value located under frame
	 * @param value - Expected Value
	 * @param fieldName - Field Name having Actual Value
	 * @param frame - FrameLocator
	 * @param page - Page having the Field
	 */
	public static void verifyFieldValue(String value, String fieldName, String frame, Page page) {
		Constant.FRAME = PlaywrightUtils.getFrame(frame, Constant.PAGE);
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(fieldName, Constant.FRAME));
		Assert.assertEquals(value, PlaywrightUtils.getText(PlaywrightUtils.getElement(fieldName, Constant.FRAME)));
	}
		
	/**
	 * Method to generate Random Number of given Digits
	 * @param digits - no. of digits
	 * @return Random Number as String
	 */
	public static String generateRandomNumber(int digits) {
		if(digits ==0)
			throw new CustomException("0 digits of number cannot be generated");
		String number = "";
		String temp;
		Random random = new Random();   
		for(int i=0; i<digits; i++) {
			temp = String.valueOf(random.nextInt(10));
			if(i==0 && temp.equals("0"))
				temp = "1";
			number += temp;
		}
		return number;
	}
	
	/**
	 * Method to logout from given user in salesforce
	 * @param user - user name
	 */
	public static void userLogout(String user) {
		try {
			PlaywrightUtils.waitForAnElement(getUserLogoutButton(user, Constant.PAGE));
			PlaywrightUtils.click(getUserLogoutButton(user, Constant.PAGE));
			PlaywrightUtils.waitForMoreSec(2);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method to assert number values are same(0.05 or less difference will be ignored)
	 * @param apiFields - API Fields(which will give Actual Values)
	 * @param uiValues - UI Values(Expected Values)
	 */
	public static void assertEqualsForDecimalNumbersWithSlightDifference(String[] apiFields, String[] uiValues) {
		if (apiFields.length != uiValues.length)
			throw new CustomException("Array count mismatch");
		else {
			float firstNumber, secondNumber;
			for (int i = 0; i < apiFields.length; i++) {
				firstNumber = Float.parseFloat(String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])));
				secondNumber = Float.parseFloat(uiValues[i]);
				if(firstNumber > secondNumber ) {
					if(!(firstNumber - secondNumber <= 0.051)) {
						System.out.println(firstNumber - secondNumber);
						throw new CustomException("Comparison fialure b/w "+apiFields[i]+ " and "+uiValues[i]);
					}
				}else if(firstNumber < secondNumber ){
					if(!(secondNumber - firstNumber <= 0.051)) {
						System.out.println(secondNumber - firstNumber);
						throw new CustomException("Comparison fialure b/w "+apiFields[i]+ " and "+uiValues[i]);
					}
				}
			}
			String uiValueStr = " ", apiValueStr = " ";
			for(int j = 0; j < apiFields.length-1; j++ ) {
				uiValueStr += apiFields[j] + " - '" + uiValues[j] + "', ";
				apiValueStr += apiFields[j] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[j])) + "', ";				
			}
			uiValueStr += apiFields[apiFields.length-1] + " - '" + uiValues[apiFields.length-1] + "'";
			apiValueStr += apiFields[apiFields.length-1] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[apiFields.length-1])) + "'";
			Constant.actualResult = uiValueStr;
			Constant.expectedResult = apiValueStr;
		}
	}
	
	/**
	 * Method for Returning Anchor Tag Locator By Given Text
	 * @param value - Text
	 * @param page - Browser Page
	 * @return Element Locator
	 */
	public static Locator getAnchorTagWebElementByText(String value, Page page) {
		return page.locator(String.format("//a[contains(text(),'%s')]",value));
	}
	
	/**
	 * Method for Returning Locator for Category by Given Category Name
	 * @param CategoryName - Category Name
	 * @param page - Browser Page
	 * @return Category Element Locator
	 */
	public static Locator findCategoryByText(String categoryName, Page page) {
      return getAnchorTagWebElementByText(categoryName, page);
    }
	
	/**
	 * Method for Returning Locator for Entity By Name
	 * @param value - Entity Name
	 * @param page - Browser Page
	 * @return Entity Element Locator
	 */
	public static Locator getEntityByName(String value, Page page) {
		return page.locator(String.format("//th//a[@title='%s']",value));
	}
	
	/**
	 * Method for Returning H1 Tag Locator By Given Text 
	 * @param value - Entity Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator getH1TagWebElementByText(String value, Page page) {
		return page.locator(String.format("//h1[contains(text(),'%s')]",value));
    }
	
	/**
	 * Method for Returning Locator for Product by Given Text
	 * @param productName - Product Name
	 * @param page - Browser Page
	 * @return Product Locator
	 */
	public static Locator findProductByText(String productName, Page page) {
      return getSpanTagWebElementByText(productName, page);
    }
	
	/**
	 * Method for Returning Span Tag Locator By Given Text 
	 * @param value - Entity Name
	 * @param page - Browser Page
	 * @return Entity Locator
	 */
	public static Locator getSpanTagWebElementByText(String value, Page page) {
		return page.locator(String.format("//span[contains(text(),'%s')]",value));
	}
	
	/**
	 * Method for Returning Button Tag Locator By Given Text 
	 * @param value - Entity Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator getButtonTagWebElementByText(String value, Page page) {
		return page.locator(String.format("//button[contains(text(),'%s')]",value));
	}
	
	/**
	 * Method for Returning Anchor Tag Locator By Given Text
	 * @param value - Text
	 * @param page - Browser Page
	 * @return Element Locator
	 */
	public static Locator findCategoryByExactText(String value, Page page) {
		return page.locator(String.format("//ul[@class='nav navbar-nav level-1']//a[text()='%s']",value));
	}
	
	/**
	 * Method selecting the Product by Given text
	 * @param product - Product Name
	 */
	public static void productSelect(String product) {
		try {
			PlaywrightUtils.waitForAnElement(CommonUtils.getAnchorTagWebElementByExactText(product, Constant.PAGE));
			PlaywrightUtils.click(CommonUtils.getAnchorTagWebElementByExactText(product, Constant.PAGE));
			Constant.result = "'" + product + "' Product selected successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

	}
	
	/**
	 * Method to select a random product form the list of products
	 * 
	 */
	public static void selectARandomProductFromTheList(String product) {
		Locator locator = 	CommonUtils.getAnchorTagWebElementByExactText(product, Constant.PAGE);	
		System.out.println(locator);
		int howMayProductsLoaded = locator.count();
		Random random = new Random();
        int randomIntBounded = random.nextInt(howMayProductsLoaded);
        Locator singleproduct = locator.nth(randomIntBounded);
        PlaywrightUtils.waitForAnElement(singleproduct);
		PlaywrightUtils.click(singleproduct);
		Constant.result = "'" + product + "' Product selected successfully";
       
	}
	/**
	 * Method for Returning Locator for Product by Given Text for Verification
	 * @param product - Product Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator verifyProductOnDetailPage(String product, Page page) {
		return page.locator(String.format("//h1[@class='product-name' and text()='%s']", product));
    }
	
	/**
	 * Method to find the searched app name of the given search box
	 * @param appName - Name of the app
	 * @param Value - 
	 * @return - app Loactor
	 */
	public static Locator AppNameSearchedontheSalesforce(String app, Page page) {
		return page.locator(String.format("//a[@data-label='%s']//b", app));
    }
	
	
	
	/**
	 * Method for Product Verify
	 * @param product - Product Name
	 */
	public static void verifyProduct(String product) {
		try {
			PlaywrightUtils.waitForAnElement(verifyProductOnDetailPage(product, Constant.PAGE));
			Assert.assertTrue(verifyProductOnDetailPage(product, Constant.PAGE).isVisible());
			Constant.result = "'" + product + "' Product displayed successfully";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	
	/**
	 * Method for Returning Locator for Option tag by Given Text
	 * @param product - Product Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator getOptionTagWebElementByExactText(String option, Page page) {
		return page.locator(String.format("//option[contains(text(),'%s')]", option));
    }
	
	/**
	 * Method for Returning Locator for Option tag by Given Text
	 * @param product - Product Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator getOptionTagForSizeWebElementByExactText(String option, Page page) {
		return page.locator(String.format("//select[@id='size-1']//option[text()='%s']", option));
    }
	
	/**
	 * Method for Returning Locator for Option tag by Given Text
	 * @param product - Product Name
	 * @param page - Browser Page
	 * @return - Entity Locator
	 */
	public static Locator getOptionTagForQuantityWebElementByExactText(String option, Page page) {
		return page.locator(String.format("//select[@id='quantity-1']//option[text()='%s']", option));
    }
	
	/**
     * Method to round off double to 2 decimal places
     * @param number - input number
     * @return - round off number
     */
    public static double roundOffDoubleToTwoDecPlace(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
    
    /**
     * Method to round off double to 2 decimal places
     * @param number - input number
     * @return - round off number
     */
    public static String roundOffDoubleToTwoDecPlaceToString(double number) {
        return String.valueOf(Math.round(number * 100.0) / 100.0);
    }
    
    /**
     * Method to round off double to 2 decimal places
     * @param number - input number
     * @return - round off number
     */
    public static String roundOffDoubleToOneDecPlaceToString(double number) {
        return String.valueOf(Math.round(number * 10.0) / 10.0);
    }
}
