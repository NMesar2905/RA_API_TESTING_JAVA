#Author: Nicolas MEsa

Feature: Testing Reqres API Methods

	@ListUsers
  Scenario Outline: List users by page
    Given The "page" "<page>" as query param
    When User calls "APIPath" with a "GET" request
    Then The API got success with status code 200

		Examples: 
      | page | 
      | 1    |
      | 2    |
      | 3    |
	
	
	@GetUser
  Scenario Outline: Get user info
    Given The "id" "<id>" as path param
    When User calls "GetUser" with a "GET" request
    Then The API got success with status code 200
    And Verify if "id" have the correct value "<id>"
    
    Examples: 
      | id | 
      | 2  |
      | 3  |
      | 5  |

	@CreateUser
  Scenario Outline: Creation of an user
    Given The "<name>" and "<job>" for user payload
    When User calls "APIPath" with a "POST" request
    Then The API got success with status code 201
    And Verify if "name" have the correct value "<name>"
    And Verify if "job" have the correct value "<job>"

		Examples: 
      | name    | job 				 |
      | Nicolas | QA Developer |
      | Natalia | Web Designer |
      | Bryan   | QA Mentor    |
      
	@UpdateUser
	Scenario Outline: Update User
    Given The "<name>" and "<job>" for user payload
    When User calls "UpdateUser" with a "PUT" request
    Then The API got success with status code 200
    And Verify if "name" have the correct value "<name>"
    And Verify if "job" have the correct value "<job>"
    
		Examples: 
      | name    | job 				 |
      | Nicolas | QA Developer |    
    
    
    
    
    