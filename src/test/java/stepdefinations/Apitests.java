package stepdefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;

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
		given().spec(PropertyData.getRequestSpec()).body(l)
		.when().post("api/login")
		.then().assertThat().statusCode(200).extract().response();
	}

	@Then("numbers of users should be {int}")
	public void getApiAllUsers(int nums) throws FileNotFoundException {
		RestAssured.baseURI="https://reqres.in/";
		u = given().spec(PropertyData.getRequestSpec())
				.when().get("api/users?page=2")
				.then().extract().response().as(UsersList.class);
		Assert.assertEquals(u.getPage(),2);
		Assert.assertEquals(u.getPer_page(),6);
		Assert.assertEquals(u.getTotal(),nums);
		Assert.assertEquals(u.getTotal_pages(),2);
	}

	@Then("the user is availble with data as {string} {string} {string} {string} {string} {string}")
	public void getSingleUser(String email,String first_name,String last_name,String avatar,String url,String text) throws FileNotFoundException {
		RestAssured.baseURI="https://reqres.in/";
		su = given().spec(PropertyData.getRequestSpec())
				.when().get("api/users/2")
				.then().extract().response().getBody().as(SingleUser.class);
		Assert.assertEquals(su.getData().getId(),2);
		Assert.assertEquals(su.getData().getEmail(),email);
		Assert.assertEquals(su.getData().getFirst_name(),first_name);
		Assert.assertEquals(su.getData().getLast_name(),last_name);
		Assert.assertEquals(su.getData().getAvatar(),avatar);
		Assert.assertEquals(su.getSupport().getText(),text);
		Assert.assertEquals(su.getSupport().getUrl(),url);
	}

	@When("I create single user with name {string} and job {string}")
	public void createUser(String name,String job) throws FileNotFoundException {
		AddedUserResponse au = new AddedUserResponse();
		au.setName(name);
		au.setJob(job);
		RestAssured.baseURI="https://reqres.in/";
		aur = given().spec(PropertyData.getRequestSpec())
				.body(au)
				.when().post("api/users")
				.then().assertThat().statusCode(201).extract().response().getBody().as(AddedUserResponse.class);
		System.out.println(aur.getCreatedAt());
		Assert.assertEquals(aur.getName(),name);
		Assert.assertEquals(aur.getJob(),job);

	}
	//		I update users job with "Morpheus" name as "DEV" job
	@When("I update users job with {string} name as {string} job")
	public void updateUser(String name , String job) throws FileNotFoundException {
		RestAssured.baseURI="https://reqres.in/";
		AddedUser au = new AddedUser();
		au.setName(name);
		au.setJob(job);
		AddedUser bu = new AddedUser();
		bu = given().spec(PropertyData.getRequestSpec())
				.body(au)
				.when().put("/api/users/2")
				.then().assertThat().statusCode(200).extract().response().as(AddedUser.class);
		Assert.assertEquals(bu.getJob(),"DEV");

	}

	@And("I register user with {string} email and {string} password")
	public void registerUser(String email,String password) throws FileNotFoundException {
		//RestAssured.baseURI="https://reqres.in/";
		Login l = new Login();
		l.setEmail(email);
		l.setPassword(password);
		String res = given().spec(PropertyData.getRequestSpec())
				.body(l)
				.when().post("/api/register")
				.then().assertThat().statusCode(200).extract().response().asPrettyString();
		JsonPath js = new JsonPath(res);
		Assert.assertEquals(js.getString("token"),"QpwL5tke4Pnpja7X4");
		Assert.assertEquals(js.getInt("id"),4);
	}

}


