package testframework.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import io.github.bonigarcia.wdm.WebDriverManager;
import testframework.TestComponenets.BaseTest;
import testframework.pageobjects.CartPage;
import testframework.pageobjects.CheckOutPage;
import testframework.pageobjects.ConfirmationPage;
import testframework.pageobjects.LandingPage;
import testframework.pageobjects.ProductCatalogue;

public class ErrorValidations extends BaseTest{ //This class Should have all the Error validation code

	@Test(groups= {"ErrorHandling"},retryAnalyzer=testframework.TestComponenets.Retry.class)
	public void loginErrorValidation() throws IOException
	{
		
		landingPage.loginPage("kumarmanish1509@gmail.com", "learAutomation@01");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void productErrorValidation() throws IOException
	{
		String productName ="ZARA COAT 3";
		ProductCatalogue productCataloge = landingPage.loginPage("anshika@gmail.com", "Iamking@000");
		List<WebElement> productList = productCataloge.getProductList();
		productCataloge.addProductToCart(productName);
		CartPage cartPage = productCataloge.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);		
			
	}
 
}
