package base;

import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class BasePageUtil {
    private static Logger log = LogManager.getRootLogger();
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePageUtil() {
        this.driver = DriverManager.getDriver();
        wait = new WebDriverWait(driver, 30);
    }

    public void click(WebElement element) throws Exception {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            waitForElementDisplayed(element, 10);
            sleep(500);
            element.click();
            sleep(500);
        } catch (StaleElementReferenceException s) {
            waitForElementDisplayed(element, 10);
            log.warn("StaleElementReferenceException....");
            log.trace("Trying to find an element again and click...");
            sleep(5000);
            wait.until(ExpectedConditions.visibilityOf(element));
            log.info("Clicking element again");
            element.click();
        }
    }

    public boolean waitForElementDisplayed(WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)) != null;
        } catch (Exception ex) {
            return wait.until(ExpectedConditions.visibilityOf(element)) != null;
        }
    }

    public void write(WebElement element, String text) throws Exception {
        try {
            element.sendKeys("");
            element.clear();
            sleep(500);
            element.sendKeys(text);
        } catch (StaleElementReferenceException s) {
            log.warn("StaleElementReferenceException....");
            log.trace("Trying to find an element again and set the same text.");
            log.info("Setting text : " + text);
            sleep(1500);
            element.sendKeys("");
            element.clear();
            sleep(500);
            element.sendKeys(text);
        }
    }

    public String getElementText(WebElement element) {
        String elementText = element.getText();
        log.info("Element text is : " + elementText);
        return elementText;
    }

    public void switchToNextTab() {
        log.info("Switching the focus to next tab");
        ArrayList<String> allTabs = new ArrayList<>(driver.getWindowHandles());
        try {
            driver.switchTo().window(allTabs.get(1));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void closeOpenedTab() {
        log.info("Closing the opened tab and return the focus to previous tab");
        ArrayList<String> allTabs = new ArrayList<>(driver.getWindowHandles());
        try {
            driver.close();
            driver.switchTo().window(allTabs.get(0));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}