package stepdefinations;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import Resources.SingleUser;
import Resources.UsersList;
import Utilities.PropertyData;
import Resources.AddedUser;
import Resources.AddedUserResponse;
import Resources.Login;


public class Apitests {
	SingleUser su = new SingleUser();
	UsersList u =  new UsersList();
	AddedUserResponse aur = new AddedUserResponse();

	@Given("I try logging in API")
	public void login() throws Exception {
		PropertyData prop = new PropertyData();
		String email = prop.getValue("loginEmail");
		String password = prop.getValue("loginPassword");
		Login l = new Login();
		l.setEmail(email);
		l.setPassword(password);
		RestAssured.baseURI="https://reqres.in/";
		Response res = given().header("Content-Type","application/json").body(l)
		.when().log().all().post("api/login")
		.then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println(res);
	}

	@Then("numbers of users should be {}")
	public void getApiAllUsers() {
		RestAssured.baseURI="https://reqres.in/";
		u = given()
		.when().get("api/users?page=2")
		.then().extract().response().as(UsersList.class);
		Assert.assertEquals(u.getPage(),2);
		Assert.assertEquals(u.getPer_page(),6);
		Assert.assertEquals(u.getTotal(),12);
		Assert.assertEquals(u.getTotal_pages(),2);
		}
	
	@Then("the user is availble with data as {string} {string} {string} {string} {string} {string}")
	public void getSingleUser(String email,String first_name,String last_name,String avatar,String url,String text) {
		RestAssured.baseURI="https://reqres.in/";
		su = given()
		.when().get("api/users/2")
		.then().log().all().extract().response().as(SingleUser.class);
		Assert.assertEquals(su.getData().getId(),2);
		Assert.assertEquals(su.getData().getEmail(),email);
		Assert.assertEquals(su.getData().getFirst_name(),first_name);
		Assert.assertEquals(su.getData().getLast_name(),last_name);
		Assert.assertEquals(su.getData().getAvatar(),avatar);
		Assert.assertEquals(su.getSupport().getText(),text);
		Assert.assertEquals(su.getSupport().getUrl(),url);
		
		System.out.println("Id "+su.getData().getId()); //just to verify
		System.out.println("Email "+su.getData().getEmail());
		System.out.println("Fname "+su.getData().getFirst_name());
		System.out.println("Lname "+su.getData().getLast_name());
		System.out.println("Avatar "+su.getData().getAvatar());
		System.out.println("text "+su.getSupport().getText());
		System.out.println("URL "+su.getSupport().getUrl());
		}
	
	

		
		public void i_create_single_user_with_name_and_job(String string, String string2) {
		    // Write code here that turns the phrase above into concrete actions
		    throw new io.cucumber.java.PendingException();
		}

	@When("I create single user with name {string} and job {string}")
	public void createUser(String name,String job) {
		AddedUserResponse au = new AddedUserResponse();
		au.setName(name);
		au.setJob(job);
		RestAssured.baseURI="https://reqres.in/";
		aur = given().header("Content-Type","application/json").body(au)
		.when().post("api/users")
		.then().assertThat().statusCode(201).extract().response().as(AddedUserResponse.class);
		System.out.println(aur.getCreatedAt());
		Assert.assertEquals(aur.getName(),name);
		Assert.assertEquals(aur.getJob(),job);
		
	}
	
	
	public void updateUser() {
		RestAssured.baseURI="https://reqres.in/";
		AddedUser au = new AddedUser();
		au.setName("Morpheus");
		au.setJob("DEV");
		AddedUser bu = new AddedUser();
		bu = given().header("Content-Type","application/json")
		.body(au)
		.when().log().all().put("/api/users/2")
		.then().log().all().assertThat().statusCode(200).extract().response().as(AddedUser.class);
		
	}
	
	
	public void registerUser() {
		RestAssured.baseURI="https://reqres.in/";
		Login l = new Login();
		l.setEmail("eve.holt@reqres.in");
		l.setPassword("pistol");
		String res = given().header("Content-Type","application/json")
		.body(l)
		.when().log().all().post("/api/register")
		.then().log().all().assertThat().statusCode(200).extract().response().asPrettyString();
		JsonPath js = new JsonPath(res);
		Assert.assertEquals(js.getString("token"),"QpwL5tke4Pnpja7X4");
		Assert.assertEquals(js.getInt("id"),4);
	}
	
}


