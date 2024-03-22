package org.openmrs;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OpenMRSTestNGTest extends OpenMRSTestNGBaseTest {

	@Test(priority = 0, groups = { "SmokeTest", "SanityTest", "RegressionTest" })
	public void registerPatientTest() {
		assertTrue(homePage.verifyLogin());
		System.out.println("---------------------------Register Patient--------------------------------");
		assertTrue(homePage.verifyModuleTile("Register a patient"), "Verify Register Patient Tile");
		homePage.clickModuleTile("Register a patient");
		Assert.assertEquals("Register a patient", registrationPage.verifyModulePage(), "Verify Register Patient Page");
		registrationPage.enterName("Ganesh, G");
		registrationPage.clikNextButton();
		registrationPage.selectGender("Male");
		registrationPage.clikNextButton();
		registrationPage.setDateOfBorth("10, January, 1992");
		registrationPage.clikNextButton();
		registrationPage.enterAddress("Flat 102, S R Nagar", "Hyderabad", "Telangana", "India", "500038");
		registrationPage.clikNextButton();
		registrationPage.setPhoneNumber("9876543210");
		registrationPage.clikNextButton();
		registrationPage.clikNextButton();
		assertTrue(registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992", "9876543210"));
		Assert.assertEquals(true,
				registrationPage.verifyRegistrationDetails("Ganesh, G", "Male", "10, January, 1992", "9876543210"),
				"Verify Registration Details");
		registrationPage.clikConfirmButton();
		assertTrue(patientDetailsPage.verifyRegesteredDetails("Ganesh, G"));
		Assert.assertEquals(true, patientDetailsPage.verifyRegesteredDetails("Ganesh, G"), "Verify Registered Details");
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
