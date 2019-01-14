package Booking;

import base.BasePageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HotelPage extends BasePageUtil {
    private final Logger log = LogManager.getRootLogger();
    private String CSS_NUMBER_OF_AVAIABLE_ROOMS = "select.hprt-nos-select option[value='%s']";

    public HotelPage() {
        PageFactory.initElements(driver, this);
    }

    public boolean checkRooms(Integer numberOfAvailableRooms) {
        log.info("Searching for the required number of rooms in this hotel");
        boolean toReturn = false;
        try {
            toReturn = driver.findElement(By.cssSelector(String.format(CSS_NUMBER_OF_AVAIABLE_ROOMS, numberOfAvailableRooms))).isDisplayed();
        } catch (Exception ex) {
            log.info("This hotel does not have the required number of rooms");
        }
        return toReturn;
    }

}
