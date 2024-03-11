package org.openmrs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class OpenMrsTest {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://demo.openmrs.org/openmrs/login.htm");

		driver.findElement(By.id("username")).sendKeys("Admin");

		driver.findElement(By.id("password")).sendKeys("Admin123");

		driver.findElement(By.id("Registration Desk")).click();

		driver.findElement(By.id("loginButton")).click();

		if (driver.findElement(By.partialLinkText("Logout")).isDisplayed()) {
			System.out.println("Login is Successfull");
//			driver.findElement(By.partialLinkText("Logout")).click();

			if (driver.findElement(By.partialLinkText("Register a patient")).isDisplayed()) {
				driver.findElement(By.partialLinkText("Register a patient")).click();

				String registerHeader = driver.findElement(By.tagName("h2")).getText().trim();

				if (registerHeader.equalsIgnoreCase("Register a patient")) {
					System.out.println("Register patient page is displayed");
					driver.findElement(By.name("givenName")).sendKeys("Ganesh");
					driver.findElement(By.name("familyName")).sendKeys("R");
					driver.findElement(By.id("next-button")).click();

					Select genderDropdown = new Select(driver.findElement(By.id("gender-field")));
					genderDropdown.selectByVisibleText("Male");

				} else {
					System.out.println("Register patient page is not displayed");
				}

			} else {
				System.out.println("Regiter Patient is not available");
			}

		} else {
			System.out.println("Login is Failed");
		}

		driver.close();

	}

}
