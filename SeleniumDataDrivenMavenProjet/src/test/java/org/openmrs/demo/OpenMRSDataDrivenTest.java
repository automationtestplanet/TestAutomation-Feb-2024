package org.openmrs.demo;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import openmrs.page.objects.Utilities;

public class OpenMRSDataDrivenTest extends OpenMRSDataDrivenBaseTest {

	@Test(priority = 0, groups = { "SmokeTest", "SanityTest", "RegressionTest" }, dataProvider = "RegisterPatientData")
	public void registerPatientTest(String name, String gender, String dateOfBirth, String address, String phneNumber) {
		assertTrue(homePage.verifyLogin());
		utilities.captureScreenshot();
		Reporter.log("<span style=\"color:green\"><strong>Verify Home Page</strong></span>");
		Reporter.log("<img src=\" "+Utilities.screenshotPath +"\">");
		System.out.println("---------------------------Register Patient--------------------------------");
		assertTrue(homePage.verifyModuleTile("Register a patient"), "Verify Register Patient Tile");
		homePage.clickModuleTile("Register a patient");
		Assert.assertEquals("Register a patient", registrationPage.verifyModulePage(), "Verify Register Patient Page");
		utilities.captureScreenshot();
		Reporter.log("<span style=\"color:green\"><strong>Verify Regiter Patient Page Page</strong></span>");
		Reporter.log("<img src=\" "+Utilities.screenshotPath +"\">");
		registrationPage.enterName(name);
		registrationPage.clikNextButton();
		registrationPage.selectGender(gender);
		registrationPage.clikNextButton();
		registrationPage.setDateOfBorth(dateOfBirth);
		registrationPage.clikNextButton();
		registrationPage.enterAddress(address);
		registrationPage.clikNextButton();
		registrationPage.setPhoneNumber(phneNumber);
		registrationPage.clikNextButton();
		registrationPage.clikNextButton();
		assertTrue(registrationPage.verifyRegistrationDetails(name, gender, dateOfBirth, phneNumber));
		Assert.assertEquals(true, registrationPage.verifyRegistrationDetails(name, gender, dateOfBirth, phneNumber),
				"Verify Registration Details");
		utilities.captureScreenshot();
		Reporter.log("<span style=\"color:green\"><strong>Verify Registration Details</strong></span>");
		Reporter.log("<img src=\" "+Utilities.screenshotPath +"\">");
		registrationPage.clikConfirmButton();
		assertTrue(patientDetailsPage.verifyRegesteredDetails(name));
		Assert.assertEquals(true, patientDetailsPage.verifyRegesteredDetails(name), "Verify Registered Details");
		utilities.captureScreenshot();
		Reporter.log("<span style=\"color:green\"><strong>Verify Registered Details</strong></span>");
		Reporter.log("<img src=\" "+Utilities.screenshotPath +"\">");
		
		patientDetailsPage.storePatientIdToPropertiesFile();
		System.out.println(patientDetailsPage.getPatientIdFromPropertis());
	}

	@Test(priority = 0, dependsOnMethods = "registerPatientTest", groups = "RegressionTest")
	public void findPatientTest() {
		assertTrue(homePage.verifyLogin());
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Verify Find Patient Tile");
		homePage.clickModuleTile("Find Patient Record");
		jsExec.executeScript("window.scroll(0, document.body.scrollHeight)");
		jsExec.executeScript("window.scroll(0, document.body.scrollTop)");
		jsExec.executeScript("arguments[0].scrollIntoView(true)", findPatientPage.getFindPatientRecordsInfo());
		jsExec.executeScript("window.scroll(0, document.body.scrollTop)");
		findPatientPage.searchPatientRecord(patientId);
		Assert.assertEquals(findPatientPage.verifyResultTableColumnValue("Identifier"), patientId,
				"Verify Patient Id in Search Result");
		WebElement resultTableElement = findPatientPage.getResultTableColumnElement("Identifier");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(resultTableElement));
		actions.moveToElement(resultTableElement).click(resultTableElement).build().perform();
		assertTrue(patientDetailsPage.verifyRegesteredDetails("Ganesh, G"), "Verify Searched Patient Details: ");
	}

	@Test(priority = 0, dependsOnMethods = "registerPatientTest", groups = { "SanityTest", "RegressionTest" })
	public void activeVisitsTest() {
		assertTrue(homePage.verifyLogin());
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Verify Find Patient Tile");
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientId);
		assertTrue(findPatientPage.verifyResultTableColumnValue("Identifier").contains(patientId),
				"Verify Patient Id in Search Result");
		findPatientPage.clickResultTableColumnElement("Identifier");
		patientDetailsPage.startVisit();
		patientDetailsPage.clickAttachmentsLink();
		String filePath = "UploadFile.pdf";
		patientDetailsPage.uploadFile(filePath, "TestUploadFile");
		assertTrue(patientDetailsPage.verifyFileUpload(), "Verify FIle Upload");

	}

	@Test(priority = 0, dependsOnMethods = { "registerPatientTest", "findPatientTest" }, groups = "RegressionTest")
	public void deletePatientTest() {
		assertTrue(homePage.verifyLogin());
		String patientId = patientDetailsPage.getPatientIdFromPropertis();
		assertTrue(homePage.verifyModuleTile("Find Patient Record"), "Verify Find Patient Tile");
		homePage.clickModuleTile("Find Patient Record");
		findPatientPage.searchPatientRecord(patientId);
		Assert.assertEquals(findPatientPage.verifyResultTableColumnValue("Identifier"), patientId,
				"Verify Patient Id in Search Result");
		findPatientPage.clickResultTableColumnElement("Identifier");
		patientDetailsPage.deletePatient("Other");
		findPatientPage.searchPatientRecord(patientDetailsPage.getPatientIdFromPropertis());
		assertTrue(findPatientPage.verifyPatientRecordNotFiltered(), "Verify Delete Patient");
	}

}
