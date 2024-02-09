package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChromeInstance extends BrowserInstance{

 ChromeInstance(){
        super();
    }

    ChromeInstance(URL hubAddress){
        super(hubAddress);
    }

    @Override

    protected void configureDriver() {
        WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
        System.setProperty(ChromeDriverService.CHROME_DRIVER_APPEND_LOG_PROPERTY,"true");
        driver=new EventFiringWebDriver(new ChromeDriver(getDefaultOptions()));
    }

//    @Override
//	public void maximize()
//    {
//
//    }

    @Override
    public void configureRemoteDriver(URL hubAddress) {

        driver=new EventFiringWebDriver(new RemoteWebDriver(hubAddress,getDefaultOptions()));
    }

    public ChromeOptions getDefaultOptions(){
        ChromeOptions chromeOptions=new ChromeOptions();
        Map<String,Object> prefs=new HashMap<>();
        prefs.put("download.prompt_for_download",false);
        chromeOptions.addArguments("start-maximized");

        chromeOptions.setExperimentalOption("prefs",prefs);
        DesiredCapabilities desiredCapabilities= new DesiredCapabilities();
        chromeOptions.merge(desiredCapabilities);
        return chromeOptions;

    }
}
