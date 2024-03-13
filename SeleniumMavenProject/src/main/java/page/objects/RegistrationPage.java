package page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BaseClass {

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

	WebElement moduleHeader = driver.findElement(By.tagName("h2"));
	WebElement givenName = driver.findElement(By.name("givenName"));
	WebElement familyName = driver.findElement(By.name("familyName"));
	WebElement nextButton = driver.findElement(By.id("next-button"));
	WebElement genderDropdown = driver.findElement(By.id("gender-field"));
	WebElement birthDay = driver.findElement(By.id("birthdateDay-field"));
	WebElement birthMonth = driver.findElement(By.id("birthdateMonth-field"));
	WebElement birthYear = driver.findElement(By.id("birthdateYear-field"));
	WebElement name = driver.findElement(By.xpath("//span[contains(text(),'Name:')]//parent::p"));
	WebElement gender = driver.findElement(By.xpath("//span[contains(text(),'Gender:')]//parent::p"));
	WebElement dob = driver.findElement(By.xpath("//span[contains(text(),'Birthdate:')]//parent::p"));
	WebElement confirmButton = driver.findElement(By.cssSelector("input[value='Confirm']"));

	public WebElement getModuleHeader() {
		return moduleHeader;
	}

	public WebElement getGivenName() {
		return givenName;
	}

	public WebElement getFamilyName() {
		return familyName;
	}

	public WebElement getNextButton() {
		return nextButton;
	}

	public WebElement getGenderDropdown() {
		return genderDropdown;
	}

	public WebElement getBirthDay() {
		return birthDay;
	}

	public WebElement getBirthMonth() {
		return birthMonth;
	}

	public WebElement getBirthYear() {
		return birthYear;
	}

	public WebElement getName() {
		return name;
	}

	public WebElement getGender() {
		return gender;
	}

	public WebElement getDob() {
		return dob;
	}

	public WebElement getConfirmButton() {
		return confirmButton;
	}

	public void verifyModulePage(String moduleName) {
		String registerHeader = getModuleHeader().getText().trim();
		if (registerHeader.equalsIgnoreCase(moduleName))
			System.out.println(moduleName + " Module Page is present");
		else
			System.out.println(moduleName + " module Page is not available");
	}

	public void enterName(String name1) {
		String name[] = name1.split(",");
		getGivenName().sendKeys(name[0].trim());
		getFamilyName().sendKeys(name[1].trim());
	}

	public void clikNextButton() {
		getNextButton().click();
	}

	public void selectGender(String gender) {
		Select genderDropdown = new Select(getGenderDropdown());
		genderDropdown.selectByVisibleText(gender);
	}

	public void setDateOfBorth(String dateOfBirth1) {
		String dateOfBirth[] = dateOfBirth1.split(",");
		getBirthDay().sendKeys(dateOfBirth[0].trim());
		Select birthMonthDropdwon = new Select(getBirthMonth());
		birthMonthDropdwon.selectByVisibleText(dateOfBirth[1].trim());
		getBirthYear().sendKeys(dateOfBirth[2].trim());
	}

	public void verifyRegistrationDetails(String name, String gender, String dateOfBirth) {
		String actualName = getName().getText().trim();
		String acualGender = getGender().getText().trim();
		String actualDob = getDob().getText().trim();
		if (actualName.equalsIgnoreCase(name) && acualGender.equalsIgnoreCase(gender) && actualDob.equals(dateOfBirth))
			System.out.println("Registered details are mmatching");
		else
			System.out.println("Registered details are not matching");
	}

	public void clikConfirmButton() {
		getConfirmButton().click();
	}

}
