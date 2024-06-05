@ETG @CreateAccountRecordsonSalesforce
Feature: CreateAccountRecordsonSalesforce

@CreateAccountRecordsonSalesforce
Scenario Outline:CreateAccountRecordsonSalesforce
  When User login into the "Salesforce"
  Then Verify the "Main Div" on "SalesForce Home" Page
  And User clicks on the "Nine dots" button on "SalesForce Home" Page
  And User enters <App> in the "Search Apps and Item" field on "SalesForce Home" Page
  And User clicks on the <App> links on the "Salesforce App" Page
  And User clicks on the "New Account" button on "Account" Page
  And User clicks on the "Next" button on "Account" Page
  And User enters <LastName> in the "New Account LName" field on "Account" Page
  And User clicks on the "Account Save" button on "Account" Page
 
 
 
  
  @CreateAccountRecordsonSalesforce
    Examples: 
      |App        |LastName     | 
      |"Accounts" | "Upendra12" | 