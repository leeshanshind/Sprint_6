package ru.praktikum.scooter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.praktikum.scooter.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Раздел «Вопросы о важном»")
public class FaqTest extends BaseTest {

    @ParameterizedTest(name = "{index}. {1}")
    @DisplayName("При нажатии на стрелочку открывается соответствующий текст")
    @CsvSource(delimiter = '|', value = {
            "0 | Сколько это стоит? И как оплатить? | Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1 | Хочу сразу несколько самокатов! Так можно? | Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "2 | Как рассчитывается время аренды? | Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "3 | Можно ли заказать самокат прямо на сегодня? | Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "4 | Можно ли продлить заказ или вернуть самокат раньше? | Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "5 | Вы привозите зарядку вместе с самокатом? | Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "6 | Можно ли отменить заказ? | Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "7 | Я жизу за МКАДом, привезёте? | Да, обязательно. Всем самокатов! И Москве, и Московской области."
    })
    public void answerIsShownWhenQuestionClicked(int index, String question, String expectedAnswer) {
        MainPage mainPage = new MainPage(driver).open();

        assertEquals(question, mainPage.getQuestionText(index), "Неверный текст вопроса");
        mainPage.clickQuestion(index);
        assertEquals(expectedAnswer, mainPage.getAnswerText(index), "Неверный текст ответа");
    }
}
