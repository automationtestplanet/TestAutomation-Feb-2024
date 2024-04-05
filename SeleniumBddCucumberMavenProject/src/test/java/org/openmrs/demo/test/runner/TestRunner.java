package org.openmrs.demo.test.runner;

import org.junit.runner.RunWith;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
			features = "src/test/resources/Features/",
			glue = "/org/openmrs/demo/stepdefs",
			dryRun = false,
			tags = "@PositiveScenario"
		)
public class TestRunner {

}
