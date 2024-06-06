package com.Providio_Automation.baseline.managers;

import java.util.ArrayList;
import java.util.List;

import com.Providio_Automation.baseline.constant.Constant;
import com.Providio_Automation.baseline.enums.EnvironmentType;
import com.Providio_Automation.baseline.enums.WebBrowserType;
import com.Providio_Automation.baseline.exception.CustomException;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * @Author: ETG QA
 */
public class PlaywrightManager {

	public static WebBrowserType webBrowserType;
	public static EnvironmentType environmentType;
	
	public PlaywrightManager() {
		
		webBrowserType = FileReaderManager.getInstance().getConfigReader().getWebBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}
	
	/**
	 * Method to initialize Playwright, Browser and Page
	 * @return Current Browser Page
	 */
	public static Page intializePlaywright() {
		try {
			if (Constant.PLAYWRIGHT == null)
				Constant.PLAYWRIGHT = Playwright.create();
			Constant.PAGE = intializeBrowser();
//			Constant.BROWSERCONTEXT.setDefaultTimeout(
//				FileReaderManager.getInstance().getConfigReader().getDefaultTimeout());
			return Constant.PAGE;
			}
			catch(Exception e) {
				throw new CustomException("Playwright is not created");
			}
			
	}
	
	/**
	 * Method to initialize Browser based on value defined in Configuration Properties file
	 * @return Current Browser Page
	 */
	public static Page intializeBrowser() {
		try {
			switch (webBrowserType) {
			case FIREFOX:
				Constant.BROWSERCONTEXT = Constant.PLAYWRIGHT.firefox().launch().newContext();
				return Constant.BROWSERCONTEXT.newPage();

			case CHROME:
				LaunchOptions lOptions = new LaunchOptions();
				lOptions.setHeadless(false);
				lOptions.channel = "chrome";
				List<String> args = new ArrayList<>();
				args.add("--start-fullscreen");
				lOptions.setArgs(args);
				Constant.BROWSERCONTEXT = Constant.PLAYWRIGHT.chromium().launch(lOptions).newContext();
				return Constant.BROWSERCONTEXT.newPage();
				
				
			case WEBKIT:
				Constant.BROWSERCONTEXT = Constant.PLAYWRIGHT.webkit().launch().newContext();
				return Constant.BROWSERCONTEXT.newPage();

			case CHROMIUM:
				Constant.BROWSERCONTEXT = Constant.PLAYWRIGHT.chromium().launch().newContext();
				return Constant.BROWSERCONTEXT.newPage();
				
			case EDGE:			
				LaunchOptions lpcOptionsEdge = new BrowserType.LaunchOptions();
				lpcOptionsEdge.setHeadless(false);
				lpcOptionsEdge.channel = "msedge";
				Constant.BROWSERCONTEXT = Constant.PLAYWRIGHT.chromium().launch(lpcOptionsEdge).newContext();
				return Constant.BROWSERCONTEXT.newPage();
			
			default:
				throw new CustomException("Playwright is not created");
			}
		}
		catch(Exception e) {
			throw new CustomException("Playwright is not created");
		}			
	}
	
	/**
	 * Method to close current Browser and Page
	 */
	public static void closePlaywright() {
		try {
			Constant.PAGE.close();
			Constant.BROWSERCONTEXT.close();
		}
		catch(Exception e) {
				throw new CustomException("Playwright is not created");
		}	
	}
}
