package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Страница статуса заказа.
 */
public class OrderStatusPage extends BasePage {

    // Картинка «Такого заказа нет»
    private final By notFoundImage = By.xpath("//div[starts-with(@class, 'Track_NotFound')]/img[@alt='Not found']");

    public OrderStatusPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOpened() {
        return wait.until(ExpectedConditions.urlContains(BASE_URL + "track"));
    }

    public boolean isOrderNotFoundShown() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundImage)).isDisplayed();
    }
}
