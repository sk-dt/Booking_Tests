package base;

import driver.DriverFactory;
import driver.DriverManager;
import manager.TestManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import resources.Browsers;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;


    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("CHROME") Browsers browser, Method method) throws IOException {
        TestManager.setTest(new Test(this.getClass().getName(), method.getName(), null, false));
        driver = DriverFactory.getNewDriver(browser);
        Properties prop = new Properties();
        prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties"));
        String webSiteUrl = prop.getProperty("webSiteUrl");
        driver.get(webSiteUrl);
    }

    public static void setHasFails(WebDriver driver, String message) {
        TestManager.getTest().setHasFails(true);
        TestManager.getTest().setErrorMessage(message);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (DriverManager.getDriver() != null) {
            driver.quit();
        }
        System.out.println("Test finished...");
    }

    protected final void throwIfVerificationFailed() throws UnLoggingException {
        if (TestManager.getTest().getHasFails()) {
            throw new UnLoggingException("Test has uncritical errors." + (TestManager.getTest().getErrorMessage() == null ? "" :
                    "\nVerify message is: " + TestManager.getTest().getErrorMessage()));
        }
    }
}

