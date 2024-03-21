package page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseClass {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

//	WebElement userName = driver.findElement(By.id("username"));
	@FindBy(id = "username")
	WebElement userName;

//	WebElement password = driver.findElement(By.id("password"));
	@FindBy(id = "password")
	WebElement password;

//	WebElement loginButton = driver.findElement(By.id("loginButton"));
	@FindBy(id = "loginButton")
	WebElement loginButton;

	public WebElement getUserName() {
		return userName;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getModule(String moduleName) {
		return driver.findElement(By.id(moduleName));
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public void setUserName(String userName) {
		getUserName().sendKeys(userName);
	}

	public void setPassword(String password) {
		getPassword().sendKeys(password);
	}

	public void selectModule(String moduleName) {
		getModule(moduleName).click();
	}

	public void clickLogin() {
		getLoginButton().click();
	}

	public void login(String userName, String password, String moduleName) {
		setUserName(userName);
		setPassword(password);
		selectModule(moduleName);
		clickLogin();
	}
	
	public boolean verifyLoginPage() {
		return driver.getTitle().trim().equalsIgnoreCase("Login");
	}

}
