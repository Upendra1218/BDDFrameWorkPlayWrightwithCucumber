@HomePageTestCases 
Feature: HomePageLinksValidation

 @SignIn_Link_Validation @Smoke 
 Scenario Outline: SignInValidation
  #Given User navigates to the "Storefront" Url
  When User clicks on the "Sign In" button on "Home" Page
  Then Verify the "Sign In Page Title" on "Login page" Page
  
  @WishList_Link_Validation @Smoke
  Scenario Outline: WishList_Link_Validation
  When User clicks on the "Wish List" button on "Home" Page
  Then Verify the "WishList Page Title" on "WishListpage" Page