package scripts;

import java.lang.reflect.Method;
import java.util.Properties;

public class InitialDriver 
{
	public static String path;
	
	static String[][] recData;
	static int firefoxColumn=0,chromeColumn=0,statusColumn=0,TCColumn=0;
	
	public static void main(String[] args) throws Exception
	{
		path=System.getProperty("user.dir");
		path=path+"/src/test/java";
		
		recData= ReusableMethods.readDataFromXl();
		ReusableMethods.setColumnNumber(recData);
		Reporting.setLogger();
		Scripts.Props=ReusableMethods.readProperties();
		
		//Running every test case from the excel sheet based on the browser
		for(int i=1; i<recData.length; i++)
		{
			//checking if we need to run or not
			if(recData[i][firefoxColumn].equalsIgnoreCase("Y"))
			{
				ReusableMethods.status="PASS";
				Scripts.browser="FireFox";
				Method testScript = Scripts.class.getMethod(recData[i][TCColumn]);
				testScript.invoke(testScript);
				ReusableMethods.writeDataToXL(i,firefoxColumn,statusColumn);
			}
			if(recData[i][chromeColumn].equalsIgnoreCase("Y"))
			{
				Scripts.browser="Chrome";
				Method testScript = Scripts.class.getMethod(recData[i][TCColumn]);
				testScript.invoke(testScript);
				ReusableMethods.writeDataToXL(i,chromeColumn,statusColumn);
			}
		}
	}
}
