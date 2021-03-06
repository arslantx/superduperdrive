package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By tabNavNotes = By.id("nav-notes-tab");
    private By tabNavCredentials = By.id("nav-credentials-tab");
    private By buttonLogout = By.xpath("//button[.='Logout']");

    // files tab
    private By inputFileUpload = By.id("fileUpload");
    private By buttonFileUpload = By.id("fileUploadButton");
    private By fileNames = By.cssSelector("[data-id='fileNames']");

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

    // notifications
    private By linkChangeSuccess = By.cssSelector("[data-id='changeSuccessLink']");

    public HomePage(WebDriver driver) {
        super.driver = driver;
    }

    @Override
    public void waitForPageToLoad() {
        wait(1);
        waitForClickability(buttonLogout);
        waitForClickability(tabNavNotes);
    }

    public void clickLogout() {
        click(buttonLogout);
    }

    public void clickChangeSuccessLink() {
        waitForVisibility(linkChangeSuccess);
        click(linkChangeSuccess);
        wait(1);
        waitForPageToLoad();
    }

    // FILE UPLOAD METHODS

    public void uploadFile(String filePath) {
        enterText(inputFileUpload, filePath);
        click(buttonFileUpload);
        wait(1);
        clickChangeSuccessLink();
    }

    public int getFileCount() {
        return getElementCount(fileNames);
    }

    public String getFirstFileName() {
        return getText(fileNames);
    }

    // NOTES METHODS
    
    public void addNote(String title, String description) {
        switchToNotesTab();
        click(buttonAddNewNote);
        submitNoteModal(title, description);
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewNote);
    }
    
    public void deleteFirstNote() {
        click(buttonNoteDelete);
        waitForDisappear(buttonAddNewNote);
        wait(1);
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewNote);
    }

    public void editFirstNote(String title, String description) {
        click(buttonNoteEdit);
        submitNoteModal(title, description);
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewNote);
    }

    public String getFirstNoteTitle() {
        return getText(noteTitles);
    }

    public String getFirstNoteDescription() {
        return getText(noteDescriptions);
    }

    public int getNoteCount() {
        return getElementCount(noteTitles);
    }

    public void submitNoteModal(String title, String description) {
        waitForVisibility(headerNoteModal);
        enterText(inputNoteTitle, title);
        enterText(inputNoteDescription, description);
        click(buttonNoteSaveChanges);
        waitForDisappear(buttonAddNewNote);
        wait(1);
        waitForVisibility(linkChangeSuccess);
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
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewCred);
    }

    public void deleteFirstCred() {
        click(buttonDeleteCred);
        waitForDisappear(buttonAddNewCred);
        wait(1);
        waitForVisibility(linkChangeSuccess);
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewCred);
    }

    public void editFirstCred(String url, String username, String password) {
        click(buttonEditCred);
        submitCredModal(url, username, password);
        clickChangeSuccessLink();
        waitForClickability(buttonAddNewCred);
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

    public int getCredentialCount() {
        return getElementCount(credUrls);
    }

    public void submitCredModal(String url, String username, String password) {
        waitForVisibility(headerCredModal);
        enterText(inputCredUrl, url);
        enterText(inputCredUser, username);
        enterText(inputCredPwd, password);
        click(buttonCredSaveChanges);
        wait(1);
        waitForVisibility(linkChangeSuccess);
    }

    public void switchToCredentialsTab() {
        click((tabNavCredentials));
        waitForClickability(buttonAddNewCred);
    }

    public void openFirstCredentialEditModal() {
        click(buttonEditCred);
        waitForVisibility(headerCredModal);
    }

    public Credential getCredentialFromModal() {
        Credential credential = new Credential();
        credential.setUrl(getValue(inputCredUrl));
        credential.setUsername(getValue(inputCredUser));
        credential.setDecryptedPassword(getValue(inputCredPwd));
        return credential;
    }
}
