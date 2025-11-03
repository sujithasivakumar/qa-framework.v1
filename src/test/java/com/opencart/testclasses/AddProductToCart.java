package com.opencart.testclasses;
import com.opencart.Utils.*;
import org.testng.annotations.Test;

import com.opencart.PageObjectModel.LoginPage;
import com.opencart.Utils.Baseclass;
import com.opencart.Utils.ExecuteTests;

import java.io.IOException;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class AddProductToCart extends Baseclass {
	
	@BeforeTest
	public void CreateTestInReport()
	{
		Baseclass.test=extent.createTest("Login Test");
	}
	
	@Test
	public void add_ProductToCart() throws InterruptedException, IOException {
		
		testLogger.info("gng to start tc1");
		new ExecuteTests().execute_Test("Login");
		    
	}

}
