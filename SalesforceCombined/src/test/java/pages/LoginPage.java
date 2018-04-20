package pages;
import org.openqa.selenium.*;

import scripts.ReusableMethods;
import scripts.Scripts;

public class LoginPage extends ReusableMethods 
{
//	public static void validateLoginPage(WebDriver driver) 
//	{
//		validateTitle(driver,"Login");	
//	}
	
	public static void enterUsername(String name)
	{
		WebElement un= (Scripts.driver).findElement(By.xpath(".//*[@id='username']"));
		enterText(un,name,"Username");
	}
	
	public static void clearPassword()
	{
		WebElement pw= Scripts.driver.findElement(By.xpath(".//*[@id='password']"));
		clearText(pw,"Password");
	}

	public static void clickLogin() throws InterruptedException 
	{
		WebElement loginButton= Scripts.driver.findElement(By.xpath(".//*[@id='Login']"));
		performClick(loginButton, "Login Button");	
		Thread.sleep(5000);
	}

	public static void displayMsg(String string) 
	{
		WebElement errorMsg= Scripts.driver.findElement(By.xpath(".//*[@id='error']"));
		validateTextContent(errorMsg,string,"Text Field");
	}
	
	public static void enterPassword(String psw)
	{
		WebElement pw= Scripts.driver.findElement(By.xpath(".//*[@id='password']"));
		enterText(pw,psw,"Password");
	}

	public static void checkRemember() 
	{
		WebElement RememberMe= Scripts.driver.findElement(By.xpath(".//*[@id='rememberUn']"));
		selectCheckbox(RememberMe, "Remember ME");
	}

	public static void validateUsername(String ID) 
	{
		WebElement name= Scripts.driver.findElement(By.xpath(".//*[@id='idcard-identity']"));
		validateText(name,ID,"UserName");
	}

	public static void ClickForgetMe(String Username) throws InterruptedException
	{
		WebElement link = Scripts.driver.findElement(By.linkText("Forgot Your Password?"));
		performClick(link, "Forgot Password Link");
		
		Thread.sleep(3000);
		
		WebElement un= Scripts.driver.findElement(By.id("un"));
		enterText(un, Username, "Username");
		
		WebElement cont=Scripts.driver.findElement(By.id("continue"));
		performClick(cont, "Click Continue");;
		
		Thread.sleep(3000);
		
		WebElement check= Scripts.driver.findElement(By.id("header"));
		validateTextContent(check, "Check Your Email", "Text Area");
	}

	public static void errorMsg(String msg) 
	{
		WebElement errorMsg= Scripts.driver.findElement(By.xpath(".//*[@id='error']"));
		validateTextContent(errorMsg,msg , "Text Area");
	}
	
	public static void loginToSalesforce(String user, String psw) throws InterruptedException
	{
		enterUsername(user);
		enterPassword(psw);
		clickLogin();		
	}

	
}
