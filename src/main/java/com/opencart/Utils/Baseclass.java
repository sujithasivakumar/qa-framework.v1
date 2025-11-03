package com.opencart.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.j2objc.annotations.Property;

public class Baseclass {
	public static WebDriver driver;
	public ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static Logger testLogger;
	public static ExtentTest test;
	String timestamp;
	static String report_path;
	public static int ss_counter = 0;
	public static Properties p;
	public static String operating_system;
	public static String br;
	
	@BeforeSuite	
	@Parameters({"OS","Browser"})
	public void initialize(String operating_system ,String br ) throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		timestamp = sdf.format(new Date());
		report_path = System.getProperty("user.dir") + "/Extent_Reports/" + timestamp;
		System.out.println(report_path);
		//File report_path_file = new File(report_path);
		//report_path_file.mkdirs();
	    this.operating_system=operating_system;
	    this.br=br;
		initialize_Log4J();
		initialize_extent_report();

	}
	

	
	
	public static void initialize_browser() throws IOException {
		testLogger.info("initialize_browser");
		FileReader file= new FileReader(System.getProperty("user.dir")+"/src/test/resources/config.properties");
		p=new Properties();
		p.load(file);
		DesiredCapabilities capablities=new DesiredCapabilities();
		testLogger.info(p.getProperty("Execution_env"));
		testLogger.info(br);
		if(p.getProperty("Execution_env").equalsIgnoreCase("remote"))
		{
			testLogger.info(operating_system);
			testLogger.info(p.getProperty("Execution_env"));
			if(operating_system.equalsIgnoreCase("Windows"))
				capablities.setPlatform(Platform.WIN11);
			else if(operating_system.equalsIgnoreCase("MAC"))
				capablities.setPlatform(Platform.MAC);
			else
				testLogger.info("No such Browser!");
			
			//browser
			switch(br)
			{
			case "chrome":
			capablities.setBrowserName("chrome");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driver = new RemoteWebDriver(new URL("http://localhost:4444/"),options);
			break;
			case "edge": capablities.setBrowserName("MicrosoftEdge");
			break;
			}
			
		}
		else
		{	
		    testLogger.info("hi");
			switch(br.toLowerCase())
			{
			case "chrome":
						driver = new ChromeDriver();
				break;
			case "edge": 
		
				driver = new EdgeDriver();
				break;
			case "firefox":
			
				driver=new FirefoxDriver();
				break;
			}
		}
			
		driver.get("http://localhost//opencart/upload/index.php");
		driver.manage().window().maximize();		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		}

		
	
	

	public void initialize_extent_report() throws UnknownHostException {

		testLogger.info("Initiating extent-Report");
		spark = new ExtentSparkReporter(report_path + "/ExtentReport.html");
		extent = new ExtentReports();	
		extent.attachReporter(spark);	
		spark.config().setReportName("Automation Report");
		spark.config().setTheme(Theme.DARK);
		spark.config().setTimeStampFormat("dd/MM/yyyy hh;mm;ss a");
	
		try {
			InetAddress iAddress = InetAddress.getLocalHost();
			extent.setSystemInfo("Host Name", iAddress.getHostName());
			extent.setSystemInfo("Host Name", iAddress.getHostAddress());
		} catch (Exception e) {
			Baseclass.testLogger.info(e);
		}
		extent.flush();
		testLogger.info(" Extent-Report initiated");

	}

	public void initialize_Log4J() {
		String currentdir = System.getProperty("user.dir");
		System.out.println(currentdir);

		try {
			System.setProperty("log4j.configurationFile",
					System.getProperty("user.dir") + "/src/main/resources/log4j2.properties");

			System.setProperty("log4jFilename", timestamp);

			System.out.println("LOG4J FILENAME = " + System.getProperty("log4jFilename"));

			// get logger AFTER setting property
			testLogger = LogManager.getLogger(Baseclass.class);
			Baseclass.testLogger.info("Created Logger");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void Report(Status TestStatus, String ActualResult, String ExpectedResult, Boolean screenshotFlag)
			throws IOException {

		String path = "";
		if (screenshotFlag) {
			path = capture_screenshot();
			test.log(TestStatus, ExpectedResult + " " + ActualResult + " ").addScreenCaptureFromPath(path);
		} else {
			test.log(TestStatus, ExpectedResult + " " + ActualResult + " ");
		}
		extent.flush();

	}

	public static String capture_screenshot() {
		Baseclass.testLogger.info("taking ss");
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File ss_folder = new File(report_path + "/Screenshots");
		ss_folder.mkdir();
		File destn = new File(report_path + "/Screenshots/" + "ss" + (++ss_counter) + ".png");
		try {
			FileHandler.copy(src, destn);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String path = report_path + "/Screenshots/" + "ss" + (ss_counter) + ".png";
		return path;
	}

	@AfterSuite
	public void teardown() {
		// driver.close();
	}
}
