package com.automation.baseline.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.exception.CustomException;
import com.automation.baseline.managers.FileReaderManager;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class OMSUtils {
	
	/**
	 * Method to update Fulfillment Order Status through API
	 * @param status = New Status
	 */
	@SuppressWarnings("unchecked")
	public static void updateFulfillmentOrderStatusThroughAPI(String status) {
		boolean found = false;
		for(int i=0; i<20; i++) {
			try {				
				APIUtils.getSoqlResult(String.format(
						"Select Id from FulfillmentOrder where OrderSummary.OrderNumber = '%s'",
						Constant.recordId), "Id");
				found = true;
				break;
			} catch (Exception e) {
				PlaywrightUtils.waitForMoreSec(5);
			}
		}
		if(!found)
			throw new CustomException("Fulfillment Orders not creates even after waiting 5 mins");
		APIUtils.getSoqlResultInJSONForMultipleRecords(String.format(
				"Select Id from FulfillmentOrder where OrderSummary.OrderNumber = '%s'",
				Constant.recordId));
		org.json.JSONObject jSON;
		org.json.JSONArray tierArray = Constant.recordsJSONArray;
		for (int j = 0; j < tierArray.length(); j++) {
			jSON = tierArray.getJSONObject(j);
			String fulfillmentOrderId = APIUtils.getSOQLFieldValueFromJSONConstantByGiveJSON("Id", jSON);
			JSONObject updatedValues = new JSONObject();
			updatedValues.put("Status", status);
			APIUtils.updateFieldValueInSObject(updatedValues, fulfillmentOrderId, "FulfillmentOrder");
			PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
					+"/"+fulfillmentOrderId, Constant.PAGE);
			PlaywrightUtils.waitForMoreSec(4);
			PlaywrightUtils.addScreenshotToReport();
		}
	}
	
	/**
	 * Method to updates the fields through API
	 * @params - Required fields to update
	 */
	public static void updateFieldsThroughAPI() {
		
	}
	
	/**
	 * Method to verify Fulfillment Order Created in OMS
	 */
	public static void verifyFulfillmentOrderCreatedInOMS() {
		boolean flag = false;
		for(int i=0; i<20; i++) {
			try {				
				APIUtils.getSoqlResult("Select Id from FulfillmentOrder where OrderSummary.OrderNumber = '"
				+Constant.recordId+"'", "Id");
				flag = true;
				break;
			} catch (Exception e) {
				PlaywrightUtils.waitForMoreSec(5);
			}
		}
		if(!flag)
			throw new CustomException("Fulfillment Order not created even after waiting 5 mins");
	}
	
	/**
	 * Method to update Order Summary Status through API
	 * @param status = New Status
	 */
	@SuppressWarnings("unchecked")
	public static void updateOrderSummaryStatusThroughAPI(String status) {
		String orderSummaryId = APIUtils.getSoqlResult("Select Id from OrderSummary where OrderNumber = '"
				+Constant.recordId+"'", "Id");
		String apiStatus = APIUtils.getSoqlResult("Select Status from OrderSummary where OrderNumber = '"
				+Constant.recordId+"'", "Status");
		if(apiStatus.equals("Created") || apiStatus.equals("In Review")) {
			JSONObject updatedValues = new JSONObject();
			updatedValues.put("Status", "Approved");
			APIUtils.updateFieldValueInSObject(updatedValues, orderSummaryId, "OrderSummary");
		}
		PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
				+"/"+orderSummaryId, Constant.PAGE);
		PlaywrightUtils.waitForMoreSec(2);
		PlaywrightUtils.addScreenshotToReport();
	}
	
	/**
	 * Method to Verify Order Summary field value
	 * @param field - Field Name
	 * @param value - Value
	 */
	public static void verifyOrderSummaryStatusThroughAPI(String field, String value) {
		PlaywrightUtils.waitForSec();
		String apiStatus = APIUtils.getSoqlResult("Select "+field+" from OrderSummary where OrderNumber = '"
				+Constant.recordId+"'", field);
		Assert.assertEquals(value, apiStatus);
		PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
				+"/"+APIUtils.getSoqlResult("Select Id from OrderSummary where OrderNumber = '"
						+Constant.recordId+"'", "Id"), Constant.PAGE);
		PlaywrightUtils.waitForMoreSec(2);
		PlaywrightUtils.addScreenshotToReport();
	}
	
	/**
	 * Method to verify Order Summary Created in OMS
	 */
	public static void verifyOrderSummaryCreatedInOMS() {
		boolean flag = false;
		for(int i=0; i<20; i++) {
			try {				
				APIUtils.getSoqlResult("Select Id from OrderSummary where OrderNumber = '"
				+Constant.recordId+"'", "Id");
				flag = true;
				break;
			} catch (Exception e) {
				PlaywrightUtils.waitForMoreSec(5);
			}
		}
		if(!flag)
			throw new CustomException("Order Summary not created even after waiting 5 mins");
	}
	
	/**
	 * Method to Verify Order Summary field value
	 * @param field - Field Name
	 * @param value - Value
	 */
	public static void verifyOrderSummaryStatusThroughAPIWithRetry(String field, String value) {
		PlaywrightUtils.waitForSec();
		boolean flag = false;
		for(int i=0; i<30; i++) {
			String apiStatus = APIUtils.getSoqlResult("Select "+field+" from OrderSummary where OrderNumber = '"
					+Constant.recordId+"'", field);
			try {
				if(apiStatus.equals(value)) {
					flag = true;
					break;
				} else {
					throw new CustomException();
				}
			} catch (Exception e) {
				PlaywrightUtils.waitForMoreSec(5);
			}
		}
		if(!flag)
			throw new CustomException("Order Summary is not updated to '"+value+"' even after waiting 7 and a half mins");
		PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
				+"/"+APIUtils.getSoqlResult("Select Id from OrderSummary where OrderNumber = '"
						+Constant.recordId+"'", "Id"), Constant.PAGE);
		PlaywrightUtils.waitForMoreSec(2);
		PlaywrightUtils.addScreenshotToReport();
	}

	/**
	 * Method to Verify IsGift value on OrderDeliveryGroupSummary Object
	 * @param value - Expected Value
	 */
	public static void veirfyIsGiftValueOnOrderDeliveryGroupSummary(String value) {
		APIUtils.getSoqlResultInJSON(String.format(
			"Select IsGift from OrderDeliveryGroupSummary where OrderSummary.OrderNumber = '%s'", 
			Constant.recordId));
		Assert.assertEquals(value, APIUtils.getSOQLFieldValueFromJSONConstant("IsGift"));
	}
	
	/**
	 * Method to verify Captured Amount in Order Payment Summary
	 */
	public static void verifyCapturedAmountOnOrderPaymentSummary() {
		APIUtils.getSoqlResultInJSONForMultipleRecords(String.format(
				"Select GrandTotalAmount from FulfillmentOrder where OrderSummary.OrderNumber = '%s'",
				Constant.recordId));
		org.json.JSONObject jSON;
		org.json.JSONArray tierArray = Constant.recordsJSONArray;
		double capturedAmount = 0;
		for (int j = 0; j < tierArray.length(); j++) {
			jSON = tierArray.getJSONObject(j);
			double fulfillmentOrderAmt = Double.parseDouble(APIUtils.getSOQLFieldValueFromJSONConstantByGiveJSON("GrandTotalAmount", jSON));
			capturedAmount += fulfillmentOrderAmt;			
		}
		APIUtils.getSoqlResultInJSON("Select CapturedAmount from OrderPaymentSummary where OrderSummary.OrderNumber = '"
				+Constant.recordId+"' and Method like '%Visa%'");
		String apiCapturedAmount = APIUtils.getSOQLFieldValueFromJSONConstant("CapturedAmount");
		if(Constant.giftVoucherApplied)
			capturedAmount -= Double.parseDouble(Constant.giftVoucherAmount);
		Assert.assertEquals(apiCapturedAmount, 
				CommonUtils.roundOffDoubleToTwoDecPlaceToString(capturedAmount));
	}
	
	/**
	 * Method to verify Gift Certificate Captured Amount in Order Payment Summary
	 */
	public static void verifyCapturedAmountForGiftCertificateOnOrderPaymentSummary() {
		APIUtils.getSoqlResultInJSON("Select CapturedAmount from OrderPaymentSummary where OrderSummary.OrderNumber = '"
				+Constant.recordId+"' and Method like '%OPS%'");
		String apiCapturedAmount = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(APIUtils.getSOQLFieldValueFromJSONConstant("CapturedAmount")));
		Assert.assertEquals(apiCapturedAmount, CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(Constant.giftVoucherAmount)));
	}
	
	/**
	 * Method to Verify Order Product Summary field value
	 * @param field - Field Name
	 * @param value - Value
	 */
	public static void verifyOrderProductSummaryStatusThroughAPI(String field, String value) {
		APIUtils.getSoqlResultInJSONForMultipleRecords("Select Id, "+field+" from OrderItemSummary where OrderSummary.OrderNumber = '"
				+Constant.recordId+"'");
		org.json.JSONObject jSON;
		org.json.JSONArray tierArray = Constant.recordsJSONArray;
		for (int j = 0; j < tierArray.length(); j++) {
			jSON = tierArray.getJSONObject(j);
			String apiStatus = APIUtils.getSOQLFieldValueFromJSONConstantByGiveJSON(field, jSON);
			String id = APIUtils.getSOQLFieldValueFromJSONConstantByGiveJSON("Id", jSON);
			Assert.assertEquals(value, apiStatus);
			PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
					+"/"+id, Constant.PAGE);
			PlaywrightUtils.waitForMoreSec(2);
			PlaywrightUtils.addScreenshotToReport();
		}
	}
	
	/**
	 * Method to create a multiple records at a time through api
	 * @param field - Field Name
	 * @param count - how many records to be create
	 * @param value - Field value
	 * @param entity - Name of the record(Account, lead etc)
	 */
	public static void createMultipleRecords(String FieldName, String value, int count, String Entity) {
		for (int i = 1; i <= count; i++) {
			JSONObject fieldValues = new JSONObject();
			HashMap<String, Object> map = new HashMap<>();
			map.put(FieldName, value+" "+ i);
			fieldValues.putAll(map);
			APIUtils.createEntity(Entity, fieldValues);
			//ExtentCucumberAdapter.addTestStepLog("'" + message + "' verified successfully");
		}

	}
	/**
	 * This method is Helper method
	 * Method is used to create a multiple record at a time with JSON through API
	 * @param JSONdata - Data of the each record contains field name and value too
	 * @param count - How many records to be created
	 * @param Entity - Object Name 
	 */
	@SuppressWarnings("unchecked")
    public static void createMultipleRecordswithMultipleFieldswithJSON(JSONObject fieldValues, int count, String entity) {
        for (int i = 1; i <= count; i++) {
            JSONObject jsonObject = new JSONObject();
            for (Object key : fieldValues.keySet()) {
            	if (key.equals("LastName")) {
            		jsonObject.put(key, fieldValues.get(key) + "_" + i);
            	}else {
            		jsonObject.put(key, fieldValues.get(key));
            	}
            }
            APIUtils.createEntity(entity, jsonObject);
            ExtentCucumberAdapter.addTestStepLog("'" + jsonObject.toString() + "' created successfully");
        }
    }
	
	/**
	 * Method is used to create a multiple record at a time with JSON through API
	 * @param JSONdata - it contains fieldNames, values, count and Entity
	 */
	public static void createMultipleRecordsWithDetails(String jsonData) {
		
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            String entity = (String) jsonObject.get("Entity");
            long count = (Long) jsonObject.get("Count");
            JSONObject fieldValuesJson = (JSONObject) jsonObject.get("FieldValues");
            createMultipleRecordswithMultipleFieldswithJSON(fieldValuesJson, (int) count, entity);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
	
	public static void json_file_with_records_details(String filePath) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try (FileReader reader = new FileReader(filePath)) {
            // Parse the JSON file
            jsonObject = (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return; // Exit method if parsing fails
        }
        // Call the method with the parsed JSON object
        create_multiple_records_with_details_from_the_json_file(jsonObject);
    }

    @SuppressWarnings("unchecked")
    public static void create_multiple_records_with_details_from_the_json_file(JSONObject jsonObject) {
        if (jsonObject != null) {
            JSONObject fieldValues = (JSONObject) jsonObject.get("FieldValues");
            String entity = (String) jsonObject.get("Entity");
            long count = (long) jsonObject.get("Count");
            for (int i = 1; i <= count; i++) {
                JSONObject record = new JSONObject(fieldValues);
                // Modify LastName to include count
                record.put("LastName", record.get("LastName") + "_" + i);
                APIUtils.createEntity(entity, record); // Example: Create the entity using APIUtils
                ExtentCucumberAdapter.addTestStepLog("'" + jsonObject.toString() + "' created successfully");
            }
        }
    }
	
	
}
