package scripts;

import java.io.*;
import java.util.List;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ReusableMethods 
{	
	public static String status;
	static String logMsg,FailMsg;
	
	public static void setColumnNumber(String[][] recData)
	{
		//Getting the column for browser and status
		for(int j=0;j<recData[0].length;j++)
		{
			if(recData[0][j].equalsIgnoreCase("Test Case Name"))
				InitialDriver.TCColumn=j;
			if(recData[0][j].equalsIgnoreCase("Run on Firefox"))
				InitialDriver.firefoxColumn=j;
			if(recData[0][j].equalsIgnoreCase("Run on Chrome"))
				InitialDriver.chromeColumn=j;
			if(recData[0][j].equalsIgnoreCase("Status"))
				InitialDriver.statusColumn=j;
		}
	}
	
	public static Properties readProperties() throws IOException
	{
		File file = new File(InitialDriver.path+"/framework/Properties.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		return properties;
	}
	
	public static String[][] readDataFromXl() throws IOException
	{
		String dt_path=InitialDriver.path+"/framework/TestSuit.xls";
		FileInputStream fs = new FileInputStream (new File(dt_path));
		
		HSSFWorkbook wb= new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheet("Sheet1");
		
		int trow= sheet.getLastRowNum()+1;
		int tcol=sheet.getRow(0).getLastCellNum();
		
		String [][]str=new String[trow][tcol];
		
		for(int i=0;i<trow;i++)
			for(int j=0;j<tcol;j++)
				str[i][j]=sheet.getRow(i).getCell(j).getStringCellValue();	
		System.out.println();
		return str;
	}
	
	public static void writeDataToXL(int row, int column, int statusColumn) throws IOException 
	{
		String dt_path=InitialDriver.path+"/framework/TestSuit.xls";
		FileInputStream fs = new FileInputStream (new File(dt_path));
		
		HSSFWorkbook wb= new HSSFWorkbook(fs);
		HSSFSheet sheet=wb.getSheet("Sheet1");
		
		HSSFCellStyle csR=wb.createCellStyle();
		csR.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		csR.setFillForegroundColor(IndexedColors.RED.index);
		
		
		HSSFCellStyle csG=wb.createCellStyle();
		csG.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		csG.setFillForegroundColor(IndexedColors.GREEN.index);
		
		//csR.setFillPattern(HSSFCellStyle.);
		
		if(status.equalsIgnoreCase("PASS"))
			sheet.getRow(row).getCell(column).setCellStyle(csG);
		if(status.equalsIgnoreCase("FAIL"))
		{
			sheet.getRow(row).getCell(column).setCellStyle(csR);
			String msg=sheet.getRow(row).getCell(statusColumn).getStringCellValue();
			msg=msg+" , "+FailMsg;
			sheet.getRow(row).getCell(statusColumn).setCellValue(msg);
		}
		FileOutputStream Fw= new FileOutputStream(new File(dt_path));
		wb.write(Fw);
		Fw.close();
	}
	
	/*Method Name: enterText
	 * Method Description: Enter a text into Text boxes
	 * Arguments: WebElement object, textValue-->text to enter into it, objectName-->text entered in?
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Modified date: March 9 2018
	 * Modified by: Tahmina
	 * */
	public static void enterText(WebElement object, String textValue, String objectName)
	{
		if(object.isDisplayed())
		{
			object.sendKeys(textValue);
			String logMsg="Pass: "+ textValue +" entered in the " + objectName + " field";
			Reporting.logger.log(Status.INFO, logMsg);
		}
		else
		{
			logMsg="Fail: " +  objectName + " is not Displayed. Please check your application ";
			FailMsg=logMsg;
			Reporting.logger.log(Status.FAIL,MarkupHelper.createLabel(logMsg,ExtentColor.RED));
			status="FAIL";
		}
	}
	
	/*Method Name: clearText
	 * Method Description: Clear the Text field
	 * Arguments: WebElement object, String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void clearText(WebElement object, String objectName)
	{
		if(object.isDisplayed())
		{
			object.clear();
			logMsg="Pass: " + objectName + " is cleared.";
			Reporting.logger.log(Status.INFO, logMsg);
		}
		else
		{
			logMsg="Fail: " + objectName + " is not displayed. Check your application";
			FailMsg=logMsg;
			Reporting.logger.log(Status.FAIL,MarkupHelper.createLabel(logMsg,ExtentColor.RED));
			status="FAIL";
		}
	}
	
	/*Method Name: performClick
	 * Method Description: Click on the specified web Element
	 * Arguments: WebElement object, String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void performClick(WebElement object, String objectName)
	{
		
		if(object.isDisplayed())
		{
			object.click();
			logMsg="Pass: " + objectName + " is Clicked ";
			Reporting.logger.log(Status.INFO, logMsg);
		}
		else
		{
			logMsg="Fail: " + objectName + " is not displayed. Check your application";
			FailMsg=logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
			status="FAIL";
		}
	}
	
	/*Method Name: validateTextContent
	 * Method Description: Check if the text displayed on that WebElement contains what we want it to
	 * Arguments: object is WebElement , checkWith--> Requiered text in the text box String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void validateTextContent(WebElement object, String requieredText, String objectName)
	{
		if(object.isDisplayed())
		{
			if( (object.getText() ).equals(requieredText))
			{
				logMsg="Pass: " + objectName + " displays " + requieredText;
				Reporting.logger.log(Status.PASS, MarkupHelper.createLabel(logMsg, ExtentColor.GREEN));
			}
			else
			{
				logMsg="Fail: " + objectName + " displays not matching with " + requieredText;
				status="FAIL";
				FailMsg=logMsg;
				Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
			}
		}
		else
		{
			logMsg="Fail: " + objectName + " is not displayed. Check your application";
			status="FAIL";
			FailMsg=logMsg;
			Reporting.logger.log(Status.FAIL, MarkupHelper.createLabel(logMsg, ExtentColor.RED));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static WebDriver validateTitle( WebDriver driver,String check)
	{
		String Title=driver.getTitle();
		if(Title.contains(check))
		{
			Reporting.logger.log(Status.PASS, "Pass: We are on the page we wanted " + check);
		}
		else
			System.out.println("Fail: We are on " + Title +" instead of " + check);
		return driver;
	}
	
	/*Method Name: getText
	 * Method Description: Returns the text in the web element
	 * Arguments: object is WebElement , String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static String getTextBox(WebElement object, String objectName)
	{
		if(object.isDisplayed())
		{
			System.out.println("Pass: "+ objectName+ " displays" + object.getAttribute("value"));
			return object.getAttribute("value");
		}	
		else
		{
			System.out.println("Fail: "+ objectName+ " is not displayed. Check your application");
			return null;
		}
	}
	
	
	
	
	
	
	

	/*Method Name: selectCheckbox
	 * Method Description: Check the specified Check box element
	 * Arguments: WebElement object, String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified by: Tahmina
	 * Last Modified date: March 9 2018
	 * */
	public static void selectCheckbox(WebElement object, String objectName){
		if(object.isDisplayed())
		{
			if( object.isSelected() )
				System.out.println("Pass: "+ objectName + "was Already Checked");
			else
			{
				object.click();
				System.out.println("Pass: " + objectName + " is Checked ");
			}
			
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
		
	}
	
	/*Method Name: deSelectCheckbox
	 * Method Description: UnCheck the specified check box element
	 * Arguments: WebElement object, String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified by: Tahmina
	 * Last Modified date: March 9 2018
	 * */
	public static void deSelectCheckbox(WebElement object, String objectName){
		if(object.isDisplayed())
		{
			if( object.isSelected() ){
				object.click();
				System.out.println("Pass: "+ objectName + " is Unchecked");
			}
			else
				System.out.println("Pass: " + objectName + " was already not checked ");
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
	}

	/*Method Name: validateTextboxContent
	 * Method Description: Check if the textBox contains what we want it to
	 * Arguments: object is WebElement , checkWith--> Requiered text in the text box String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void validateTextBoxContent(WebElement object, String requieredText, String objectName)
	{
		if(object.isDisplayed())
		{
			String input= object.getAttribute("value");
			if(input.equals(requieredText))
				System.out.println("Pass: " + objectName + " displays " + requieredText);
			else
				System.out.println("Fail: " + objectName + " displays " + input + " not matching with " + requieredText);
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
	}
	
	/*Method Name: validateText
	 * Method Description: Check if the text of the element is what should be there
	 * Arguments: object is WebElement , checkWith--> Requiered text in the text box String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void validateText(WebElement object, String requieredText, String objectName)
	{
		if(object.isDisplayed())
		{
			String input= object.getText();
//			System.out.println("Displayed Name :" + input +" of length "+input.length());
//			System.out.println("Requiered Name :" + requieredText +" of length "+requieredText.length());
			if(input.contains(requieredText))
				System.out.println("Pass: " + objectName + " displays " + requieredText);
			else
				System.out.println("Fail: " + objectName + " displays " + input+ " not matching with " + requieredText);
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
	}
	
	
	
	/*Method Name: validateTitleContent
	 * Method Description: Check if the Title of the element contains what we want it to
	 * Arguments: object is WebElement , checkWith--> Requiered text in the text box String objectName---> Name of the Object for reference
	 * Created By: Automation Team
	 * Creation date:March 9 2018
	 * Last Modified date: March 9 2018
	 * Last Modified by: Tahmina
	 * */
	public static void validateTitleContent(WebElement object, String requieredText, String objectName)
	{
		if(object.isDisplayed())
		{
			String input= object.getAttribute("title");
			if(input.equals(requieredText))
				System.out.println("Pass: " + objectName + " displays " + requieredText);
			else
				System.out.println("Fail: " + objectName + " displays " + input + " not matching with " + requieredText);
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
	}
	
	public static void uploadFile(WebElement object, String path, String objectName)
	{
		if(object.isDisplayed())
		{
			object.sendKeys(path);
			System.out.println("Pass: " + objectName + "  done ");
		}
		else
			System.out.println("Fail: " + objectName + " is not displayed. Check your application");
		
	}

	public static void validateSelectOptions(WebElement object, String[] reqTab, String objectName) 
	{
		if (object.isDisplayed())
		{
			Select select3=new Select(object);
			List<WebElement> options=select3.getOptions();
			for(WebElement opt:options)
			{
				for(int j=0;j<reqTab.length;j++)
				{
					if(opt.getText().equalsIgnoreCase(reqTab[j]))
					{
						System.out.println("Pass: "+ objectName +" displays "+ reqTab +" Tab");
						return;
					}
				}
				System.out.println("Fail: "+ objectName +" does not displays "+ reqTab +" Tab. Check your application." );
			}
		}
		else
			System.out.println("Fail: "+ objectName +" is not displayed . Check your application." );
	}
	
	public static void validateSelectOption(WebElement object, String reqTab, String objectName) 
	{
		if (object.isDisplayed())
		{
			Select select3=new Select(object);
			List<WebElement> options=select3.getOptions();
			for(WebElement opt:options)
			{
				if(opt.getText().equalsIgnoreCase(reqTab))
				{
					System.out.println("Pass: "+ objectName +" displays "+ reqTab +" Tab");
					return;
				}
			}
			System.out.println("Fail: "+ objectName +" does not displays "+ reqTab +" Tab. Check your application." );
		}
		else
			System.out.println("Fail: "+ objectName +" is not displayed . Check your application." );
	}
	
	public static void performSelectOptions(WebElement object, String reqTab, String objectName) 
	{
		if (object.isDisplayed())
		{
			Select select3=new Select(object);
			List<WebElement> options=select3.getOptions();
			for(WebElement opt:options)
			{
				if(opt.getText().equalsIgnoreCase(reqTab))
				{
					System.out.println("Pass: "+ objectName +" displays "+ reqTab +" Tab");
					select3.selectByVisibleText(reqTab);
					System.out.println("Pass: "+  reqTab +" is Selected");
					return;
				}
			}
			System.out.println("Fail: "+ objectName +" does not displays "+ reqTab +" Tab. Check your application." );
		}
		else
			System.out.println("Fail: "+ objectName +" is not displayed . Check your application." );
	}
	
	public static void validateTableContent(WebElement table, String reqColumn) 
	{
		List<WebElement> rows=table.findElements(By.tagName("tr"));
		for(int i=0;i<rows.size();i++)
		{
			List<WebElement> columns=rows.get(i).findElements(By.tagName("td"));
			for(int j=0;j<columns.size();j++)
			{
				if((columns.get(j).getText()).equalsIgnoreCase(reqColumn))
				{
					System.out.println("Pass: Columns Display "+columns.get(j).getText());
					return;
				}
			}
		}
		
	}
	

	
	public static void isDisplay(WebElement object, String option, String objectName) 
	{
		if(object.isDisplayed())
			System.out.println("Pass: " + objectName + " displays for your option " + option);
		else
			System.out.println("Fail: " + objectName + " doesnt display anything for your option " + option);
	}

	
}