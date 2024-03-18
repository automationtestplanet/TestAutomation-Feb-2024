package org.openmrs;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import page.objects.BaseClass;
import page.objects.FindPatientPage;
import page.objects.HomePage;
import page.objects.LoginPage;
import page.objects.PatientDetailsPage;
import page.objects.RegistrationPage;

public class OpenMrsTest {

	public static WebDriver driver;

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		BaseClass baseCls = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);
		RegistrationPage registrationPage = new RegistrationPage(driver);
		PatientDetailsPage patientDetailsPage = new PatientDetailsPage(driver);
		FindPatientPage findPatientPage = new FindPatientPage(driver);
		Actions actions = new Actions(driver);

		baseCls.navigateToApp("https://demo.openmrs.org/openmrs/login.htm");

		loginPage.login("Admin", "Admin123", "Registration Desk");

		System.out.println("---------------------------Register Patient--------------------------------");
		
		homePage.verifyLogin();
		homePage.verifyModuleTile("Register a patient");
		homePage.clickModuleTile("Register a patient");

		registrationPage.verifyModulePage("Register a patient");
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
		registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992", "9876543210");
		registrationPage.clikConfirmButton();

		patientDetailsPage.verifyRegesteredDetails("Ganesh, G");
		patientDetailsPage.storePatientIdToPropertiesFile();
		System.out.println(patientDetailsPage.getPatientIdFromPropertis());

		
		System.out.println("---------------------------Find Patient--------------------------------");
		
		homePage.clickHomeIcon();
		homePage.verifyModuleTile("Find Patient Record");
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientDetailsPage.getPatientIdFromPropertis());
		findPatientPage.verifyResultTableColumnValue("Identifier",patientDetailsPage.getPatientIdFromPropertis());		
		Thread.sleep(5000);		
		WebElement resultTableElement = findPatientPage.getResultTableColumnElement("Identifier");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(resultTableElement));		
		actions.moveToElement(resultTableElement).click(resultTableElement).build().perform();	
		patientDetailsPage.verifyRegesteredDetails("Ganesh, G");
		
		System.out.println("---------------------------Active Visits --------------------------------");
		
		homePage.clickHomeIcon();
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientDetailsPage.getPatientIdFromPropertis());	
		Thread.sleep(5000);
		findPatientPage.clickResultTableColumnElement("Identifier");
		patientDetailsPage.startVisit();
		patientDetailsPage.clickAttachmentsLink();
		
//		String filePath = "C:\\Users\\RAJU CHELLE\\Desktop\\UploadFile.pdf";
		String filePath = "UploadFile.pdf";
		patientDetailsPage.uploadFile(filePath, "TestUploadFile");
		Thread.sleep(5000);
		patientDetailsPage.verifyFileUpload();

		

//		driver.close();
	}
}
