package DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import commonFunLibrary.ERPFunction;
import utilities.ExcelFileUtil;
public class DataDriven {
WebDriver driver;
String inputpath="D:\\Selenium10\\ERP_StockAccounting\\TestInput\\TestData.xlsx";
String outputpath="D:\\Selenium10\\ERP_StockAccounting\\TestOutput\\DatadrivenResult.xlsx";
@BeforeTest
public void setUp()throws Throwable
{
String launch=ERPFunction.launchApp("http://projects.qedgetech.com/webapp/login.php");
Reporter.log(launch,true);
String login=ERPFunction.verifyLogin("admin", "master");
}
@Test
public void supplierCreation()throws Throwable
{
//accessing excel util methods
   ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//row count
int rc=xl.rowCount("Supplier");
int cc=xl.colCount("Supplier");
Reporter.log("no of rows are::"+rc+"   "+"no of columns are::"+cc,true);
for(int i=1;i<=rc;i++)
{
String sname=xl.getCellData("Supplier", i, 0);
String address=xl.getCellData("Supplier", i, 1);
String city=xl.getCellData("Supplier", i, 2);
String country=xl.getCellData("Supplier", i, 3);
String cperson=xl.getCellData("Supplier", i, 4);
String pnumber=xl.getCellData("Supplier", i, 5);
String mail=xl.getCellData("Supplier", i, 6);
String mnumber=xl.getCellData("Supplier", i, 7);
String note=xl.getCellData("Supplier", i, 8);
//call supplier creation method
String Results=ERPFunction.verifySupplier(sname, address, city, country, cperson, pnumber,
		mail, mnumber, note);
Reporter.log(Results,true);
xl.setCellData("Supplier", i, 9, Results, outputpath);
}
}
@AfterTest
public void tearDown()
{
//ERP_Functions.verifylogout();	
}

}
















