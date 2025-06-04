package testframework.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testframework.TestComponenets.BaseTest;
import testframework.pageobjects.CartPage;
import testframework.pageobjects.CheckOutPage;
import testframework.pageobjects.ConfirmationPage;
import testframework.pageobjects.LandingPage;
import testframework.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest{

	//To access globally we are writing here
	public LandingPage landingPage;
	public ProductCatalogue productCataloge;
	public ConfirmationPage confirmationPage;
	
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	 //Put regular expression (.+) which represent any value and character
	//Tell your string this is regular expression to represent have to start with ^ and end with $
	 @Given("^Logged in with username (.+) and password (.+)$")
	 public void logged_in_username_and_password(String username, String password)
	 {
		 productCataloge = landingPage.loginPage(username, password);
	 }
	 
	 @When("^I add product (.+) to Cart$")
	 public void i_add_product_to_cart(String productName)
	 {
		 List<WebElement> productList = productCataloge.getProductList();
			productCataloge.addProductToCart(productName);
	 }
	 
	 @When("^Checkout (.+) and submit the order$")
	 public void checkout_submit_order(String productName)
	 {
		 CartPage cartPage = productCataloge.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckOutPage checkoutpage = cartPage.goToCheckOut();
		checkoutpage.selectCountry("India");
		confirmationPage = checkoutpage.submitOrder();
	 }
	 
	 //Here we can not use "THANKYOU FOR THE ORDER." so instead of this using {string}
	 @Then("{string} message is displayed on ConfirmationPage")
	 public void message_displayed_confirmationPage(String string)
	 {
		 String confirmMsg = confirmationPage.getConfirmationMessage();
		 Assert.assertTrue(confirmMsg.equalsIgnoreCase(string));
		 driver.close();
	 }
	 
		
	  @Then("^\"([^\"]*)\" message is displayed$")
	 public void something_message_is_displayed(String strArg1) 
	  {
		  Assert.assertEquals(strArg1,landingPage.getErrorMessage()); 
		  driver.close(); 
	  }
		
}
