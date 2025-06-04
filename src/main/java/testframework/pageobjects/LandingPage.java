package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testframework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	//Created constructor to access driver object from the parent class
	//Below constructor code will run first then only other testcase will run
	public LandingPage(WebDriver driver)
	{
		super(driver);//Child class to parent class also sends the variable using super keyword
		this.driver=driver;
		//Accept 2 arguments - 1> driver object and 2> this refer current class driver
		PageFactory.initElements(driver, this);
	} 
	
	//Using PageFactory method to find the locator
	//FindBy - Construct the - driver.findElement(By.id("userEmail")); as same, So instead or writing this yu can use FindBy
	@FindBy(id="userEmail")
	WebElement userEmailID;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement loginButton;
	
	@FindBy(css="[class*='flyInOut'")
	WebElement errorMessage;
	
	
	public ProductCatalogue loginPage(String email, String password)
	{
		userEmailID.sendKeys(email);
		userPassword.sendKeys(password);
		loginButton.click();
		ProductCatalogue productCataloge = new ProductCatalogue(driver);
		return productCataloge;
	}
	
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
		
	}
	public void webURL()
	{
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
}
