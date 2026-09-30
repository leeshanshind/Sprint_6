package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Общие элементы и действия для всех страниц сервиса: шапка сайта и баннер про куки.
 */
public abstract class BasePage {

    public static final String BASE_URL = "https://qa-scooter.education-services.ru/";
    protected static final Duration TIMEOUT = Duration.ofSeconds(10);

    // Логотип «Самоката» в шапке
    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");
    // Логотип «Яндекса» в шапке
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");
    // Кнопка «Заказать» в шапке страницы
    private final By headerOrderButton = By.xpath("//div[starts-with(@class, 'Header_Nav')]/button[text()='Заказать']");
    // Кнопка «Статус заказа» в шапке
    private final By orderStatusButton = By.xpath("//button[text()='Статус заказа']");
    // Поле «Введите номер заказа» в шапке
    private final By orderNumberInput = By.xpath("//input[@placeholder='Введите номер заказа']");
    // Кнопка «Go!» рядом с полем номера заказа
    private final By goButton = By.xpath("//button[text()='Go!']");
    // Кнопка «да все привыкли» в баннере про куки
    private final By cookieConfirmButton = By.id("rcc-confirm-button");

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    public void acceptCookies() {
        List<WebElement> buttons = driver.findElements(cookieConfirmButton);
        if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
            buttons.get(0).click();
        }
    }

    public OrderPage clickHeaderOrderButton() {
        click(headerOrderButton);
        return new OrderPage(driver);
    }

    public void clickScooterLogo() {
        click(scooterLogo);
    }

    public void clickYandexLogo() {
        click(yandexLogo);
    }

    public OrderStatusPage searchOrder(String orderNumber) {
        click(orderStatusButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderNumberInput)).sendKeys(orderNumber);
        click(goButton);
        return new OrderStatusPage(driver);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void scrollTo(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
}
