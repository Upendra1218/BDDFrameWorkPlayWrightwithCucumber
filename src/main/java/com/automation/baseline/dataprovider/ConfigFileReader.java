package com.automation.baseline.dataprovider;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.automation.baseline.constant.Constant;
import com.automation.baseline.enums.EnvironmentType;
import com.automation.baseline.enums.WebBrowserType;
import com.automation.baseline.utils.AESUtils;

/**
 * @Author: RafterOne QA
 */
public class ConfigFileReader {

	private Properties properties;
	private final String propertyFilePath = Constant.ConfigPropertiesFilePath;

	public ConfigFileReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader (propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace ( );
			throw new RuntimeException ("config.properties not found at " + propertyFilePath);
		}
	}

	public long getDefaultTimeout() {
		String defaultTimeout = properties.getProperty ("DefaultTimeout");
		if (defaultTimeout != null)
			return Long.parseLong (defaultTimeout);
		else
			throw new RuntimeException ("'DefaultTimeout' not specified in the config.properties file.");
	}

	public String getSfdcUrl() {
		String url;
		if(Constant.env != null)
			url = properties.getProperty ("SFDCURL."+Constant.env);
		else
			url = properties.getProperty ("SFDCURL");
		if (url != null) return url;
		else throw new RuntimeException ("'SFDCURL' not specified in the config.properties file.");
	}
	
	public String getSfdcUsername() {
		String username;
		if(Constant.env != null)
			username = properties.getProperty ("SFDCUsername."+Constant.env);
		else
			username = properties.getProperty ("SFDCUsername");
		if (username != null) return username;
		else throw new RuntimeException ("'SFDCUsername' not specified in the config.properties file.");
	}
	
	public String getSfdcPassword() {
		String password;
		if(Constant.env != null)
			password = properties.getProperty ("SFDCPassword."+Constant.env);
		else
			password = properties.getProperty ("SFDCPassword");
		if (password != null) return AESUtils.decrypt(password);
		else throw new RuntimeException ("'SFDCPassword' not specified in the config.properties file.");
	}
	
	public String getEmailFrom() {
		String emailFrom = properties.getProperty ("EmailFrom");
		if (emailFrom != null) return emailFrom;
		else throw new RuntimeException ("'emailfrom' not specified in the config.properties file.");
	}
	
	public String getEmailTo() {
		String emailTo = properties.getProperty ("EmailTo");
		if (emailTo != null) return emailTo;
		else throw new RuntimeException ("'emailto' not specified in the config.properties file.");
	}
	
	public String getEmailCC() {
		String emailCC = properties.getProperty ("EmailCC");
		if (emailCC != null) return emailCC;
		else throw new RuntimeException ("'emailto' not specified in the config.properties file.");
	}
	
	public String getEmailPassword() {
		String emailPassword = properties.getProperty ("EmailPassword");
		if (emailPassword != null) return AESUtils.decrypt(emailPassword);
		else throw new RuntimeException ("'emailpwd' not specified in the config.properties file.");
	}
	
	public String getEmailSMTP() {
		String emailSMTP = properties.getProperty ("EmailSMTP");
		if (emailSMTP != null) return emailSMTP;
		else throw new RuntimeException ("'emailsmtp' not specified in the config.properties file.");
	}
	
	public String getCardHolder() {
		String cardHolderName = properties.getProperty ("CardHolder");
		if (cardHolderName != null) return cardHolderName;
		else throw new RuntimeException ("'CardHolder' not specified in the config.properties file.");
	}
	
	public String getCardNumber() {
		String cardNo = properties.getProperty ("CardNumber");
		if (cardNo != null) return cardNo;
		else throw new RuntimeException ("'CardNumber' not specified in the config.properties file.");
	}
	
	public String getCardCVV() {
		String cvvNo = properties.getProperty ("CVV");
		if (cvvNo != null) return cvvNo;
		else throw new RuntimeException ("'CVV' not specified in the config.properties file.");
	}
	
	public String getCardExpireDateMonth() {
		String month = properties.getProperty ("Expiration");
		if (month != null) return month;
		else throw new RuntimeException ("'Expiration' not specified in the config.properties file.");
	}
	
	public String getSecretKey() {
		String secretKey = properties.getProperty ("SecretKey");
		if (secretKey != null) return secretKey;
		else throw new RuntimeException ("'SecretKey' not specified in the config.properties file.");
	}

	public String getSalt() {
		String saltValue = properties.getProperty ("Salt");
		if (saltValue != null) return saltValue;
		else throw new RuntimeException ("'Salt' not specified in the config.properties file.");
	}

	public String getPayloadURL() {
		String payloadURL;
		if(Constant.env != null)
			payloadURL = properties.getProperty ("PayloadURL."+Constant.env);
		else
			payloadURL = properties.getProperty ("PayloadURL");
		if (payloadURL != null) return payloadURL;
		else throw new RuntimeException ("'PayloadURL' not specified in the config.properties file.");
	}
	
	public String getSecurityToken() {
		String securityToken;
		if(Constant.env != null)
			securityToken = properties.getProperty ("SecurityToken."+Constant.env);
		else
			securityToken = properties.getProperty ("SecurityToken");
		if (securityToken != null) 
			return securityToken;
		else{
			//System.out.println("'SecurityToken' values not specified in the config.properties file.");
			return "";
		}
	}
	
	public String getClientId() {
		String clientId;
		if(Constant.env != null)
			clientId = properties.getProperty ("ClientId."+Constant.env);
		else
			clientId = properties.getProperty ("ClientId");
		if (clientId != null) return clientId;
		else throw new RuntimeException ("'ClientId' not specified in the config.properties file.");
	}
	
	public String getClientSecret() {
		String clientSecret;
		if(Constant.env != null)
			clientSecret = properties.getProperty ("ClientSecret."+Constant.env);
		else
			clientSecret = properties.getProperty ("ClientSecret");
		if (clientSecret != null) return clientSecret;
		else throw new RuntimeException ("'ClientSecret' not specified in the config.properties file.");
	}
	
	public String getAIOAuthorizationToken() {
		String aioToken = properties.getProperty ("AIOToken");
		if (aioToken != null) return aioToken;
		else throw new RuntimeException ("'AIOToken' not specified in the config.properties file.");
	}

	public String getAIOAutomationOwnerID() {
		 String ownerId = properties.getProperty ("AIOAutomationOwnerID");
		 if (ownerId != null) return ownerId;
		 else throw new RuntimeException ("'AIOAutomationOwnerID' not specified in the config.properties file.");
	}
	 
	public String getJiraProjectId() {
		 String projectId = properties.getProperty ("JIRAProjectId");
		 if (projectId != null) return projectId;
		 else throw new RuntimeException ("'JIRAProjectId' not specified in the config.properties file.");
	}
	
	public String getJiraUrl() {
		 String jiraUrl = properties.getProperty ("JIRAURL");
		 if (jiraUrl != null) return jiraUrl;
		 else throw new RuntimeException ("'JIRAURL' not specified in the config.properties file.");
	}
	
	public String getJiraToken() {
		 String token = properties.getProperty ("JIRAToken");
		 if (token != null) return token;
		 else throw new RuntimeException ("'JIRAToken' not specified in the config.properties file.");
	}
	
	public String getJiraStory() {
		 String projectId = properties.getProperty ("JIRAStory");
		 if (projectId != null) return projectId;
		 else throw new RuntimeException ("'JIRAStory' not specified in the config.properties file.");
	}
	 
	public WebBrowserType getWebBrowser() {
			String browserName = properties.getProperty("Browser");
			if(browserName == null || browserName.equalsIgnoreCase("chrome")) return WebBrowserType.CHROME;
			else if(browserName.equalsIgnoreCase("firefox")) return WebBrowserType.FIREFOX;
			else if(browserName.equalsIgnoreCase("chromium")) return WebBrowserType.CHROMIUM;
			else if(browserName.equalsIgnoreCase("edge")) return WebBrowserType.EDGE;
			else if(browserName.equalsIgnoreCase("webkit")) return WebBrowserType.WEBKIT;
			else throw new RuntimeException("'Browser' Name Key value in config.properties is not matched : " + browserName);
	}
	 
	public EnvironmentType getEnvironment() {
			String environmentName = properties.getProperty("Environment");
			if(environmentName == null || environmentName.equalsIgnoreCase("web")) return EnvironmentType.WEB;
			else throw new RuntimeException("'Environment' Type Key value in config.properties is not matched : " + environmentName);
	}
	
	public String getStoreUrl() {
		String url;
		if(Constant.env != null)
			url = properties.getProperty ("StoreURL."+Constant.env);
		else
			url = properties.getProperty ("StoreURL");
		if (url != null) return url;
		else throw new RuntimeException ("'StoreURL' not specified in the config.properties file.");
	}
	
	public String getStoreUsername() {
		String username;
		if(Constant.env != null)
			username = properties.getProperty ("StoreUsername."+Constant.env);
		else
			username = properties.getProperty ("StoreUsername");
		if (username != null) return username;
		else throw new RuntimeException ("'StoreUsername' not specified in the config.properties file.");
	}
	
	public String getStorePassword() {
		String password;
		if(Constant.env != null)
			password = properties.getProperty ("StorePassword."+Constant.env);
		else
			password = properties.getProperty ("StorePassword");
		if (password != null) return AESUtils.decrypt(password);
		else throw new RuntimeException ("'StorePassword' not specified in the config.properties file.");
	}
	
	public String getCycleDescripiton() {
		String desc = properties.getProperty ("CycleDescription");
		if (desc != null) return desc;
		else throw new RuntimeException ("'CycleDescription' not specified in the Configuration.properties file.");
	}
	
	public String getEmailSubject() {
		String value = properties.getProperty ("Subject");
		if (value != null) return value;
		else throw new RuntimeException ("'Subject' not specified in the Configuration.properties file.");
	}

	public String getEmailInitial() {
		String value = properties.getProperty ("Initial");
		if (value != null) return value;
		else throw new RuntimeException ("'Initial' not specified in the Configuration.properties file.");
	}

	public String getEmailMessage() {
		String value = properties.getProperty ("Message");
		if (value != null) return value;
		else throw new RuntimeException ("'Message' not specified in the Configuration.properties file.");
	}

	public String getEmailRegards() {
		String value = properties.getProperty ("Regards");
		if (value != null) return value;
		else throw new RuntimeException ("'Regards' not specified in the Configuration.properties file.");
	}

	public String getEmailSender() {
		String value = properties.getProperty ("Sender");
		if (value != null) return value;
		else throw new RuntimeException ("'Sender' not specified in the Configuration.properties file.");
	}

	public String getEmailReportName() {
		String value = properties.getProperty ("ReportName");
		if (value != null) return value;
		else throw new RuntimeException ("'ReportName' not specified in the Configuration.properties file.");
	}
	
	public String getEmaiHtmlReportName() {
		String value = properties.getProperty ("HtmlReportName");
		if (value != null) return value;
		else throw new RuntimeException ("'HtmlReportName' not specified in the Configuration.properties file.");
	}
	
	public String getEmailSignatureLogo() {
		String value = properties.getProperty ("EmailSignatureLogo");
		if (value != null) return value;
		else throw new RuntimeException ("'ReportName' not specified in the Configuration.properties file.");
	}
	
	public String getPdfReportFilePath() {
		String value = properties.getProperty ("PdfReportFilePath");
		if (value != null) return value;
		else throw new RuntimeException ("'PdfReportFilePath' not specified in the Configuration.properties file.");
	}
	public String getHtmlReportFilePath() {
		String value = properties.getProperty ("HtmlReportFilePath");
		if (value != null) return value;
		else throw new RuntimeException ("'HtmlReportFilePath' not specified in the Configuration.properties file.");
	}
	
	public String getAddress1() {
		String value = properties.getProperty ("Address1");
		if (value != null) return value;
		else throw new RuntimeException ("'Address1' not specified in the Configuration.properties file.");
	}
	
	public String getAddress2() {
		String value = properties.getProperty ("Address2");
		if (value != null) return value;
		else throw new RuntimeException ("'Address2' not specified in the Configuration.properties file.");
	}
	
	public String getCity() {
		String value = properties.getProperty ("City");
		if (value != null) return value;
		else throw new RuntimeException ("'City' not specified in the Configuration.properties file.");
	}
	
	public String getState() {
		String value = properties.getProperty ("State");
		if (value != null) return value;
		else throw new RuntimeException ("'State' not specified in the Configuration.properties file.");
	}
	
	public String getCountry() {
		String value = properties.getProperty ("Country");
		if (value != null) return value;
		else throw new RuntimeException ("'Country' not specified in the Configuration.properties file.");
	}
	
	public String getZipCode() {
		String value = properties.getProperty ("ZipCode");
		if (value != null) return value;
		else throw new RuntimeException ("'ZipCode' not specified in the Configuration.properties file.");
	}
	
	public String getPhoneNumber() {
		String value = properties.getProperty ("PhoneNumber");
		if (value != null) return value;
		else throw new RuntimeException ("'PhoneNumber' not specified in the Configuration.properties file.");
	}
	
	public String getAddressSearchValue() {
		String value = properties.getProperty ("AddressSearchValue");
		if (value != null) return value;
		else throw new RuntimeException ("'AddressSearchValue' not specified in the Configuration.properties file.");
	}
	
	public String getAddressSelectValue() {
		String value = properties.getProperty ("AddressSelectValue");
		if (value != null) return value;
		else throw new RuntimeException ("'AddressSelectValue' not specified in the Configuration.properties file.");
	}
}