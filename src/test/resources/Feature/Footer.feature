@footer_Section_TestCases 
Feature: Verifying the footer section Links

 @Verify_random_footer_links_navigation @Regression
 Scenario Outline: Verify random footer links navigation
  When User validates the current page with the "Home Page" page and is redirected to the desired page
  And User scroll into "Footer" section and click on "Random" on the "Home" page

  
  