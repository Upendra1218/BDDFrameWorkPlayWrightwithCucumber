@ETG @OrderSummaryStatusValidations
Feature: Order Summary Object Status Validaton

  @OrderSummaryStatusValidations
  Scenario Outline: Order Summary Object Status Validaton
    Given User navigates to the "Storefront" Url
    When User navigates to <SubCategory> Sub-Category under <Category> Category on "Home" Page
    And User selects the <Product> Product with <Color> Color, <Size> Size  and <Quantity> Quantity from "Product List" Page
    And User clicks on the "Add to Cart" button on "Product Detail" Page
    Then Verify "Product added to cart" message on "Product Detail" Page
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
    When User login into the "Salesforce"
    And User wait for "Order Summary" record to be created in "OMS" through API
    And Verify "Status" is "Created" for "Order Summary" record
    And Verify "Status" is "ORDERED" for "Order Product Summary" record
    When User updates "Status" to "Approved" for "Order Summary" record
    And User wait for "Fulfillment Order" record to be created in "OMS" through API
    And Verify "Status" is "Waiting to Fulfill" for "Order Summary" record
    And Verify "Status" is "ALLOCATED" for "Order Product Summary" record
    And User updates "Status" to "Fulfilled" for "Fulfillment Order" record
    Then Verify "Status" updated to "Payment Settlement" for "Order Summary" record
    And Verify "Status" is "FULFILLED" for "Order Product Summary" record
    And Verify "Status" updated to "Fulfilled" for "Order Summary" record

    @OrderSummaryStatusValidations
    Examples: 
      | SubCategory | Category | Product              | Color  | Size | Quantity | Email                           |
      | "Pants"     | "Men"    | "Straight Leg Pants" | "Gray" | "32" | "4"      | "yatharthdubeycorp01@gmail.com" |
