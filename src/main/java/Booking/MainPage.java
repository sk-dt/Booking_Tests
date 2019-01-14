package Booking;

import base.BasePageUtil;
import helpers.TestDataHelpers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import resources.TestData;
import utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainPage extends BasePageUtil {
    private final Logger log = LogManager.getRootLogger();
    private String XPATH_DATE_CELL_IN_CALENDAR = "//td[@data-date='%s']";
    private String CSS_SELECT_LANGUAGE = "li.lang_%s";
    private String CSS_SELECT_CURRENCY = "li.currency_%s";

    public MainPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input#ss")
    private WebElement placesField;

    @FindBy(css = "div.xp__dates-inner.xp__dates__checkin button.sb-date-field__icon")
    private WebElement checkinDate;

    @FindBy(css = "div.xp__dates-inner.xp__dates__checkout button.sb-date-field__icon")
    private WebElement checkoutDate;

    @FindBy(css = "label#xp__guests__toggle")
    private WebElement guestToggle;

    @FindBy(css = "div.sb-group__field-adults button.bui-stepper__subtract-button")
    private WebElement subtractAdultButton;

    @FindBy(css = "div.sb-group__field-adults button.bui-stepper__add-button")
    private WebElement addAdultButton;

    @FindBy(css = "div.sb-group__field-adults span.bui-stepper__display")
    private WebElement currentAdultCount;

    @FindBy(css = "div.sb-searchbox-submit-col button.sb-searchbox__button")
    private WebElement searchButton;

    @FindBy(css = "div#user_form li.user_center_option.uc_currency.js-uc-currency a")
    private WebElement currencyButton;

    @FindBy(css = "div#currency_dropdown_all li")
    private List<WebElement> currency;

    @FindBy(css = "li.user_center_option.uc_language.js-uc-language")
    private WebElement languageButton;

    @FindBy(xpath = "//li[contains(@class, 'lang')]")
    private List<WebElement> allLanguages;

    public MainPage setLocationToTravel(String location) throws Exception {
        log.info("Typing the location: " + location);
        write(placesField, location);
        sleep(600);
        return new MainPage();
    }

    private MainPage selectDate(String date) throws Exception {
        WebElement dateInCalendar = driver.findElement(By.xpath(String.format(XPATH_DATE_CELL_IN_CALENDAR, date)));
        click(dateInCalendar);
        sleep(600);
        return new MainPage();
    }

    public MainPage selectTravelDate() throws Exception {
        clickCheckinDateButton();
        Date firstWeekendDay = DateUtil.getFirstWeekendDay();
        Date secondWeekendDay = DateUtil.getDatePlusDays(firstWeekendDay, 1);
        log.info("Selecting check-in date: " + DateUtil.convertDateToShortFormat(firstWeekendDay));
        selectDate(DateUtil.convertDateToShortFormat(firstWeekendDay));
        log.info("Selecting check-out date: " + DateUtil.convertDateToShortFormat(secondWeekendDay));
        selectDate(DateUtil.convertDateToShortFormat(secondWeekendDay));
        return new MainPage();
    }

    private MainPage clickCheckinDateButton() throws Exception {
        log.info("Clicking on date-picker");
        click(checkinDate);
        sleep(600);
        return new MainPage();
    }

    private MainPage clickGuestsButton() throws Exception {
        log.info("Selection number of guests");
        click(guestToggle);
        sleep(600);
        return new MainPage();
    }

    private MainPage clickAddAdultGuest() throws Exception {
        log.info("Adding one guest");
        click(addAdultButton);
        sleep(600);
        return new MainPage();
    }

    private MainPage clickSubtractAdultGuest() throws Exception {
        log.info("Subtracting one guest");
        click(subtractAdultButton);
        sleep(600);
        return new MainPage();
    }

    private Integer getCurrentAdultGuests() {
        log.info("Getting current number of adult guests");
        return Integer.parseInt(getElementText(currentAdultCount));
    }

    public MainPage selectNumberOfAdultGuests(Integer numberOfGuests) throws Exception {
        log.info("Selecting number of guests");
        clickGuestsButton();
        while (!getCurrentAdultGuests().equals(numberOfGuests)) {
            if (getCurrentAdultGuests() < numberOfGuests) {
                clickAddAdultGuest();
            } else {
                clickSubtractAdultGuest();
            }
        }
        return new MainPage();
    }

    public SearchResultPage clickSearchButton() throws Exception {
        log.info("Clicking on [Search] button");
        click(searchButton);
        sleep(600);
        return new SearchResultPage();
    }

    public MainPage clickCurrencyButton() throws Exception {
        log.info("Clicking on currency button");
        click(currencyButton);
        sleep(600);
        return new MainPage();
    }

    public MainPage selectCurrency(String currency) throws Exception {
        log.info(String.format("Selection [%s] currency", currency));
        WebElement webElement = driver.findElement(By.cssSelector(String.format(CSS_SELECT_CURRENCY, currency)));
        if (!webElement.getAttribute("class").contains(TestData.PART_OF_CSS_LOCATOR_FOR_CURRENCY)) {
            click(webElement);
        } else {
            log.info(String.format("The currency [%s] is already selected", currency));
            clickCurrencyButton();
        }
        sleep(600);
        return new MainPage();
    }

    public List<String> getAllMainCurrency() {
        log.info("Getting main currency from page");
        List<String> currencyList = new ArrayList<>();
        for (WebElement currencyFromPage : currency) {
            currencyList.add(currencyFromPage.getAttribute("data-lang"));
        }
        return currencyList;
    }

    public boolean compareCurrency(List<String> currencyFromPage, String[] currency) {
        if (currencyFromPage.size() != currency.length) {
            log.info("The number of expected and actual currencies is not equal");
            return false;
        } else {
            for (String expectedCurrency : currency) {
                if (!currencyFromPage.contains(expectedCurrency)) {
                    log.info(String.format("The currency [%s] is not presented on the page.", expectedCurrency));
                    return false;
                }
            }
        }
        return true;
    }

    public MainPage clickLanguageButton() throws Exception {
        log.info("Clicking on language button");
        click(languageButton);
        sleep(600);
        return new MainPage();
    }

    public MainPage selectLanguage(String language) throws Exception {
        log.info(String.format("Selection [%s] language", language));
        click(driver.findElement(By.cssSelector(String.format(CSS_SELECT_LANGUAGE, language))));
        sleep(600);
        return new MainPage();
    }

    public String getRandomLanguage() {
        int randomNumber = TestDataHelpers.getRandomNumber(1, allLanguages.size());
        String randomLanguage = allLanguages.get(randomNumber).getAttribute("data-lang");
        log.info("Random language is: " + randomLanguage);
        return randomLanguage;
    }

    public boolean checkLanguageOnSite(String language) {
        log.info("Checking of site language");
        String currentUrl = driver.getCurrentUrl().split("\\?")[0];
        String expectedUrl = String.format(TestData.BOOKING_URL, language.equals(TestData.ENGLISH_LANGUAGE) ? "" : "." + language);
        return currentUrl.equals(expectedUrl);
    }
}


