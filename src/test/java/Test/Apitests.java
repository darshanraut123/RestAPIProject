package Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import Resources.SingleUser;
import Resources.UsersList;
import Resources.AddedUser;
import Resources.AddedUserResponse;


public class Apitests {
	SingleUser su = new SingleUser();
	UsersList u =  new UsersList();
	AddedUserResponse aur = new AddedUserResponse();
	
	@Test
	public void login() {
		RestAssured.baseURI="https://reqres.in/";
		Response res = given().header("Content-Type","application/json")
				.body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "    \"password\": \"cityslicka\"\r\n"
				+ "}")
		.when().log().all().post("api/login")
		.then().log().all().assertThat().statusCode(200).extract().response();
		System.out.println(res);
		
	}
	
	@Test
	public void postApi() throws Exception {
		RestAssured.baseURI="https://reqres.in/";
		Response r  = given().header("Content-Type","application/json")
				.body("{\r\n"
				+ "    \"name\": \"morpheus\",\r\n"
				+ "    \"job\": \"leader\"\r\n"
				+ "}")
		.when().log().all().post("api/users")
		.then().log().all().assertThat().statusCode(201).extract().response();
		}

	@Test
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
	
	@Test
	public void getSingleUser() {
		RestAssured.baseURI="https://reqres.in/";
		su = given()
		.when().get("api/users/2")
		.then().extract().response().as(SingleUser.class);
		Assert.assertEquals(su.getData().getId(),2);
		Assert.assertEquals(su.getData().getEmail(),"janet.weaver@reqres.in");
		Assert.assertEquals(su.getData().getFirst_name(),"Janet");
		Assert.assertEquals(su.getData().getLast_name(),"Weaver");
		Assert.assertEquals(su.getData().getAvatar(),"https://reqres.in/img/faces/2-image.jpg");
		Assert.assertEquals(su.getSupport().getText(),"To keep ReqRes free, contributions towards server costs are appreciated!");
		Assert.assertEquals(su.getSupport().getUrl(),"https://reqres.in/#support-heading");
		}
	
	@Test
	public void addUser() {
		AddedUser au = new AddedUser();
		au.setName("Darshan");
		au.setJob("QA");
		RestAssured.baseURI="https://reqres.in/";
		aur = given().header("Content-Type","application/json").body(au)
		.when().post("api/users")
		.then().extract().response().as(AddedUserResponse.class);
		System.out.println(aur.getCreatedAt());
		Assert.assertEquals(aur.getName(),"Darshan");
		Assert.assertEquals(aur.getJob(),"QA");
		
	}
	
}


