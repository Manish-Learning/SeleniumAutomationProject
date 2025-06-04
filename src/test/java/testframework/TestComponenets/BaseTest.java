package testframework.TestComponenets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import testframework.pageobjects.LandingPage;

//BaseTest class should have all the common methods details like, Browser, URL
public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException
	{
		
		// Properties class : file name should be with .properties extension (If you write any file name with .properties name extension
		//the using prroperties class in java you will be able to parse these file and extract all global parameter values.
		Properties prop = new Properties();
		//Create FileInputStream because load() is expecting file as a input
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\testframework\\resources\\GlobalData.properties");
		prop.load(fis);
		
		//Java Ternary Operator does it first provide the condition, If condiotion is true then 2nd argu will execute
		//if condition wis false then 3rd argu will execute
		String browserName = System.getProperty("browser")!= null ? System.getProperty("browser") : prop.getProperty("browser");
		//prop.getProperty("browser");

		//To run the script in headless mode the below code 
		if(browserName.contains("chrome")){
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);	
			driver.manage().window().setSize(new Dimension(1440,900)); //Browser in full screen mode
		}
		
		/*if(browserName.equalsIgnoreCase("chrome"))
		{
		WebDriverManager.chromedriver().setup();
		 driver = new ChromeDriver();	
		}*/
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			//Firefox driver details
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\kumar\\Downloads\\Selenium Project Software\\geckodriver-v0.36.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//edge driver details
			System.setProperty("webdriver.edge.driver",
					"C:\\Users\\kumar\\Downloads\\Selenium Project Software\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//Read json data as string
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//Convert String to HashMap --> Add New dependency in Pom.xml file - Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>>  data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String ,String>>>(){
		}); 
		return data;
	}
	
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		//return file;
		return System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
	}
	
	//When you are grouping to run only particular test then in TestNg few annotation will not work so 
	//to make it run always then use (alwaysRun=true) along with the TestNG annotation.
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver =initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.webURL(); //Hitting web URL link
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
}
