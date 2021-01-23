Feature: CRUD FEATURE

Scenario Outline: To perform CRUD API tests
Given I try logging in API
When I create single user with name "morpheus" and job "leader"
Then the user is availble with data as "<email>" "<first_name>" "<last_name>" "<avatar>" "<url>" "<text>"
Examples:
|email									|first_name	|last_name	|avatar																	|url																|text																																			|																		
|janet.weaver@reqres.in	|Janet			|Weaver			|https://reqres.in/img/faces/2-image.jpg|https://reqres.in/#support-heading	|To keep ReqRes free, contributions towards server costs are appreciated!	|


Scenario: To perform update register and count users 
When I update users job with "Morpheus" name as "DEV" job
And I register user with "eve.holt@reqres.in" email and "pistol" password
Then numbers of users should be 12