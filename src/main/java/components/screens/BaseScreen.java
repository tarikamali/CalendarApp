package components.screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public abstract class BaseScreen {

    public AppiumDriver<? extends MobileElement> driver;

    public BaseScreen(AppiumDriver<? extends MobileElement> driver) {
        this.driver = driver;
    }

    public AppiumDriver<? extends MobileElement> getDriver() {
        return driver;
    }

    public void clickButton(String s)
    {
        driver.findElement(MobileBy.xpath("//android.widget.Button[@text='"+s+"'][1]")).click();
    }
    public void clickText(String s)
    {
        driver.findElement(MobileBy.xpath("//android.view.View[@text='"+s+"'][1]")).click();
    }

    public boolean waitForElementPresent(By locator, long timeoutSeconds) {
        return waitTillElementPresent(locator,timeoutSeconds);
    }
    public boolean waitTillElementPresent(By locator, long timeoutSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if(driver.findElements(locator).size()>0)
        {
            return true;
        }else {
            System.out.println("Exception from waitForElementPresent :" + locator);
            return false;
        }
    }

    public WebElement waitTillElementVisibilty(By xpath) throws IOException {
        WebDriverWait wait=new WebDriverWait(driver,30);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
    }
    public void scrollToElement(MobileElement element, int time) {
        int counter=0;
        do {
            if (isElementPresent(element)) {
                break;
            } else {
                      scroll("DOWN", 1000);

                counter=counter+1;
            }
        } while (counter < time);
    }

    public void scroll(String direction, int duration) {
        Double screenHeightStart,screenHeightEnd;
        Dimension dimensions = driver.manage().window().getSize();

            screenHeightStart = dimensions.getHeight() * 0.5;
            screenHeightEnd = dimensions.getHeight() * 0.2;

        int scrollStart = screenHeightStart.intValue();
        int scrollEnd = screenHeightEnd.intValue();
        if (direction == "UP") {
            driver.swipe(0, scrollEnd, 0, scrollStart, duration);
        } else if (direction == "DOWN") {
            driver.swipe(0, scrollStart, 0, scrollEnd, duration);
        }
    }

    public boolean isElementPresent(MobileElement mobileElement) {
        try {
            return mobileElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitSeconds(Integer sec) throws Exception {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (java.lang.InterruptedException e) {

        }

    }

}
