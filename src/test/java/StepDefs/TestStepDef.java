package StepDefs;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestStepDef {
    int arg1, arg2;
    int expectedVal;
    int actualVal;
    @Given("User has the required values")
    public void user_has_the_required_values() {

        arg1=10;
        arg2=20;
    }
    @When("Addition is performed on the values")
    public void addition_is_performed_on_the_values() {
        actualVal=arg1+arg2;

    }
    @Then("addition of values should be as actual")
    public void addition_of_values_should_be_as_actual() {
    expectedVal=30;
    assertEquals(expectedVal,actualVal);

    }
}
