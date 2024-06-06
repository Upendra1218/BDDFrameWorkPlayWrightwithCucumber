package com.Providio_Automation.baseline.steps;

import java.io.IOException;
import java.util.Map;

import org.junit.Assert;

import com.Providio_Automation.baseline.commonpages.CommonPage;
import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.exception.CustomException;
import com.Providio_Automation.baseline.managers.FileReaderManager;
import com.Providio_Automation.baseline.pageobjects.APIPage;
import com.Providio_Automation.baseline.pageobjects.CheckoutPage;
import com.Providio_Automation.baseline.pageobjects.PDP;
import com.Providio_Automation.baseline.pageobjects.PaymentPage;
import com.Providio_Automation.baseline.pageobjects.RegisterPage;
import com.Providio_Automation.baseline.utils.CommonUtils;
import com.Providio_Automation.baseline.utils.OMSUtils;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepFile {
	

	
	RegisterPage registeruser = new RegisterPage();
	CheckoutPage checkout = new CheckoutPage();
	PaymentPage payment = new PaymentPage();
	PDP pdp = new PDP();
	APIPage api = new APIPage();
	
	@Given("User login into the {string}")
	public void user_login_into_the(String application) {
	    switch (application) {
		case "Salesforce":
			CommonPage.salesforcelogin();
			break;
		case "Storefront":
			CommonPage.storelogin();
			break;
		default:
			throw new CustomException("Please define case for '" + application + "' in respective StepDef");
		}
	}
	
	@Given("User navigates to the {string} Url")
	public void user_navigates_to_the_url(String application) throws IOException {
	    switch (application) {
		case "Storefront":
			PlaywrightUtils.navigateToURL(FileReaderManager.getInstance().getConfigReader().getStoreUrl(), Constant.PAGE);
			ExtentCucumberAdapter.addTestStepLog("Navigated to '" +FileReaderManager.getInstance().getConfigReader().getStoreUrl()+ "' URL successfully");
			break;
		default:
			throw new CustomException("Please define case for '" + application + "' in respective StepDef");
		}
	}

	@Given("User select the {string} on {string} Page")
	public void user_select_the_on_page(String contactName, String page) {
	    CommonPage.searchContactInSalesforce(contactName);
	}

	@When("User navigates to {string} Category on {string} Page")
	public void user_navigates_to_category_on_page(String category, String page) {
	    CommonPage.selectCategory(category);
	}
	
	@When("User selects the {string} Product with {string} Color, {string} Size  and {string} Quantity from {string} Page")
	public void user_selects_the_product_from_page(String product, String color, String size,  String quantity, String page) {
	    CommonUtils.productSelect(product);
	    pdp.selectColor(color, "Color");
	    pdp.selectSize(size, "Size");
	    pdp.enterQuantity(quantity, "Quantity PDP");
	    PlaywrightUtils.waitForMoreSec(2);
	    CommonUtils.addKeyValueInGivenMap("Price", pdp.getPrice(), product, Constant.productSpecs);
	    CommonUtils.addKeyValueInGivenMap("Size", size, product, Constant.productSpecs);
	    CommonUtils.addKeyValueInGivenMap("Color", color, product, Constant.productSpecs);
	    CommonUtils.addKeyValueInGivenMap("Quantity", quantity, product, Constant.productSpecs);
	    ExtentCucumberAdapter.addTestStepLog("Selected '" +product+ "' Product with '" +color+ "' Color and '" +quantity+"' Quantity");
	}

	@Then("Verify the {string} Product is selected on {string} Page")
	public void verify_the_product_on_page(String product, String page) {
	    CommonUtils.verifyProduct(product);
	}
	
	

	@When("User selects {string} in the {string} field on {string} Page")
	public void user_selects_in_the_field_on_page(String value, String field, String page) {
		switch (field) {
		case "Size":
			pdp.selectSize(value, field);
			break;
		case "Quantity PDP":
			pdp.enterQuantity(value, field);
			break;
		case "Color":
			pdp.selectColor(value, field);
			break;
		default:
			throw new CustomException("Please define case for '" + field + "' in respective StepDef");
		}
	}

	@When("User clicks on the {string} button on {string} Page")
	public void user_clicks_on_the_button_on_page(String button, String page) {
		switch (button) {
		case "Checkout":
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Cart icon", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Cart icon", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(button, Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement(button, Constant.PAGE));
			break;
		case "Apply":
			PlaywrightUtils.waitForMoreSec(2);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(button, Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement(button, Constant.PAGE));
			PlaywrightUtils.waitForMoreSec(2);
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(
					"Gift Certificate Redeemed Successfully", Constant.PAGE));
			Constant.giftVoucherApplied = true;
			Constant.giftVoucherAmount = PlaywrightUtils.getText(PlaywrightUtils.getElement(
					"Gift Certificate Redeemed Successfully", Constant.PAGE)).trim().split(" ")[0]
							.replace("$", "");
			break;
		
		case "Add to Cart":
		case "Guest Checkout":
			PlaywrightUtils.waitForMoreSec(3);
		default:
			
			//System.out.println("Coming Here");
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(button, Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement(button, Constant.PAGE));
			//PlaywrightUtils.click(PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(button, Constant.PAGE)));
			break;
		}
		ExtentCucumberAdapter.addTestStepLog("'" + button +"' Button clicked successfully");
	}

	@Then("Verify the {string} on {string} Page")
	public void verify_the_on_page(String element, String page) {
	    PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(element, Constant.PAGE));
	    Assert.assertTrue(PlaywrightUtils.getElement(element, Constant.PAGE).isVisible());
	    ExtentCucumberAdapter.addTestStepLog("'" + element + "' verified successfully");
	    PlaywrightUtils.addScreenshotToReport();
	}

	@Then("Verify {string} Object created with required field values in {string} through API")
	public void verify_object_created_with_required_field_values_in_through_api(String object, String page) {
	    switch (object) {
		case "Object Summary":
			api.verifyOrderSummaryObject();
			break;
		case "Object Product Summary":
			for (Map.Entry<String, Map<String, Object>> entry : Constant.productSpecs.entrySet())
				api.verifyOrderItemSummaryObject(entry.getKey());
			break;
		default:
			throw new CustomException("Please define case for '" + object + "' in respective StepDef");
		}
	}
	
	@When("User navigates to {string} Sub-Category under {string} Category on {string} Page")
	public void user_navigates_to_subcategory_under_category_on_page(String subCategory, String category, String page) {
	    PlaywrightUtils.hover(CommonUtils.findCategoryByExactText(category, Constant.PAGE));
	    PlaywrightUtils.click(CommonUtils.findCategoryByExactText(subCategory, Constant.PAGE));
	    ExtentCucumberAdapter.addTestStepLog("Navigated to '"+subCategory+"' Sub-Category under '"+category+"' Category successfully");
	}
	
	@When("User clicks on the {string} links on the {string} Page")
	public void user_clicks_on_salesforce_app_on_page(String app, String page) {
		PlaywrightUtils.click(CommonUtils.AppNameSearchedontheSalesforce(app, Constant.PAGE));
	}
	
	@Then("Verify {string} message on {string} Page")
	public void verify_message(String message, String page) {
		switch (message) {
		case "Thank you for your order":
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(message, Constant.PAGE));
			Constant.recordId = PlaywrightUtils.getText(PlaywrightUtils.getElement("Order Number", Constant.PAGE));
			PlaywrightUtils.addScreenshotToReport();
			break;
		default:
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(message, Constant.PAGE));
			break;
		}
		ExtentCucumberAdapter.addTestStepLog("'" + message + "' verified successfully");
	}
	
	@When("User enters {string} in the {string} field on {string} Page")
	public void user_enters_in_the_field_on_page(String value, String field, String page) {
		switch (field) {
		case "Email":
			Constant.email = value;
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.setValueUsingKeyboard(value, PlaywrightUtils.getElement(field, Constant.PAGE));
			break;
		case "Redeem Gift Certificate":
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.setValue(value, PlaywrightUtils.getElement(field, Constant.PAGE));
			break;
		default:
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
			PlaywrightUtils.setValueUsingKeyboard(value, PlaywrightUtils.getElement(field, Constant.PAGE));
			break;
		}
		ExtentCucumberAdapter.addTestStepLog("Entered '" +value+ "' in '" +field+ "' Field");
	}
	
	@When("User enters all the {string} on {string} Page")
	public void user_enters_all_the_on_page(String section, String page) {
		switch (section) {
		case "Shipping Details":			
			checkout.searchAndEnterShippingDetails();
			break;
		case "Credit Card Details":
			payment.enterCreditCardDetails();
			break;
		default:
			throw new CustomException("Please define case for '" + section + "' in respective StepDef");
		}
		ExtentCucumberAdapter.addTestStepLog("'" + section + "' section fields filled successfully");
	}
	
	@When("User updates {string} to {string} for {string} record")
	public void user_updates_to_for_record(String field, String value, String record) {
		switch (field+"_"+record) {
		case "Status_Order Summary":
			OMSUtils.updateOrderSummaryStatusThroughAPI(value);
			break;
		case "Status_Fulfillment Order":
			OMSUtils.updateFulfillmentOrderStatusThroughAPI(value);
			break;
		default:
			throw new CustomException("Please define case for '" + field+"_"+record + "' in respective StepDef");
		}
		ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value updated to '"+value+"' successfully for '"+record+"' Record");
	}
	
	@Then("Verify {string} updated to {string} for {string} record")
	public void verify_updated_to_for_record(String field, String value, String record) {
		switch (field +"_"+ value +"_"+ record) {
		case "Status_Fulfilled_Order Summary":
			OMSUtils.verifyOrderSummaryStatusThroughAPIWithRetry(field, value);
			ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value updated to '"+value+"' successfully for '"+record+"' Record");
			break;
		default:
			OMSUtils.verifyOrderSummaryStatusThroughAPI(field, value);
			ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value updated to '"+value+"' successfully for '"+record+"' Record");
			break;
		}
	}
	
	@Then("Verify {string} is updated on {string} record")
	public void verify_updated_to_for_record(String field, String record) {
		OMSUtils.verifyCapturedAmountOnOrderPaymentSummary();
		ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value updated to required value successfully for '"+record+"' Record");
	}
	
	@When("User checks the {string} checkbox on {string} Page")
	public void user_checks_the_checkbox_on_page(String checkbox, String page) {
		switch (checkbox) {
		case "This is a Gift":
			PlaywrightUtils.click(PlaywrightUtils.getElement("Phone Number", Constant.PAGE));
			PlaywrightUtils.keyPress("Tab", Constant.PAGE);
			PlaywrightUtils.keyPress("Space", Constant.PAGE);
			break;
		default:
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(checkbox, Constant.PAGE));
			PlaywrightUtils.checkCheckbox(PlaywrightUtils.getElement(checkbox, Constant.PAGE));
			break;
		}
		ExtentCucumberAdapter.addTestStepLog("'" +checkbox+ "' Checkbox checked successfully");
	}
	
	@When("User enters all the {string} with values {string} Name, {string} Card Number, {string} CVV and {string} Expiry on {string} Page")
	public void user_enters_all_the_with_values(String section, String name, String cardNumber, String cvv, String expiry, String page) {
		payment.enterCreditCardDetailsWithGivenValues(name, cardNumber, cvv, expiry);
	}
	
	@When("User enters all the values of Register {string} fName, {string} lname, {string} phone, {string} email, {string} conformemail, {string} pwd, and {string} conformpwd on {string} Page")
	public void user_enters_all_the_with_values(String fname, String lname, String phone,String email,String conformemail,String pwd,String conformpwd, String page) {
		registeruser.enterRegiesterformValueswithgivenValues(fname, lname, phone, email, conformemail, pwd, conformpwd);
	}
	
	@Then("Verify {string} value is {string} for {string} record")
	public void verify_value_is_for_record(String field, String value, String page) {
		switch (field) {
		case "IsGift":
			OMSUtils.veirfyIsGiftValueOnOrderDeliveryGroupSummary(value);
			break;
		default:
			throw new CustomException("Please define case for '" + field + "' in respective StepDef");
		}
		ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value is '"+value+"' verified successfully for '"+page+"' Record");
	}
	
	@When("Verify {string} is already {string} on {string} record")
	public void verify_amount_is_already_on_record(String field, String field2, String record) {
		OMSUtils.verifyCapturedAmountForGiftCertificateOnOrderPaymentSummary();
	}
	
	@When("User wait for {string} record to be created in {string} through API")
	public void user_wait_for_record_to_be_created_in_through_api(String record, String app) {
		switch (record) {
		case "Order Summary":			
			OMSUtils.verifyOrderSummaryCreatedInOMS();
			break;
		case "Fulfillment Order":			
			OMSUtils.verifyFulfillmentOrderCreatedInOMS();
			break;
		default:
			throw new CustomException("Please define case for '" + record + "' in respective StepDef");
		}
	}
	
	@When("Verify {string} is {string} for {string} record")
	public void verify_is_for_record(String field, String value, String record) {
		switch (field + "_" + value + "_"+ record) {
		case "Status_Created_Order Summary":
		case "Status_Waiting to Fulfill_Order Summary":
		case "Status_Fulfilled_Order Summary":
			OMSUtils.verifyOrderSummaryStatusThroughAPI(field, value);
			break;
		case "Status_ORDERED_Order Product Summary":
		case "Status_ALLOCATED_Order Product Summary":
		case "Status_FULFILLED_Order Product Summary":
			OMSUtils.verifyOrderProductSummaryStatusThroughAPI(field, value);
			break;
		default:
			throw new CustomException("Please define case for '" + field + "_" + value + "_"+ record + "' in respective StepDef");
		}
		ExtentCucumberAdapter.addTestStepLog("'" + field + "' Field value is '"+value+"' successfully for '"+record+"' Record");
	}
	
	@When("User switches to {string} app in {string}")
	public void user_switches_to_app_in(String appName, String applicationName) {
		CommonPage.switchToApp(appName);
	}
	
	@When("User selects all the Products from {string} file")
	public void user_selects_all_the_products_from_file(String file) {
		pdp.selectProductsFromExcel();
	}
	@When ("User creates multiple records via the API with the field name {string}, totaling {int} records, using the name {string} and the entity {string}.")
	//@When("User create multiple records {string} Field Name, {int} Records, {string} Name and {string} entity through api")
	public void user_create_multiple_records_through_api(String FieldName, int count, String value, String Entity) {
		OMSUtils.createMultipleRecords(FieldName, value, count, Entity);
	}
	
	 @When("User creates multiple records via the API with JSON data:")
	    public void create_multiple_records_with_details(String jsonData) {
		 OMSUtils.createMultipleRecordsWithDetails(jsonData);
	    }
	
	 
	 @Given("User creates multiple records with JSON file {string} records details")
	    public void the_json_file_path_with_records_details(String filename) {
		 switch (filename) {
		case "Data":
			OMSUtils.json_file_with_records_details(Constant.JsonData);
			break;
		default:
			throw new CustomException("Please define case for '" + filename + "' in respective StepDef");
		
		}
		 
	 }
	
}
