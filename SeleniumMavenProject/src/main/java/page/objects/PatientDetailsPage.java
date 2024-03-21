package page.objects;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientDetailsPage extends BaseClass {

	public PatientDetailsPage(WebDriver driver) {
		super(driver);
	}

	WebDriverWait wait = new WebDriverWait(driver, 10);

	public static Properties appProperties = new Properties();
	public static String filePath = "//src//main//resources//application.properties";

//	WebElement givenName = driver.findElement(By.className("PersonName-givenName"));
	@FindBy(className = "PersonName-givenName")
	WebElement givenName;

//	WebElement familyName = driver.findElement(By.className("PersonName-familyName"));
	@FindBy(className = "PersonName-familyName")
	WebElement familyName;

//	WebElement patientId = driver.findElement(By.xpath("//em[contains(text(),'Patient ID')]//following-sibling::span"));
	@FindBy(xpath = "//em[contains(text(),'Patient ID')]//following-sibling::span")
	WebElement patientId;

	@FindBy(id = "start-visit-with-visittype-confirm")
	WebElement startVisitConfirmButton;

	@FindBy(partialLinkText = "Attachments")
	WebElement attachmentsLink;

	@FindBy(xpath = "//div[text()='Click or drop a file here.']")
	WebElement dragAndDropElement;

	@FindBy(xpath = "//textarea[@placeholder='Enter a caption']")
	WebElement captionELement;

	@FindBy(xpath = "//button[text()='Upload file']")
	WebElement uploadFilebutton;

	@FindBy(xpath = "//div[contains(@class,'att_thumbnail-frame')]")
	WebElement uploadedFile;

	@FindBy(css = "#delete-patient-creation-dialog .confirm.right")
	WebElement deletePatientCofirmButton;

	@FindBy(id = "delete-reason")
	WebElement deleteReasoninput;

	public WebElement getGivenName() {
		return givenName;
	}

	public WebElement getFamilyName() {
		return familyName;
	}

	public WebElement getPatientId() {
		return patientId;
	}

	public WebElement getStartVisitConfirmButton() {
		return startVisitConfirmButton;
	}

	public WebElement getAttachmentsLink() {
		return attachmentsLink;
	}

	public WebElement getDragAndDropElement() {
		return dragAndDropElement;
	}

	public WebElement getCaptionElement() {
		return captionELement;
	}

	public WebElement getUplodFileButton() {
		return uploadFilebutton;
	}

	public WebElement getUplodedFile() {
		return uploadedFile;
	}

	public WebElement getDeleteReasoninput() {
		return deleteReasoninput;
	}

	public WebElement getDeletePatientCofirmButton() {
		return deletePatientCofirmButton;
	}

	public boolean verifyRegesteredDetails(String name) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(getGivenName()));

		String givenName = getGivenName().getText().trim();
		String familyName = getFamilyName().getText().trim();
//		if (name.contains(givenName) && name.contains(familyName))
//			System.out.println("Registration Successfull");
//		else
//			System.out.println("Registration Failed");

		return name.contains(givenName) && name.contains(familyName);
	}

	public void storePatientIdToPropertiesFile() {
		try {
			String patientId = getPatientId().getText().trim();
			System.out.println("Pateint Id: " + patientId);
			appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
			appProperties.setProperty("patient.id", patientId);
			appProperties.store(new FileOutputStream(new File(System.getProperty("user.dir") + filePath)), "Raju");
		} catch (Exception e) {
			System.out.println("Exception Occured while storing the PaientId: " + e.getClass());
		}
	}

	public String getPatientIdFromPropertis() {
		String patientId = null;
		try {
			appProperties.load(new FileInputStream(new File(System.getProperty("user.dir") + filePath)));
			patientId = appProperties.getProperty("patient.id");
		} catch (Exception e) {
			System.out.println("Exception Occured while fetching the PaientId from Properties file:  " + e.getClass());
		}
		return patientId;
	}

	public WebElement getGeneralAction(String generalAtion) {
		return driver.findElement(By.xpath("//div[contains(text(),'" + generalAtion + "')]"));
	}

	public void clickGeneralAction(String generalAtion) {
		getGeneralAction(generalAtion).click();
	}

	public void clickStartVisitConfirmButton() {
		getStartVisitConfirmButton().click();
	}

	public void startVisit() {
		clickGeneralAction("Start Visit");
		clickStartVisitConfirmButton();
	}

	public void clickAttachmentsLink() throws Exception {
		try {
			Thread.sleep(5000);
			wait.until(ExpectedConditions.elementToBeClickable(getAttachmentsLink()));
			getAttachmentsLink().click();
		} catch (Exception e) {
			System.out.println("Exception Occured while clicking Attahments Link:  " + e.getClass());
		}
	}

	public void clickDragAndDropElement() {
		getDragAndDropElement().click();
	}

	public void setCaption(String caption) {
		getCaptionElement().sendKeys(caption);
	}

	public void clickUploadFileButton() {
		getUplodFileButton().click();
		;
	}

	public void uploadFile(String filePath, String caption) {
		try {
			clickDragAndDropElement();
			Thread.sleep(5000);
			StringSelection strSelect = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSelect, null);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(2000);
			setCaption(caption);
			clickUploadFileButton();
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Exception Occured while Upload the attachment:  " + e.getClass());
		}
	}

	public boolean verifyFileUpload() {
//		if (getUplodedFile().isDisplayed()) {
//			System.out.println("File Upload is sucessfulll");
//		} else {
//			System.out.println("File Upload is failed");
//		}
		return getUplodedFile().isDisplayed();
	}

	public void setDeleteReason(String reason) {
		getDeleteReasoninput().sendKeys(reason);
	}

	public void clickDeletePatientCofirmButton() {
		getDeletePatientCofirmButton().click();
	}

	public void deletePatient(String reason) {
		clickGeneralAction("Delete Patient");
		setDeleteReason(reason);
		clickDeletePatientCofirmButton();
	}
}
