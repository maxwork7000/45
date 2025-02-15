package core;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.*;

class Common {
	static WebDriver driver;
    static Writer report;
    static Properties p = new Properties();
	static String url;
	static String browser;
    
	static void getWebDriver(String browser){
		Logger.getLogger("").setLevel(Level.OFF);
		String driverPath = "";	
		
		if (browser.equalsIgnoreCase("firefox")) {
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./resources/webdrivers/mac/geckodriver.sh";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/geckodriver.exe";
		else throw new IllegalArgumentException("Unknown OS");
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver(); 
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		
		else if (browser.equalsIgnoreCase("chrome")) {
			if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./resources/webdrivers/mac/chromedriver";
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/chromedriver.exe";
			else throw new IllegalArgumentException("Unknown OS");
			System.setProperty("webdriver.chrome.driver", driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("disable-infobars");
			option.addArguments("--disable-notifications");
			if (System.getProperty("os.name").toUpperCase().contains("MAC"))
				option.addArguments("-start-fullscreen");
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
				option.addArguments("--start-maximized");
			else throw new IllegalArgumentException("Unknown OS");
			driver = new ChromeDriver(option);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		
		else if (browser.equalsIgnoreCase("safari")) {
			if (!System.getProperty("os.name").contains("Mac")) {
				throw new IllegalArgumentException("Safari is available only on Mac");
			}
			driver = new SafariDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		
		else if (browser.equalsIgnoreCase("edge")) {
			if (!System.getProperty("os.name").contains("Windows"))
				throw new IllegalArgumentException("MS Edge is available only on Windows");
			System.setProperty("webdriver.edge.driver", "./resources/webdrivers/pc/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}
		else {throw new WebDriverException("Unknown WebDriver");}
		}
	
	static void open(String browser, String url){
		getWebDriver(browser);
		driver.get(url);
		}
	
	static boolean isElementPresent(By by) throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty()) {
			highlightElement(driver.findElement(by));
			unhighlightElement(driver.findElement(by));
			return true;}
		else return false;}
	
	static String getSize(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getSize().toString().replace(", ", "x"); else return "null";}

	static String getLocation(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("Safari")) return "(0x0)";
		else {
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
		return driver.findElement(by).getLocation().toString().replace(", ", "x"); 
		else return "null";}
		}
	
    public static void highlightElement(WebElement element) throws Exception{
    	((RemoteWebDriver) driver).executeScript("arguments[0].setAttribute('style','border: solid 3px red');", element);
        Thread.sleep(100);}
    public static void unhighlightElement(WebElement element) throws Exception{
    	Thread.sleep(100);
        ((RemoteWebDriver) driver).executeScript("arguments[0].setAttribute('style','border: solid 0px red');", element);}

	static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(value);
	}
	
	static String getValue(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && 
			 driver.findElement(by).isDisplayed() && driver.findElement(by).getTagName().equalsIgnoreCase("input"))
		return driver.findElement(by).getAttribute("value").toString().trim();

		else if (!driver.findElements(by).isEmpty() && 
				  driver.findElement(by).isDisplayed() && driver.findElement(by).getTagName().equalsIgnoreCase("span"))
			return driver.findElement(by).getText().trim();
		else return "null";}
	
	static void submit(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).submit();}
	
	static String getBrowser() {
		String browser = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().trim();
		return browser.replaceFirst(String.valueOf(browser.charAt(0)), String.valueOf(browser.charAt(0)).toUpperCase());
			}
	
	static String getFileName() {
		String file = driver.getCurrentUrl().toString().trim();
		return file.substring(file.lastIndexOf('/') + 1);
			}

	static void waitTitlePage(String title){
		WebDriverWait wait = new WebDriverWait(driver, 15); 
		wait.until(ExpectedConditions.titleIs(title));
		}
	
	static void writeReportHeader(Writer report) throws IOException {
		report.write("#,Browser,Page,Field,isPresent,Value,Size,Location" + "\n");
		System.out.print("#,Browser,Page,Field,isPresent,Value,Size,Location" + "\n");
		}

	static void writeReportLine(String index, String fieldName, By by, Writer report) throws Exception {
		
		report.write(
				index + "," + 
				Common.getBrowser() + "," + 
				Common.getFileName() + "," + 
				fieldName + "," + 
				Common.isElementPresent(by) + "," +
				Common.getValue(by) + "," +
				Common.getSize(by) + "," +
				Common.getLocation(by) + "\n");
		
	System.out.print(
				index + "," + 
				Common.getBrowser() + "," + 
				Common.getFileName() + "," + 
				fieldName + "," + 
				Common.isElementPresent(by) + "," +
				Common.getValue(by) + "," +
				Common.getSize(by) + "," +
				Common.getLocation(by) + "\n");
		}
	
	static void quit(){driver.quit();}
	
	public static void checkCheckBox(By by) {
	     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	     if (!driver.findElements(by).isEmpty() && 
	    	  driver.findElement(by).isDisplayed() && 
	    	 !driver.findElement(by).isSelected())
	    	 driver.findElement(by).click();}

	public static void checkRadioButton(By by) {
	     if (!driver.findElements(by).isEmpty() && 
		      driver.findElement(by).isDisplayed() && 
		     !driver.findElement(by).isSelected())
		     driver.findElement(by).click();}
	
	public static void selectDropDown(By by, String value) {
	     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	     if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
	    	 // new Select(driver.findElement(by)).selectByIndex(5);
	    	 // new Select(driver.findElement(by)).selectByValue(value);  // <option value="CA">
	    	 new Select(driver.findElement(by)).selectByVisibleText(value);
	     	}
}
