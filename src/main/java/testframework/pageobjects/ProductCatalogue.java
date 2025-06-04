package testframework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import testframework.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{

	WebDriver driver;
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	} 
	//PageFactory will work only with driver.constructer
	
	//Searching all the product on the page
	@FindBy(css=".mb-3")
	List<WebElement> productList;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	//Waiting to visible to the element
	By productBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMsg = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productBy);
		return productList;
	}

	public WebElement getProductByName(String productName)
	{
		WebElement product = getProductList().stream().filter(productname 
		->productname.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return product;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement product = getProductByName(productName);
		product.findElement(addToCart).click();//Q. Can you apply page factory within WebElement.findelement --> Not Possible
		waitForElementToAppear(toastMsg);
		waitForElementToDisappear(spinner);
	}
	
}
