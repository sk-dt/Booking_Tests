package Booking;

import base.BaseTest;
import org.testng.annotations.Test;
import resources.TestData;
import verification.Verify;

import java.util.List;

public class BookingTest extends BaseTest {
    @Test
    public void CheckAvailableRooms() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.setLocationToTravel(TestData.PLACE_TO_TRAVEL);
        mainPage.selectTravelDate();
        mainPage.selectNumberOfAdultGuests(TestData.NUMBER_OF_ADULT_GUESTS);
        SearchResultPage resultPage = mainPage.clickSearchButton();
        resultPage.selectHotelInFilter();
        Verify.verifyTrue(resultPage.checkAvailableRoomsInPresentedHotels(TestData.NUMBER_OF_AVAILABLE_ROOMS), "Required number of rooms is available");

        throwIfVerificationFailed();
    }

    @Test
    public void CheckCurrency() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.clickLanguageButton().selectLanguage(TestData.RUSSIAN_LANGUAGE);
        mainPage.clickCurrencyButton();
        Verify.verifyTrue(mainPage.compareCurrency(mainPage.getAllMainCurrency(), TestData.ALL_CURRENCY), "The currencies are equal");
        mainPage.clickLanguageButton().selectLanguage(TestData.ENGLISH_LANGUAGE);
        mainPage.clickCurrencyButton();
        Verify.verifyTrue(mainPage.compareCurrency(mainPage.getAllMainCurrency(), TestData.ALL_CURRENCY_FOR_US_WITHOUT_BYR), "The currencies are equal");

        throwIfVerificationFailed();
    }

    @Test
    public void CheckLanguage() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.clickLanguageButton();
        String randomLanguage = mainPage.getRandomLanguage();
        mainPage.selectLanguage(randomLanguage);
        Verify.verifyTrue(mainPage.checkLanguageOnSite(randomLanguage), "Site language has been changed");

        throwIfVerificationFailed();
    }

    @Test
    public void CheckLowestPriceSorting() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.clickLanguageButton().selectLanguage(TestData.RUSSIAN_LANGUAGE);
        mainPage.clickCurrencyButton().selectCurrency(TestData.BYN_CURRENCY);
        mainPage.setLocationToTravel(TestData.PLACE_TO_TRAVEL);
        mainPage.selectTravelDate();
        mainPage.selectNumberOfAdultGuests(TestData.NUMBER_OF_ADULT_GUESTS);
        SearchResultPage resultPage = mainPage.clickSearchButton();
        resultPage.selectHotelInFilter().clickLowestPriceButton();
        List<Integer> allPricesFromPage = resultPage.getAllPricesFromPageWithoutCurrencyType(TestData.BYN_CURRENCY);
        Verify.verifyTrue(resultPage.checkSortingByLowestPrice(allPricesFromPage), "Prices are sorted correctly");

        throwIfVerificationFailed();
    }

    @Test
    public void CheckFilterByPriceRange() throws Exception {
        MainPage mainPage = new MainPage();
        mainPage.clickLanguageButton().selectLanguage(TestData.RUSSIAN_LANGUAGE);
        mainPage.clickCurrencyButton().selectCurrency(TestData.BYN_CURRENCY);
        mainPage.setLocationToTravel(TestData.PLACE_TO_TRAVEL);
        mainPage.selectTravelDate();
        mainPage.selectNumberOfAdultGuests(TestData.NUMBER_OF_ADULT_GUESTS);
        SearchResultPage resultPage = mainPage.clickSearchButton();
        resultPage.selectSecondPriceRange().clickLowestPriceButton();
        List<Integer> minPriceRangeFromFilter = resultPage.getMinAndMaxPricesFromFilter(TestData.BYN_CURRENCY);
        List<Integer> minPriceRangeFromPage = resultPage.getMinAndMaxPricesFromPage(TestData.BYN_CURRENCY);
        Verify.verifyTrue(resultPage.compareMinAndMaxPrices(minPriceRangeFromFilter, minPriceRangeFromPage), "The price filter works correctly");

        throwIfVerificationFailed();

    }
}
