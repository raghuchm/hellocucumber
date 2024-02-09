package StepDefs;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.notNullValue;

import org.json.JSONObject;

import Pageclasses.CreateBookings;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class CreateBookingsRunSteps 
{
	String baseUrl="https://restful-booker.herokuapp.com";
	int bookingId;
	Response  responseData;


@Given("user should have access to create booking details")
public void user_should_have_access_to_create_booking_details() 
{
    System.out.println("1");
    
    
}



@When("create booking details {string} {string} {int} {string} {string} {string} {string}")
public void create_booking_detailsramrahim1000true2018_01breakfast(String firstname, String lastname, Integer totalprice, String depositpaid, String checkin,String checkout,String additionalneeds) {
	
 RestAssured.baseURI = baseUrl;
JSONObject credDetails = new JSONObject();
JSONObject credDetails1 = new JSONObject();
credDetails.put("firstname",firstname);
credDetails.put("lastname",lastname);
credDetails.put("totalprice",totalprice);
credDetails.put("depositpaid",depositpaid);
credDetails1.put("checkin",checkin);
credDetails1.put("checkout",checkout);
//credDetails.put("bookingdates",bookingdates);
credDetails.put("bookingdates",credDetails1);
credDetails.put("additionalneeds","Breakfast");
RequestSpecification requestSpecification=given().body(credDetails.toString()).contentType("application/json");
 responseData = requestSpecification.when().post("/booking");
responseData.then().statusCode(200).body("bookingid",notNullValue());
//System.out.println(responseData.prettyPrint());
 bookingId = with(responseData.prettyPrint()).get("bookingid");
System.out.println("BookingId"+bookingId);
}

@Then("Booking details should be created")
public void booking_details_should_be_created() {
	responseData.then().statusCode(200).body("bookingid",notNullValue());
	//System.out.println(responseData.prettyPrint());
	 bookingId = with(responseData.prettyPrint()).get("bookingid");

}
}
