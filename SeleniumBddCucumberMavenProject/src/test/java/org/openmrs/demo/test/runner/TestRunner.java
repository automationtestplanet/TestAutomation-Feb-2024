package org.openmrs.demo.test.runner;

import org.junit.runner.RunWith;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
			features = "src/test/resources/Features/",
			glue = "/org/openmrs/demo/stepdefs",
			dryRun = false,
			monochrome = true,
			snippets = SnippetType.CAMELCASE,
			strict = true,
			tags = "@PositiveScenario",
			plugin = {"pretty", "html:target",
					"json:target/cucumberJosn.josn",
					"junit:target/cucumberJunit.xml"
					} 
		)
public class TestRunner {

}
