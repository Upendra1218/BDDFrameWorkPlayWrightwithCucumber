@HomePageTestCases 
Feature: Verifying the Home Page Links

 @Verify_random_Category_and_Sub-Category_selection @Regression
  Scenario Outline: Verify random Category and Sub-Category selection
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User navigates to a random sub-category under a random category on the "Home" page

 @Verify_Mega_Menu_navigation @Smoke @Regression
  Scenario Outline: Verify Mega Menu navigation
	When User validates the current page with the "Home Page" page and is redirected to the desired page
	And User navigates to <SubCategory> Sub-Category under <Category> Category on "Home" Page
	Then Verify the "PLP Page Product Grid" on "Login page" Page

    Examples: 
      | SubCategory | Category |
      | "Pants"     | "Men"    | 
      

 @Verify_Sign_In_Link @Smoke @Regression
 Scenario Outline: Verify Sign In Link 
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User clicks on the "Sign In" button on "Home" Page
  Then Verify the "Sign In Page Title" on "Login page" Page
  
  @Verify_WishList_Link @Smoke @Regression
  Scenario Outline: Verify wish list Link 
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User clicks on the "Wish List" button on "Home" Page
  Then Verify the "WishList Page Title" on "WishListpage" Page
  
  @Verify_Search_Bar_functionality @Smoke @Regression
  Scenario Outline: Verify Search Bar functionality
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User enters <SearchItem> in the "Search Bar" field on "Home" Page
  Then Verify the "Search Header" on "Search" Page
  
    Examples: 
      | SearchItem |
      | "Pants"     |
  
  @Verify_Category_Landing_Page_navigation @Smoke @Regression
  Scenario Outline: Verify Category Landing Page navigation
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User navigates to <Mega Menu> Category landing page from "Home" Page
  Then Verify the "Category Header" on "Category-landing" Page

    Examples: 
      | Mega Menu |
      | "Men"     |
      
  @Verify_Carousel_navigation_with_Prev/Next_arrows @Regression
  Scenario Outline: Verify Carousel navigation with Prev/Next arrows
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User clicks on the "Slick_Next" button on "Home" Page
  Then Verify the "Hero Banner" on "Home" Page
  And User clicks on the "Slick_Prev" button on "Home" Page
  Then Verify the "Hero Banner" on "Home" Page
  
  
  @Verify_Carousel_links @Regression
  Scenario Outline: Verify Carousel links
  When User validates the current page with the "Home Page" page and is redirected to the desired page
   And User clicks on the "Hero Banner Link" button on "Home" Page
  Then Verify the "Category Header" on "Category-landing" Page
  
  
  
  @Verify_Category_Tiles_navigation @Regression
  Scenario Outline: Verify Category Tiles navigation
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User navigates to <Category> category tile on "Home" page
  Then Verify the "Category Header" on "Category-landing" Page
  
    Examples: 
      | Category  |
      | "Mens"    |
      
   @Verify_random_Category_Tile_navigation @Regression
   Scenario Outline: Verify random Category Tile navigation
   When User validates the current page with the "Home Page" page and is redirected to the desired page
   And User navigates to "Random" category tile on "Home" page
   Then Verify the "Category Header" on "Category-landing" Page
  

  
  
  
  

    
   
   
   