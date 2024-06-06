package com.Providio_Automation.baseline.pageobjects;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.exception.CustomException;
import com.Providio_Automation.baseline.utils.CommonUtils;
import com.Providio_Automation.baseline.utils.ExcelUtils;
import com.Providio_Automation.baseline.utils.PlaywrightUtils;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.microsoft.playwright.Locator;

public class PDP {

	/**
	 * Method to Select Size for a Product on PDP
	 * @param size - Size to be selected in UI
	 * @param field - field name
	 */
	public void selectSize(String size, String field) {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
		List<Locator> options =  Constant.PAGE.locator(Constant.locatorsMap.get(field.toLowerCase().replace(" ", "_")+"_options")).all();
		//System.out.println(options.size());
		int index = -1;
		for(int i=0; i<options.size(); i++) {
			if(options.get(i).textContent().contains(size)) {
				index = i + 1;
				break;
			}
		}
		PlaywrightUtils.click(PlaywrightUtils.getElement(field, Constant.PAGE));
		PlaywrightUtils.waitForSec();
		for(int j=1; j<index; j++) {
			PlaywrightUtils.keyPress("ArrowDown", Constant.PAGE);
//			try {
//				TimeUnit.MILLISECONDS.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		PlaywrightUtils.keyPress("Enter", Constant.PAGE);
	}
	
	/**
	 * Method to Select Quantity for a Product on PDP
	 * @param quantity - Quantity to be selected in UI
	 * @param field - field name
	 */
	public void enterQuantity(String quantity, String field) {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
		PlaywrightUtils.click(PlaywrightUtils.getElement(field, Constant.PAGE));
		PlaywrightUtils.waitForSec();
		PlaywrightUtils.keyPress("Backspace", Constant.PAGE);
		PlaywrightUtils.keyPress("Backspace", Constant.PAGE);
		PlaywrightUtils.clearAndSetValueUsingKeyboard(quantity, PlaywrightUtils.getElement(field, Constant.PAGE));
	}
	
	/**
	 * Method to Select Color for a Product on PDP
	 * @param color - Color to be selected in UI
	 * @param field - field name
	 */
	public void selectColor(String color, String field) {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement(field, Constant.PAGE));
		List<Locator> options =  Constant.PAGE.locator(Constant.locatorsMap.get(field.toLowerCase().replace(" ", "_")+"_options")).all();
		int index = -1;
		for(int i=0; i<options.size(); i++) {
			if(options.get(i).textContent().contains(color)) {
				index = i + 1;
				break;
			}
		}
		PlaywrightUtils.click(PlaywrightUtils.getElement(field, Constant.PAGE));
		PlaywrightUtils.waitForSec();
		for(int j=1; j<index; j++) {
			PlaywrightUtils.keyPress("ArrowDown", Constant.PAGE);
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PlaywrightUtils.keyPress("Enter", Constant.PAGE);
	}
	
	/**
	 * Method to store price of a product in Map
	 */
	public String getPrice() {
		PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Product Price", Constant.PAGE));
		return PlaywrightUtils.getText(PlaywrightUtils.getElement("Product Price", Constant.PAGE)).replace("$", "").trim();
	}
	
	/**
	 * Method to select all Products defined in Excel File
	 */
	public void selectProductsFromExcel() {
		String category = null, subCategory = null, product = null, size = null, 
				color = null, quantity = null;
		int productCount = ExcelUtils.getRowCount("Products");
		if(productCount <= 0) 
			throw new CustomException("Please define some Products in Excel file");
		for(int i=1; i<=productCount; i++) {
			try {
				category = ExcelUtils.readExcel("Products", String.valueOf(i), "Category");
				subCategory = ExcelUtils.readExcel("Products", String.valueOf(i), "Sub_Category");
				product = ExcelUtils.readExcel("Products", String.valueOf(i), "Product");
				size = ExcelUtils.readExcel("Products", String.valueOf(i), "Size");
				color = ExcelUtils.readExcel("Products", String.valueOf(i), "Color");
				quantity = ExcelUtils.readExcel("Products", String.valueOf(i), "Quantity");
			} catch (Exception e) {
				e.printStackTrace();
			}
			PlaywrightUtils.hover(CommonUtils.findCategoryByExactText(category, Constant.PAGE));
		    PlaywrightUtils.click(CommonUtils.findCategoryByExactText(subCategory, Constant.PAGE));
		    ExtentCucumberAdapter.addTestStepLog("Navigated to '"+subCategory+"' Sub-Category under '"+category+"' Category successfully");
			CommonUtils.productSelect(product);
			selectColor(color, "Color");
		    selectSize(size, "Size");
		    enterQuantity(quantity, "Quantity PDP");
		    PlaywrightUtils.waitForMoreSec(2);
		    //need to add a comment
		    if(PlaywrightUtils.elementIsEnabled(PlaywrightUtils.getElement("Add to Cart", Constant.PAGE))) {
		    PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Add to Cart", Constant.PAGE));
			PlaywrightUtils.click(PlaywrightUtils.getElement("Add to Cart", Constant.PAGE));
			PlaywrightUtils.waitForAnElement(PlaywrightUtils.getElement("Product added to cart", Constant.PAGE));
		    PlaywrightUtils.waitForMoreSec(2);
		    CommonUtils.addKeyValueInGivenMap("Price", getPrice(), product, Constant.productSpecs);
		    CommonUtils.addKeyValueInGivenMap("Size", size, product, Constant.productSpecs);
		    CommonUtils.addKeyValueInGivenMap("Color", color, product, Constant.productSpecs);
		    CommonUtils.addKeyValueInGivenMap("Quantity", quantity, product, Constant.productSpecs);
		    ExtentCucumberAdapter.addTestStepLog("Selected '" +product+ "' Product with '" +color+ "' Color and '" +quantity+"' Quantity");
		    }else {
		    	ExtentCucumberAdapter.addTestStepLog("Selected '" +product+ "' Product with '" +color+ "' Color and '" +quantity+"' Quantity is Out of Stock" );
		    	System.out.println("Product is out of stock");
		    }
		}
	}
}
