package page.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PatientDetailsPage extends BaseClass {

	public PatientDetailsPage(WebDriver driver) {
		super(driver);
	}

	public static Properties appProperties = new Properties();
	public static String filePath = "//src//main//resources//application.properties";

//	WebElement givenName = driver.findElement(By.className("PersonName-givenName"));
	@FindBy(className = "PersonName-givenName")
	WebElement givenName;

//	WebElement familyName = driver.findElement(By.className("PersonName-familyName"));
	@FindBy(className = "PersonName-familyName")
	WebElement familyName;

//	WebElement patientId = driver.findElement(By.xpath("//em[contains(text(),'Patient ID')]//following-sibling::span"));
	@FindBy(xpath = "//em[contains(text(),'Patient ID')]//following-sibling::span")
	WebElement patientId;

	public WebElement getGivenName() {
		return givenName;
	}

	public WebElement getFamilyName() {
		return familyName;
	}

	public WebElement getPatientId() {
		return patientId;
	}

	public void verifyRegesteredDetails(String name) {
		String givenName = getGivenName().getText().trim();
		String familyName = getFamilyName().getText().trim();
		if (name.contains(givenName) && name.contains(familyName))
			System.out.println("Registration Successfull");
		else
			System.out.println("Registration Failed");
	}

	public void storePatientId() throws Exception {
		String patientId = getPatientId().getText().trim();
		System.out.println("Pateint Id: " + patientId);
		appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
		appProperties.setProperty("patient.id", patientId);
		appProperties.store(new FileOutputStream(new File(System.getProperty("user.dir") + filePath)), "Raju");
	}

	public String getPatientIdFromPropertis() throws Exception {
		appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
		return appProperties.getProperty("patient.id");
	}
}
