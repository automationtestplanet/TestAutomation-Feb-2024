package org.openmrs;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import page.objects.BaseClass;
import page.objects.FindPatientPage;
import page.objects.HomePage;
import page.objects.LoginPage;
import page.objects.PatientDetailsPage;
import page.objects.RegistrationPage;

class OpenMRSJunitTest extends JUnitTestSuit {

	public static WebDriver driver;
	BaseClass baseCls;
	LoginPage loginPage;
	HomePage homePage;
	RegistrationPage registrationPage;
	PatientDetailsPage patientDetailsPage;
	FindPatientPage findPatientPage;
	Actions actions;
	JavascriptExecutor jsExec;

	@BeforeAll
	public void setUpBeforeClass() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		baseCls = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		registrationPage = new RegistrationPage(driver);
		patientDetailsPage = new PatientDetailsPage(driver);
		findPatientPage = new FindPatientPage(driver);
		actions = new Actions(driver);
		jsExec = (JavascriptExecutor) driver;

		baseCls.navigateToApp("https://demo.openmrs.org/openmrs/login.htm");
	}

	@AfterAll
	public void tearDownAfterClass() {
//		driver.close();
		driver.quit();
	}

	@BeforeEach
	public void setUp() {
		loginPage.login("Admin", "Admin123", "Registration Desk");
		Assert.assertTrue(homePage.verifyLogin());
	}

	@AfterEach
	public void tearDown() {
		homePage.clickLogout();
		Assert.assertTrue(loginPage.verifyLoginPage());
	}

	@Test
	@Order(1)
	void registerPatientTest() {
		System.out.println("---------------------------Register Patient--------------------------------");
		assertTrue("Verify Register Patient Tile", homePage.verifyModuleTile("Register a patient"));
		homePage.clickModuleTile("Register a patient");
		Assert.assertEquals("Verify Register Patient Page", "Register a patient", registrationPage.verifyModulePage());
		registrationPage.enterName("Ganesh, G");
		registrationPage.clikNextButton();
		registrationPage.selectGender("Male");
		registrationPage.clikNextButton();
		registrationPage.setDateOfBorth("10, January, 1992");
		registrationPage.clikNextButton();
		registrationPage.enterAddress("Flat 102, S R Nagar", "Hyderabad", "Telangana", "India", "500038");
		registrationPage.clikNextButton();
		registrationPage.setPhoneNumber("9876543210");
		registrationPage.clikNextButton();
		registrationPage.clikNextButton();
		assertTrue(registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992", "9876543210"));
		Assert.assertEquals("Verify Registration Details", true,
				registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992", "9876543210"));
		registrationPage.clikConfirmButton();
		assertTrue(patientDetailsPage.verifyRegesteredDetails("Ganesh, G"));
		Assert.assertEquals("Verify Registered Details", true, patientDetailsPage.verifyRegesteredDetails("Ganesh, G"));
		patientDetailsPage.storePatientIdToPropertiesFile();
		System.out.println(patientDetailsPage.getPatientIdFromPropertis());
	}

	@Test
	@Order(2)
	@Disabled
	void findPatientTest() {
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue("Verify Find Patient Tile", homePage.verifyModuleTile("Find Patient Record"));
		homePage.clickModuleTile("Find Patient Record");
		jsExec.executeScript("window.scroll(0, document.body.scrollHeight)");
		jsExec.executeScript("window.scroll(0, document.body.scrollTop)");
		jsExec.executeScript("arguments[0].scrollIntoView(true)", findPatientPage.getFindPatientRecordsInfo());
		jsExec.executeScript("window.scroll(0, document.body.scrollTop)");
		findPatientPage.searchPatientRecord(patientId);
		Assert.assertEquals("Verify Patient Id in Search Result",
				findPatientPage.verifyResultTableColumnValue("Identifier"), patientId);
		WebElement resultTableElement = findPatientPage.getResultTableColumnElement("Identifier");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(resultTableElement));
		actions.moveToElement(resultTableElement).click(resultTableElement).build().perform();
		assertTrue("Verify Searched Patient Details: ", patientDetailsPage.verifyRegesteredDetails("Ganesh, G"));
	}

	@Test
	@Order(3)
	@Disabled
	void actieVisitsTest() throws Exception {
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue("Verify Find Patient Tile", homePage.verifyModuleTile("Find Patient Record"));
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientId);
		Assert.assertEquals("Verify Patient Id in Search Result",
				findPatientPage.verifyResultTableColumnValue("Identifier"), patientId);
		findPatientPage.clickResultTableColumnElement("Identifier");
		patientDetailsPage.startVisit();
		patientDetailsPage.clickAttachmentsLink();
		String filePath = "UploadFile.pdf";
		patientDetailsPage.uploadFile(filePath, "TestUploadFile");
		assertTrue("Verify FIle Upload", patientDetailsPage.verifyFileUpload());
	}

	@Test
	@Order(4)
	@Disabled
	void deletePatientTest() {
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue("Verify Find Patient Tile", homePage.verifyModuleTile("Find Patient Record"));
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientId);
		Assert.assertEquals("Verify Patient Id in Search Result",
				findPatientPage.verifyResultTableColumnValue("Identifier"), patientId);
		findPatientPage.clickResultTableColumnElement("Identifier");
		patientDetailsPage.deletePatient("Other");
		findPatientPage.searchPatientRecord(patientDetailsPage.getPatientIdFromPropertis());
		assertTrue("Verify Delete Patient", findPatientPage.verifyPatientRecordNotFiltered());
	}

}
