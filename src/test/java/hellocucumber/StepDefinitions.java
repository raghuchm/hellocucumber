package hellocucumber;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    String actual;
    String today;
    @Given("an example scenario")
    public void anExampleScenario() {
        actual="Saturday";
    }

    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
        today="Saturday";
    }

    @Then("the scenario passes")
    public void theScenarioPasses() {
        assertEquals(actual,today);
    }

}
