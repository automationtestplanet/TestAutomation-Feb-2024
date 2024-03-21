package org.openmrs;


import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnitTest extends JUnitTestSuit{

	@BeforeAll
	public void beforeAllTests() {
		System.out.println("Launch Browser");
	}
	
	@AfterAll
	public void afterAllTests() {
		System.out.println("Close Browser");
	}
	
	@BeforeEach
	void beforeTest() {
		System.out.println("Login to Application");
	}
	
	@AfterEach
	void afterTest() {
		System.out.println("Logout Application");
	}
	
	@Test
	void test1() {
//		fail("Not yet implemented");
//		assertTrue(true);
		System.out.println("Test is Started");
		String actualText = "Hello Java";
//	if(actualText.equalsIgnoreCase("Hello Java1")) {
//		System.out.println("Test is Pass");
//	}else {
////		System.out.println("Test is Fail");
//		System.out.println("Test is Pass");
//	}
		Assert.assertEquals("Hello Java", actualText);
		System.out.println("Test is Completed");
	}

	@Test
	void test2() {

		System.out.println("Test is Started");

		String actualText = "Hello Java";

		Assert.assertEquals("Hello Java", actualText);

		System.out.println("Test is Completed");

	}

}
