@ETG @PlaceOrderAsGift
Feature: Purchase Product from B2B Store as a Gift

  @PlaceOrderAsGift
  Scenario Outline: Purchase Product from B2B Store as a Gift
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
    And User checks the "This is a Gift" checkbox on "Checkout" Page
    And User enters <Message> in the "Message" field on "Checkout" Page
    And User clicks on the "Next Payment" button on "Checkout" Page
    And User enters all the "Credit Card Details" on "Payment" Page
    And User clicks on the "Next Review Order" button on "Checkout" Page
    And User clicks on the "Place Order" button on "Checkout" Page
    Then Verify "Thank you for your order" message on "Order Confirmation" Page
    #When User login into the "Salesforce"
    #And User wait for "Order Summary" record to be created in "OMS" through API
    #Then Verify "Object Summary" Object created with required field values in "OMS" through API
    #And Verify "Object Product Summary" Object created with required field values in "OMS" through API
    #And Verify "IsGift" value is "true" for "Order Delivery Group Summary" record
    #When User updates "Status" to "Approved" for "Order Summary" record
    #And User updates "Status" to "Fulfilled" for "Fulfillment Order" record
    #Then Verify "Status" updated to "Payment Settlement" for "Order Summary" record
    #And Verify "Status" updated to "Fulfilled" for "Order Summary" record
    #And Verify "Captured Amount" is updated on "Order Summary" record

    @PlaceOrderAsGift
    Examples: 
      | SubCategory | Category | Product                    | Color   | Size | Quantity | Email                           | Message        |
      | "Pants"     | "Men"    | "Basic Leg Trousers" | "White" | "32" | "2"      | "yatharthdubeycorp01@gmail.com" | "Test Message" |
