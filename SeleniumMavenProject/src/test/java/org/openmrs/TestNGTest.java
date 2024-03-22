package org.openmrs;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestNGTest {

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		System.out.println("This is Before Suit");
	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		System.out.println("This is Before Test");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		System.out.println("This is Before Class");
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "UserName", "Password", "ModuleName" })
	public void beforeMethod(String userName, String password, String moduleNmae) {
		System.out.println("This is Before Method");
		System.out.println(
				"Login with UserName: " + userName + " and Password: " + password + " and Module Name: " + moduleNmae);

	}

	@Test(priority = 0, groups = { "SmokeTest", "SanityTest", "RegressionTest" })
	public void registerPatient() {
		System.out.println("This is registerPatient Test");
	}

	@Test(priority = 0, dependsOnMethods = "registerPatient", groups = "RegressionTest")
	public void findpatient() {
		System.out.println("This is findpatient Test");
	}

	@Test(priority = 0, dependsOnMethods = "registerPatient", groups = { "SanityTest", "RegressionTest" })
	public void activeVisits() {
		System.out.println("This is activeVisits Test");
	}

	@Test(priority = 0, dependsOnMethods = { "registerPatient", "findpatient" }, groups = "RegressionTest")
	public void deletePatient() {
		System.out.println("This is deletePatient Test");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		System.out.println("This is After Method");
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println("This is After Class");
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		System.out.println("This is After Test");
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		System.out.println("This is After Suit");
	}
}
