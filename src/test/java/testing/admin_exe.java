package testing;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ReusableMethods.ExtentReport;
import pages.login_admin;

public class admin_exe extends login_admin{
	
	ExtentReport e = new ExtentReport("Browser Open");
	ExtentTest report;
	boolean result ;
	public static Logger log = LogManager.getLogger(login_admin.class);
	
	@BeforeClass
	public void launch() throws IOException {
		report=e.createTest("Verify Open browser","Anusha","Smoke");
		driver=launchurl();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		result = driver != null;
		e.logresult(result, report, "Browser Open Sucessfully", driver,"browser", "Unable to open browser");
	}
	
	@Test(priority =1)
	public void credentials() {
		report = e.createTest("Verify credentials","Anusha","Smoke");
		usernameenter("mocks");
		passwordenter("mocks@123");
	}

	@Test(priority =2)
	public void button() throws IOException {
		report = e.createTest("login successfully", "Anusha", "Smoke");
		submit();
		Boolean verifyTitle = driver.getTitle().equalsIgnoreCase("MOCKS | Dashboard");
		assertTrue(verifyTitle);	
		e.logresult(result, report, "Login Successfully", driver,"Login", "Unable to login with credentials");
	}
	
	@AfterMethod
	 public void getResult(ITestResult result) throws Exception {
	        if(result.getStatus() == ITestResult.FAILURE) {
	            report.log(Status.FAIL,result.getThrowable());
	           e.testfail(driver, report,"Login Failed", "login");
	        }
	        else if(result.getStatus() == ITestResult.SUCCESS) {
	        	report.log(Status.PASS, result.getTestName());
	        }
	        else {
	        	report.log(Status.SKIP, result.getTestName());
	        }
	    }
	
	
	@AfterTest
	public void endReport()
	{
		e.flush();		
	}
}
