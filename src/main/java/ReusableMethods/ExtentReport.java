package ReusableMethods;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReport {

	private ExtentReports ereports;
	private ExtentSparkReporter sparkreport;
	
	public ExtentReport(String reportname) {
		String dt = LocalDateTime.now().toString().substring(0, 19).replace(':', '-');
		ereports = new ExtentReports();
		sparkreport = new ExtentSparkReporter(".//Extentreport//"+reportname+"-"+dt+".html");
		ereports.attachReporter(sparkreport);
	}
	
	public ExtentTest createTest(String test,String author,String type) {
		
		ExtentTest report = ereports.createTest(test)
				.assignAuthor(author).assignCategory(type);
		return report;
	}
	
	public ExtentTest createTest(String test,String details) {
		
		ExtentTest report = ereports.createTest(test, details);
		return report;
	}
	
	public void testInfo(ExtentTest report, String details) {
		report.info(details);
	}
	
	public void testSkip(ExtentTest report, String details) {
		report.skip(details);
	}
	
	public void testWarning(ExtentTest report, String details) {
		report.warning(details);
	}
	
	public void testpass(ExtentTest report,String details) {
		report.pass(details);
	}
	
	public void testfail(WebDriver driver,ExtentTest report, String details,String imagename) throws IOException {
		
		String dt = LocalDateTime.now().toString().substring(0, 19).replace(':', '-');
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcfile = ts.getScreenshotAs(OutputType.FILE);
		File destfile = new File(".//Screenshot//"+ dt +"-"+ imagename +".png");
		FileUtils.copyFile(srcfile, destfile);
		
		String screenshot = new String(destfile.getAbsolutePath());
		report.fail(details, MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
	}
	
	public void flush() {
		ereports.flush();
	} 
	
	public void logresult(boolean result,ExtentTest report,String passmessage,WebDriver driver,String image ,String failmessage ) throws IOException {
		
		if(result) {
			testpass(report,passmessage);
		}
		else {
			testfail(driver,report,failmessage,image);
		}
		Assert.assertTrue(result, failmessage + "\t");
		System.out.println(passmessage);
	}
}
