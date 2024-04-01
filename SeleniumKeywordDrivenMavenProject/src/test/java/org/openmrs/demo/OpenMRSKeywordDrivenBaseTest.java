package org.openmrs.demo;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import openmrs.page.objects.BaseClass;
import openmrs.page.objects.FindPatientPage;
import openmrs.page.objects.HomePage;
import openmrs.page.objects.LoginPage;
import openmrs.page.objects.PatientDetailsPage;
import openmrs.page.objects.RegistrationPage;
import openmrs.page.objects.Utilities;

public class OpenMRSKeywordDrivenBaseTest {

	public static WebDriver driver;
	BaseClass baseCls;
	LoginPage loginPage;
	HomePage homePage;
	RegistrationPage registrationPage;
	PatientDetailsPage patientDetailsPage;
	FindPatientPage findPatientPage;
	Actions actions;
	JavascriptExecutor jsExec;
	Properties testProperties;
	Utilities utilities;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		try {
			testProperties = new Properties();
			testProperties.load(new FileInputStream(
					new File(System.getProperty("user.dir") + "\\src\\test\\resources\\test.properties")));
		} catch (Exception e) {
			System.out.println("Exception Occured while reading the test properties: " + e.getClass());
		}
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + testProperties.getProperty("chrome.driver.path"));

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
		utilities = new Utilities(driver);
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		baseCls.navigateToApp("https://demo.openmrs.org/openmrs/login.htm");
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "UserName", "Password", "ModuleName" })
	public void beforeMethod(String userName, String password, String moduleNmae) {
		loginPage.login(userName, password, moduleNmae);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		try {
			Thread.sleep(5000);
			homePage.clickLogout();
			Assert.assertTrue(loginPage.verifyLoginPage());
		} catch (Exception e) {
			System.out.println("Exception Occured while clicking Logout:  " + e.getClass());
		}
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
//		driver.close();
		driver.quit();
	}

	@DataProvider(name = "TestDataProvider")
	public Iterator<Object[]> testDataProvider(Method method) {
		return Utilities.readKeywordDrivenTestDataFromExcel(
				System.getProperty("user.dir") + testProperties.getProperty("test.data.file.path"), "TestData",
				method.getName());

	}
}
