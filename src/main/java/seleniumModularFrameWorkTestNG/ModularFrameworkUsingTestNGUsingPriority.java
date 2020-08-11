package seleniumModularFrameWorkTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ModularFrameworkUsingTestNGUsingPriority {

	WebDriver driver;
	String browser= null;
	
	
	@BeforeTest
	public void readPropertyFile() {
		
		Properties prop = new Properties();
		
		 try {
			 FileInputStream fis = new FileInputStream(".\\Propertiesfile\\config.properties");
		     prop.load(fis);
		     browser = prop.getProperty("browser");
		     System.out.println("Browser used: " + browser);
		
		 }
		 catch(IOException e) {
			e.printStackTrace();
		 }
	
	
	} 
	 
	 
	@BeforeMethod
	public void init() {
	   
	
	   if(browser.equalsIgnoreCase("chrome")) {
	        System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");  
	        driver = new ChromeDriver() ;
	   }
	
	   else if(browser.equalsIgnoreCase("Firefox")) {
		   System.setProperty("webdriver.gecko.driver", "\\driver\\geckodriver.exe");  
		   driver = new FirefoxDriver() ;	
	   }
	
	
	
	   driver.manage().window().maximize();
	   driver.manage().deleteAllCookies();
	
	   driver.get("http://www.techfios.com/ibilling/?ng=admin/");
	
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
	   
	}
   
   @Test(priority = 2)
   public void logIn() throws InterruptedException {
	     
	     // Element Library
		 WebElement USERNAME_ELEMENT = driver.findElement(By.id("username"));
		 WebElement PASSWORD_ELEMENT =  driver.findElement(By.xpath("//input[@id='password']"));
		 WebElement LOGIN_BUTTON = driver.findElement(By.name("login"));
		
		 
		 // Data
		 String userName = "demo@techfios.com";
		 String password = "abc123";
		
		 
		 USERNAME_ELEMENT.sendKeys(userName); 
		 PASSWORD_ELEMENT.sendKeys(password);
		 LOGIN_BUTTON.click();
		
			
		 WebElement DASHBOARD_BUTTON_ELEMENT = driver.findElement(By.xpath("//span[contains(text(), 'Dashboard')]"));
		 String dashBoardValidationText = DASHBOARD_BUTTON_ELEMENT.getText();
		 Assert.assertEquals(dashBoardValidationText, "Dashboard", "Wrong Page!");
		 
		 Thread.sleep(2000);
		
	   
   }
   @Test(priority = 1)
   public void logInNegetive() throws InterruptedException {
	     
	     // Element Library
		 WebElement USERNAME_ELEMENT = driver.findElement(By.id("username"));
		 WebElement PASSWORD_ELEMENT =  driver.findElement(By.xpath("//input[@id='password']"));
		 WebElement LOGIN_BUTTON = driver.findElement(By.name("login")); 
		
		 
		 // Data
		 String userName = "demo@techfios.com";
		 String password = "abc1234";  // giving wrong password
		 
		 
		 USERNAME_ELEMENT.sendKeys(userName); 
		 PASSWORD_ELEMENT.sendKeys(password);
		 LOGIN_BUTTON.click();
		 
	     WebElement DASHBOARD_BUTTON_ELEMENT = driver.findElement(By.xpath("//span[contains(text(), 'Dashboard')]"));
	     String dashBoardValidationText = DASHBOARD_BUTTON_ELEMENT.getText();
		 Assert.assertEquals( dashBoardValidationText, "Dashboard", "Wrong Page!");
		 
		 Thread.sleep(2000);
		 
	   
   }
	
   
   
   @AfterMethod
   public void tearDown() {
	  
	  driver.close();
	  driver.quit();
   
   
   }
   

	











}
