package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By headerLogin = By.xpath("//h1[.='Login']");
    private By inputUsername = By.id("inputUsername");
    private By inputPassword = By.id("inputPassword");
    private By buttonSubmit = By.xpath("//button[.='Login']");
    private By linkSignup = By.linkText("Click here to sign up");

    public LoginPage(WebDriver driver) {
        super.driver = driver;
    }

    @Override
    public void waitForPageToLoad() {
        wait(1);
        waitForVisibility(headerLogin);
        waitForVisibility(inputUsername);
        waitForVisibility(inputPassword);
        waitForClickability(buttonSubmit);
    }

    public void clickSignup() {
        driver.findElement(linkSignup);
    }

    public void login(String username, String password) {
        waitForPageToLoad();
        enterText(inputUsername, username);
        enterText(inputPassword, password);
        click(buttonSubmit);
        wait(1);
    }
}
