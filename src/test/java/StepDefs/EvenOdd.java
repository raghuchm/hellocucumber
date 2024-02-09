package StepDefs;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class EvenOdd {
    int valuePassed;
    String outPut;
    @Given("User passes the given number  {int}")
    public void user_passes_the_given_number(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        valuePassed=int1;
    }
    @When("number is assesed for the Even or Odd")
    public void number_is_assesed_for_the_even_or_odd() {
        // Write code here that turns the phrase above into concrete actions
        outPut= (valuePassed%2==0) ?"Even":"Odd";
    }

    @Then("Output is asserted with as {string}")
    public void output_is_asserted_with_as(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertTrue(outPut.equalsIgnoreCase(string));
    }
}
