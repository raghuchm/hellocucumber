package browsers;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.*;

public abstract class BrowserInstance {

    /**
     * Browser instance based on the browser type.
     */
	String mainWindowHandle;
    EventFiringWebDriver driver;

    BrowserInstance()
    {
        configureDriver();
        maximize();
        mainWindowHandle=getWindowHandle();
    }
    public String getWindowHandle() {
		// TODO Auto-generated method stub
		return driver.getWindowHandle();
	}
	BrowserInstance(URL hubAdress)
    {
        configureRemoteDriver(hubAdress);
        maximize();
    }
    public String getMainWindowHAndle()
    {
    	return mainWindowHandle;
    }

    public String getTitle(){
        return driver.getTitle();
    }

    public void sendKeys(By element, String value){
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(value);

    }
    
    public void click(By element){

    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated((element)));
        driver.findElement(element).click();

    }
    
    
    
    
    protected abstract void configureDriver();

   // protected abstract  void maximize();

    public abstract void configureRemoteDriver(URL hubAddress);

    public static BrowserInstance getRemoteInstance(String browser,URL hubAddress){
        if(browser.toLowerCase(Locale.ROOT).contains("chrome")){
            return new ChromeInstance(hubAddress);
        }
        else{
            //TODO for other browsers
            return new ChromeInstance(hubAddress);

        }
    }

    public static BrowserInstance getRemoteInstance(String browser){
        if(browser.toLowerCase(Locale.ROOT).contains("chrome")){
            return new ChromeInstance();
        }
        else{
            //TODO for other browsers
            return new ChromeInstance();

        }
    }

    public void registerEventHandler(WebDriverEventListener listener){
        driver.register(listener);
    }

    public void shutdown(){
        if(driver!=null){
            driver.quit();
        }
    }

    public void loadURL(String url){
        driver.get(url);
        waitForPageLoaded();
    }

    public int getElementCount(By element){
        waitForElement(element);
        return  driver.findElements(element).size();
    }

    public String getText(By element){
        waitForElement(element);
        return  driver.findElement(element).getText();
    }

    public void clearText(By element){
        waitForElement(element);
        driver.findElement(element).clear();
    }

    public long getElementsCountWithMatchingText(By element,String pattern){
       return getResultText(element).stream().filter(text-> text.toLowerCase().contains(pattern)).count();

    }

    public List<String> getResultText(By element){
        List<String> textVals=new ArrayList<>();
        driver.findElements(element).stream().forEach(text->textVals.add(text.getText().toLowerCase()));
        return textVals;
    }

    public void reload(){
        driver.navigate().refresh();
        waitForPageLoaded();
    }

    private boolean waitForPageLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            boolean testVal = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState")).equals(true);
            return testVal;
        } catch (TimeoutException te) {
            return false;
        }
    }
    /**
     * Method to select the value from the dropdown using byValue method
     */

    public void SelectDropDown(By selectElement,String valToSelect){
        Select select=new Select(driver.findElement(selectElement));
        select.selectByValue(valToSelect);
    }
    
    
    public void waitForElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    /* method for execution of  wait for the desired results of java script*/
//    public boolean waitForJavascriptResult(String script, Object expected,int timeout)
//    {
//		WebDriverWait wait= new WebDriverWait(driver,timeout);
//		try
//		{
//			wait.until(ExpectedConditionz.jsResultEquals(script,expected));
//		}
//		catch(TimeOutException toe)
//		{
//			return false;
//		}
//    	return true;
//    	
//    }
//    
    /* method to set the directory for downloaded file*/
    public File getDefaultDownloadDirectory()
    {
		return new 
		File(SystemUtils.getUserHome().getAbsolutePath()+File.separator + "Downloads");
    	
    }
    
    public Keys getControlKey()
    {
		return SystemUtils.IS_OS_WINDOWS ? Keys.COMMAND: Keys.CONTROL;
    	
    }
//    public int getNumberofBrowserProcessesRunning()
//    {
//		return SystemHelper.getNumberProcessRunning(getBrowerProcessorName());	
//    }
//    
//    public void terminateDriverProcesses()
//    {
//    	SystemHelper.terminateProcess(getDriverProcessName());
//    }
//    
//    public void terminateBrowserProcesses()
//    {
//    	SystemHelper.terminateProcess(getBrowserProcessName());
//    }
    
     public Object runScript(String script, Object... args)
     {
		return ((JavascriptExecutor)driver).executeScript(script, args);
    	 
     }
     
     public JavascriptExecutor getJavaScriptEXecutor()
     {
    	 return driver;
     }
     /**
      *the size of the browser will be set using dimension class and setsize method
      */
     

     
     public void setBrowserWindowSize(int width,int height)
     {
    	 driver.manage().window().setPosition(new Point(0,0));
    	 java.awt.Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    	 width=Math.min(width, (int)screenSize.getWidth());
    	 height=Math.min(height, (int)screenSize.getHeight());
    	 driver.manage().window().setSize(new Dimension(width,height)); 
     }
     /**
      *
      */

     
     public void maximize()
     {
    	 driver.manage().window().maximize();
     }
     /**
     *
     */
     
     public void navigateBack()
     {
    	 driver.navigate().back();
     }
     /**
     *
     */
     public void navigateForward()
     {
    	 driver.navigate().forward();
     }
     /**
     *
     */
     public void refresh()
     {
    	 driver.navigate().refresh();
     }
     /**
     *
     */
     public String getURL()
     {
    	 return driver.getCurrentUrl();
     }
     /**
     *
     */
     public LogEntries getLogEntries(String logType)
     {
		LogEntries logEntries;
		try
		{
			logEntries=driver.manage().logs().get(logType);
		}
		catch(InvalidArgumentException e)
		{
			logEntries=null;
		}
    	 return logEntries;
    	 
     }
     /**
     *
     */
     public void loadURl(String url)
     {
    	 driver.get(url);
    	 waitForPageLoaded();
    	 
     }
     /**
     *
     */
     public String getWindowTitle()
     {
    	 return driver.getTitle();
     }
     /**
     *
     */
     public boolean waitForUrlContains(String subStr, int timeout , boolean expected)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expected)
    		 {
    			 wait.until(ExpectedConditions.urlContains(subStr));
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.urlContains(subStr)));
    		 }
    		 return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }	 
     }
     /**
     *
     */
     public boolean waitForUrlIs(String subStr, int timeout , boolean expected)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expected)
    		 {
    			 wait.until(ExpectedConditions.urlToBe(subStr));
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(subStr)));
    		 }
    		 return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 } 
     }
     /**
     *
     */
     
     
     public boolean waitForTitleContains(String subStr, int timeout , boolean expected)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expected)
    		 {
    			 wait.until(ExpectedConditions.titleContains(subStr));
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.titleContains(subStr)));
    		 }
    		 return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
		
    	 
     }
     /**
     *
     */
     
     public boolean waitForTitleIs(String subStr, int timeout , boolean expected)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expected)
    		 {
    			 wait.until(ExpectedConditions.titleIs(subStr));
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.titleIs(subStr)));
    		 }
    		 return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
     }
     /**
     *
     */
     public Set<String> getWindowHandles()
     {
		return driver.getWindowHandles();
    	 
     }
     /**
     *
     */
     public boolean switchFocusToMainWindow()
     {
    	 return switchToWindow(mainWindowHandle,5);
     }
     /**
     *
     */
     public String getMainWindowHandle()
     {
    	 return mainWindowHandle;
     }
     /**
     *
     */
     public boolean closeAllWindowsButMain()
     {
		
    	 Set<String> handles= getWindowHandles(); 
    	 for(String handle:handles)
    	 {
    		 if(handle.equals((mainWindowHandle)))
    				 continue;
    		 Set<String> tempHandles= getWindowHandles(); 
    		 switchToWindow(handle,3);
    		 driver.close();
    		 //waitForNumberOfWindowsToDecrease(tempHandles,3);
    	 }
    	 
    	 return (getWindowHandles().size()==1&& switchFocusToMainWindow());
    	 
     }
     
     /**
     *
     */
     public boolean switchToWindow(final String windowHandle, int timeout)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		  //wait.until(ExpectedConditions.windowToExist(windowHandle));
    		  driver.switchTo().window(windowHandle);
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
    	 return isCurrentWindow(windowHandle);
     }
     private boolean isCurrentWindow(String windowHandle) {
		// TODO Auto-generated method stub
    	 
		return getWindowHandle().equals(windowHandle);
	}
	/**
     *
     */
     
     public boolean WaitForPageLoad()
     {
    	 
    	 try
    	 {
    		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    		 // wait.until(ExpectedConditions.jsResultEquals("return document.readyState;","completed");
    		  return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
    	 
     }
     
     /**
     *
     */
     public boolean openNewTab(String url)
     {
		Set<String> handles=getWindowHandles();
    	 JavascriptExecutor js=driver;
    	 js.executeScript("window.open()");
    	 if(url != null)
    	 {
    		 boolean success=false;
    		 //success=switchFocusToNewWindow(handles,5);
    		 loadURL(url);
    		 return success;
    	}
    	 else {
    	 return false;
    	 }
    	 
     }
     /**
     *
     */
     public boolean openNewTab()
     {
    	 return openNewTab(null);
     }
     
     public boolean switchToFrame(By locator, int timeout)
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		  wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    		  return true;
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
    	 
     }
     /**
     *
     */
     public void switchToRootFrame()
     {
    	 driver.switchTo().defaultContent();
     }
     
     private String getCssValue(By locator,String cssAttribute,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		  
    		  return driver.findElement(locator).getCssValue(cssAttribute);
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getCssValue(locator,cssAttribute,false);
    		 }
    		 else throw e;
    	 }
    	  
     }
     /**
     *
     */
     public String getCssValue(By locator,String cssAttribute) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return getCssValue(locator,cssAttribute,true);
     }
     
     private String getElementAttribute(By locator,String attribute,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		  String sAttributeValue=null;
    		  WebElement we=driver.findElement(locator);
    		  sAttributeValue=we.getAttribute(attribute);
    		  return sAttributeValue;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getElementAttribute(locator,attribute,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     /**
     *
     */
     public String getElementAttribute(By locator,String attribute) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return getCssValue(locator,attribute,true);
     }
     
     public boolean isElementEnabled(By selector) throws NoSuchElementException
     {
    	 WebElement el=driver.findElement(selector);
    	 return el.isEnabled();
    	 
     }
     
     public boolean isElementDisabled(By selector) throws NoSuchElementException
     {
    	 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return !isElementEnabled(selector);
    	 
     }
     
     /**
     *
     */
     public boolean waitForElementEnabled(By selector,boolean expected, int timeout) throws NoSuchElementException
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		  //wait.until(ExpectedConditions.toBeEnabled(selector,expected));
    		  return true;
    	 }
    	 catch(TimeoutException toe)
    	 {
    		 return false;
    	 }
    	
     }
     /**
     *
     */
     public int getNumberOfElements(By locator) throws NoSuchElementException
     {
    	
    	 return  driver.findElements(locator).size();
    	 
     }
     /**
     *
     */
     private int getNumberOfChildElements(By parent,By child,boolean retry) throws NoSuchElementException
     {
    	
    	 try
    	 {
    		  
    		  return driver.findElement(parent).findElements(child).size();
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getNumberOfChildElements(parent,child,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     /**
     *
     */
     public int getNumberOfChildElements(By parent,By child) throws NoSuchElementException
     {
    	 return getNumberOfChildElements(parent,child,true);
     }
     
     public boolean isElementFound(By locator)
     {
    	 try
    	 {
    		 driver.findElement(locator);
    		  return true;
    	 }
    	 catch(NoSuchElementException e)
    	 {
    		 return false;
    	 }
     }
     /**
     *
     */
    
     private boolean isElementDisplayed(By locator,boolean retry) throws NoSuchElementException
     {
    	 try
    	 {
    		return driver.findElement(locator).isDisplayed();
    		  
    	 }
    	 catch(NoSuchElementException e)
    	 {
    		 if(retry)
    		 {
    			 return isElementDisplayed(locator,false);
    		 }
    		 else throw e;
    	 }
     }
     /**
     *
     */
     
     
     
     
     public boolean isElementDisplayed(By locator) throws NoSuchElementException 
     {
    	 return isElementDisplayed(locator,true);
     }
     
     
     public boolean waitForElementsDisplayed( int timeout,boolean expected, By ... locators) 
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 
    		 if(expected)
    		 {
    		  //wait.until(ExpectedConditions.elementsLocatedByAreDisplayed(locators));
    		  return true;
    		 }
    		 else
    		 {
    			// wait.until(ExpectedConditions.elementsLocatedByAreHidden(locators));
       		  return true;
    		 }
    	 }
    		 
    	 catch(TimeoutException toe)
    	 {
    		 return false;
    	 }
    	
     }
     /**
     *
     */
     public boolean waitForElementDisplayed(boolean expected, int timeout, By locator)
     {
    	 return waitForElementsDisplayed(timeout,false,locator);
     }
     
     
     public boolean waitForElementDisappear(By locator, int timeout)
     {
    	 return waitForElementsDisplayed(timeout,false,locator);
     }
     
     
     public boolean waitForSelected(By locator,boolean expected, int timeout) 
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		  wait.until(ExpectedConditions.elementSelectionStateToBe(locator, expected));
    		  return true;
    		
    	 }
    		 
    	 catch(TimeoutException toe)
    	 {
    		 return false;
    	 }
    	
     }
     /**
     *
     */
     public boolean waitForElementToHaveText(final By locator,final String expectedText, int timeout,boolean expected) 
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 
    	 try
    	 {
    		 if(expected)
    		 {
    		  wait.until(ExpectedConditions.textToBe(locator, expectedText));
    		  return true;
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, expectedText)));
       		  
    		 }
    		 return true;
    		
    	 }
    		 
    	 catch(TimeoutException toe)
    	 {
    		 return false;
    	 }
    	
     }
     
     /**
     *
     */
     public boolean waitForElementToContainText(final By locator,final String expectedText, int timeout,boolean expected) 
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expected)
    		 {
    		  wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
    		  return true;
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText)));
       		  
    		 }
    		 return true;
    		
    	 }
    		 
    	 catch(TimeoutException toe)
    	 {
    		 return false;
    	 }
    	
     }
     /**
     *
     */
     public String getElementText(By locator) throws NoSuchElementException
     {
    	 return getElementText(locator,true);
     }
     
     private String getElementText(By locator, boolean retry) {
		// TODO Auto-generated method stub
try {
	return driver.findElement(locator).getText();
}
catch(StaleElementReferenceException sere) {
	if(retry) return getElementText(locator,false);
	else throw sere;
}
}
	private ArrayList<String> getTextOfChildElements(By parentBy,By childBy,boolean includeHidden,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		  String sAttributeValue=null;
    		  List<WebElement> children=driver.findElement(parentBy).findElements(childBy);
    		  ArrayList<String> childText=new ArrayList<String>(children.size());
    		  for(WebElement el:children)
    		  {
    			  if(includeHidden)
    			  {
    				 // childText.add(getTextEvenIfNotDisplayed(el));
    			  }
    			  else if(el.isDisplayed())
    			  {
    				  childText.add(el.getText());
    			  }
    		  }
			  return childText;

    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getTextOfChildElements(parentBy,childBy,includeHidden,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public ArrayList<String> getTextOfChildElements(By parentBy,By childBy,boolean includeHidden) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return getTextOfChildElements(parentBy,childBy,includeHidden,true);
     }
     
     
     private ArrayList<String> getAttributeOfChildElements(By parentBy,By childBy,String attribute,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		 ArrayList<String> sAttributeValue=new ArrayList<String>();
    		  List<WebElement> elements=driver.findElement(parentBy).findElements(childBy);
    		  
    		  for(WebElement el:elements)
    		  {
    			 String value=el.getAttribute(attribute);
    			  if(value!=null && !value.isEmpty())
    			  {
    				  sAttributeValue.add(value);
    			  }
    		  }
    		  return sAttributeValue;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getAttributeOfChildElements(parentBy,childBy,attribute,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public ArrayList<String> getAttributeOfChildElements(By parentBy,By childBy,String attribute) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return getAttributeOfChildElements(parentBy,childBy,attribute,true);
     }
     
     public boolean waitForElementClickable(By locator, int timeout,boolean expectedClickable) 
     {
    	 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    	 try
    	 {
    		 if(expectedClickable)
    		 {
    		  wait.until(ExpectedConditions.elementToBeClickable(locator));
    		  return true;
    		 }
    		 else
    		 {
    			 wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(locator)));
       		  
    		 }
    		 return true;
    	 }
    		 
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
    	
     }
     
     public boolean waitForAttributeToBe(By locator,String attribute,String value, int timeout)
     {
    	
    	 try
    	 {
    		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
    		 return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
    		 
    	 }
    	 catch(TimeoutException e)
    	 {
    		 return false;
    	 }
    	 
     }
     
     private boolean isValueInDropDown(By locator,String value,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		  String sAttributeValue=null;
    		  WebElement element=driver.findElement(locator);
    		  Select sDropDown=new Select(element);
    		  List<WebElement> eOptions=sDropDown.getOptions();
    		  List<String> valueList=new ArrayList<String>();
    		  for(WebElement el:eOptions)
    		  {
    			  if(el.getAttribute("value").equalsIgnoreCase(value))
    			  {
    				  return true;
    			  }
    		  }
    		  return false;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return isValueInDropDown(locator,value,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public boolean isValueInDropDown(By locator,String value) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return isValueInDropDown(locator,value,true);
     }
     
     private boolean isDropDownValueSelected(By locator,String value,boolean exclusive,boolean retry) throws NoSuchElementException
     {
    	 try
    	 {
    		 
    		  WebElement element=driver.findElement(locator);
    		  Select sDropDown=new Select(element);
    		  List<WebElement> selected=sDropDown.getAllSelectedOptions();
    		  List<String> valueList=new ArrayList<String>();
    		  if(selected.isEmpty())
			  {
				  return false;
			  }
    		  else if(exclusive && selected.size()>1)
    		  {
    			  return false;
    		  }
    		  else
    		  {
    			  String selectedValue;
    			  for(WebElement selection:selected)  
    			  {
    				  selectedValue=selection.getAttribute("value");
    				  if(selectedValue.equalsIgnoreCase(value))
    				  {
    					  return true;
    				  }
    			  }
    		  }
    		  return false;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return isDropDownValueSelected(locator,value,exclusive,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public boolean isDropDownValueSelected(By locator,String value,boolean exclusive) throws NoSuchElementException
     {
    	 return isDropDownValueSelected(locator,value,exclusive,true);
     }
     
     
     
     private boolean isDropDownTextSelected(By locator,String displayText,boolean exclusive,boolean retry) throws NoSuchElementException
     {
    	 try
    	 {
    		 
    		  WebElement element=driver.findElement(locator);
    		  Select sDropDown=new Select(element);
    		  List<WebElement> selected=sDropDown.getAllSelectedOptions();
    		  List<String> valueList=new ArrayList<String>();
    		  if(selected.isEmpty())
			  {
				  return false;
			  }
    		  else if(exclusive && selected.size()>1)
    		  {
    			  return false;
    		  }
    		  else
    		  {
    			  String selectedText;
    			  for(WebElement selection:selected)  
    			  {
    				  selectedText=selection.getText();
    				  if(selectedText.equalsIgnoreCase(displayText))
    				  {
    					  return true;
    				  }
    			  }
    		  }
    		  return false;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return isDropDownTextSelected(locator,displayText,exclusive,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public boolean isDropDownTextSelected(By locator,String displayText,boolean exclusive) throws NoSuchElementException
     {
    	 return isDropDownValueSelected(locator,displayText,exclusive,true);
     }
     
     
     
     
     
     
     private boolean isDropDownIndexSelected(By locator,int index,boolean exclusive,boolean retry) throws NoSuchElementException
     {
    	 try
    	 {
    		 
    		  WebElement element=driver.findElement(locator);
    		  Select sDropDown=new Select(element);
    		  List<WebElement> selected=sDropDown.getAllSelectedOptions();
    		  //List<String> valueList=new ArrayList<String>();
    		  if(selected.isEmpty())
			  {
				  return false;
			  }
    		  else if(exclusive && selected.size()>1)
    		  {
    			  return false;
    		  }
    		  else
    		  {
    			  int selectedIndex;
    			  for(WebElement selection:selected)  
    			  {
    				  selectedIndex=Integer.parseInt(selection.getAttribute("index"));
    				  if(index==selectedIndex)
    				  {
    					  return true;
    				  }
    			  }
    		  }
    		  return false;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return isDropDownIndexSelected(locator,index,exclusive,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public boolean isDropDownIndexSelected(By locator,int index,boolean exclusive) throws NoSuchElementException
     {
    	 return isDropDownIndexSelected(locator,index,exclusive,true);
    	 
     }
     
     private List<String> getDropDownSelectedValues(By locator,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		 
	   		  WebElement selectedElement=driver.findElement(locator);
	   		  Select select=new Select(selectedElement);
	   		  List<WebElement> selected=select.getAllSelectedOptions();
	   		  List<String> selectedValues=new ArrayList<String>();
	   		  for(WebElement selection:selected)
	   		  {
	   			  selectedValues.add(selection.getAttribute("value"));
	   		  }
	   		  return selectedValues;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getDropDownSelectedValues(locator,false);
    		 }
    		 else throw e;
    	 }
    	 
     }
     
     public List<String> getDropDownSelectedValues(By locator) throws NoSuchElementException,StaleElementReferenceException
     {
    	 return getDropDownSelectedValues(locator,true);
     }
     private List<String> getDropDownSelectedText(By locator,boolean retry) throws NoSuchElementException,StaleElementReferenceException
     {
    	 try
    	 {
    		 
	   		  WebElement selectionElement=driver.findElement(locator);
	   		  Select select=new Select(selectionElement);
	   		  List<WebElement> selected=select.getAllSelectedOptions();
	   		  List<String> selectedValues=new ArrayList<String>();
	   		  for(WebElement selection:selected)
	   		  {
	   			  selectedValues.add(selection.getText());
	   		  }
	   		  return selectedValues;
    	 }
    	 catch(StaleElementReferenceException e)
    	 {
    		 if(retry)
    		 {
    			 return getDropDownSelectedText(locator,false);
    		 }
    		 else throw e;
    	 }
     }
    	
     
    public List<String> getDropDownSelectedText(By locator) throws NoSuchElementException,StaleElementReferenceException
    {
    	 return getDropDownSelectedText(locator,true);
    }
    
    private List<Integer> getDropDownSelectedIndices(By locator,boolean retry) throws NoSuchElementException,StaleElementReferenceException
    {
   	 try
   	 {
   		 
	   		  WebElement selectionElement=driver.findElement(locator);
	   		  Select select=new Select(selectionElement);
	   		  List<WebElement> selected=select.getAllSelectedOptions();
	   		  List<Integer> selectedIndex=new ArrayList<Integer>();
	   		  for(WebElement selection:selected)
	   		  {
	   			  selectedIndex.add(Integer.parseInt(selection.getAttribute("index")));
	   		  }
	   		  return selectedIndex;
   	 }
   	 catch(StaleElementReferenceException e)
   	 {
   		 if(retry)
   		 {
   			 return getDropDownSelectedIndices(locator,false);
   		 }
   		 else throw e;
   	 }
    }
         
   public List<Integer> getDropDownSelectedIndices(By locator) throws NoSuchElementException,StaleElementReferenceException
    {	 
	   return getDropDownSelectedIndices(locator,true);
    }
   
   private boolean isCheckBoxChecked(By checkboxObject,boolean retry)throws NoSuchElementException,StaleElementReferenceException
   {
	  
	  	 try
	  	 {
	  		 
	  		WebElement checkbox=driver.findElement(checkboxObject);
	  		return checkbox.isSelected();
	  	 }
	  		 
	  	catch(StaleElementReferenceException e)
	   	 {
	   		 if(retry)
	   		 {
	   			 return isCheckBoxChecked(checkboxObject,false);
	   		 }
	   		 else throw e;
	   	 }
   }
   
   public boolean isCheckBoxChecked(By checkboxObject)throws NoSuchElementException,StaleElementReferenceException
   {
	   return isCheckBoxChecked(checkboxObject,true);
   }
   
   private boolean selectDropDownByValue(By locator,String value,boolean retry) throws NoSuchElementException,StaleElementReferenceException
   {
  	 try
  	 {
  		 
	   		  WebElement element=driver.findElement(locator);
	   		  Select dropdown=new Select(element);
	   		  dropdown.selectByValue(value);
	   		  return isDropDownValueSelected(locator, value, retry);
  	 }
  	 catch(StaleElementReferenceException e)
  	 {
  		 if(retry)
  		 {
  			 return  selectDropDownByValue(locator,value,false);
  		 }
  		 else throw e;
  	 }
   }
   
   public boolean selectDropDownByValue(By locator,String value) throws NoSuchElementException,StaleElementReferenceException
   {
	   return  selectDropDownByValue(locator,value,true);
   }
   
   private boolean selectDropDownByText(By locator,String displayText,boolean retry) throws NoSuchElementException,StaleElementReferenceException
   {
  	 try
  	 {
  		 
	   		  WebElement element=driver.findElement(locator);
	   		  Select dropdown=new Select(element);
	   		  dropdown.selectByVisibleText(displayText);
	   		  return isDropDownTextSelected(locator,displayText, retry);
  	 }
  	 catch(StaleElementReferenceException e)
  	 {
  		 if(retry)
  		 {
  			 return  selectDropDownByText(locator,displayText,false);
  		 }
  		 else throw e;
  	 }
   }
   
   public boolean selectDropDownByText(By locator,String displayText) throws NoSuchElementException,StaleElementReferenceException
   {
	   return  selectDropDownByValue(locator,displayText,true);
   }
   
   
   private boolean selectDropDownByIndex(By locator,int index,boolean retry) throws NoSuchElementException,StaleElementReferenceException
   {
  	 try
  	 {
  		 
	   		  WebElement element=driver.findElement(locator);
	   		  Select dropdown=new Select(element);
	   		  dropdown.selectByIndex(index);
	   		  return isDropDownIndexSelected(locator, index, retry);
  	 }
  	 catch(StaleElementReferenceException e)
  	 {
  		 if(retry)
  		 {
  			 return  selectDropDownByIndex(locator,index,false);
  		 }
  		 else throw e;
  	 }
   }
   
   public boolean selectDropDownByIndex(By locator,int index) throws NoSuchElementException,StaleElementReferenceException
   {
	   return  selectDropDownByIndex(locator,index,true);
   }
   
   public boolean toggleCheckBox(By locator,boolean expected)throws NoSuchElementException
   {
	   WebElement element=driver.findElement(locator);
	   if(element.isSelected()!=expected)
	   {
		   element.click();
		   return waitForSelected(locator, expected, 3);
	   }
	   return true;
	   
   }
   
   public void clearTextcopy(By locator)throws NoSuchElementException,StaleElementReferenceException
   {
	   try
	   {
		   driver.findElement(locator).clear();
	   }
	   catch(StaleElementReferenceException e)
	   {
		   driver.findElement(locator).clear();
	   }
   }
   
   public boolean enterText(By locator,String textTosend)throws NoSuchElementException,StaleElementReferenceException
   {
	   clearText(locator);
	   if(!waitForElementToHaveText(locator, "", 3, true)) return false;
	   sendKeys(locator, textTosend);
	   return waitForElementToHaveText(locator, textTosend, 5, true);
   }


   public void pressKey(Keys key,int time)
   {
	   StringBuilder sb=new StringBuilder();
	   for(int i=0;i<time;i++)
	   {
		   sb.append(key);
	   }
	   
	   WebElement active=driver.switchTo().activeElement();
	   if(!active.isDisplayed())
	   {
		   new Actions(driver).sendKeys(sb.toString()).perform();
	   }
	   else active.sendKeys(sb.toString());
   }
   
   
   public void pressKeyWhileHoldingDownAnother(Keys holddown ,String keysToSend)
   {
	   Actions a=new Actions(driver);
	   a.keyDown(holddown).sendKeys(keysToSend).keyUp(holddown).perform();
	   
   }
   
   public void dragAndDrop(By dragFromLocator,By dragTargetLocator)
   {
	   WebElement e1=driver.findElement(dragFromLocator);
	   WebElement target=driver.findElement(dragTargetLocator);
	   Actions action=new Actions(driver);
	   action.dragAndDrop(e1, target);
	   action.perform();
	   
   }
   
   public void dragAndDrop(By dragFromLocator,int xOffSet,int yOffSet)
   {

	   WebElement e1=driver.findElement(dragFromLocator);
	   Actions action=new Actions(driver);
	   action.clickAndHold(e1);
	   action.pause(500);
	   action.moveByOffset(xOffSet, yOffSet);
	   action.pause(500);
	   action.release();
	   action.perform();
   }
   
   public boolean doubleClickElement(By locator,int timeout)
   {
	   WebElement element=null;
	   long start=System.currentTimeMillis();
	   
	   try
  	 {
  		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
  		 wait.until(ExpectedConditions.elementToBeClickable(locator));
  		Actions action=new Actions(driver);
  		action.moveToElement(element).doubleClick().build().perform();
  		return true;
  		 
  	 }
  	 catch(TimeoutException e)
  	 {
  		 return false;
  	 }
  	 
	   
   }
   
   public boolean clickElementWithKeyPressed(By locator,Keys keys)
   {
	   if(waitForElementClickable(locator, 3, true)) 
	   {
			Actions action=new Actions(driver);
			action.keyDown(keys);
			action.click(driver.findElement(locator));
			action.keyUp(keys);
			action.perform();
			return true;
			
	   }
	   else return false;
   }
   
   public boolean clickElement(By locator,int timeout)
   {
	   try
	  	 {
	  		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
	  		 wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	  		return true;
	  		 
	  	 }
	  	 catch(TimeoutException e)
	  	 {
	  		 return false;
	  	 }
   }
   
   public boolean clickLink(String fullText)
   {
	   return clickElement(By.linkText(fullText), 3);
	   
   }
   private void contextLink(By locator,boolean retry)throws NoSuchElementException,StaleElementReferenceException
   {
	   try
	  	 {
		   WebElement e1=driver.findElement(locator);
		   Actions action=new Actions(driver);
		   action.contextClick(e1).build().perform();
	  	 }
	  	 catch(StaleElementReferenceException e)
	  	 {
	  		 if(retry) contextLink(locator,false);
	  		 else throw e;
	  			 
	  	 }
   }
   
   public void contextLink(By locator)throws NoSuchElementException,StaleElementReferenceException
   {
	   contextLink(locator,true);
   }
   
   protected Alert getAlertDialouge()
   {
	   Alert atr=driver.switchTo().alert();
	   return atr;
	   
	   
   }
   
   protected Alert waitForAndGetAlertDialouge(int timeout)
   {
	   Alert atr=null;
	   try
	  	 {
	  		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
	  		 wait.until(ExpectedConditions.alertIsPresent()); 
	  	 }
	  	 catch(TimeoutException e)
	  	 {
	  	
	  	 }
	   return atr;
   }
   
   public boolean waitForAlertNotPresent(int timeOut)
   {
	   try
	  	 {
	  		 WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(10));
	  		 wait.until(ExpectedConditions.not(ExpectedConditions.alertIsPresent()));
	  		return true;
	  		 
	  	 }
	  	 catch(TimeoutException e)
	  	 {
	  		 return false;
	  	 }
   }
   
   public boolean waitForAndAcceptAlert(int timeOut)
   {
	   boolean ret=false;
	   Alert alert=waitForAndGetAlertDialouge(timeOut);
	   if(alert != null)
	   {
		   alert.accept();
		   ret=waitForAlertNotPresent(2);
	   }
	   return ret;	   
   }
   
   public boolean waitForAndDismissAlert(int timeOut)
   {
	   boolean ret=false;
	   Alert alert=waitForAndGetAlertDialouge(timeOut);
	   if(alert != null)
	   {
		   alert.dismiss();
		   switchFocusToMainWindow();
		   ret=waitForAlertNotPresent(2);
	   }
	   return ret;	   
   }
   
   public String waitForAndGetAlertMessage(int timeOut)
   {
	  String ret=null;
	   Alert alert=waitForAndGetAlertDialouge(timeOut);
	   if(alert != null)
	   {
		  
		   ret=alert.getText();
	   }
	   return ret;	   
   }
   
   public File takeScreenShot(String filePath)
   {
	   File tmpImage=driver.getScreenshotAs(OutputType.FILE);
	   File image=new File(filePath);
	   if(!image.getParentFile().exists())
	   {
		   image.getParentFile().mkdir();	   }
	   try
	   {
		   FileUtils.copyFile(tmpImage, image);
	   }
	   catch(IOException e)
	   {
		   return null;
	   }
	   return image;
   }
   
   
   public void deleteAllCookies()
   {
	   driver.manage().deleteAllCookies();
   }
   
   public boolean isChrome()
   {
	   return this instanceof ChromeInstance;
   }
   
   public boolean isFirefox()
   {
	   return this instanceof  FireFoxInstance;
   }
   
   public List<WebElement> getListOfElements(By Locator)
   {
	   sleep(3000);
	   
	return driver.findElements(Locator);
	   
	
   }
   public void sleep(int value)
   {
	   try {
		Thread.sleep(value);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   
   public boolean waitforvisibility(By findby)
	{
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated((findby)));
		
		return true;
	}
   
   
   public void mouseover(By locator)
   {
	   
	   Actions a= new Actions(driver);
		//a.moveToElement(driver.findElement(By.xpath("//div[@class='exehdJ']"))).build().perform();
		a.moveToElement(driver.findElement(locator)).build().perform();
		
	}
   
   public void clickElementWithCtrlKeyPressed(WebElement e,Keys keys)
   {
	   
	   
			Actions action=new Actions(driver);
			action.keyDown(keys).click(e).keyUp(keys).perform();
			
			
	   }
   
   public void readJsonData()
   {
	   try {
		String jsoncontent=FileUtils.readFileToString(new File("E://selenium_workspace//ecomAuto//src//test//java//resources//data.json"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
	   
   }
   
   



    
     

