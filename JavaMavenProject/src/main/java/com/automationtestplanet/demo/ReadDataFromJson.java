package com.automationtestplanet.demo;


import java.io.FileReader;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ReadDataFromJson {

	public static void main(String[] args) throws Exception {
		
		JSONParser jParse = new JSONParser();
		
		JSONObject jObject = (JSONObject) jParse.parse(new FileReader(System.getProperty("user.dir")+"//src//main//resources//employee.json"));
		
		System.out.println(jObject.get("name"));
		System.out.println(jObject.get("empId"));
		System.out.println(jObject.get("salary"));
		
		ObjectMapper objMap = new ObjectMapper();
		Employee empObj = objMap.readValue(Paths.get(System.getProperty("user.dir")+"//src//main//resources//employee.json").toFile(), Employee.class);
		
//		System.out.println(empObj.getName());
//		System.out.println(empObj.getEmpId());
//		System.out.println(empObj.getSalary());
		
		
		System.out.println("--------------------------");
		empObj.displayEmployDetails();

	}

}
