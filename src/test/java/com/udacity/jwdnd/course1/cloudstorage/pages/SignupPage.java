package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {
    
    private By headerSignup = By.xpath("//h1[.='Sign Up']");
    private By linkBackToLogin = By.id("linkBackToLogin");
    private By inputFirstname = By.id("inputFirstName");
    private By inputLastname = By.id("inputLastName");
    private By inputUsername = By.id("inputUsername");
    private By inputPassword = By.id("inputPassword");
    private By buttonSignup = By.xpath("//button[.='Sign Up']");
    private By alertSignupSuccess = By.id("messageSuccess");

    public SignupPage(WebDriver driver) {
        super.driver = driver;
    }

    @Override
    public void waitForPageToLoad() {
        wait(1);
        waitForVisibility(headerSignup);
    }

    public void clickBackToLogin() {
        click(linkBackToLogin);
    }

    public boolean isSuccessMessageVisible() {
        waitForVisibility(alertSignupSuccess);
        return isDisplayed(alertSignupSuccess);
    }

    public void submitSignUpForm(String firstname, String lastname, String username,
            String password) {
                enterText(inputFirstname, firstname);
                enterText(inputLastname, lastname);
                enterText(inputUsername, username);
                enterText(inputPassword, password);
                click(buttonSignup);
                wait(1);
    }
}
