package com.automationtestplanet.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadAndWriteDataInExcel {

	public static void main(String[] args) throws Exception {
		
		System.out.println(System.getProperty("user.dir"));
		
		File excelFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\Test.xlsx");
		
		XSSFWorkbook wrkbook = new XSSFWorkbook(new FileInputStream(excelFile));
		
		XSSFSheet languages	= wrkbook.getSheet("Languages");
		
		System.out.println(languages.getLastRowNum());	
		Iterator<Row> allRows= languages.rowIterator();
		
		while(allRows.hasNext()) {
			Row eachRow = allRows.next();
			
			Iterator<Cell> allCells = eachRow.cellIterator();		
			
			while(allCells.hasNext()) {
				
				Cell eachCell = allCells.next();
				
				System.out.println(eachCell.getCellType());				
				System.out.println(eachCell.getStringCellValue());				
				
			}
		}
		
//		XSSFSheet testSheet = wrkbook.createSheet("TestSheet");
		XSSFSheet testSheet = wrkbook.getSheet("TestSheet");
		Row newRow  = testSheet.createRow(0);
		Cell newCell = newRow.createCell(1);
		newCell.setCellValue("This is New Cell Value");
		
		FileOutputStream writeExcel = new FileOutputStream(excelFile);
		wrkbook.write(writeExcel);		
		writeExcel.close();
		
	}

}
