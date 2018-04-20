package scripts;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pages.LoginPage;
import pages.UserPage;


public class Modules extends  ReusableMethods
{
	public static void launchBrowser(String browser, String URL) 
	{
		if(browser.equalsIgnoreCase("FireFox"))
		{
			String DriverPath=InitialDriver.path+"/framework/geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", DriverPath);
			Scripts.driver= new FirefoxDriver();
		}
		if(browser.equalsIgnoreCase("Chrome"))
		{
			String DriverPath=InitialDriver.path+"/framework/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", DriverPath);
			Scripts.driver= new ChromeDriver();
			Scripts.driver.manage().window().maximize();
		}
		Scripts.driver.get(URL);
	}
	
	public static void launchAndLogin(String browser,String URL, String un, String psw) throws InterruptedException 
	{
		launchBrowser(browser,URL);
		LoginPage.loginToSalesforce(un,psw);
		UserPage.validateUserPage();
	}

}
