package testframework.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import testframework.TestComponenets.BaseTest;
import testframework.pageobjects.CartPage;
import testframework.pageobjects.CheckOutPage;
import testframework.pageobjects.ConfirmationPage;
import testframework.pageobjects.LandingPage;
import testframework.pageobjects.OrderPage;
import testframework.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

	String productName ="ZARA COAT 3";
	
	//To get the data from the DataProvider method and pass into your testcase use@Test(dataProvider="getData") 
	//and pass the number of variables used in the Data Provider
	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException , InterruptedException
	{
		ProductCatalogue productCataloge = landingPage.loginPage(input.get("email"), input.get("password"));
		List<WebElement> productList = productCataloge.getProductList();
		productCataloge.addProductToCart(input.get("productName"));
		CartPage cartPage = productCataloge.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckOutPage checkoutpage = cartPage.goToCheckOut();
		checkoutpage.selectCountry("India");
		ConfirmationPage confirmationPage = checkoutpage.submitOrder();
		String confirmMsg = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
			
	}
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest()
	{
	
		ProductCatalogue productCataloge = landingPage.loginPage("kumarmanish1509@gmail.com", "learnAutomation@01");
		OrderPage orderPage = productCataloge.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}	
	
	//DataProvider will help you to drive the data ans pass the multiple data source.
	/*This is 1st way to pass multiple data source
	 * @DataProvider public Object[][] getData() { return new Object[][]
	 * {{"kumarmanish1509@gmail.com","learnAutomation@01","ZARA COAT 3"},
	 * {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL"}}; }
	 */
	
		
	//DataProvider also allow to return HashMap. It just not a Arrays.
	//This is 2nd ways to pass multiple data source using HashMap
	/*
	 * @DataProvider public Object[][] getData() throws IOException {
	 * 
	 * HashMap<String,String> map = new HashMap<String,String>(); map.put("email",
	 * "kumarmanish1509@gmail.com"); map.put("password", "learnAutomation@01");
	 * map.put("productName", "ZARA COAT 3");
	 * 
	 * HashMap<String,String> map1 = new HashMap<String,String>();
	 * map1.put("email","shetty@gmail.com"); map1.put("password", "Iamking@000");
	 * map1.put("productName", "ADIDAS ORIGINAL");
	 * 
	 * return new Object[][] {{map},{map1}}; }
	 */
	
	//Check the below code why it's not working, With above code its working
 
	//This is 3rd way to read the data from the Json file and pass it into hashMap
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\testframework\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
}
