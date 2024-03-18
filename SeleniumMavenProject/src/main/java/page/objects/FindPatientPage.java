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

	public WebElement getSearchField() {
		return searchField;
	}

	public void searchPatientRecord(String patientId) {
		getSearchField().sendKeys(patientId);
	}

//	List<WebElement> resultTableHeaders = driver.findElements(By.cssSelector("table#patient-search-results-table>thead>tr>th>div"));	
	@FindBy(css = "table#patient-search-results-table>thead>tr>th>div")
	List<WebElement> resultTableHeaders;

	public List<WebElement> getResultTableHeaders() {
		return resultTableHeaders;
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
		return driver.findElement(By.cssSelector("table#patient-search-results-table>tbody>tr>td:nth-of-type("
				+ getResultTableColumnIndex(columnName) + ")"));
	}

	public String getResultTableColumnValue(String columnName) {
		return getResultTableColumnElement(columnName).getText().trim();
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
	
	public void clickResultTableColumnElement(String columnName) {
		getResultTableColumnElement(columnName).click();;
	}

}
