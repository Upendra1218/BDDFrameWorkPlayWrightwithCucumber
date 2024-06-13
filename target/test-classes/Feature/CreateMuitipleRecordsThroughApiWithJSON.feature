@ETG @CreateMultipleRecordsThroughAPI
Feature: CreateMultipleRecordsThroughAPIWithJSON

@CreateMultipleRecordsThroughAPIWithJSON
Scenario Outline:CreateMultipleRecordsThroughAPIWithJSON
  When User creates multiple records via the API with JSON data:
  """
      {
        "FieldValues": {
          "Salutation": "Mr.",
          "FirstName": "Vijay",
          "LastName": "Reddy",
          "AccountNumber": "122222222222222222"
        },
        "Entity": "Account",
        "Count": 2,
      }
      """
      
    