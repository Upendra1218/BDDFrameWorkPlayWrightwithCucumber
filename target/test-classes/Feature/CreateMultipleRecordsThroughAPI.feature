@ETG @CreateMultipleRecordsThroughAPI
Feature: CreateMultipleRecordsThroughAPI

@CreateMultipleRecordsThroughAPI
Scenario Outline:CreateMultipleRecordsThroughAPI
  When User creates multiple records via the API with the field name <ApiFiledName>, totaling <Count> records, using the name <Value> and the entity <Entity>.
  

  
  @CreateMultipleRecordsThroughAPI
    Examples: 
      | ApiFiledName |Count    |Value			 |Entity    |
      | "LastName"   |  4      |"Test Etg" |"Account" |