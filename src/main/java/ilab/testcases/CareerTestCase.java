package ilab.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import ilab.utility.BrowserFactory;
import ilab.utility.ConfigData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class CareerTestCase {
    public static WebDriver driver;
    ConfigData configData;
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    @BeforeClass
    public void setup(){
        configData = new ConfigData();
       driver = BrowserFactory.launchWebBrowser(driver, configData.getBrowser(), configData.getURL());

    }

    @BeforeTest
    public void ReportSetup(){
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"./Report/iLabReport.html");

        sparkReporter.config().setDocumentTitle("iLab Career Automation Report");
        sparkReporter.config().setReportName("Functional Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent=new ExtentReports();

        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("Hostname", "LocalHost");
        extent.setSystemInfo("OS", "Windows10");
        extent.setSystemInfo("Tester Name", "Pfariso");
      //  extent.setSystemInfo("Browser", configData.getBrowser());
    }

    @Test(priority = 3)
    public void clickApplyOnline() throws InterruptedException {
       Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement webElement = driver.findElement(By.linkText("Apply Online"));
        js.executeScript("arguments[0].scrollIntoView();",webElement);
        driver.findElement(By.linkText("Apply Online")).click();


    }
    @Test(priority = 1)
    public void clickLatestPost() throws InterruptedException {
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[2]/div[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/a[1]")).click();

    }
    @Test(priority = 0)
    public void clickCountryLink() throws InterruptedException {
        Thread.sleep(3000);
        test =  extent.createTest("clickCountryLink");
        WebElement element = driver.findElement(By.linkText("South Africa"));
        element.click();
    }
    @Test(priority = 4)
    public void firstNameAndEmail(){
        test =  extent.createTest("firstNameAndEmail");
        driver.findElement(By.id("applicant_name")).sendKeys("Pfariso");
        driver.findElement(By.id("email")).sendKeys("autoAssessment@iLABQuality.com");

    }
    @Test(priority = 5)
    public void phoneNumberGenerated(){
        Random randGen = new Random();
        String c = "0";
        for (int x=0; x<9; x++){
            c = c+randGen.nextInt(9);
        }
        test =  extent.createTest("phoneNumberGenerated");
        driver.findElement(By.id("phone")).sendKeys(c);

    }
    @Test(priority = 6)
    public void clickSubmitButton() throws InterruptedException {
        Thread.sleep(3000);
        test =  extent.createTest("clickSubmitButton");
        driver.findElement(By.id("wpjb_submit")).click();
    }
    @Test(priority = 7)
    public void validateUploadMessage() throws InterruptedException {
        Thread.sleep(3000);
        test =  extent.createTest("validateUploadMessage");
        Assert.assertTrue(driver.findElement(By.xpath("//li[contains(text(),'You need to upload at least one file.')]")).isDisplayed());

    }

    @AfterTest
    public void endReportSetup(){
        extent.flush();
    }

    @AfterMethod
    public void reportScreenshots(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL, "Test Case Failed is "+ result.getName());
            test.log(Status.FAIL, "Test Case Failed is "+ result.getThrowable());

            String screenshotPath =  CareerTestCase.getScreenshot(driver, result.getName());
            test.log(Status.FAIL,screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);

        }else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP, "Test case Skipped " + result.getName());

        }else if(result.getStatus() == ITestResult.SUCCESS){
            String screenshotPath =  CareerTestCase.getScreenshot(driver, result.getName());
            test.log(Status.PASS,screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath);
            test.log(Status.PASS, "Test Case Passed Is " + result.getName());

        }
    }
    @AfterClass
    public void quit(){
        BrowserFactory.quitBrowser(driver);
    }

    public void scrollToElement(WebElement webElement) throws Exception {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoViewIfNeeded()", webElement);
        Thread.sleep(3000);
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
        String dateName  = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File file = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(file, finalDestination);
        return destination;
    }
}
