package testframework.TestComponenets;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import testframework.resources.ExtentReporterNG;
 
public class Listeners extends BaseTest implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	//ThreadLocal will make 'test' thread safe. it means no matter if you run concurrently each object creation
	//have it's own thread. So it won't interrupt the other overriding variable.
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();//Create this line to resolve parallel run test issue
	
	@Override
	public void onTestStart(ITestResult result)
	{
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);//It will assign one unique thread ID(ErrorValidationTest) -- Test
	}
	@Override
	public void onTestSuccess(ITestResult result)
	{
		extentTest.get().log(Status.PASS, "Test Passed");
	}
	@Override
	public void onTestFailure(ITestResult result)
	{
		//test.log(Status.FAIL, "Test Failed");	
		extentTest.get().fail(result.getThrowable());
		 
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//Take Screenshot, Attach to the report
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}
	
	@Override		
    public void onTestSkipped(ITestResult result) {					
        // TODO Auto-generated method stub				
        		
    }	
	
	@Override		
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) 
	{					
	        // TODO Auto-generated method stub				
	        		
	}		
	 
	@Override		
    public void onStart(ITestContext context) 
	{					
        // TODO Auto-generated method stub				
        		
    }
	@Override		
    public void onFinish(ITestContext context) 
	{					
        // TODO Auto-generated method stub				
        extent.flush();
    }
}
