package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    protected WebDriver driver;

    private static final long DEFAULT_WAIT_IN_SECONDS = 15;

    public abstract void waitForPageToLoad();

    protected void clear(By locator) {
        driver.findElement(locator).clear();
    }
    
    protected void click(By locator) {
        waitForClickability(locator);
        driver.findElement(locator).click();
    }

    protected void enterText(By locator, String text) {
        waitForVisibility(locator);
        waitForClickability(locator);
        clear(locator);
        driver.findElement(locator).sendKeys(text);
    }

    protected int getElementCount(By locator) {
        return driver.findElements(locator).size();
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }
    
    private WebDriverWait getWebDriverWaitObject() {
        return new WebDriverWait(driver, DEFAULT_WAIT_IN_SECONDS);
    }

    protected boolean isDisplayed(By locator) {
        return driver.findElement(locator).isDisplayed();
    }

    protected void waitForVisibility(By locator) {
        WebDriverWait wait = getWebDriverWaitObject();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForClickability(By locator) {
        WebDriverWait wait = getWebDriverWaitObject();
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForDisappear(By locator) {
        WebDriverWait wait = getWebDriverWaitObject();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
