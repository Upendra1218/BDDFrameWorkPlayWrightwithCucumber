@ETG @PlaceOrder
Feature: Purchase Product from B2B Store

  @PlaceOrder
  Scenario Outline: Purchase Product from B2B Store
    Given User navigates to the "Storefront" Url
    And User selects all the Products from "Excel" file
    When User clicks on the "Checkout" button on "Cart" Page
    And User clicks on the "Guest Checkout" button on "Checkout" Page
    And User enters <Email> in the "Email" field on "Checkout" Page
    And User clicks on the "Continue as guest" button on "Checkout" Page
    And User enters all the "Shipping Details" on "Checkout" Page
    And User clicks on the "Next Payment" button on "Checkout" Page
    And User enters all the "Credit Card Details" on "Payment" Page
    And User clicks on the "Next Review Order" button on "Checkout" Page
    And User clicks on the "Place Order" button on "Checkout" Page
    Then Verify "Thank you for your order" message on "Order Confirmation" Page
    #When User login into the "Salesforce"
    #And User switches to <App> app in "Salesforce"
    #And User wait for "Order Summary" record to be created in "OMS" through API
    #Then Verify "Object Summary" Object created with required field values in "OMS" through API
    #And Verify "Object Product Summary" Object created with required field values in "OMS" through API
    #And Verify "IsGift" value is "false" for "Order Delivery Group Summary" record
    #When User updates "Status" to "Approved" for "Order Summary" record
    #And User wait for "Fulfillment Order" record to be created in "OMS" through API
    #Then Verify "Status" updated to "Waiting to Fulfill" for "Order Summary" record
    #And User updates "Status" to "Fulfilled" for "Fulfillment Order" record
    #Then Verify "Status" updated to "Payment Settlement" for "Order Summary" record
    #And Verify "Status" updated to "Fulfilled" for "Order Summary" record
    #And Verify "Captured Amount" is updated on "Order Summary" record
    
    @PlaceOrder
    Examples: 
      | App                | Email                           |
      | "Order Management" | "yatharthdubeycorp01@gmail.com" |
