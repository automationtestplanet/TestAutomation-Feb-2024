package org.openmrs;

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
import org.testng.annotations.Parameters;

import page.objects.BaseClass;
import page.objects.FindPatientPage;
import page.objects.HomePage;
import page.objects.LoginPage;
import page.objects.PatientDetailsPage;
import page.objects.RegistrationPage;

public class OpenMRSTestNGBaseTest {

	public static WebDriver driver;
	BaseClass baseCls;
	LoginPage loginPage;
	HomePage homePage;
	RegistrationPage registrationPage;
	PatientDetailsPage patientDetailsPage;
	FindPatientPage findPatientPage;
	Actions actions;
	JavascriptExecutor jsExec;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {

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

}
