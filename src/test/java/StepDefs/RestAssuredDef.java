package StepDefs;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import com.fasterxml.*;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.BookingDTO;
import model.BookingDetailsDTO;
import org.hamcrest.Matchers;
import org.json.Cookie;
import org.json.JSONObject;
import org.openqa.selenium.json.Json;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.with;
import static org.hamcrest.Matchers.*;

public class RestAssuredDef
{

    String baseUrl="https://restful-booker.herokuapp.com";
    int bookingId;
    String token;
    @Given("Get the access Token")
    public void getTokenDetails()
    {
        RestAssured.baseURI=baseUrl;
        JSONObject credDetails = new JSONObject();
        credDetails.put("username","admin");
        credDetails.put("password","password123");

        RequestSpecification requestSpecification=given().body(credDetails.toString()).contentType("application/json");
        Response responseData = requestSpecification.when().post("/auth");
        responseData.then().statusCode(200).body("token",notNullValue());
        System.out.println(responseData.prettyPrint());
        token = with(responseData.prettyPrint()).get("token");

//        RequestSpecification requestSpecification =given();

//        Response response = requestSpecification.body(credDetails.toString()).when().post("/auth");


    }
@Given("Create booking Id")
public void createBookingDetails()
{
    RestAssured.baseURI = baseUrl;
    JSONObject credDetails = new JSONObject();
    JSONObject credDetails1 = new JSONObject();
    credDetails.put("firstname","rag");
    credDetails.put("lastname","pra");
    credDetails.put("totalprice","500");
    credDetails.put("depositpaid","true");
    credDetails1.put("checkin","2018-01-01");
    credDetails1.put("checkout","2019-01-01");
    credDetails.put("bookingdates",credDetails1);
    //credDetails.put("bookingdates","checkin":"2018-01-01","checkout");
    credDetails.put("additionalneeds","Breakfast");
    RequestSpecification requestSpecification=given().body(credDetails.toString()).contentType("application/json");
    Response responseData = requestSpecification.when().post("/booking");
    responseData.then().statusCode(200).body("bookingid",notNullValue());
//    System.out.println(responseData.prettyPrint());
     bookingId = with(responseData.prettyPrint()).get("bookingid");
    System.out.println("BookingId"+bookingId);
//
   // RequestSpecification rs1 = given();
}
@Given("get the bookings details")

    public void getBookingDetails()
{

    RestAssured.baseURI = baseUrl;
    RequestSpecification rs = given();
    Response responseData = rs.when().get("/booking/" + bookingId);
    responseData.then().statusCode(200);
    System.out.println(responseData.prettyPrint());
}

@Given("Delete booking Details")

public void deleteBookings()
{
    RestAssured.baseURI = baseUrl;
    RequestSpecification rs = given().header("cookie","token=" +token);
    Response responseData = rs.when().delete("/booking/" + bookingId);
    responseData.then().statusCode(201);
    System.out.println(responseData.prettyPrint());
    rs.when().get("/booking/" + bookingId).then().statusCode(not(200));
}




















    @Then("create the booking Id")
    public void createBookingId() throws JsonProcessingException {
        JSONObject bookingBody = new JSONObject();
        bookingBody.put("firstname", "Mahesh");
        bookingBody.put("lastname", "ramadurga");
        bookingBody.put("totalprice", 100);
        bookingBody.put("depositpaid", true);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-12-12");
        bookingDates.put("checkout", "2023-12-13");
        bookingBody.put("bookingdates", bookingDates);
        bookingBody.put("additionalneeds", "breakfast");

        RequestSpecification requestSpecification =given().contentType("application/json").accept("application/json");

        Response response = requestSpecification.body(bookingBody.toString()).when().post("/booking");
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody().prettyPrint());
        ObjectMapper objectMapper= new ObjectMapper();
        BookingDTO bookingDTO= objectMapper.readValue(response.getBody().asString(), BookingDTO.class);
        System.out.println(bookingDTO.getBooking().getLastname());
        System.out.println(bookingDTO.getBooking().getFirstname());
    }

    @Then("get the bookings")
    public void getBookingIds()
    {
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
       ResponseSpecification responseSpecification = resBuilder.expectResponseTime(Matchers.lessThan(3000l)).build();
        RequestSpecification requestSpecification =given().contentType("application/json").accept("application/json");
        Response response= requestSpecification.when().get("/booking");


        responseSpecification.then().body("bookingid",hasItem(1350));
        System.out.println(response.getBody().prettyPrint());

    }

    @Then("get the specific details of booking")
    public void getBookingIddetails()
    {
        RequestSpecification requestSpecification =given().contentType("application/json").accept("application/json");
        Response response= requestSpecification.when().get("/booking/1350");
        response.then().body("firstname",equalTo("Mahesh"));

        System.out.println(response.getBody().prettyPrint());

    }

}
