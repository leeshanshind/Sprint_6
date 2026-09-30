package ru.praktikum.scooter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.scooter.pages.MainPage;
import ru.praktikum.scooter.pages.OrderPage;
import ru.praktikum.scooter.pages.OrderStatusPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Шапка сайта")
public class HeaderTest extends BaseTest {

    @Test
    @DisplayName("Клик по логотипу «Самоката» ведёт на главную страницу")
    public void scooterLogoOpensMainPage() {
        OrderPage orderPage = new OrderPage(driver).open();
        assertTrue(orderPage.isCustomerFormOpened(), "Не открылась страница заказа");

        orderPage.clickScooterLogo();

        assertTrue(new MainPage(driver).isOpened(), "Не открылась главная страница «Самоката»");
    }

    @Test
    @DisplayName("Клик по логотипу Яндекса открывает главную страницу Яндекса в новом окне")
    public void yandexLogoOpensYandexInNewWindow() {
        MainPage mainPage = new MainPage(driver).open();
        String scooterWindow = driver.getWindowHandle();

        mainPage.clickYandexLogo();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(scooterWindow)) {
                driver.switchTo().window(handle);
                break;
            }
        }

        assertTrue(wait.until(ExpectedConditions.urlContains("ya")),
                "В новом окне не открылась страница Яндекса: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Поиск несуществующего заказа показывает, что такого заказа нет")
    public void wrongOrderNumberShowsNotFound() {
        OrderStatusPage statusPage = new MainPage(driver).open().searchOrder("0000000");

        assertTrue(statusPage.isOpened(), "Не открылась страница статуса заказа");
        assertTrue(statusPage.isOrderNotFoundShown(), "Не показано сообщение, что такого заказа нет");
    }
}
