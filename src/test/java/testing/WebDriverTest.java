package testing;

import browsers.BrowserInstance;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.PropertyfileData;

import java.io.IOException;
import java.net.URL;

public abstract class WebDriverTest implements WebDriverEventListener {
    /**
     * WebDriverTest creates driver instance based on the browser type.
     */

public final static String DEFAULT_BROWSER="chrome";
private static boolean REMOTE=Boolean.getBoolean("remote");
private static final String SELENIUM_HUB_URL=System.getProperty("selenium.hub.url","http://127.0.0.1:4444/wd/hub");

protected ThreadLocal<BrowserInstance> browser=new ThreadLocal<>();

private ScreenRecorder screenRecorder;

@Parameters("browser")
@BeforeClass
    public synchronized void beforeClass(@Optional(DEFAULT_BROWSER) String browserType) throws IOException {
    REMOTE= Boolean.parseBoolean(PropertyfileData.getPropertyFileData("remote"));
    try {
        if (REMOTE) {
            if (SELENIUM_HUB_URL == null) {
                Assert.fail("Remote requested but no value specified for URL***");
            }
            browser.set(BrowserInstance.getRemoteInstance(browserType, new URL(SELENIUM_HUB_URL)));
        } else {
            browser.set(BrowserInstance.getRemoteInstance(browserType));
        }
        browser.get().registerEventHandler(this);
    }
    catch (Exception e){
        e.printStackTrace();
    }
}

@AfterClass(alwaysRun = true)
    public synchronized void tearClass(){
   try{
       browser.get().shutdown();
   }
    finally {
        browser.set(null);
    }

}

    @Override
    public void beforeAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertAccept(WebDriver driver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver driver) {

    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver driver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> target) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {

    }

    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {

    }
}
