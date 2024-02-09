package StepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class EvenOddOutline {
    int valuePassed;
    String outPut;
    @Given("User passes the number as input  {int}")
    public void user_passes_the_number_as_input(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        valuePassed=int1;
    }
    @When("number is validated for the Even or Odd")
    public void number_is_validated_for_the_even_or_odd() {
        // Write code here that turns the phrase above into concrete actions
        outPut= (valuePassed%2==0) ?"Even":"Odd";
    }
    //"([^"]*)"$
    @Then("Output is asserted if it Even or Odd {string}")
    public void output_is_asserted_if_it_even_or_odd(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(outPut.equalsIgnoreCase(string));
    }

}
