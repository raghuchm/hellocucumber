package testing;

import browsers.BrowserInstance;

public abstract class PageObject 
{
    protected BrowserInstance driver;
    public PageObject(BrowserInstance browser)
    {
        driver=browser;
    }
    public abstract boolean waitForLoaded();
}
