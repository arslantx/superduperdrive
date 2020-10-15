package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By tabNavNotes = By.id("nav-notes-tab");
    private By tabNavCredentials = By.id("nav-credentials-tab");
    private By buttonLogout = By.xpath("//button[.='Logout']");
    private By buttonFileUpload = By.xpath("//button[.='Upload']");

    // notes tab
    private By buttonAddNewNote = By.id("buttonAddNewNote");
    private By noteTitles = By.cssSelector("#notesTable tbody tr th");
    private By noteDescriptions = By.cssSelector("#notesTable tbody tr td:nth-of-type(2)");
    private By buttonNoteEdit = By.cssSelector("#notesTable form button:nth-of-type(1)");
    private By buttonNoteDelete = By.cssSelector("#notesTable form button:nth-of-type(2)");

    // note modal
    private By headerNoteModal = By.id("noteModalLabel");
    private By inputNoteTitle = By.id("note-title");
    private By inputNoteDescription = By.id("note-description");
    private By buttonNoteSaveChanges = By.id("noteModalSaveChanges");

    // credentials tab
    private By buttonAddNewCred = By.id("buttonAddNewCred");
    private By buttonEditCred = By.cssSelector("[data-id='editCred']");
    private By buttonDeleteCred = By.cssSelector("[data-id='deleteCred']");
    private By credUrls = By.cssSelector("[data-id='credentialUrl']");
    private By credUsernames = By.cssSelector("[data-id='credentialUsername']");
    private By credPasswords = By.cssSelector("[data-id='credentialPassword']");

    // credentials modal
    private By headerCredModal = By.id("credentialModalLabel");
    private By inputCredUrl = By.id("credential-url");
    private By inputCredUser = By.id("credential-username");
    private By inputCredPwd = By.id("credential-password");
    private By buttonCredSaveChanges = By.id("credModalSaveChanges");

    public HomePage(WebDriver driver) {
        super.driver = driver;
    }

    @Override
    public void waitForPageToLoad() {
        wait(1);
        waitForVisibility(buttonFileUpload);
        waitForClickability(buttonLogout);
        waitForClickability(tabNavNotes);
    }

    public void clickLogout() {
        click(buttonLogout);
    }

    // NOTES METHODS
    
    public void addNote(String title, String description) {
        switchToNotesTab();
        click(buttonAddNewNote);
        submitNoteModal(title, description);
    }
    
    public void deleteFirstNote() {
        click(buttonNoteDelete);
        waitForDisappear(buttonAddNewNote);
        wait(1);
    }

    public void editFirstNote(String title, String description) {
        click(buttonNoteEdit);
        submitNoteModal(title, description);
    }

    public String getFirstNoteTitle() {
        return getText(noteTitles);
    }

    public String getFirstNoteDescription() {
        return getText(noteDescriptions);
    }

    public int getNumberOfNotes() {
        return getElementCount(noteTitles);
    }

    public void submitNoteModal(String title, String description) {
        waitForVisibility(headerNoteModal);
        enterText(inputNoteTitle, title);
        enterText(inputNoteDescription, description);
        click(buttonNoteSaveChanges);
        waitForDisappear(buttonAddNewNote);
        wait(1);
    }

    public void switchToNotesTab() {
        click(tabNavNotes);
        waitForClickability(buttonAddNewNote);
    }

    
    // CREDENTIALS METHODS

    public void addCredential(String url, String username, String password) {
        switchToCredentialsTab();
        click(buttonAddNewCred);
        submitCredModal(url, username, password);
    }

    public void deleteFirstCred() {
        click(buttonDeleteCred);
        waitForDisappear(buttonAddNewCred);
        wait(1);
    }

    public void editFirstCred(String url, String username, String password) {
        click(buttonEditCred);
        submitCredModal(url, username, password);
    }

    public String getFirstCredUrl() {
        return getText(credUrls);
    }

    public String getFirstCredUsername() {
        return getText(credUsernames);
    }

    public String getFirstCredPassword() {
        return getText(credPasswords);
    }

    public int getNumberOfCredentials() {
        return getElementCount(credUrls);
    }

    public void submitCredModal(String url, String username, String password) {
        waitForVisibility(headerCredModal);
        enterText(inputCredUrl, url);
        enterText(inputCredUser, username);
        enterText(inputCredPwd, password);
        click(buttonCredSaveChanges);
        wait(1);
    }

    public void switchToCredentialsTab() {
        click((tabNavCredentials));
        waitForClickability(buttonAddNewCred);
    }
}
