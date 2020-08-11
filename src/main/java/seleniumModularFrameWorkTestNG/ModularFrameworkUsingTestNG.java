package seleniumModularFrameWorkTestNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ModularFrameworkUsingTestNG {

	WebDriver driver;
	String browser = null;

	@BeforeTest
	public void readfile() {

		Properties prop = new Properties();

		try {
			FileInputStream fis = new FileInputStream(".\\Propertiesfile\\config.properties");
			prop.load(fis);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if (browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", ".\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		driver.get("http://www.techfios.com/ibilling/?ng=admin/");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@Test 
	public void logInAndAddcontact() throws InterruptedException {

		// Element Library
		By USERNAME_ELEMENT = By.id("username");
		By PASSWORD_ELEMENT = By.xpath("//input[@id='password']");
		By LOGIN_BUTTON = By.name("login");
	    By DASHBOARD_BUTTON_ELEMENT = By.xpath("//span[contains(text(), 'Dashboard')]");
	    By CUSTOMERS_BUTTON_ELEMENT = By.xpath("//span[contains(text(), 'Customers')]");
		By ADDCUSTOMER_BUTTON_ELEMENT =By.xpath("//a[contains(text(), 'Add Customer')]");
		By ADDCONTACT_BUTTON_ELEMENT = By.xpath("//h5[contains(text(), 'Add Contact')]");
		By FULLNAME_ELEMENT = By.id("account");
		By EMAIL_ELEMENT = By.xpath("//input[@id='email']");

		// Data
		String userName = "demo@techfios.com";
		String password = "abc123";
		String fullName= "Test Spring";
	    String email = "@gmail.com";
		

		// TestCase 1: Login Test case  (login to Techfios0
		driver.findElement(USERNAME_ELEMENT).sendKeys(userName);
		driver.findElement(PASSWORD_ELEMENT).sendKeys(password);
		driver.findElement(LOGIN_BUTTON).click();

		// Explicit Wait for Dashboard element
	 
		waitForElement(driver, 5, By.xpath("//span[contains(text(), 'Dashboard')]"));

		// Assertion for Dashboard Element
		String dashBoardValidationText = driver.findElement(DASHBOARD_BUTTON_ELEMENT).getText();
		Assert.assertEquals(dashBoardValidationText, "Dashboard", "Wrong Page!");

	    // TestCase 2: Addcontact (FullName, email)
		
		//To click Customers button and AddCustomer button
		driver.findElement(CUSTOMERS_BUTTON_ELEMENT).click();
		//driver.get("http://www.techfios.com/ibilling/?ng=contacts/add/");
	    driver.findElement(ADDCUSTOMER_BUTTON_ELEMENT).click();
		
		// Explicit wait for AddContact element
	    waitForElement(driver, 3, By.xpath("//h5[contains(text(), 'Add Contact')]") );
		
	    // Assertion for Addcontact button element
		String addContactValidationText = driver.findElement(ADDCONTACT_BUTTON_ELEMENT) .getText();
		Assert.assertEquals(addContactValidationText, "Add Contact", "Wrong Page!");
	
		
	    // Creating Random Numbers for FullName and Email elements
		Random rnd = new Random();
	    int randomNum = rnd.nextInt(999);
      
	    driver.findElement(FULLNAME_ELEMENT).sendKeys(fullName + randomNum);
	    driver.findElement(EMAIL_ELEMENT).sendKeys(randomNum  + email);
	    Thread.sleep(3000);
	
	
	}
   
	@AfterMethod
	public void tearDown() throws InterruptedException {

		//Thread.sleep(3000);
		driver.close();
		
		driver.quit(); // When you use new version of Firefox browser do not use quit method

	}

	public void waitForElement(WebDriver driver, int timeInSeconds, By locator) {

		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

}
