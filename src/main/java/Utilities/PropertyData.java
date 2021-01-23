package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.http.client.methods.RequestBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PropertyData {
	private static RequestSpecification req;
	private static ResponseSpecification res;
	private static String fileLocation = "properties.txt";


	public String getValue(String key) throws Exception {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(fileLocation);
		prop.load(fis);
		return prop.getProperty(key);
	}


	public static RequestSpecification getRequestSpec() throws FileNotFoundException {
		PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
		req =  (RequestSpecification) new RequestSpecBuilder().setBaseUri("https://reqres.in/")		
				.addHeader("Content-Type","application/json").addFilter(ResponseLoggingFilter.logResponseTo(log))
				.addFilter(RequestLoggingFilter.logRequestTo(log)).build();
		return req;
	}
	public static ResponseSpecification getResponseSpec() throws FileNotFoundException {
		res =  (ResponseSpecification) new ResponseBuilder().build();
		return res;
	}
}