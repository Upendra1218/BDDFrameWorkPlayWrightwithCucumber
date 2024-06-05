package com.automation.baseline.pageobjects;

import java.util.Arrays;
import java.util.Map;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.exception.CustomException;
import com.automation.baseline.utils.APIUtils;
import com.automation.baseline.utils.CommonUtils;
import com.automation.baseline.utils.PlaywrightUtils;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class APIPage {

	/**
	 * Method to verify Order Summary Object through API in OMS
	 */
	public void verifyOrderSummaryObject() {
		String[] fields = {"BillingEmailAddress", "TotalAdjustedProductAmount", 
			"TotalAdjustedProductTaxAmount", "TotalAdjustedDeliveryAmount", "TotalAdjustedDeliveryTaxAmount", 
			"TotalAmount", "TotalTaxAmount", "TotalAdjProductAmtWithTax", "TotalAdjDeliveryAmtWithTax", 
			"GrandTotalAmount", "Id"}, tableFields = { "OrderNumber" }, tableFieldValues = { Constant.recordId };
		String tableName = "OrderSummary";
		double tp = 0;
		boolean flag = false;
		for(int i=0; i<20; i++) {
			try {				
				CommonUtils.getSOQLResultsFromAPIByGivenFieldsAndValuesInJSON(fields, tableName, tableFields, tableFieldValues);
				flag = true;
				break;
			} catch (Exception e) {
				PlaywrightUtils.waitForMoreSec(5);
			}
		}
		if(!flag)
			throw new CustomException("Order Summary not created even after waiting 5 mins");
		Map<String, Object> tempMap = null;
		for (Map.Entry<String, Map<String, Object>> entry : Constant.productSpecs.entrySet()) {
			String product = entry.getKey();
			tempMap = Constant.productSpecs.get(product);
			String quantity = (String) tempMap.get("Quantity");
			String unitPrice = (String) tempMap.get("Price");
			tp += (Double.parseDouble(quantity) * Double.parseDouble(unitPrice));
		}
		String subtotal = CommonUtils.roundOffDoubleToTwoDecPlaceToString(tp);
		String subtotalTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(tp * 0.05);
		String shipping = Constant.shippingCharges;
		String shippingTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(
				CommonUtils.roundOffDoubleToOneDecPlaceToString((Double.parseDouble(shipping) * 0.05))));
		String pretaxSubtotal = CommonUtils.roundOffDoubleToTwoDecPlaceToString(tp + Double.parseDouble(shipping));
		String tax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(subtotalTax) + Double.parseDouble(shippingTax));
		String subtotalWithTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(subtotal) + Double.parseDouble(subtotalTax));
		String shippingWithTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(shipping) + Double.parseDouble(shippingTax));
		String total = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(subtotalWithTax) + Double.parseDouble(shippingWithTax));
		String[] orderSummaryExpectedValues = {Constant.email, subtotal, subtotalTax, shipping, shippingTax, pretaxSubtotal,
			tax, subtotalWithTax, shippingWithTax, total};
		APIUtils.validateResponsesThroughJSON(Arrays.copyOf(fields, fields.length - 1), orderSummaryExpectedValues);
		ExtentCucumberAdapter.addTestStepLog("Order Summary Object Values");
		ExtentCucumberAdapter.addTestStepLog(Constant.actualResult);
		ExtentCucumberAdapter.addTestStepLog(Constant.expectedResult);
	}
	
	/**
	 * Method to verify Order Summary Object through API in OMS
	 */
	public void verifyOrderItemSummaryObject(String productName) {
		String[] fields = {"UnitPrice", "ListPrice", "TotalLineAmount", "TotalLineTaxAmount", 
			"AdjustedLineAmount", "TotalAdjustedLineTaxAmount", "TotalPrice", "TotalTaxAmount", 
			"GrossUnitPrice", "TotalLineAmountWithTax", "AdjustedLineAmtWithTax", "TotalAmtWithTax", 
			"Quantity", "QuantityOrdered", "QuantityAllocated", "Product2.Name", "Id"}, 
			tableFields = {"OrderSummary.OrderNumber", "Product2.Name"}, 
			tableFieldValues = {Constant.recordId, productName};
			String tableName = "OrderItemSummary";
			CommonUtils.getSOQLResultsFromAPIByGivenFieldsAndValuesInJSON(fields, tableName, tableFields, tableFieldValues);
			String unitPrice = (String) Constant.productSpecs.get(productName).get("Price");
			String listPrice = unitPrice;
			String quantity = (String) Constant.productSpecs.get(productName).get("Quantity");
			String tp = CommonUtils.roundOffDoubleToTwoDecPlaceToString((Double.parseDouble(quantity) * Double.parseDouble(unitPrice)));
			String lineSubtotal = tp;
			String lineSubtotalTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString(Double.parseDouble(tp) * 0.05);
			String adjustedLineSubtotal = tp;
			String adjustedLineSubtotalTax = lineSubtotalTax;
			String pretaxTotal = tp;
			String tax = lineSubtotalTax;
			String grossUnitPrice = CommonUtils.roundOffDoubleToOneDecPlaceToString((Double.parseDouble(tp) + Double.parseDouble(lineSubtotalTax)) / Double.parseDouble(quantity));
			String lineSubtotalWithTax = CommonUtils.roundOffDoubleToTwoDecPlaceToString((Double.parseDouble(tp) + Double.parseDouble(lineSubtotalTax)));
			String lineTotal = lineSubtotalWithTax;
			String totalWithTax = lineSubtotalWithTax;
			String[] orderItemSummaryExpectedValues = {unitPrice, listPrice, lineSubtotal, lineSubtotalTax,
					adjustedLineSubtotal, adjustedLineSubtotalTax, pretaxTotal, tax, grossUnitPrice, lineSubtotalWithTax,
					lineTotal, totalWithTax, quantity, quantity, quantity, productName};
			APIUtils.validateResponsesThroughJSON(Arrays.copyOf(fields, fields.length - 1), orderItemSummaryExpectedValues);
			ExtentCucumberAdapter.addTestStepLog("Order Item Summary Object Values");
			ExtentCucumberAdapter.addTestStepLog(Constant.actualResult);
			ExtentCucumberAdapter.addTestStepLog(Constant.expectedResult);
	}
}
