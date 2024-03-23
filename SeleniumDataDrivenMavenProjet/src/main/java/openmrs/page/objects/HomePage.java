package openmrs.page.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BaseClass {

	public HomePage(WebDriver driver) {
		super(driver);
	}

//	WebElement logoutButton = driver.findElement(By.partialLinkText("Logout"));
	@FindBy(partialLinkText = "Logout")
	WebElement logoutButton;

	@FindBy(css = ".icon-home.small")
	WebElement homeIcon;

	public WebElement getLogoutButton() {
		return logoutButton;
	}

	public WebElement getHomeIcon() {
		return homeIcon;
	}

	public WebElement getModuleTile(String moduleName) {
		return driver.findElement(By.partialLinkText(moduleName));
	}

	public boolean verifyLogin() {
//		if (getLogoutButton().isDisplayed())
//			System.out.println("Login is Successfull");
//		else
//			System.out.println("Login is Failed");
		return getLogoutButton().isDisplayed();
	}

	public boolean verifyModuleTile(String moduleName) {
//		if (getModuleTile(moduleName).isDisplayed())
//			System.out.println(moduleName + " Module Tile is present");
//		else
//			System.out.println(moduleName + " module Tile is not available");
		
		return getModuleTile(moduleName).isDisplayed();
	}
	
	public void clickModuleTile(String moduleName) {
		getModuleTile(moduleName).click();
	}

	public void clickHomeIcon() {
		getHomeIcon().click();
	}
	
	public void clickLogout() {
		getLogoutButton().click();
	}

}
