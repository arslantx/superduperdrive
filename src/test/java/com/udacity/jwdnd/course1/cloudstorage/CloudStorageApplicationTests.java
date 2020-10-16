package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.HttpResponseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    NoteService noteService;

    @Autowired
    CredentialService credentialService;

	@LocalServerPort
    private int port;
    
    private WebDriver driver;
    private LoginPage loginPage;
    private SignupPage signupPage;
    private HomePage homePage;
    
	@BeforeAll
	static void beforeAll() throws HttpResponseException {
        WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
        this.driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        signupPage = new SignupPage(driver);
        homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
    }
    
    @Test
	public void verifyUnauthorizedAccessToHome() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    @Test
    public void verifyNewUserSignupAndLogin() {
        // verify signup
        driver.get("http://localhost:" + this.port + "/signup");
        String username = "testUser";
        String password = "testPwd123";
        signupPage.submitSignUpForm("fn", "ln", username, password);
        Assertions.assertTrue(signupPage.isSuccessMessageVisible());
        
        //verify login
        signupPage.clickBackToLogin();
        loginPage.login(username, password);
        homePage.waitForPageToLoad();
        Assertions.assertEquals("Home", driver.getTitle());
        
        //verify logout
        homePage.clickLogout();
        loginPage.waitForPageToLoad();
        Assertions.assertEquals("Login", driver.getTitle());

        //verify Home no longer accessible
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("Home", driver.getTitle());
    }

    @Test
    public void verifyNoteCreate() {
        loginAsTestUser();
        String noteTitle = "Test Note 1";
        String noteDesc = "Note 1 desc";
        homePage.addNote(noteTitle, noteDesc);
        Assertions.assertEquals(noteTitle, homePage.getFirstNoteTitle());
        Assertions.assertEquals(noteDesc, homePage.getFirstNoteDescription());
    }

    @Test
    public void verifyNoteEdit() {
        loginAsUserWithExistingNote();
        homePage.switchToNotesTab();
        String noteTitle = homePage.getFirstNoteTitle() + " updated";
        String noteDesc = homePage.getFirstNoteDescription() + " updated";
        homePage.editFirstNote(noteTitle, noteDesc);
        Assertions.assertEquals(noteTitle, homePage.getFirstNoteTitle());
        Assertions.assertEquals(noteDesc, homePage.getFirstNoteDescription());
    }

    @Test
    public void verifyNoteDelete() {
        loginAsUserWithExistingNote();
        homePage.switchToNotesTab();
        homePage.deleteFirstNote();
        homePage.switchToNotesTab();
        Assertions.assertEquals(0, homePage.getNumberOfNotes());
    }

    @Test
    public void verifyCredentialCreate() {
        loginAsTestUser();
        String credUrl = "http://www.google.com";
        String credUser = "testUser1";
        String credPwd = "testPwd1";
        homePage.addCredential(credUrl, credUser, credPwd);
        Assertions.assertEquals(credUrl, homePage.getFirstCredUrl());
        Assertions.assertEquals(credUser, homePage.getFirstCredUsername());
        Assertions.assertNotEquals(credPwd, homePage.getFirstCredPassword());
        homePage.openFirstCredentialEditModal();
        Credential credFromModal = homePage.getCredentialFromModal();
        Assertions.assertEquals(credUrl, credFromModal.getUrl());
        Assertions.assertEquals(credUser, credFromModal.getUsername());
        Assertions.assertEquals(credPwd, credFromModal.getDecryptedPassword());
    }

    @Test
    public void verifyCredentialEdit() {
        loginAsUserWithExistingCreds();
        homePage.switchToCredentialsTab();
        String credUrl = homePage.getFirstCredUrl().replace("com", "net");
        String credUser = homePage.getFirstCredUsername() + "new";
        String credPwd = homePage.getFirstCredPassword() + "new";
        homePage.editFirstCred(credUrl, credUser, credPwd);
        Assertions.assertEquals(credUrl, homePage.getFirstCredUrl());
        Assertions.assertEquals(credUser, homePage.getFirstCredUsername());
        Assertions.assertNotEquals(credPwd, homePage.getFirstCredPassword());
        homePage.openFirstCredentialEditModal();
        Credential credFromModal = homePage.getCredentialFromModal();
        Assertions.assertEquals(credUrl, credFromModal.getUrl());
        Assertions.assertEquals(credUser, credFromModal.getUsername());
        Assertions.assertEquals(credPwd, credFromModal.getDecryptedPassword());
    }

    @Test
    public void verifyCredentialDelete() {
        loginAsUserWithExistingCreds();
        homePage.switchToCredentialsTab();
        homePage.deleteFirstCred();
        Assertions.assertEquals(0, homePage.getNumberOfCredentials());
    }
    
    
    // HELPER METHODS ONLY BEYOND THIS LINE

    private User createTestUser() {
        String username = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomAlphabetic(5);
        User user = new User();
        user.setFirstname("firstname");
        user.setLastname("lastname");
        user.setUsername(username);
        user.setPassword(password);
        userService.createUser(user);
        // re-set password since service hashes it
        user.setPassword(password);
        return user;
    }

    private void loginAsTestUser(User... users) {
        User user;
        if (users.length == 0) {
            user = createTestUser();
        } else {
            user = users[0];
        }
        driver.get("http://localhost:" + this.port + "/login");
        loginPage.login(user.getUsername(), user.getPassword());
    }

    private void loginAsUserWithExistingNote() {
        User user = createTestUser();
        Note note = new Note();
        note.setUserid(user.getUserid());
        note.setNotetitle("sample title");
        note.setNotedescription("sample description");
        noteService.createOrEditNote(note);
        loginAsTestUser(user);
    }

    private void loginAsUserWithExistingCreds() {
        User user = createTestUser();
        Credential cred = new Credential();
        cred.setUrl("http://www.google.com");
        cred.setUsername("testUser1");
        cred.setDecryptedPassword("testPass1");
        cred.setUserid(user.getUserid());
        credentialService.createOrEditCredential(cred);
        // re-set password since service encrypts it
        loginAsTestUser(user);
    }
}
