package Booking;

import base.BasePageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SearchResultPage extends BasePageUtil {
    private final Logger log = LogManager.getRootLogger();

    public SearchResultPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#filter_hoteltype div.filteroptions a:nth-child(2)")
    private WebElement hotelType;

    @FindBy(xpath = "//*[@id='hotellist_inner']//div[@class='sr-cta-button-row']//a")
    private List<WebElement> openHotelPage;

    @FindBy(css = "td.roomPrice strong.price")
    private List<WebElement> priveFields;

    @FindBy(css = "li.sort_price a.sort_option")
    private WebElement lowestPriceSortButton;

    @FindBy(xpath = "//ul[@class='bui-pagination__list']/li[last()]/a[@class='bui-pagination__link sr_pagination_link']")
    private WebElement lastElementInPagination;

    @FindBy(xpath = "//a[@data-id='pri-2']")
    private WebElement secondPriceRange;

    public SearchResultPage selectHotelInFilter() throws Exception {
        log.info("Selecting 'Hotel' in filter");
        click(hotelType);
        sleep(600);
        return new SearchResultPage();
    }

    public HotelPage clickOpenHotelPageButton(Integer hotelNumber) throws Exception {
        log.info("Opening hotel page");
        sleep(2000);
        click(openHotelPage.get(hotelNumber));
        sleep(2000);
        switchToNextTab();
        return new HotelPage();
    }

    public boolean checkAvailableRoomsInPresentedHotels(Integer numberOfAvailableRooms) throws Exception {
        log.info("Checking available rooms in hotel");
        int numberOfHotelsOnPage = openHotelPage.size();
        boolean toReturn = false;
        for (int i = 0; i < numberOfHotelsOnPage; i++) {
            HotelPage hotelPage = clickOpenHotelPageButton(i);
            if (hotelPage.checkRooms(numberOfAvailableRooms)) {
                toReturn = true;
                closeOpenedTab();
                break;
            } else {
                closeOpenedTab();
            }
        }
        return toReturn;
    }

    public SearchResultPage clickLowestPriceButton() throws Exception {
        log.info("Clicking on [Lowest Price] button");
        click(lowestPriceSortButton);
        sleep(600);
        return new SearchResultPage();
    }

    private Integer getPriceWithoutCurrency(String priceWithCurrency, String currency) {
        String price = priceWithCurrency.replace(currency, "").replace("\\,", "").replace("\\.", "").replaceAll("\u00a0", "").trim();
        return Integer.parseInt(price);
    }

    public List<Integer> getAllPricesFromPageWithoutCurrencyType(String currency) throws Exception {
        log.info("Getting prices without currency type");
        List<Integer> listToReturn = new ArrayList<>();
        sleep(600);
        for (WebElement priceField : priveFields) {
            listToReturn.add(getPriceWithoutCurrency(priceField.getText(), currency));
        }
        return listToReturn;
    }

    public boolean checkSortingByLowestPrice(List<Integer> prices) {
        log.info("Check sorting by lowest price");
        boolean toReturn = true;
        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i - 1) > prices.get(i)) {
                toReturn = false;
                break;
            }
        }
        return toReturn;
    }

    public SearchResultPage selectSecondPriceRange() throws Exception {
        log.info("Selecting second filter by Price Range");
        click(secondPriceRange);
        sleep(600);
        return new SearchResultPage();
    }

    public SearchResultPage clickOnLastPageOnPagination() throws Exception {
        log.info("Clicking on last page on pagination");
        click(lastElementInPagination);
        sleep(600);
        return new SearchResultPage();
    }

    public List<Integer> getMinAndMaxPricesFromFilter(String currency) {
        log.info("Getting values for Min and Max price from filter");
        List<Integer> priceRange = new ArrayList<>();
        String valueFromFilter = secondPriceRange.getText().split(" - ")[0];
        priceRange.add(Integer.valueOf(valueFromFilter.replace(currency, "").replaceAll("\u00a0", "").trim()));
        priceRange.add(Integer.valueOf(secondPriceRange.getAttribute("data-value")));
        return priceRange;
    }

    public List<Integer> getMinAndMaxPricesFromPage(String currency) throws Exception {
        log.info("Getting values for Min and Max price from page");
        List<Integer> priceRange = new ArrayList<>();
        priceRange.add(getPriceWithoutCurrency(priveFields.get(0).getText(), currency));
        clickOnLastPageOnPagination();
        priceRange.add(getPriceWithoutCurrency(priveFields.get(priveFields.size() - 1).getText(), currency));
        return priceRange;
    }

    public boolean compareMinAndMaxPrices(List<Integer> priceRangeFromFilter, List<Integer> priceRangeFromPage) {
        log.info("Compare Min and Max prices");
        boolean minPrice = priceRangeFromFilter.get(0) <= (priceRangeFromPage.get(0));
        if (!minPrice) {
            log.info(String.format("Min price from filter is - %s, and Min price from page is - %s", priceRangeFromFilter.get(0), priceRangeFromPage.get(0)));
        } else {
            log.info("Min price is correct: " + minPrice);
        }
        boolean maxPrice = priceRangeFromFilter.get(1) >= priceRangeFromPage.get(1);
        if (!maxPrice) {
            log.info(String.format("Max price from filter is - %s, and Max price from page is - %s", priceRangeFromFilter.get(1), priceRangeFromPage.get(1)));
        } else {
            log.info("Max price is correct: " + maxPrice);
        }
        return minPrice & maxPrice;
    }
}
