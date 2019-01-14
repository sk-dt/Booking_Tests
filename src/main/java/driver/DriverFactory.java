package driver;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import resources.Browsers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    private static Logger logger = LogManager.getRootLogger();

    public static WebDriver getNewDriver(Browsers browser) throws IOException {
        logger.debug(String.format("Test started with Browser ::: '%s'\n", browser));
        Properties prop = new Properties();
        prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties"));
        Properties log4jProperties = new Properties();
        log4jProperties.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\log4j2.properties"));
        PropertyConfigurator.configure(log4jProperties);
        String driverLocation;
        WebDriver driver = null;
        switch (browser) {
            case CHROME:
                driverLocation = prop.getProperty("chrome.driver.location.windows");
                System.setProperty("webdriver.chrome.driver", driverLocation);
                ChromeOptions ChromeOptions = new ChromeOptions();
                ChromeOptions.addArguments("--start-maximized");
                ChromeOptions.addArguments("disable-infobars");
                ChromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
                ChromeOptions.addArguments("--disable-bundled-ppapi-flash");
                ChromeOptions.addArguments("-incognito");
                ChromeOptions.addArguments("--dns-prefetch-disable");
                ChromeOptions.addArguments("--lang=en_US.UTF-8");
                ChromeOptions.setCapability(org.openqa.selenium.chrome.ChromeOptions.CAPABILITY, ChromeOptions);
                driver = new ChromeDriver(ChromeOptions);
                break;
        }
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(240, TimeUnit.SECONDS);
        DriverManager.setWebDriver(driver);
        return driver;
    }
}
