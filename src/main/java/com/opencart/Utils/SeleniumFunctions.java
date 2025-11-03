package com.opencart.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SeleniumFunctions {
	
	public static void select_drpdown(WebDriver driver,WebElement element,String visibletext)
	{
		try {
		Select select = new Select(element);
		select.selectByVisibleText(visibletext);

		}
		catch(Exception e)
		{
		    Baseclass.testLogger.info(e);
	    }

   }
	public static void click_element(WebElement element)
	{
		try {
			
			element.click();
		}
		catch(Exception e)
		{
			Baseclass.testLogger.info(e);
		}
	}
	
}
