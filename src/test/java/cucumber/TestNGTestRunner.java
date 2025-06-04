package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//Run your cucumber test -> you have to use TestNG runners or JUnit, It depends what type of runner you have used
//features="src/test/java/cucumber" -- means run all features file present in this package
//glue="testframework.stepDefinitions" -- means map them here is my definition page
//Monochrome = true means to print the report in readable format
//tags ="@Regression" = Nothing but telling which test has to run
//plugin= {"html:target/cucumber.html"}  -- Generate the html report
@CucumberOptions(features="src/test/java/cucumber",glue="testframework.stepDefinitions",
monochrome=true, tags ="@Regression" , plugin= {"html:target/cucumber.html"})

//By default cucumber don't haave power to scan the TestNG code but if your cucumber runner file 
//extends "AbstractTestNGCucumberTests" class then you are getting all the capabilities to run TestNG 
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

	
}
