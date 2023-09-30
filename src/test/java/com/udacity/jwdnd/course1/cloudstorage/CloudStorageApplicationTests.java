package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

  @LocalServerPort
  private int port;

  private static final String noteTitle = "Test Note Title";
  private static final String noteDescription = "Test Note Description";
  private static final String url = "http://test.url/test";
  private static final String username = "Test Username";
  private static final String password = "Test Password";

  @Autowired
  private Helper helper;

  protected WebDriver driver;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.driver = new ChromeDriver();
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  @Test
  public void getLoginPage() {
    driver.get("http://localhost:" + this.port + "/login");
    Assertions.assertEquals("Login", driver.getTitle());
  }

  /**
   * PLEASE DO NOT DELETE THIS method.
   * Helper method for Udacity-supplied sanity checks.
   **/
  private void doMockSignUp(
    String firstName,
    String lastName,
    String userName,
    String password
  ) {
    // Create a dummy account for logging in later.

    // Visit the sign-up page.
    WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
    driver.get("http://localhost:" + this.port + "/signup");
    webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

    // Fill out credentials
    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName"))
    );
    WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
    inputFirstName.click();
    inputFirstName.sendKeys(firstName);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName"))
    );
    WebElement inputLastName = driver.findElement(By.id("inputLastName"));
    inputLastName.click();
    inputLastName.sendKeys(lastName);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))
    );
    WebElement inputUsername = driver.findElement(By.id("inputUsername"));
    inputUsername.click();
    inputUsername.sendKeys(userName);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))
    );
    WebElement inputPassword = driver.findElement(By.id("inputPassword"));
    inputPassword.click();
    inputPassword.sendKeys(password);

    // Attempt to sign up.
    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp"))
    );
    WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
    buttonSignUp.click();
    /* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
  }

  /**
   * PLEASE DO NOT DELETE THIS method.
   * Helper method for Udacity-supplied sanity checks.
   **/
  private void doLogIn(String userName, String password) {
    // Log in to our dummy account.
    driver.get("http://localhost:" + this.port + "/login");
    WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername"))
    );
    WebElement loginUserName = driver.findElement(By.id("inputUsername"));
    loginUserName.click();
    loginUserName.sendKeys(userName);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword"))
    );
    WebElement loginPassword = driver.findElement(By.id("inputPassword"));
    loginPassword.click();
    loginPassword.sendKeys(password);

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("login-button"))
    );
    WebElement loginButton = driver.findElement(By.id("login-button"));
    loginButton.click();

    webDriverWait.until(ExpectedConditions.titleContains("Home"));
  }

  /**
   * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
   * rest of your code.
   * This test is provided by Udacity to perform some basic sanity testing of
   * your code to ensure that it meets certain rubric criteria.
   *
   * If this test is failing, please ensure that you are handling redirecting users
   * back to the login page after a succesful sign up.
   * Read more about the requirement in the rubric:
   * https://review.udacity.com/#!/rubrics/2724/view
   */
  @Test
  public void testRedirection() throws InterruptedException {
    // Create a test account
    doMockSignUp("Redirection", "Test", "RT", "123");
    WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
    webDriverWait.until(
      ExpectedConditions.presenceOfElementLocated(By.id("login"))
    );
    // Check if we have been redirected to the log in page.
    Assertions.assertEquals(
      "http://localhost:" + this.port + "/login",
      driver.getCurrentUrl()
    );
  }

  /**
   * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
   * rest of your code.
   * This test is provided by Udacity to perform some basic sanity testing of
   * your code to ensure that it meets certain rubric criteria.
   *
   * If this test is failing, please ensure that you are handling bad URLs
   * gracefully, for example with a custom error page.
   *
   * Read more about custom error pages at:
   * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
   */
  @Test
  public void testBadUrl() {
    // Create a test account
    doMockSignUp("URL", "Test", "UT", "123");
    doLogIn("UT", "123");

    // Try to access a random made-up URL.
    driver.get("http://localhost:" + this.port + "/some-random-page");
    Assertions.assertFalse(
      driver.getPageSource().contains("Whitelabel Error Page")
    );
  }

  /**
   * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
   * rest of your code.
   * This test is provided by Udacity to perform some basic sanity testing of
   * your code to ensure that it meets certain rubric criteria.
   *
   * If this test is failing, please ensure that you are handling uploading large files (>1MB),
   * gracefully in your code.
   *
   * Read more about file size limits here:
   * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
   */
  @Test
  public void testLargeUpload() {
    // Create a test account
    doMockSignUp("Large File", "Test", "LFT", "123");
    doLogIn("LFT", "123");

    // Try to upload an arbitrary large file
    WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
    String fileName = "upload5m.zip";

    webDriverWait.until(
      ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload"))
    );
    WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
    fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

    WebElement uploadButton = driver.findElement(By.id("uploadButton"));
    uploadButton.click();
    try {
      webDriverWait.until(
        ExpectedConditions.presenceOfElementLocated(By.id("success"))
      );
    } catch (org.openqa.selenium.TimeoutException e) {
      System.out.println("Large File upload failed");
    }
    Assertions.assertFalse(
      driver.getPageSource().contains("HTTP Status 403 – Forbidden")
    );
  }

  // ===========================================================================
  // Note Tests
  // ===========================================================================

  @Test
  public void testDelete() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createNote(homePage);
    homePage.navToNotesTab();
    homePage = new HomePage(driver);
    Assertions.assertFalse(homePage.noNotes(driver));
    deleteNote(homePage);
    Assertions.assertTrue(homePage.noNotes(driver));
  }

  private void deleteNote(HomePage homePage) {
    homePage.deleteNote();
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
  }

  @Test
  public void testCreateAndDisplay() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createNote(homePage);
    homePage.navToNotesTab();

    homePage = new HomePage(driver);
    Note note = homePage.getFirstNote();
    Assertions.assertEquals(noteTitle, note.getNoteTitle());
    Assertions.assertEquals(noteDescription, note.getNoteDescription());

    deleteNote(homePage);
    homePage.logout();
  }

  @Test
  public void testModify() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createNote(homePage);
    homePage.navToNotesTab();

    homePage = new HomePage(driver);
    homePage.editNote();
    String modifiedNoteTitle = "Modified Note Title";
    homePage.modifyNoteTitle(modifiedNoteTitle);
    String modifiedNoteDescription = "Modified Note Description";
    homePage.modifyNoteDescription(modifiedNoteDescription);
    homePage.saveNoteChanges();

    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
    homePage.navToNotesTab();
    Note note = homePage.getFirstNote();
    Assertions.assertEquals(modifiedNoteTitle, note.getNoteTitle());
    Assertions.assertEquals(modifiedNoteDescription, note.getNoteDescription());
  }

  private void createNote(HomePage homePage) {
    homePage.navToNotesTab();
    homePage.addNewNote();
    homePage.setNoteTitle(noteTitle);
    homePage.setNoteDescription(noteDescription);
    homePage.saveNoteChanges();

    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
    homePage.navToNotesTab();
    homePage.saveNoteChanges();
  }

  // ===========================================================================
  // Credential Tests
  // ===========================================================================

  @Test
  public void testCredentialCreation() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createAndVerifyCredential(homePage);
    homePage.deleteCredential();
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
    homePage.logout();
  }

  private void createAndVerifyCredential(HomePage homePage) {
    createCredential(homePage);
    homePage.navToCredentialsTab();
    Credential credential = homePage.getFirstCredential();
    Assertions.assertEquals(url, credential.getUrl());
    Assertions.assertEquals(username, credential.getUsername());
    Assertions.assertNotEquals(password, credential.getPassword());
  }

  private void createCredential(HomePage homePage) {
    homePage.navToCredentialsTab();
    homePage.addNewCredential();
    setCredentialFields(url, username, password, homePage);
    homePage.saveCredentialChanges();
    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
  }

  private void setCredentialFields(
    String url,
    String username,
    String password,
    HomePage homePage
  ) {
    homePage.setCredentialUrl(url);
    homePage.setCredentialUsername(username);
    homePage.setCredentialPassword(password);
  }

  @Test
  public void testCredentialModification() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createAndVerifyCredential(homePage);
    Credential originalCredential = homePage.getFirstCredential();
    String firstEncryptedPassword = originalCredential.getPassword();
    homePage.editCredential();
    String newUrl = "Test Modified Url";
    String newCredentialUsername = "Test Modified Name";
    String newPassword = "Test Modified Password";
    setCredentialFields(newUrl, newCredentialUsername, newPassword, homePage);
    homePage.saveCredentialChanges();

    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();

    homePage.navToCredentialsTab();
    Credential modifiedCredential = homePage.getFirstCredential();
    Assertions.assertEquals(newUrl, modifiedCredential.getUrl());
    Assertions.assertEquals(
      newCredentialUsername,
      modifiedCredential.getUsername()
    );
    String modifiedCredentialPassword = modifiedCredential.getPassword();

    Assertions.assertNotEquals(newPassword, modifiedCredentialPassword);
    Assertions.assertNotEquals(
      firstEncryptedPassword,
      modifiedCredentialPassword
    );

    homePage.deleteCredential();
    resultPage.clickOk();
    homePage.logout();
  }

  @Test
  public void testDeletion() {
    HomePage homePage = helper.signUpAndLogin(driver, this.port);
    createCredential(homePage);
    Assertions.assertFalse(homePage.noCredentials(driver));

    homePage.navToCredentialsTab();
    setCredentialFields(
      "Test second url",
      "Test second username",
      "Test second password",
      homePage
    );
    homePage.saveCredentialChanges();

    ResultPage resultPage = new ResultPage(driver);
    resultPage.clickOk();
    homePage.navToCredentialsTab();

    homePage.deleteCredential();
    resultPage.clickOk();
    homePage.navToCredentialsTab();

    homePage.deleteCredential();
    resultPage.clickOk();
    homePage.navToCredentialsTab();

    Assertions.assertTrue(homePage.noCredentials(driver));
    homePage.logout();
  }
}
