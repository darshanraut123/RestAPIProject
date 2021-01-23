package Utilities;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/java/Featues",
		glue={"stepdefinations"},
		tags="",
		plugin = { "json:target/cucumber-report/cucumber.json","html:target/cucumber-report/report.html" }		
		)
public class Testrunner {

}
