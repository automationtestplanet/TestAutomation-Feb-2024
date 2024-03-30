
package openmrs.page.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities extends BaseClass {

	public Utilities(WebDriver driver) {
		super(driver);
	}

	TakesScreenshot ts;
	public static String screenshotPath;

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			System.out.println(new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).replaceAll("[^0-9]", ""));
			Thread.sleep(1000);
		}
	}

	public void captureScreenshot() {
		try {
			ts = (TakesScreenshot) driver;

			File screenshot = ts.getScreenshotAs(OutputType.FILE);
			String screenshotName = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date()).replaceAll("[^0-9]",
					"") + ".jpg";
			screenshotPath = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\" + screenshotName;

//			screenshotPath = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\screenshot.jpg";
			File destinaionFile = new File(screenshotPath);
			FileUtils.copyFile(screenshot, destinaionFile);
		} catch (Exception e) {
			System.out.println("Exception Occured while Capturing the screenshot:  " + e.getClass());
		}

	}

	public static List<String[]> readDataFromExcel(String filePath, String sheetname) {
		List<String[]> testDataList = new ArrayList<>();
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetname);

			int rows = sheet.getLastRowNum();
			System.out.println("Row Count: " + rows);

			int index = 0;
			for (int row1 = 1; row1 < rows + 1; row1++) {
				String[] eachRowData = new String[sheet.getRow(row1).getLastCellNum()];

				Iterator<Cell> cellIterator = sheet.getRow(row1).cellIterator();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case NUMERIC:
						eachRowData[index] = String.valueOf(cell.getNumericCellValue());
						break;
					case STRING:
						eachRowData[index] = cell.getStringCellValue();
						break;
					case BOOLEAN:
						eachRowData[index] = String.valueOf(cell.getBooleanCellValue());
						break;
					case BLANK:
						eachRowData[index] = "";
						break;
					}
					index++;
				}
				index = 0;
				testDataList.add(eachRowData);
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return testDataList;
	}
}
