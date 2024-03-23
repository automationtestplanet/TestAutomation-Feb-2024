package openmrs.page.objects;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Utilities {
	
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
