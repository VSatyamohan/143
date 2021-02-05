package commonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.PropertyFileUtil;

public class FunctionLibrary 
{
   public static WebDriver driver;
   
   public static WebDriver startBrowser() throws Throwable
   {
	   if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
	   {
		   System.setProperty("webdriver.chrome.driver", "D:\\Selenium10\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");
		   driver=new ChromeDriver();  
	   }
	   else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
	   {
		   System.setProperty("webdriver.gecko.driver", "D:\\Selenium10\\ERP_StockAccounting\\CommonDrivers\\geckodriver.exe");
		   driver=new FirefoxDriver();
	   }
	   return driver;
   }
   
   public static void openApplication(WebDriver driver) throws Throwable
   {
	  driver.get(PropertyFileUtil.getValueForKey("Url"));
	  driver.manage().window().maximize();
	  System.out.println("Execting Open App method");
   }
   
   public static void waitForElement(WebDriver driver, String Locatortype, String Locatorvalue, String timewait)
   {
	   WebDriverWait mywait=new WebDriverWait(driver, Integer.parseInt(timewait));
	   if(Locatortype.equalsIgnoreCase("id"))
	   {
		   mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locatorvalue)));
	   }
	   else if(Locatortype.equalsIgnoreCase("name"))
	   {
		   mywait.until(ExpectedConditions.invisibilityOfElementLocated(By.name(Locatorvalue)));
		  
	   }
	   else if(Locatortype.equalsIgnoreCase("xpath"))
	   {
		   mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locatorvalue)));
	   }
	   
   }
   
   public static void typeAction(WebDriver driver, String Locatortype, String Locatorvalue, String testdata )
   {
	   if(Locatortype.equalsIgnoreCase("id"))
	   {
		   driver.findElement(By.id(Locatorvalue)).clear();
		   driver.findElement(By.id(Locatorvalue)).sendKeys(testdata);		   
   }
	   else if(Locatortype.equalsIgnoreCase("name"))
	   {
		   driver.findElement(By.id(Locatorvalue)).clear();
		   driver.findElement(By.id(Locatorvalue)).sendKeys(testdata);		  
	   }
	   else if (Locatortype.equalsIgnoreCase("xpath"))
	   {
		   driver.findElement(By.id(Locatorvalue)).clear();
		   driver.findElement(By.id(Locatorvalue)).sendKeys(testdata);		  
	   }
		   
	   }
   
   public static void clickAction(WebDriver driver, String Locatortype, String Locatorvalue)
   {
	   if(Locatortype.equalsIgnoreCase("id"))
	   {
		   driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);  
	   }
	   else if (Locatortype.equalsIgnoreCase("name"))
	   {
		   driver.findElement(By.id(Locatorvalue)).click();  
	   }
	   else if (Locatortype.equalsIgnoreCase("xpath"))
	   {
		   driver.findElement(By.id(Locatorvalue)).click();
	   }
	   
   }
   public static void closeBrowser(WebDriver driver)
   {
	   driver.quit();   
   }
   
   public static String generateDate()
   {
	   Date date=new Date();
	   SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_DD_SS");
	   return sdf.format(date);
   }
   
   public static void captureData(WebDriver driver, String Locatortype, String Locatorvalue) throws Throwable
   {
	   String snumber="";
	   if(Locatortype.equalsIgnoreCase("id"))
	   {
		   snumber=driver.findElement(By.id(Locatorvalue)).getAttribute("value");
	   }
	   else if(Locatortype.equalsIgnoreCase("name"))
	   {
		   snumber=driver.findElement(By.id(Locatorvalue)).getAttribute("value");
	   }
	   else if(Locatortype.equalsIgnoreCase("xpath"))
	   {
		   snumber=driver.findElement(By.id(Locatorvalue)).getAttribute("value");
	   }
	   FileWriter fw=new FileWriter("D:\\Selenium10\\ERP_StockAccounting\\CaptureData\\supplier.txt");
	   BufferedWriter bw=new BufferedWriter(fw);
	   bw.write(snumber);
	   bw.flush();
	   bw.close();   
   }
   
   public static void tableValidation(WebDriver driver, String testdata) throws Throwable
   {
	   FileReader fr=new FileReader("D:\\Selenium10\\ERP_StockAccounting\\CaptureData\\supplier.txt");
	   BufferedReader br= new BufferedReader(fr);
	   String exp_data=br.readLine();
	   int column=Integer.parseInt(testdata);
	   if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed())
		   driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-panel"))).click();
	       driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
	       driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(exp_data);
	       driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
	       WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("supp-table")));
	       List<WebElement> rows=table.findElements(By.tagName("tr"));
	       for(int i=1; i<=rows.size(); i++)
	       {
	    	   String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column+"]/div/span/span")).getText();
	    	   System.out.println(exp_data+"   "+act_data);
	    	   Assert.assertEquals(act_data, exp_data, "Snumber is not matching");
	    	   break;   
	       }
	       
   }

}
