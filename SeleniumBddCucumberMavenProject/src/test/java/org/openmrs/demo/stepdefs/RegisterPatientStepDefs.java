package org.openmrs.demo.stepdefs;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import openmrs.page.objects.BaseClass;
import openmrs.page.objects.FindPatientPage;
import openmrs.page.objects.HomePage;
import openmrs.page.objects.LoginPage;
import openmrs.page.objects.PatientDetailsPage;
import openmrs.page.objects.RegistrationPage;
import openmrs.page.objects.Utilities;

public class RegisterPatientStepDefs {

	public static WebDriver driver;
	static BaseClass baseCls;
	LoginPage loginPage;
	HomePage homePage;
	RegistrationPage registrationPage;
	PatientDetailsPage patientDetailsPage;
	FindPatientPage findPatientPage;
	Actions actions;
	JavascriptExecutor jsExec;
	static Properties testProperties;
	Utilities utilities;

	static {
		try {
			testProperties = new Properties();
			testProperties.load(new FileInputStream(
					new File(System.getProperty("user.dir") + "\\src\\test\\resources\\test.properties")));
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + testProperties.getProperty("chrome.driver.path"));

		} catch (Exception e) {
			System.out.println("Exception Occured while reading the test properties: " + e.getClass());
		}

	}

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		baseCls = new BaseClass(driver);
		baseCls.navigateToApp("https://demo.openmrs.org/openmrs/login.htm");

		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
		registrationPage = new RegistrationPage(driver);
		patientDetailsPage = new PatientDetailsPage(driver);
		findPatientPage = new FindPatientPage(driver);
		actions = new Actions(driver);
		jsExec = (JavascriptExecutor) driver;
		utilities = new Utilities(driver);
		loginPage.login(testProperties.getProperty("user.name"), testProperties.getProperty("password"),
				testProperties.getProperty("module.name"));
	}

	@After
	public void tearDown() {
		try {
			Thread.sleep(5000);
			homePage.clickLogout();
			org.junit.Assert.assertTrue(loginPage.verifyLoginPage());
			driver.quit();
		} catch (Exception e) {
			System.out.println("Exception Occured while clicking Logout:  " + e.getClass());
		}
	}

	@Given("user is on Register patient page")
	public void user_is_on_Register_patient_page() {
		org.junit.Assert.assertTrue(homePage.verifyLogin());
		utilities.captureScreenshot();
		org.junit.Assert.assertTrue(homePage.verifyModuleTile("Register a patient"));
		homePage.clickModuleTile("Register a patient");
		org.junit.Assert.assertEquals("Register a patient", registrationPage.verifyModulePage());
		utilities.captureScreenshot();
	}

	@When("user enters patient name as {string} gender as {string} date of birth as {string} address as {string} phone number as {string}")
	public void user_enters_patient_name_as_gender_as_date_of_birth_as_address_as_phone_number_as(String name,
			String gender, String dateOfBirth, String address, String phneNumber) {
		registrationPage.enterName(name);
		registrationPage.clikNextButton();
		registrationPage.selectGender(gender);
		registrationPage.clikNextButton();
		registrationPage.setDateOfBorth(dateOfBirth);
		registrationPage.clikNextButton();
		registrationPage.enterAddress(address);
		registrationPage.clikNextButton();
		registrationPage.setPhoneNumber(phneNumber);
		registrationPage.clikNextButton();
		registrationPage.clikNextButton();
		org.junit.Assert.assertEquals(true,
				registrationPage.verifyRegistrationDetails(name, gender, dateOfBirth, phneNumber));
		utilities.captureScreenshot();
	}

	@When("clicks confirm button")
	public void clicks_confirm_button() {
		registrationPage.clikConfirmButton();
	}

	@When("patient name {string} should be displayed")
	public void patient_name_should_be_displayed(String name) {
		org.junit.Assert.assertTrue(patientDetailsPage.verifyRegesteredDetails(name));
		utilities.captureScreenshot();
	}

	@Then("patient id must be generated")
	public void patient_id_must_be_generated() {
		patientDetailsPage.storePatientIdToPropertiesFile();
		System.out.println(patientDetailsPage.getPatientIdFromPropertis());
	}

}
