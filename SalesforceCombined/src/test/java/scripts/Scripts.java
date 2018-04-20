package scripts;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.UserPage;


public class Scripts extends ReusableMethods
{
	public  static WebDriver driver;
	public static String browser;
	public static String status;
	public static Properties Props;
	
	public static void Login_Error_Message_01() throws InterruptedException
	{
		Reporting.startReport(("TestCase 1: Login Error Message to be displayed run on "+browser));
		
		Modules.launchBrowser(browser,Props.getProperty("URL"));
		LoginPage.enterUsername(Props.getProperty("Username"));
		LoginPage.clearPassword();
		LoginPage.clickLogin();
		LoginPage.displayMsg(Props.getProperty("noPswMsg"));
		
		Reporting.endReport();
		driver.close();	
	}
	
	public static void Login_To_Salesforce_02() throws InterruptedException
	{
		Reporting.startReport("TestCase 2: Successful Login To SalesForce website ");
		
		Modules.launchBrowser(browser,Props.getProperty("URL"));
		LoginPage.enterUsername(Props.getProperty("Username"));
		LoginPage.enterPassword(Props.getProperty("Password"));
		LoginPage.clickLogin();
		UserPage.validateUserPage();
		UserPage.displayFreeTrial();
		
		Reporting.endReport();
		driver.close();	
	}
	
	public static void Check_RememberMe_03() throws InterruptedException
	{
		Reporting.startReport("TestCase 3: Check Remeber Me");
		
		Modules.launchBrowser(browser,Props.getProperty("URL"));	
		LoginPage.enterUsername(Props.getProperty("Username"));
		LoginPage.enterPassword(Props.getProperty("Password"));
		LoginPage.checkRemember();
		LoginPage.clickLogin();
		UserPage.selectMenu("Logout");
		LoginPage.validateUsername(Props.getProperty("Username"));
		
		Reporting.endReport();
		driver.close();
	}
	
	public static void Forgot_Password_04A() throws InterruptedException
	{
		Reporting.startReport("TestCase 4A: Check the working of Forgot Password");
		
		Modules.launchBrowser(browser,Props.getProperty("URL"));
		LoginPage.ClickForgetMe(Props.getProperty("Username"));
				
		Reporting.endReport();
		driver.close();
	}
	
	public static void Validate_Login_Error_Message_04B() throws InterruptedException
	{
		Reporting.startReport("TestCase 4B: Validate Display of an error message on incorrect login ");
		
		Modules.launchAndLogin(browser,Props.getProperty("URL"),Props.getProperty("Username")," ");
		LoginPage.errorMsg(Props.getProperty("errorMsg"));
				
		Reporting.endReport();
		driver.close();
	}

	public static void Check_User_Menu_05() throws InterruptedException
	{
		Reporting.startReport("TestCase 5: Check User Menu Drop Down Options");
		
		Modules.launchAndLogin(browser,Props.getProperty("URL"),Props.getProperty("Username"),Props.getProperty("Password"));
		UserPage.UserNavMenu();		
		UserPage.validateUserOptions(Props.get("reqDropdown").toString().split(","));
		
		Reporting.endReport();
		driver.close();
	}
}