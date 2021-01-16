Feature: CRUD FEATURE

Scenario Outline: To perform CRUD API tests
Given I try logging in API
When I create single user with name "morpheus" and job "leader"
Then the user is availble with data as "<email>" "<first_name>" "<last_name>" "<avatar>" "<url>" "<text>"
Examples:
|email					|first_name	|last_name	|avatar									|url								|text																		|																		
|janet.weaver@reqres.in	|Janet		|Weaver		|https://reqres.in/img/faces/2-image.jpg|https://reqres.in/#support-heading	|To keep ReqRes free, contributions towards server costs are appreciated!	|