package StepDefs;
import io.cucumber.java.en.*;
import org.junit.platform.engine.reporting.ReportEntry;

import static org.junit.jupiter.api.Assertions.*;
public class OutLineDef {
    String math;
    int actualVal;
    @Given("User has decided on {string}")
    public void user_has_decided_on_Math(String string) {
        if (string.equals("addition")){
            math="add";
        }
    }
    @When("User passes <arg{int}> and <arg{int}>")
    public void user_passes_and(int string, int string2) {
        if(math.equalsIgnoreCase("add")){
            actualVal=string+string2;
        }

    }
    @Then("Values are as per <expectedOutput>")
    public void values_are_as_per(int string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string,actualVal);
    }


}
