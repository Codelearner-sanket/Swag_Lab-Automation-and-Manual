package storeAPP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class EStoreApp {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void setup() {
		 WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();

	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));  
	        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        driver.get("https://www.saucedemo.com/");
	}

	@Test(priority = 1)
	public void Login() {
		driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
		driver.findElement(By.xpath("//input[@id='login-button']")).click();
       
		String ActualTitle="Swag Labs";
		String ExpTitle=driver.getTitle();
		
		Assert.assertEquals(ActualTitle, ExpTitle);
		
		System.out.println("------------------------login completed-----------------------------------");
		
		
	}

	@Test(priority = 2)
	public void regressionTestSearch() throws Exception {
		WebElement productsort =  driver.findElement(By.xpath("//select[@class='product_sort_container']"));
		Select product = new Select(productsort);
		
		product.selectByIndex(2);
	  

	}

	@Test(priority = 3)
	public void AddToCart() throws Exception {
		
		
		driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-onesie']")).click();
		driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
		
		WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='cart_list']")));
		Assert.assertTrue(cartItem.isDisplayed(), "Item was not added to cart!");
	
        WebElement totalAmountElement = driver.findElement(By.xpath("//div[@class='inventory_item_price']"));  


        String amountText = totalAmountElement.getText().replace("$", "").trim();;
     
        double actualAmount = Double.parseDouble(amountText);
        
        System.out.println(actualAmount);

        double expectedAmount = 7.99; 

       
        Assert.assertEquals(actualAmount, expectedAmount, "Amoungt verification failed");

        System.out.println("Amount verification passed! Total amount is " + actualAmount);
	}

	@Test(priority = 4)
	public void checkout() throws Exception{
		
		
		driver.findElement(By.id("checkout")).click();
		
		
		
		driver.findElement(By.id("first-name")).sendKeys("Test");
		driver.findElement(By.id("last-name")).sendKeys("User");
		
		driver.findElement(By.id("postal-code")).sendKeys("400083");
		
		
		
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();
        
        
        List<WebElement> errorMessages = driver.findElements(By.xpath("//div[contains(@class, 'error-message-container')]//h3"));
        for (WebElement error : errorMessages) {
            System.out.println(error.getText());
        }

        
//        String Error = driver.findElement(By.xpath("//div[@class=\"error-message-container error\"] ")).getText();
//        System.out.println(Error);
//        Thread.sleep(2000);
        driver.findElement(By.id("finish")).click();
        
      
        String ActualTitle="Thank you for your order!";
		String ExpTitle=driver.findElement(By.xpath("//*[contains(text(), \"Thank you for your order!\")]")).getText();
		
		
		Assert.assertEquals(ActualTitle, ExpTitle,"not verified ");
//		Thread.sleep(2000);
		System.out.println("Success ! Your Order is on the way ");
				
	}

	@AfterClass
	public void teardown() {
		driver.quit();
	}

}
