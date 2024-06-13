@ETG @CreateAccountonStoreFront
Feature: CreateAccountonStoreFront

  @CreateAccountonStoreFront
  Scenario Outline: CreateAccountonStoreFront
    Given User navigates to the "Storefront" Url
    And User clicks on the "Sign In" button on "Home" Page
    And User clicks on the "Create Account" button on "Sign In" Page
    When User enters all the values of Register <FirstName> fName, <LastName> lname, <Phone> phone, <Email> email, <ConformEmail> conformemail, <Password> pwd, and <ConformPassword> conformpwd on "Create Account" Page
    And User clicks on the "Register Submit" button on "Create Account" Page
    Then Verify the "My Account Page Title" on "My Account page" Page

    @CreateAccountonStoreFront
    Examples: 
      | FirstName   | LastName | Phone        | Email                   | ConformEmail            | Password       | ConformPassword |
      | "Upendra" | "y"      | "5555555555" | "upendra@gmail.com" | "upendra@gmail.com" | "Upendra@1234" | "Upendra@1234"  |
			
      
      
      