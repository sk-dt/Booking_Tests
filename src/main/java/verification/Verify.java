package verification;

import base.BaseTest;
import driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class Verify {
    private static final Logger log = LogManager.getRootLogger();

    public static boolean verifyTrue(Boolean condition, String message) {
        try {
            Assert.assertTrue(condition);
            log.info(message);
            return true;
        } catch (AssertionError e) {
            log.info(message + "\n" + e.getMessage());
            BaseTest.setHasFails(DriverManager.getDriver(), message);
            return false;
        }
    }

    public static boolean verifyFalse(Boolean condition, String message) {
        try {
            Assert.assertFalse(condition);
            log.info(message);
            return true;
        } catch (AssertionError e) {
            log.info(message + "\n" + e.getMessage());
            BaseTest.setHasFails(DriverManager.getDriver(), message);
            return false;
        }
    }
}
