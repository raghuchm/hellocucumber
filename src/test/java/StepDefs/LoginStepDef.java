package StepDefs;

import Pageclasses.LoginPage;
import browsers.BrowserInstance;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import testing.WebDriverTest;

import java.io.IOException;

public class LoginStepDef extends WebDriverTest
{
    BrowserInstance driver;
    LoginPage login_page;
    boolean flag;
    @Given("Load url open the website")
    public void load_url_open_the_website() throws IOException
    {
        driver=browser.get();
        login_page= new LoginPage(driver);
        login_page.launchApplication();


    }

    @When("enter the login credentials")
    public void enter_the_login_credentials() throws InterruptedException {

        flag=login_page.login("veenavj.sauvg@gmail.com", "Sauvg@1143");

    }

    @Then("the website should load the product page")
    public void the_website_should_load_the_product_page() {

        Assert.assertTrue(flag);
    }

}
