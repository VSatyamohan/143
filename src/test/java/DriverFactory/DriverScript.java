package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
static WebDriver driver;
String inputpath="D:\\Selenium10\\MAVEN_ERP1\\TestInput\\InputSheet.xlsx";
String outputpath="D:\\Selenium10\\MAVEN_ERP1\\TestOutput\\Hybrid.xlsx";
ExtentReports report;
ExtentTest test;
@Test
public void ERP_Account()throws Throwable
{
	//creating reference object for excel util methods
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//iterating all row in MasterTestCases sheet
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
	{
	if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
	{
		//store module name into TCModule 
		String TCModule=xl.getCellData("MasterTestCases", i, 1);
		//generate user define html report
report=new ExtentReports("./Reports//"+TCModule+FunctionLibrary.generateDate()+".html");		
		//iterate all rows in TCModule sheet
		for(int j=1;j<=xl.rowCount(TCModule);j++)
		{
			//to statrt test case here
			test=report.startTest(TCModule);
			//read all columns from TC Module
		String Description=xl.getCellData(TCModule, j, 0);
		String Function_Name=xl.getCellData(TCModule, j, 1);
		String Locator_Type=xl.getCellData(TCModule, j, 2);
		String Locator_Value=xl.getCellData(TCModule, j, 3);
		String Test_Data=xl.getCellData(TCModule, j, 4);
		try
		{
		if(Function_Name.equalsIgnoreCase("startBrowser"))
		{
		driver=FunctionLibrary.startBrowser();
		test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("openApplication"))
		{
			FunctionLibrary.openApplication(driver);
			test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("waitForElement"))
		{
FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("typeAction"))
		{
FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);	
test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("clickAction"))
		{
FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("closeBrowser"))
		{
			FunctionLibrary.closeBrowser(driver);
			test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("captureData"))
		{
FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
test.log(LogStatus.INFO, Description);
		}
		else if(Function_Name.equalsIgnoreCase("tableValidation"))
		{
			FunctionLibrary.tableValidation(driver, Test_Data);
			test.log(LogStatus.INFO, Description);
		}
		//write as pass into status column
		xl.setCellData(TCModule, j, 5, "Pass",outputpath);
		xl.setCellData("MasterTestCases", i, 3, "Pass", outputpath);
		test.log(LogStatus.PASS, Description);
		}catch(Throwable t)
		{
		System.out.println("exception handled");
		xl.setCellData(TCModule, j, 5, "Fail", outputpath);
		xl.setCellData("MasterTestCases", i, 3, "Fail", outputpath);
		test.log(LogStatus.FAIL, Description);
		}
		report.endTest(test);
		report.flush();
		}
	}
	else 
	{
		//write as not executed into status column in MasterTestCases
	xl.setCellData("MasterTestCases", i, 3, "Not Executed", outputpath);	
	}
	}
}
}
