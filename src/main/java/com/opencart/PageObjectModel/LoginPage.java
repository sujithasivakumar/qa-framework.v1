package com.opencart.PageObjectModel;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.opencart.Utils.Baseclass;
import com.opencart.Utils.SeleniumFunctions;

public class LoginPage {

	public WebDriver driver;
	@FindBy(xpath = "//span[text()='My Account']")
	WebElement drpdwn_Account;
	@FindBy(xpath = "//a[text()='Login']")
	WebElement login_link;

	@FindBy(xpath = "//input[@name='email']")
	WebElement email;
	@FindBy(xpath = "//input[@name='password']")
	WebElement password;

	@FindBy(xpath = "//*[@class='btn btn-primary' and text()='Login']")
	WebElement Loginbtn;

	@FindBy(xpath = "//*[@id='content']/h2[1]")
	WebElement account_after_login;

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public  void login(WebDriver driver) throws InterruptedException, IOException
	{
		Baseclass.testLogger.info("gng to click login");
		 WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(180));
		 String email_id="sujithasivakumar18@gmail.com";
		 String pwd="Admin@1234";
			 
			 SeleniumFunctions.click_element(drpdwn_Account);
			 SeleniumFunctions.click_element(login_link);
			 email.sendKeys(email_id);	
			 password.sendKeys(pwd);
			
		  WebElement element = driver.findElement(By.xpath("//*[@class='btn btn-primary' and text()='Login']"));
		  Actions actions = new Actions(driver);
		  actions.moveToElement(element).click().build().perform();
		  Thread.sleep(6000);
		  try {
				 if(account_after_login.isDisplayed())	
				 {
					 Baseclass.Report(Status.PASS,"Login Success","",true);
					 Baseclass.testLogger.info("Login success");
				 }
			        }
				 catch(Exception e)
				 {
				 Baseclass.Report(Status.FAIL,"Login Failed. Some Error Occured! "+e
					 		+ "Please check the attached Screenshot","",true);
				 Baseclass.testLogger.info("Login Failed. Some Error Occured!"+e);

				 }
 		 
		
	}
}

		
	
