
package openmrs.page.objects;

import java.io.File;
import java.io.FileInputStream;
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

import io.cucumber.java.Scenario;



public class Utilities extends BaseClass {
	
	Scenario scenario;

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
	
	@SuppressWarnings("deprecation")
	public void takeScreenshot() {
		try {
			ts = (TakesScreenshot) driver;
			byte[] sreenByteArr = ts.getScreenshotAs(OutputType.BYTES);
			scenario.embed(sreenByteArr, "image/jpeg");
			
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

	public static Iterator<Object[]> readKeywordDrivenTestDataFromExcel(String filePath, String sheetname,
			String methodName) {
		List<Map<String, String>> testData = null;
		try {
			FileInputStream file = new FileInputStream(new File(filePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(sheetname);

			testData = getTestDataBySheet(sheet, methodName);

		} catch (Exception e) {
			System.out.println("Exception Occured while read the data from excel: " + e.getClass());
		}

		return getTestDataIterator(testData);
	}

	public static List<Map<String, String>> getTestDataBySheet(XSSFSheet testDataSheet, String testMethodName) {

		List<Map<String, String>> testData = new ArrayList<>();

		int startRow = getStartRow(testDataSheet, testMethodName);
		List<String> testDataHeaders = getTestDataHeaders(startRow + 1, testDataSheet);
		int endRow = getEndRow(startRow, testDataSheet, testMethodName);

		for (int i = startRow + 2; i < endRow; i++) {
			Map<String, String> testDataMap = new Hashtable<>();
			Row testDataRow = testDataSheet.getRow(i);

			Cell cellData;

			for (int j = 0; j < testDataHeaders.size(); j++) {
				if (!testDataHeaders.get(j).equals("CELL NOT FOUND")
						&& !testDataHeaders.get(j).equals("NO DATA FOUND IN THE CELL")) {
					cellData = testDataRow.getCell(j, MissingCellPolicy.RETURN_NULL_AND_BLANK);
					if (cellData != null) {
						cellData.setCellType(CellType.STRING);
					}

					if (cellData != null && !(cellData.getStringCellValue().toString().trim().equals(""))) {
						testDataMap.put(testDataHeaders.get(j), cellData.getStringCellValue());
					}
				}
			}
			testData.add(testDataMap);
		}

		return testData;

	}

	public static int getStartRow(XSSFSheet testDataSheet, String testMethodName) {
		try {
			for (int i = 1; i < testDataSheet.getLastRowNum(); i++) {
				Cell cellData = testDataSheet.getRow(i).getCell(0);
				if (testDataSheet.getRow(i).getCell(0) != null) {
					if( !(cellData.getStringCellValue().toString().trim().equals(""))) {
						if (testDataSheet.getRow(i).getCell(0).getStringCellValue().toString().trim()
								.equalsIgnoreCase(testMethodName))
							return i;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Exception Occured while getting Start Row Val: " + e.getClass());
		}
		return 0;
	}

	public static int getEndRow(int startRow, XSSFSheet testDataSheet, String testMethodName) {
		try {
			for (int i = startRow + 1; i < testDataSheet.getLastRowNum(); i++) {
				if (testDataSheet.getRow(i).getCell(0) != null) {
					if(!(testDataSheet.getRow(i).getCell(0).getStringCellValue().toString().trim().equals(""))) {
						if (testDataSheet.getRow(i).getCell(0).getStringCellValue().toString().trim()
								.equalsIgnoreCase(testMethodName))
							return i;
					}
					
				}
			}
		} catch (Exception e) {
			System.out.println("Exception Occured while getting End Row Val: " + e.getClass());
		}
		return startRow;
	}

	public static List<String> getTestDataHeaders(int startRow, XSSFSheet testDataSheet) {
		List<String> columnHeadersNamesList = new ArrayList<>();

		Row columnsHeadersRow = testDataSheet.getRow(startRow);

		for (int i = 0; i < columnsHeadersRow.getLastCellNum(); i++) {
			Cell dataCell = columnsHeadersRow.getCell(i, MissingCellPolicy.RETURN_NULL_AND_BLANK);
			if (dataCell == null)
				columnHeadersNamesList.add("CELL NOT FOUND");
			else if (dataCell.getStringCellValue().toString().equals(""))
				columnHeadersNamesList.add("NO DATA FOUND IN THE CELL");
			else {
				dataCell.setCellType(CellType.STRING);
				columnHeadersNamesList.add(dataCell.getStringCellValue());
			}
		}

		return columnHeadersNamesList;
	}

	public static Iterator<Object[]> getTestDataIterator(List<Map<String, String>> testData) {

		List<Object[]> iteratorList = new ArrayList<>();

		for (Map<String, String> eachMap : testData)
			iteratorList.add(new Object[] { eachMap });
		return iteratorList.iterator();
	}
}
