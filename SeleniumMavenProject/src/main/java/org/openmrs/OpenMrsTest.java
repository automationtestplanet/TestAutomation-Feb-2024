package org.openmrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import page.objects.BaseClass;
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

		BaseClass baseCls = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		HomePage homePage = new HomePage(driver);
		RegistrationPage registrationPage = new RegistrationPage(driver);
		PatientDetailsPage patientDetailsPage = new PatientDetailsPage(driver);

		baseCls.navigateToApp("https://demo.openmrs.org/openmrs/login.htm");
		loginPage.login("Admin", "Admin123", "Registration Desk");
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
		registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992");
		registrationPage.clikConfirmButton();

		patientDetailsPage.verifyRegesteredDetails("Ganesh, G");
		patientDetailsPage.storePatientId();
		System.out.println(patientDetailsPage.getPatientIdFromPropertis());
		driver.close();
	}
}
