package com.opencart.Utils;

import com.opencart.PageObjectModel.*;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ExecuteTests extends Baseclass {
	String TCName;
	public void execute_Test(String testcaseName) throws InterruptedException, IOException {
		TCName = testcaseName;
		initialize_browser();
		switch (TCName) {
		case "Login":
			new LoginPage(driver).login(driver);
			break;
		}
	}

}
