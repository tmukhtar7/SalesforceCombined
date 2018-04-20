package pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import scripts.ReusableMethods;
import scripts.Scripts;

public class UserPage extends ReusableMethods
{
//	public static void goToTab(WebDriver driver,String giventab) throws InterruptedException
//	{
//		WebElement tab=driver.findElement(By.linkText(giventab));
//		performClick(tab, giventab);
//		
//		Thread.sleep(3000);
//	}
	
	public static void validateUserPage()
	{
		validateTitle(Scripts.driver,"Home Page");		
	}

	public static void displayFreeTrial()
	{
		WebElement body= Scripts.driver.findElement(By.xpath(".//*[@id='bodyCell']"));
		validateTextContent(body, "Welcome to Your Free Trial ", "Free Trial Body");
	}
	
	public static void selectMenu(String item) throws InterruptedException 
	{
		UserNavMenu();
		List<WebElement> menu= Scripts.driver.findElements(By.xpath(".//*[@id='userNav-menuItems']/a"));
		for(WebElement option:menu)
		{
			String menuItem=option.getText();
			if(item.equals(menuItem))
			{	
				performClick(option, menuItem);
				Thread.sleep(5000);
				return;
			}
		}
		System.out.println("Fail: "+ item+ " Option not in the Menu");
	}
	
	public static void UserNavMenu() throws InterruptedException
	{
		WebElement navmenu=Scripts.driver.findElement(By.xpath(".//*[@id='userNavButton']"));
		performClick(navmenu, "User Navigation Menu");
		
		Thread.sleep(5000);
	}
		
	public static void validateUserOptions(String[] req) 
	{
		List<String> reqlist=Arrays.asList(req);
		List<WebElement> menu= Scripts.driver.findElements(By.xpath(".//*[@id='userNav-menuItems']/a"));
		for(WebElement option:menu)
		{
			String menuItem=option.getText();
			if(reqlist.contains(menuItem))
				System.out.println("Pass: " + menuItem + " is Present");
		}		
	}
}
