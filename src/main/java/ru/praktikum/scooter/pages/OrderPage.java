package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Страница оформления заказа: форма «Для кого самокат», форма «Про аренду» и модальные окна.
 */
public class OrderPage extends BasePage {

    // ---------- Форма «Для кого самокат» ----------
    // Заголовок формы
    private final By customerFormHeader = By.xpath("//div[starts-with(@class, 'Order_Header') and text()='Для кого самокат']");
    // Поле «Имя»
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    // Поле «Фамилия»
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    // Поле «Адрес: куда привезти заказ»
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле «Станция метро»
    private final By metroInput = By.xpath("//input[@placeholder='* Станция метро']");
    // Пункт выпадающего списка станций метро по названию станции
    private static final String METRO_OPTION = "//div[@class='select-search__select']//div[text()='%s']";
    // Поле «Телефон: на него позвонит курьер»
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка «Далее»
    private final By nextButton = By.xpath("//button[text()='Далее']");
    // Сообщение об ошибке под полем ввода (по плейсхолдеру поля), когда оно показано
    private static final String INPUT_ERROR = "//input[@placeholder='%s']/following-sibling::div[contains(@class, 'Input_ErrorMessage') and contains(@class, 'Input_Visible')]";
    // Сообщение об ошибке под полем «Станция метро»
    private final By metroError = By.xpath("//div[starts-with(@class, 'Order_MetroError')]");

    // ---------- Форма «Про аренду» ----------
    // Заголовок формы
    private final By rentFormHeader = By.xpath("//div[starts-with(@class, 'Order_Header') and text()='Про аренду']");
    // Поле «Когда привезти самокат»
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    // Поле «Срок аренды»
    private final By rentalPeriodDropdown = By.className("Dropdown-control");
    // Пункт выпадающего списка «Срок аренды» по тексту
    private static final String RENTAL_PERIOD_OPTION = "//div[@class='Dropdown-option' and text()='%s']";
    // Чекбокс цвета самоката по id: black — «чёрный жемчуг», grey — «серая безысходность»
    // Поле «Комментарий для курьера»
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    // Кнопка «Заказать» под формой
    private final By orderButton = By.xpath("//div[starts-with(@class, 'Order_Buttons')]/button[text()='Заказать']");

    // ---------- Модальные окна ----------
    // Кнопка «Да» в окне «Хотите оформить заказ?»
    private final By confirmYesButton = By.xpath("//div[starts-with(@class, 'Order_Modal')]//button[text()='Да']");
    // Заголовок модального окна («Хотите оформить заказ?» / «Заказ оформлен»)
    private final By modalHeader = By.xpath("//div[starts-with(@class, 'Order_ModalHeader')]");

    public static final String NAME_PLACEHOLDER = "* Имя";
    public static final String SURNAME_PLACEHOLDER = "* Фамилия";
    public static final String ADDRESS_PLACEHOLDER = "* Адрес: куда привезти заказ";
    public static final String PHONE_PLACEHOLDER = "* Телефон: на него позвонит курьер";

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public OrderPage open() {
        driver.get(BASE_URL + "order");
        acceptCookies();
        return this;
    }

    public boolean isCustomerFormOpened() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(customerFormHeader)).isDisplayed();
    }

    public OrderPage setName(String name) {
        type(nameInput, name);
        return this;
    }

    public OrderPage setSurname(String surname) {
        type(surnameInput, surname);
        return this;
    }

    public OrderPage setAddress(String address) {
        type(addressInput, address);
        return this;
    }

    public OrderPage selectMetroStation(String station) {
        type(metroInput, station);
        click(By.xpath(String.format(METRO_OPTION, station)));
        return this;
    }

    public OrderPage setPhone(String phone) {
        type(phoneInput, phone);
        return this;
    }

    public OrderPage clickNext() {
        click(nextButton);
        return this;
    }

    public OrderPage fillCustomerForm(String name, String surname, String address, String station, String phone) {
        setName(name);
        setSurname(surname);
        setAddress(address);
        selectMetroStation(station);
        setPhone(phone);
        return clickNext();
    }

    public String getInputError(String placeholder) {
        By error = By.xpath(String.format(INPUT_ERROR, placeholder));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(error)).getText();
    }

    public String getMetroError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(metroError)).getText();
    }

    public boolean isRentFormOpened() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(rentFormHeader)).isDisplayed();
    }

    /**
     * @param date дата в формате dd.MM.yyyy
     */
    public OrderPage setDeliveryDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(dateInput)).sendKeys(date, Keys.ENTER);
        return this;
    }

    public OrderPage selectRentalPeriod(String period) {
        click(rentalPeriodDropdown);
        click(By.xpath(String.format(RENTAL_PERIOD_OPTION, period)));
        return this;
    }

    /**
     * @param colorId id чекбокса: black или grey
     */
    public OrderPage selectColor(String colorId) {
        click(By.id(colorId));
        return this;
    }

    public OrderPage setComment(String comment) {
        type(commentInput, comment);
        return this;
    }

    public OrderPage clickOrder() {
        click(orderButton);
        return this;
    }

    public OrderPage fillRentForm(String date, String period, String colorId, String comment) {
        setDeliveryDate(date);
        selectRentalPeriod(period);
        selectColor(colorId);
        setComment(comment);
        return clickOrder();
    }

    public OrderPage confirmOrder() {
        click(confirmYesButton);
        return this;
    }

    public boolean isOrderCreated() {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(modalHeader, "Заказ оформлен"));
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void type(By locator, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).sendKeys(text);
    }
}
