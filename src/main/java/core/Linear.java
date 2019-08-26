package core;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Linear {

public static void main(String[] args) throws Exception {

System.setProperty("webdriver.gecko.driver", "./resources/webdrivers/mac/geckodriver.sh");
WebDriver driver = new FirefoxDriver();
driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
driver.get("http://alex.academy/exe/signup/www/index.php");

driver.findElement(By.id("id_fname")).sendKeys("Max");
driver.findElement(By.id("id_lname")).sendKeys("Vaulini");
driver.findElement(By.id("id_email")).sendKeys("abc@gmail.com");
driver.findElement(By.id("id_phone")).sendKeys("415 555-1212");
driver.findElement(By.id("id_submit_button")).click();
Thread.sleep(1000);
System.out.println(driver.getTitle().equals("Confirmation") ? "pass" : "fail");
driver.quit();
}
}