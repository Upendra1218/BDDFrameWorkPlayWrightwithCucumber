package com.Providio_Automation.baseline.utils;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.exception.CustomException;
import com.Providio_Automation.baseline.managers.FileReaderManager;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

/**
 * @Author: ETG QA
 */
public class APIUtils {

	/**
	 * Method for Status Code returned while connecting with API
	 * @return Status Code
	 */
	public static int getStatusCode() {

		return given()
				.urlEncodingEnabled(true)
				.param("username", FileReaderManager.getInstance().getConfigReader().getSfdcUsername())
				.param("password",
						FileReaderManager.getInstance().getConfigReader().getSfdcPassword() +
						FileReaderManager.getInstance().getConfigReader().getSecurityToken())
				.param("client_id", FileReaderManager.getInstance().getConfigReader().getClientId())
				.param("client_secret", FileReaderManager.getInstance().getConfigReader().getClientSecret())
				.param("grant_type", "password")
				.header("Accept", "application/json")
				.when()
				.post(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/oauth2/token")
				.getStatusCode();
	}

	/**
	 * Method to Establish Connection Salesforce through API
	 * @return Access Token received from Salesforce
	 */
	public static String establishConnection() {

		return given()
				.urlEncodingEnabled(true)
				.param("username", FileReaderManager.getInstance().getConfigReader().getSfdcUsername())
				.param("password",
						FileReaderManager.getInstance().getConfigReader().getSfdcPassword()+
						FileReaderManager.getInstance().getConfigReader().getSecurityToken())
				.param("client_id", FileReaderManager.getInstance().getConfigReader().getClientId())
				.param("client_secret", FileReaderManager.getInstance().getConfigReader().getClientSecret())
				.param("grant_type", "password")
				.header("Accept", "application/json")
				.when()
				.post(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/oauth2/token")
				.then()
				.log()
				.body()
				.extract()
				.path("access_token");
	}

	/**
	 * Method to get only one Field valuefrom Salesforce through API
	 * @param soql - SOQL Query
	 * @param fieldName - Field Value that you need to get from Salesforce
	 * @return Field Value received from Salesforce
	 */
	public static String getSoqlResult(String soql, String fieldName) {
		System.out.println("SOQL to execute is: " + soql);

		List<Map<String, Object>> recordsArray = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + establishConnection())
				.get(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/data/v56.0/query?q="
						+ soql + "")
				.then()
				.extract()
				.path("records");

		return (String) recordsArray.get(0).get(fieldName);
	}

	/**
	 * Method to get Field values from Salesforce through API then store it in Global List of Map
	 * @param soql - SOQL Query
	 */
	public static void getSoqlResultInConstant(String soql) {
		System.out.println("SOQL to execute is: " + soql);
		if(!Constant.recordsFromAPI.isEmpty())
			Constant.recordsFromAPI.clear();
		Constant.recordsFromAPI = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + establishConnection())
				.get(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/data/v56.0/query?q="
						+ soql + "")
				.then()
				.extract()
				.path("records");

	}

	/**
	 * Method to Create Entity in Salesforce through API
	 * @param typeOfEntity - Type of Entity(like Account, Contact, Lead, etc.)
	 * @param jsonObject - Entity Fields and Values in JSON Format
	 */
	public static void createEntity(String typeOfEntity, org.json.simple.JSONObject jsonObject) {

		System.out.println(jsonObject.toJSONString());
		ExtractableResponse<Response> response = given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + establishConnection())
				.header("Content-Type", "application/json")
				.body(jsonObject.toJSONString())
				.post(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
						+ "/services/data/v56.0/sobjects/" + typeOfEntity.toLowerCase())
				.then()
				.log()
				.body()
				.extract();
		boolean status = response.path("success");
		if(response.statusCode() == 201 && status)
			Constant.recordId = response.path("id");
		else
			throw new CustomException("Record not Created in Salesforce with status code: " + response.statusCode());
	}

	/**
	 * Method to validate Response returned from Salesforce API is matching Expected Results(for Global List of Map)
	 * @param apiFields - API Fields Array through which we get all the Actual Values from Salesforce
	 * @param uiValues - Expected Values Array
	 */
	public static void validateResponses(String[] apiFields, String[] uiValues) {
		if (apiFields.length != uiValues.length)
			throw new CustomException("API Fields and UI Values count mismatch");
		else {
			String uiValueStr = "Actual: ", apiValueStr = "Expected: ";
			for(int j = 0; j < apiFields.length-1; j++ ) {
				uiValueStr += apiFields[j] + " - '" + uiValues[j] + "', ";
				apiValueStr += apiFields[j] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[j])) + "', ";				
			}
			uiValueStr += apiFields[apiFields.length-1] + " - '" + uiValues[apiFields.length-1] + "'";
			apiValueStr += apiFields[apiFields.length-1] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[apiFields.length-1])) + "'";
			for (int i = 0; i < apiFields.length; i++) {
				if(String.valueOf(CommonUtils.getSOQLFieldValueFromAPIConstant(apiFields[i])).matches(".*[0-9]T[0-9].*:.*"))
					Assert.assertEquals(String.valueOf(CommonUtils.getSOQLFieldValueFromAPIConstant(apiFields[i])).split("T")[0], Constant.expectedValuesMap.get(uiValues[i]));
				else
					Assert.assertEquals(String.valueOf(CommonUtils.getSOQLFieldValueFromAPIConstant(apiFields[i])), Constant.expectedValuesMap.get(uiValues[i]));
			}
			Constant.actualResult = uiValueStr;
			Constant.expectedResult = apiValueStr;
		}
	}

	/**
	 * Method for Bye-passing Salesforce Login using SOAP API
	 */
	public static void directSalesforceLoginUsingSOAPAPI() {

		String sessionId = given()
				.contentType(ContentType.XML)
				.accept(ContentType.XML)
				.header("SOAPAction", "''")
				.header("Content-Type", "text/xml")
				.body("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:partner.soap.sforce.com\">\r\n"
						+ "   <soapenv:Body>  \r\n" + "      <urn:login>  \r\n" + "         <urn:username>"
						+ FileReaderManager.getInstance().getConfigReader().getSfdcUsername().replace("&", "&amp;").replace("<","&lt;").replace(">", "&gt;") + "</urn:username>  \r\n"
						+ "         <urn:password>"
						+ FileReaderManager.getInstance().getConfigReader().getSfdcPassword().replace("&", "&amp;").replace("<","&lt;").replace(">", "&gt;")
						+ "</urn:password>  \r\n"
						+ "      </urn:login>  \r\n" + "   </soapenv:Body>  \r\n" + "</soapenv:Envelope>  ")
				.when()
				.post(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/Soap/u/57.0")
				.thenReturn()
				.asString().split("<sessionId>")[1].split("</sessionId>")[0];
		
		PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getPayloadURL()
				+ "/secur/frontdoor.jsp?sid=" + sessionId, Constant.PAGE);
	}
	
	/**
	 * Method to get records from Salesforce when more than 1 records might come for a Query and then Store the results in Global JSON Array
	 * @param soql - SOQL Query
	 */
	public static void getSoqlResultInJSONForMultipleRecords(String soql) {
		System.out.println("SOQL to execute is: " + soql);
		JSONObject json = new JSONObject(given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + APIUtils.establishConnection())
				.get(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/data/v56.0/query?q="
						+ soql + "")
				.then()
				.log()
				.body()
				.extract()
				.asString());
		Constant.recordsJSONArray = json.getJSONArray("records");
	}
	
	/**
	 * Method to get records from Salesforce when only 1 record will come for a Query and then Store the results in Global JSON Object
	 * @param soql - SOQL Query
	 */
	public static void getSoqlResultInJSON(String soql) {
		System.out.println("SOQL to execute is: " + soql);
		JSONObject json = new JSONObject(given()
				.contentType(ContentType.JSON)
				.header("Authorization", "Bearer " + APIUtils.establishConnection())
				.get(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + "/services/data/v56.0/query?q="
						+ soql + "")
				.then()
				.log()
				.body()
				.extract()
				.asString());
		JSONArray array = json.getJSONArray("records");
		Constant.recordsJSON = array.getJSONObject(0);

	}
	
	/**
	 * Method to get SOQL Field Value stored in Global JSON Object
	 * @param fieldName - Field Name
	 * @return Field Value received from Salesforce
	 */
	public static String getSOQLFieldValueFromJSONConstant(String fieldName) {
		
		if(fieldName.contains("."))
			try {
				return (String) Constant.recordsJSON.getJSONObject(fieldName.split("\\.")[0]).get(fieldName.split("\\.")[1]);
			}
			catch (Exception e) {
				return Constant.recordsJSON.getJSONObject(fieldName.split("\\.")[0]).get(fieldName.split("\\.")[1]).toString();
			}
		else
			try {
				return (String) Constant.recordsJSON.get(fieldName);
			}
			catch (Exception e) {
				return Constant.recordsJSON.get(fieldName).toString();
			}
	}
	
	/**
	 * Method to get SOQL Field Value from Given JSON Object
	 * @param fieldName - Field Name
	 * @return Field Value extracted from Given JSON Object
	 */
	public static String getSOQLFieldValueFromJSONConstantByGiveJSON(String fieldName, JSONObject obj) {
		
		if(fieldName.contains("."))
			try {
				return (String) obj.getJSONObject(fieldName.split("\\.")[0]).get(fieldName.split("\\.")[1]);
			}
			catch (Exception e) {
				return obj.getJSONObject(fieldName.split("\\.")[0]).get(fieldName.split("\\.")[1]).toString();
			}
		else
			try {
				return (String) obj.get(fieldName);
			}
			catch (Exception e) {
				return obj.get(fieldName).toString();
			}
	}
	
	/**
	 * Method to validate Response returned from Salesforce API is matching Expected Results(for Global JSON Object)
	 * @param apiFields - API Fields Array through which we get all the Actual Values from Salesforce
	 * @param uiValues - Expected Values Array
	 */
	public static void validateResponsesThroughJSON(String[] apiFields, String[] uiValues) {
		if (apiFields.length != uiValues.length)
			throw new CustomException("API Fields and UI Values count mismatch");
		else {
			String uiValueStr = "Expected: ", apiValueStr = "Actual: ";
			for(int j = 0; j < apiFields.length-1; j++ ) {
				uiValueStr += apiFields[j] + " - '" + uiValues[j] + "', ";
				apiValueStr += apiFields[j] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[j])) + "', ";				
			}
			uiValueStr += apiFields[apiFields.length-1] + " - '" + uiValues[apiFields.length-1] + "'";
			apiValueStr += apiFields[apiFields.length-1] + " - '" + String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[apiFields.length-1])) + "'";
			for (int i = 0; i < apiFields.length; i++) {
				if(String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])).matches("^\\d+\\.\\d{3,}$")) {
					if(uiValues[i].matches("[0-9]+\\.[0-9]+$"))
						if(uiValues[i].charAt(uiValues[i].length()-3) == '.' && uiValues[i].charAt(uiValues[i].length()-1) == '0') 
							Assert.assertEquals(String.valueOf(CommonUtils.roundOffFloatToTwoDecPlace(Float.parseFloat(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])))), 
									uiValues[i].substring(0, uiValues[i].length()-1));
						else
							Assert.assertEquals(String.valueOf(CommonUtils.roundOffFloatToTwoDecPlace(Float.parseFloat(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])))), 
							uiValues[i]);
				}
				else {
					if(uiValues[i].matches("[0-9]+\\.[0-9]+$"))
						if(uiValues[i].charAt(uiValues[i].length()-3) == '.' && uiValues[i].charAt(uiValues[i].length()-1) == '0') 
							Assert.assertEquals(String.valueOf(CommonUtils.roundOffFloatToTwoDecPlace(Float.parseFloat(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])))), 
									uiValues[i].substring(0, uiValues[i].length()-1));
						else
							Assert.assertEquals(String.valueOf(APIUtils.getSOQLFieldValueFromJSONConstant(apiFields[i])), uiValues[i]);
				}
			}
			Constant.actualResult = apiValueStr;
			Constant.expectedResult = uiValueStr;
		}
	}
	
	/**
	 * Method to Update Field Value in SObject through API
	 * @param updatedValues - Updated Field Values in JSON Format
	 * @param objectId - Object Id which you want to Update
	 * @param objectType - Type of Object(like Account, Contact, Lead, etc.)
	 */
	public static void updateFieldValueInSObject(org.json.simple.JSONObject updatedValues, String objectId, String objectType) {
		int statusCode = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer " + APIUtils.establishConnection())
			.header("Content-Type", "application/json")
			.body(updatedValues.toJSONString())
			.patch(FileReaderManager.getInstance().getConfigReader().getPayloadURL() + 
					"/services/data/v57.0/sobjects/" + objectType + "/" + objectId)
			.then()
			.extract()
			.statusCode();
		if(statusCode != 204)
			throw new CustomException("Field Update field with status code: " + statusCode);
		else
			System.out.println("Field Values updated successfully");
	}
}
