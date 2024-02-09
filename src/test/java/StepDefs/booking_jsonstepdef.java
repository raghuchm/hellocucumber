package StepDefs;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.notNullValue;

import org.json.JSONObject;

import Datareader.JsonReader;
import Pageclasses.CreateBookings;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class booking_jsonstepdef 
{
	String baseUrl="https://restful-booker.herokuapp.com";
	int bookingId;
	Response  responseData;
	JsonReader js;
	JSONObject jsonData;

@Given("user should create booking details")
public void createbookingdetails() 
{
	js=new JsonReader();
	String filePath = "/hellocucumber/src/test/resources/data.json";
	org.json.simple.JSONObject jsonData = js.readJsonFile(filePath);
}



@When("create booking details read data from json")
public void readdatajson() {
	
 RestAssured.baseURI = baseUrl;
JSONObject credDetails = new JSONObject();
JSONObject credDetails1 = new JSONObject();
credDetails.put("firstname",jsonData.get("firstname"));
credDetails.put("lastname",jsonData.get("lastname"));
credDetails.put("totalprice",jsonData.get("totalprice"));
credDetails.put("depositpaid",jsonData.get("depositpaid"));
credDetails1.put("checkin",jsonData.get("checkin"));
credDetails1.put("checkout",jsonData.get("checkout"));
//credDetails.put("bookingdates",bookingdates);
credDetails.put("bookingdates",credDetails1);
credDetails.put("additionalneeds",jsonData.get("Breakfast"));
RequestSpecification requestSpecification=given().body(credDetails.toString()).contentType("application/json");
 responseData = requestSpecification.when().post("/booking");
responseData.then().statusCode(200).body("bookingid",notNullValue());
//System.out.println(responseData.prettyPrint());
 bookingId = with(responseData.prettyPrint()).get("bookingid");
System.out.println("BookingId"+bookingId);
}

@Then("Booking details should be created with id")
public void bookingid_creation() {
	responseData.then().statusCode(200).body("bookingid",notNullValue());
	//System.out.println(responseData.prettyPrint());
	 bookingId = with(responseData.prettyPrint()).get("bookingid");

}
}
