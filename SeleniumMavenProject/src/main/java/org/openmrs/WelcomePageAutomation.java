package org.openmrs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WelcomePageAutomation {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		
		
		driver.get("C:\\Users\\RAJU CHELLE\\Desktop\\Welcome.html");
		
		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());
		System.out.println(driver.getClass().getName());
		
//		System.out.println(driver.getPageSource());
		
		WebElement userNameField = driver.findElement(By.id("userName"));
		
		userNameField.sendKeys("Ganesh M");
		
		System.out.println("--------------------------------------------------------");
		
		System.out.println(userNameField.getAttribute("id"));
		System.out.println(userNameField.getAttribute("placeholder"));
		System.out.println(userNameField.getAttribute("minlength"));
		System.out.println(userNameField.getAttribute("maxlength"));		
		System.out.println(userNameField.getAttribute("value"));
		
//		WebElement passwordField = driver.findElement(By.name("Password"));		
//		passwordField.sendKeys("Test@123");
		
		driver.findElement(By.name("Password")).sendKeys("Test@123");
		
		driver.findElement(By.className("checkbox")).click();
		
		driver.findElement(By.tagName("textarea")).sendKeys("W3Schools is optimized for learning and training. Examples might be simplified to improve reading and learning.Tutorials, references, and examples are constantly reviewed to avoid errors, but we cannot warrant full correctness of all content. While using W3Schools, you agree to have read and accepted our terms of use, cookie and privacy policy");
		
		driver.findElement(By.linkText("OpenMRS in New Tab")).click();
		
		driver.findElement(By.partialLinkText("OpenMRS in Seperate")).click();
		
//		driver.findElement(By.cssSelector("[type='file']")).click();
		
		
		

	}

}
