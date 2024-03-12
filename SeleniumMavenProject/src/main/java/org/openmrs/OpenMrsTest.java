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

public class OpenMrsTest {

	public static Properties appProperties = new Properties();
	public static String filePath = "//src//main//resources//application.properties";
	public static WebDriver driver;

	public static void navigateToApp(String url) {
		driver.get(url);
	}

	public static void setUserName(String userName) {
		driver.findElement(By.id("username")).sendKeys(userName);
	}

	public static void setPassword(String password) {
		driver.findElement(By.id("password")).sendKeys(password);
	}

	public static void selectModule(String moduleName) {
		driver.findElement(By.id(moduleName)).click();
	}

	public static void clickLogin() {
		driver.findElement(By.id("loginButton")).click();
	}

	public static void login(String userName, String password, String moduleName) {
		setUserName(userName);
		setPassword(password);
		selectModule(moduleName);
		clickLogin();
	}

	public static void verifyLogin() {
		if (driver.findElement(By.partialLinkText("Logout")).isDisplayed())
			System.out.println("Login is Successfull");
		else
			System.out.println("Login is Failed");
	}

	public static void verifyModuleTile(String moduleName) {
		if (driver.findElement(By.partialLinkText(moduleName)).isDisplayed())
			System.out.println(moduleName + " Module Tile is present");
		else
			System.out.println(moduleName + " module Tile is not available");
	}

	public static void clickModuleTile(String moduleName) {
		driver.findElement(By.partialLinkText(moduleName)).click();
	}

	public static void verifyModulePage(String moduleName) {
		String registerHeader = driver.findElement(By.tagName("h2")).getText().trim();
		if (registerHeader.equalsIgnoreCase(moduleName))
			System.out.println(moduleName + " Module Page is present");
		else
			System.out.println(moduleName + " module Page is not available");
	}

	public static void enterName(String name1) {
		String name[] = name1.split(",");
		driver.findElement(By.name("givenName")).sendKeys(name[0].trim());
		driver.findElement(By.name("familyName")).sendKeys(name[1].trim());
	}

	public static void clikNextButton() {
		driver.findElement(By.id("next-button")).click();
	}

	public static void selectGender(String gender) {
		Select genderDropdown = new Select(driver.findElement(By.id("gender-field")));
		genderDropdown.selectByVisibleText(gender);
	}

	public static void setDateOfBorth(String dateOfBirth1) {
		String dateOfBirth[] = dateOfBirth1.split(",");
		driver.findElement(By.id("birthdateDay-field")).sendKeys(dateOfBirth[0].trim());
		Select birthMonthDropdwon = new Select(driver.findElement(By.id("birthdateMonth-field")));
		birthMonthDropdwon.selectByVisibleText(dateOfBirth[1].trim());
		driver.findElement(By.id("birthdateYear-field")).sendKeys(dateOfBirth[2].trim());
	}

	public static void verifyRegistrationDetails(String name, String gender, String dateOfBirth) {
		String actualName = driver.findElement(By.xpath("//span[contains(text(),'Name:')]//parent::p")).getText()
				.trim();
		String acualGender = driver.findElement(By.xpath("//span[contains(text(),'Gender:')]//parent::p")).getText()
				.trim();
		String actualDob = driver.findElement(By.xpath("//span[contains(text(),'Birthdate:')]//parent::p")).getText()
				.trim();
		if (actualName.equalsIgnoreCase(name) && acualGender.equalsIgnoreCase(gender) && actualDob.equals(dateOfBirth))
			System.out.println("Registered details are mmatching");
		else
			System.out.println("Registered details are not matching");
	}

	public static void clikConfirmButton() {
		driver.findElement(By.cssSelector("input[value='Confirm']")).click();
	}

	public static void verifyRegesteredDetails(String name) {
		String givenName = driver.findElement(By.className("PersonName-givenName")).getText().trim();
		String familyName = driver.findElement(By.className("PersonName-familyName")).getText().trim();
		if (name.contains(givenName) && name.contains(familyName))
			System.out.println("Registration Successfull");
		else
			System.out.println("Registration Failed");
	}

	public static void storePatientId() throws Exception {
		String patientId = driver.findElement(By.xpath("//em[contains(text(),'Patient ID')]//following-sibling::span"))
				.getText().trim();
		System.out.println("Pateint Id: " + patientId);
		appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
		appProperties.setProperty("patient.id", patientId);
		appProperties.store(new FileOutputStream(new File(System.getProperty("user.dir") + filePath)), "Raju");
	}

	public static String getPatientId() throws Exception {
		appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
		return appProperties.getProperty("patient.id");
	}

	public static void main(String[] args) throws Exception {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		navigateToApp("https://demo.openmrs.org/openmrs/login.htm");
		login("Admin", "Admin123", "Registration Desk");
		verifyLogin();

		verifyModuleTile("Register a patient");
		clickModuleTile("Register a patient");
		verifyModulePage("Register a patient");

		enterName("Ganesh, G");
		clikNextButton();
		selectGender("Male");
		clikNextButton();
		setDateOfBorth("10, January, 1992");
		clikNextButton();
		verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992");
		clikConfirmButton();
		
		verifyRegesteredDetails("Ganesh, G");
		storePatientId();
		System.out.println(getPatientId());
		driver.close();

	}

}
