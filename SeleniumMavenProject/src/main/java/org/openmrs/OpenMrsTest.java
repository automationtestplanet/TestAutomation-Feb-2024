package org.openmrs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenMrsTest {

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")
				+ "\\src\\main\\resources\\drivers\\chrome\\chrome122\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

//		driver.get("https://demo.openmrs.org/openmrs/login.htm");
//		
//		drvier.navigate().to("https://demo.openmrs.org/openmrs/login.htm");
//
//		driver.findElement(By.id("username")).sendKeys("Admin");
//
//		driver.findElement(By.id("password")).sendKeys("Admin123");
//
//		driver.findElement(By.id("Registration Desk")).click();
//
//		driver.findElement(By.id("loginButton")).click();
//
//		drvier.close();

//		drvier.quit();
		
		
		
		
		

	}

}
