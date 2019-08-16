package core;
 
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 
public class Linear {
              static String browser;
              static WebDriver driver;
              static Writer report;
              static Properties p = new Properties();
              static String url;
              static By by;
 
              static boolean isElementPresent(By by) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (!driver.findElements(by).isEmpty())
                           return true;
                     else
                           return false;
              }
 
              static String getSize(By by) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
                           // return driver.findElement(by).getRect().getDimension()
                           return driver.findElement(by).getSize().toString().replace(", ", "x");
                     else
                           return "null";
              }
 
              static String getLocation(By by) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().equals("Safari"))
                           return "(0x0)";
                     else {
                           if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
                                  return driver.findElement(by).getRect().getPoint().toString().replace(", ", "x");
                           else
                                  return "null";
                     }
              }
 
              static void setValue(By by, String value) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
                           driver.findElement(by).sendKeys(value);
              }
 
              static String getValue(By by) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed()
                                  && driver.findElement(by).getTagName().equalsIgnoreCase("input"))
                           return driver.findElement(by).getAttribute("value").toString().trim();
                     else if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed()
                                  && driver.findElement(by).getTagName().equalsIgnoreCase("span"))
                           return driver.findElement(by).getText().trim();
                     else
                           return "null";
              }
 
              static void submit(By by) {
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
                           driver.findElement(by).submit();
              }
      
// SignUp validation       
              static void validateSignUp() throws IOException {
                     p.load(new FileInputStream("./input.properties"));
                     url = p.getProperty("url");
                     String browser1 = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().trim();
                     browser = browser1.replaceFirst(String.valueOf(browser1.charAt(0)), String.valueOf(browser1.charAt(0)).toUpperCase());
                     report = new FileWriter("./report_linear_" + browser.toString().toLowerCase() + ".csv", false);
                     report.write("#," + browser + ",Page,Field,isPresent,Value,Size,Location" + "\n");
                     System.out.print("#," + browser + ",Page,Field,isPresent,Value,Size,Location" + "\n");
                     String file1 = driver.getCurrentUrl().toString().trim();
                     String file = file1.substring(file1.lastIndexOf('/') + 1);
                    
                     // 01 :: First Name
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     By el01 = By.id(p.getProperty("fname_id"));
                     if (!driver.findElements(el01).isEmpty() && driver.findElement(el01).isDisplayed())
                            driver.findElement(el01).sendKeys(p.getProperty("fname_value"));
                     report.write("01" + "," + browser + "," + file + "," + "First Name" + "," + 
                                  isElementPresent(el01) + "," + getValue(el01) + "," + 
                                  getSize(el01) + "," + getLocation(el01) + "\n");
                    
                     System.out.print("01" + "," + browser + "," + file + "," + "First Name" + "," + 
                                  isElementPresent(el01) + "," + getValue(el01) + "," + 
                                  getSize(el01) + "," + getLocation(el01) + "\n");
                    
                     // 02 :: Last Name
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     By el02 = By.id(p.getProperty("lname_id"));
                     if (!driver.findElements(el02).isEmpty() && driver.findElement(el02).isDisplayed())
                           driver.findElement(el02).sendKeys(p.getProperty("lname_value"));
                     report.write("02" + "," + browser + "," + file + "," + "Last Name" + "," + 
                                  isElementPresent(el02) + "," + getValue(el02) + "," + 
                                  getSize(el02) + "," + getLocation(el02) + "\n");
                    
                     System.out.print("02" + "," + browser + "," + file + "," + "Last Name" + "," + 
                                  isElementPresent(el02) + "," + getValue(el02) + "," + 
                                  getSize(el02) + "," + getLocation(el02) + "\n");
                    
                     // 03 :: Email
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     By el03 = By.id(p.getProperty("email_id"));
                     if (!driver.findElements(el03).isEmpty() && driver.findElement(el03).isDisplayed())
                           driver.findElement(el03).sendKeys(p.getProperty("email_value"));
                     report.write("03" + "," + browser + "," + file + "," + "Email" + "," + 
                                  isElementPresent(el03) + "," + getValue(el03) + "," + 
                                  getSize(el03) + "," + getLocation(el03) + "\n");
             
                     System.out.print("03" + "," + browser + "," + file + "," + "Email" + "," + 
                                  isElementPresent(el03) + "," + getValue(el03) + "," + 
                                  getSize(el03) + "," + getLocation(el03) + "\n");
                    
                     // 04 :: Phone
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     By el04 = By.id(p.getProperty("phone_id"));
                     if (!driver.findElements(el04).isEmpty() && driver.findElement(el04).isDisplayed())
                           driver.findElement(el04).sendKeys(p.getProperty("phone_value"));
                     report.write("04" + "," + browser + "," + file + "," + "Phone" + "," + 
                                  isElementPresent(el04) + "," + getValue(el04) + "," + 
                                  getSize(el04) + "," + getLocation(el04) + "\n");
             
                     System.out.print("04" + "," + browser + "," + file + "," + "Phone" + "," + 
                                  isElementPresent(el04) + "," + getValue(el04) + "," + 
                                  getSize(el04) + "," + getLocation(el04) + "\n");
                    
                     // SUBMIT
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                     submit(By.id(p.getProperty("submit_id")));
                     WebDriverWait wait = new WebDriverWait(driver, 15);
                     wait.until(ExpectedConditions.titleIs("Confirmation"));
                     report.flush();
                     report.close();
              }
             
// Confirmation validation
              static void validateConfirmation() throws IOException {
                     p.load(new FileInputStream("./input.properties"));
                     report = new FileWriter("./report_linear_" + browser.toString().toLowerCase() + ".csv", true);
                     String file1 = driver.getCurrentUrl().toString().trim();
                     String file = file1.substring(file1.lastIndexOf('/') + 1);
                     // 05 :: First Name
                     By el05 = By.id(p.getProperty("fname_id"));
                     report.write("05" + "," + browser + "," + file + "," + "First Name" + "," + 
                                  isElementPresent(el05) + "," + getValue(el05) + "," + getSize(el05) + "," + getLocation(el05) + "\n");
                     System.out.print("05" + "," + browser + "," + file + "," + "First Name" + "," + 
                                  isElementPresent(el05) + "," + getValue(el05) + "," + getSize(el05) + "," + getLocation(el05) + "\n");
                     // 06 :: Last Name
                     By el06 = By.id(p.getProperty("lname_id"));
                     report.write("06" + "," + browser + "," + file + "," + "Last Name" + "," + 
                                  isElementPresent(el06) + "," + getValue(el06) + "," + getSize(el06) + "," + getLocation(el06) + "\n");
                     System.out.print("06" + "," + browser + "," + file + "," + "Last Name" + "," + 
                                  isElementPresent(el06) + "," + getValue(el06) + "," + getSize(el06) + "," + getLocation(el06) + "\n");
                     // 07 :: Email
                     By el07 = By.id(p.getProperty("email_id"));
                     report.write("07" + "," + browser + "," + file + "," + "Email" + "," + 
                                  isElementPresent(el07) + "," + getValue(el07) + "," + getSize(el07) + "," + getLocation(el07) + "\n");
                     System.out.print("07" + "," + browser + "," + file + "," + "Email" + "," + 
                                  isElementPresent(el07) + "," + getValue(el07) + "," + getSize(el07) + "," + getLocation(el07) + "\n");
                     // 08 :: Phone
                     By el08 = By.id(p.getProperty("phone_id"));
                     report.write("08" + "," + browser + "," + file + "," + "Phone" + "," + 
                                  isElementPresent(el08) + "," + getValue(el08) + "," + getSize(el08) + "," + getLocation(el08) + "\n");
                     System.out.print("08" + "," + browser + "," + file + "," + "Phone" + "," + 
                                  isElementPresent(el08) + "," + getValue(el08) + "," + getSize(el08) + "," + getLocation(el08) + "\n");
             
                     report.flush();
                     report.close();
              }
             
// Main method
              public static void main(String[] args) throws Exception {
                     // checking browser 
                     browser = System.getProperty("browser"); // -Dbrowser="firefox"
                     if (browser == null) {
                           System.err.println("Please provide browser: -Dbrowser=firefox");
                           System.exit(0);
                     } else if (!browser.equalsIgnoreCase("chrome") && !browser.equalsIgnoreCase("firefox")
                                  && !browser.equalsIgnoreCase("safari") && !browser.equalsIgnoreCase("edge")) {
                           System.err.println("Framework supports only: Chrome, Firefox, Safari or Edge");
                           System.exit(0);
                     }
                    
                     //getting web driver
                     Logger.getLogger("").setLevel(Level.OFF);
 
                     String driverPath = "";
 
                     if (browser.equalsIgnoreCase("firefox")) {
                           if (System.getProperty("os.name").toUpperCase().contains("MAC"))
                                  driverPath = "./resources/webdrivers/mac/geckodriver.sh";
                           else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
                                  driverPath = "./resources/webdrivers/pc/geckodriver.exe";
 
                           else
                                  throw new IllegalArgumentException("Unknown OS");
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
                           else
                                  throw new IllegalArgumentException("Unknown OS");
                           System.setProperty("webdriver.chrome.driver", driverPath);
                           System.setProperty("webdriver.chrome.silentOutput", "true");
                           ChromeOptions option = new ChromeOptions();
                           option.addArguments("disable-infobars");
                           option.addArguments("--disable-notifications");
                           if (System.getProperty("os.name").toUpperCase().contains("MAC"))
                                  option.addArguments("-start-fullscreen");
                           else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
                                  option.addArguments("--start-maximized");
                           else
                                  throw new IllegalArgumentException("Unknown OS");
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
 
                     else {
                           throw new WebDriverException("Unknown WebDriver");
                     }
                    
 
                     p.load(new FileInputStream("./input.properties"));
                     url=p.getProperty("url");
                     driver.get(url);
                    
                    
                     validateSignUp();
                     validateConfirmation();
                     // Close browser
                     driver.quit();
              }
       }
 
 