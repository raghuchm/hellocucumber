
package StepDefs;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import Datareader.Propertyreader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class create_tokendef
{
    String baseUrl;
	String token;
	Response  responseData;
	Propertyreader p;
	
@Given("Get the token_id") 
public void get_the_token_id()
{
	
	 p = new Propertyreader();
	try {
		baseUrl=p.getPropertyFileData("baseUrl");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
    
}

@When("login details {string} and {string} passed")
public void login_details_and_passed(String string1, String string2) 
{
	 RestAssured.baseURI=baseUrl;
     JSONObject credDetails = new JSONObject();
     credDetails.put("username",string1);
     credDetails.put("password",string2);
     RequestSpecification requestSpecification=given().body(credDetails.toString()).contentType("application/json");
     responseData = requestSpecification.when().post("/auth");
     
}

@Then("Get the token_id and save to file")
public void get_the_token_id_and_save_to_file() {
	responseData.then().statusCode(200).body("token",notNullValue());
    token = with(responseData.prettyPrint()).get("token");
    p.setPropertyFileData("token",token);
    p.setPropertyFileData("name","veena");
}
}