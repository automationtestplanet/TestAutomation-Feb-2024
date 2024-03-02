package com.automationtestplanet.demo;

import java.nio.file.Paths;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ReaDataFromXML {

	public static void main(String[] args) throws Exception {
		
		ObjectMapper objMap = new XmlMapper();
		
		Employee2 emp = objMap.readValue(Paths.get(System.getProperty("user.dir")+"//src//main//resources//employee.xml").toFile(), Employee2.class);
		
		Employee2 emp2 = objMap.readValue(new File(System.getProperty("user.dir")+"//src//main//resources//employee.xml"), Employee2.class);
		
		emp.displayEmployDetails();
		
		System.out.println(emp2.getEmpId());
	}
}
