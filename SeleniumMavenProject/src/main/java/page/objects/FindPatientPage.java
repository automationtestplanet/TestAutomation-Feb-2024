package page.objects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FindPatientPage extends BaseClass {

	public FindPatientPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "patient-search")
	WebElement searchField;

//	List<WebElement> resultTableHeaders = driver.findElements(By.cssSelector("table#patient-search-results-table>thead>tr>th>div"));	
	@FindBy(css = "table#patient-search-results-table>thead>tr>th>div")
	List<WebElement> resultTableHeaders;

	@FindBy(xpath = "//td[text()='No matching records found']")
	WebElement noMatchingReordsElement;

	public List<WebElement> getResultTableHeaders() {
		return resultTableHeaders;
	}

	@FindBy(id = "patient-search-results-table_info")
	WebElement findPatientRecordsInfo;

	public WebElement getSearchField() {
		return searchField;
	}

	public int getResultTableColumnIndex(String columnName) {
		Map<String, Integer> headersMap = new LinkedHashMap<>();
		int index = 1;
		for (WebElement eachHeaderElement : getResultTableHeaders()) {
			headersMap.put(eachHeaderElement.getText().trim(), index);
			index++;
		}
		return headersMap.get(columnName);
	}

	public WebElement getResultTableColumnElement(String columnName) {
		try {
			Thread.sleep(5000);
			return driver.findElement(By.cssSelector("table#patient-search-results-table>tbody>tr>td:nth-of-type("
					+ getResultTableColumnIndex(columnName) + ")"));
		} catch (Exception e) {
			System.out.println("Exception Occured while fetcing the Result table Column Element:  " + e.getClass());
		}
		return null;
	}

	public String getResultTableColumnValue(String columnName) {
		return getResultTableColumnElement(columnName).getText().trim();
	}

	public WebElement getNoMatchingReordsElement() {
		return noMatchingReordsElement;
	}

	public WebElement getFindPatientRecordsInfo() {
		return findPatientRecordsInfo;
	}

	public void searchPatientRecord(String patientId) {
		try {
			getSearchField().sendKeys(patientId);
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Exception Occured while clicking Attahments Link:  " + e.getClass());
		}
	}

	public void verifyResultTableColumnValue(String columnName, String expectedColumnValue) {
		String actualColumnValue = getResultTableColumnValue(columnName);
		if (actualColumnValue.contains(expectedColumnValue)) {
			System.out.println(columnName + " Column actual value: " + actualColumnValue
					+ " is matching with expected value: " + expectedColumnValue);
		} else {
			System.out.println(columnName + " Column actual value: " + actualColumnValue
					+ " is not matching with expected value: " + expectedColumnValue);
		}
	}

	public String verifyResultTableColumnValue(String columnName) {
		return getResultTableColumnValue(columnName);
	}

	public void clickResultTableColumnElement(String columnName) {
		getResultTableColumnElement(columnName).click();
	}

	public boolean verifyPatientRecordNotFiltered() {
//		if (getNoMatchingReordsElement().isDisplayed()) {
//			System.out.println("Patient Record deleted successfully");
//		} else {
//			System.out.println("Patient Record is not deleted");
//		}		
		return getNoMatchingReordsElement().isDisplayed();
	}

}
