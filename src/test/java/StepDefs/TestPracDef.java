package StepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TestPracDef {

    @Given("I remember the concepts of coding")
    public void testPrac1(){
        System.out.printf("This is the Given situation of current coding");
    }

    @When("I start practising the code")
    public void testPrac2(){
        System.out.println(" this is the plan of action that I have");
    }
    @Then("I will be on track and confident writting code snippets")
    public void testPrac3(){
        System.out.println("This is where I should be reaching to");
    }
}
