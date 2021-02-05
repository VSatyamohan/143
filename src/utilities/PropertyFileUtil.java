package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil 
{
	public static String getValueForKey(String key) throws Throwable
	{
		Properties configproperties=new Properties();
		configproperties.load(new FileInputStream(new File("D:\\Selenium10\\ERP_StockAccounting\\TestInput\\InputSheet.xlsx")));
		return configproperties.getProperty(key);
		
	}

}
