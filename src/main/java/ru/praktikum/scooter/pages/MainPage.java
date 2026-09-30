package ru.praktikum.scooter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Главная страница «Самоката». Элементы шапки описаны в {@link BasePage}.
 */
public class MainPage extends BasePage {

    // Заголовок главной страницы «Самокат на пару дней»
    private final By homeHeader = By.xpath("//div[starts-with(@class, 'Home_Header')]");
    // Кнопка «Заказать» внизу страницы
    private final By bottomOrderButton = By.xpath("//div[starts-with(@class, 'Home_FinishButton')]/button[text()='Заказать']");
    // Вопрос в разделе «Вопросы о важном» (стрелочка раскрывающегося списка) по порядковому номеру
    private static final String FAQ_QUESTION = "accordion__heading-%d";
    // Ответ на вопрос в разделе «Вопросы о важном» по порядковому номеру
    private static final String FAQ_ANSWER = "accordion__panel-%d";

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get(BASE_URL);
        acceptCookies();
        return this;
    }

    public boolean isOpened() {
        return driver.getCurrentUrl().equals(BASE_URL)
                && wait.until(ExpectedConditions.visibilityOfElementLocated(homeHeader)).isDisplayed();
    }

    public OrderPage clickBottomOrderButton() {
        scrollTo(bottomOrderButton);
        click(bottomOrderButton);
        return new OrderPage(driver);
    }

    public String getQuestionText(int index) {
        By question = By.id(String.format(FAQ_QUESTION, index));
        scrollTo(question);
        return driver.findElement(question).getText();
    }

    public void clickQuestion(int index) {
        By question = By.id(String.format(FAQ_QUESTION, index));
        scrollTo(question);
        click(question);
    }

    public String getAnswerText(int index) {
        By answer = By.id(String.format(FAQ_ANSWER, index));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }
}
