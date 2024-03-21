package page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class RegistrationPage extends BaseClass {

	public RegistrationPage(WebDriver driver) {
		super(driver);
	}

//	WebElement moduleHeader = driver.findElement(By.tagName("h2"));
	@FindBy(tagName = "h2")
	WebElement moduleHeader;

//	WebElement givenName = driver.findElement(By.name("givenName"));
	@FindBy(name = "givenName")
	WebElement givenName;

//	WebElement familyName = driver.findElement(By.name("familyName"));
	@FindBy(name = "familyName")
	WebElement familyName;

//	WebElement nextButton = driver.findElement(By.id("next-button"));
	@FindBy(id = "next-button")
	WebElement nextButton;

//	WebElement genderDropdown = driver.findElement(By.id("gender-field"));
	@FindBy(id = "gender-field")
	WebElement genderDropdown;

//	WebElement birthDay = driver.findElement(By.id("birthdateDay-field"));
	@FindBy(id = "birthdateDay-field")
	WebElement birthDay;

//	WebElement birthMonth = driver.findElement(By.id("birthdateMonth-field"));
	@FindBy(id = "birthdateMonth-field")
	WebElement birthMonth;

//	WebElement birthYear = driver.findElement(By.id("birthdateYear-field"));
	@FindBy(id = "birthdateYear-field")
	WebElement birthYear;

//	WebElement confirmButton = driver.findElement(By.cssSelector("input[value='Confirm']"));
	@FindBy(css = "input[value='Confirm']")
	WebElement confirmButton;

//	WebElement address_1 = driver.findElement(By.id("address1"));
	@FindBy(id = "address1")
	WebElement address_1;

//	WebElement city = driver.findElement(By.id("cityVillage"));
	@FindBy(id = "cityVillage")
	WebElement city;

//	WebElement state = driver.findElement(By.id("stateProvince"));
	@FindBy(id = "stateProvince")
	WebElement state;

//	WebElement country = driver.findElement(By.id("country"));
	@FindBy(id = "country")
	WebElement country;

//	WebElement pinCode = driver.findElement(By.id("postalCode"));
	@FindBy(id = "postalCode")
	WebElement pinCode;

//	WebElement phoneNumber = driver.findElement(By.name("phoneNumber"));
	@FindBy(name = "phoneNumber")
	WebElement phoneNumber;

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

	public WebElement getConfirmButton() {
		return confirmButton;
	}

	public WebElement getAddress_1() {
		return address_1;
	}

	public WebElement getCity() {
		return city;
	}

	public WebElement getState() {
		return state;
	}

	public WebElement getCountry() {
		return country;
	}

	public WebElement getPinCode() {
		return pinCode;
	}

	public WebElement getPhoneNumber() {
		return phoneNumber;
	}

	public void verifyModulePage(String moduleName) {
		String registerHeader = getModuleHeader().getText().trim();
		if (registerHeader.equalsIgnoreCase(moduleName))
			System.out.println(moduleName + " Module Page is present");
		else
			System.out.println(moduleName + " module Page is not available");
	}
	
	public String verifyModulePage() {
		return getModuleHeader().getText().trim();
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

	public String getLabelText(String labelName) {
		return driver.findElement(By.xpath("//span[contains(text(),'" + labelName + "')]//parent::p")).getText().trim();
	}

	public boolean verifyRegistrationDetails(String name, String gender, String dateOfBirth, String PhoneNumber) {
		
		try {
		String actualName = getLabelText("Name:");
		String acualGender = getLabelText("Gender:");
		String actualDob = getLabelText("Birthdate:");
		String phoneNo = getLabelText("Phone Number:");
//		if (actualName.equalsIgnoreCase(name) && acualGender.equalsIgnoreCase(gender) && actualDob.equals(dateOfBirth)
//				&& phoneNo.equals(phoneNo))
//			System.out.println("Registered details are mmatching");
//		else
//			System.out.println("Registered details are not matching");
		
		return actualName.contains(name) && acualGender.contains(gender) && actualDob.contains(dateOfBirth)
		&& phoneNo.contains(phoneNo);
		}catch(Exception e) {
			
			
		}
		return false;
	}
	
	public void clikConfirmButton() {
		getConfirmButton().click();
	}

	public void setAddress_1(String address_1) {
		getAddress_1().sendKeys(address_1);
		;
	}

	public void setCity(String city) {
		getCity().sendKeys(city);
	}

	public void setState(String state) {
		getState().sendKeys(state);
	}

	public void setCountry(String country) {
		getCountry().sendKeys(country);
	}

	public void setPinCode(String pinCode) {
		getPinCode().sendKeys(pinCode);
	}

	public void setPhoneNumber(String phoneNumber) {
		getPhoneNumber().sendKeys(phoneNumber);
	}

	public void enterAddress(String address_1, String cty, String state, String country, String pinCode) {
		setAddress_1(address_1);
		setCity(cty);
		setState(state);
		setCountry(country);
		setPinCode(pinCode);
	}

}
