package StepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserDef
{
    WebDriver driver=null;
    @Given("The Browser configuration is completed")
    public void browserConfiguration(){
        System.setProperty("webdriver.chrome.driver","E:\\Automation\\chromedriver\\chromedriver.exe");
    }
    @When("Browser Launch is initiated with URL")
    public void launchingtheBrowser(){
        driver=new ChromeDriver();
        driver.get("https://www.google.com/");
        driver.manage().window().maximize();

    }

    @Then("Browser should open with {string}")
    public void verifyTitle(String urlVal){
        Assertions.assertEquals(driver.getTitle().equalsIgnoreCase(urlVal),true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }

}
