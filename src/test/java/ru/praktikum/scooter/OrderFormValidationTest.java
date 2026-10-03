package ru.praktikum.scooter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.praktikum.scooter.pages.OrderPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Ошибки в форме заказа «Для кого самокат»")
public class OrderFormValidationTest extends BaseTest {

    private static final String VALID_NAME = "Иван";
    private static final String VALID_SURNAME = "Петров";
    private static final String VALID_ADDRESS = "Москва, ул. Тверская, д. 1";
    private static final String VALID_STATION = "Сокольники";
    private static final String VALID_PHONE = "+79991234567";

    @ParameterizedTest(name = "{index}. {0} = «{1}»")
    @DisplayName("Некорректное значение поля показывает ошибку")
    @CsvSource({
            "'* Имя', Ivan, Введите корректное имя",
            "'* Имя', И, Введите корректное имя",
            "'* Фамилия', Petrov, Введите корректную фамилию",
            "'* Фамилия', П, Введите корректную фамилию",
            "'* Адрес: куда привезти заказ', Мск, Введите корректный адрес",
            "'* Телефон: на него позвонит курьер', 123, Введите корректный номер",
            "'* Телефон: на него позвонит курьер', телефон, Введите корректный номер",
    })
    public void invalidFieldShowsError(String placeholder, String invalidValue, String expectedError) {
        OrderPage orderPage = new OrderPage(driver).open();

        String name = VALID_NAME;
        String surname = VALID_SURNAME;
        String address = VALID_ADDRESS;
        String phone = VALID_PHONE;
        switch (placeholder) {
            case OrderPage.NAME_PLACEHOLDER:
                name = invalidValue;
                break;
            case OrderPage.SURNAME_PLACEHOLDER:
                surname = invalidValue;
                break;
            case OrderPage.ADDRESS_PLACEHOLDER:
                address = invalidValue;
                break;
            case OrderPage.PHONE_PLACEHOLDER:
                phone = invalidValue;
                break;
        }

        orderPage.setName(name)
                .setSurname(surname)
                .setAddress(address)
                .selectMetroStation(VALID_STATION)
                .setPhone(phone)
                .clickNext();

        assertEquals(expectedError, orderPage.getInputError(placeholder));
    }

    @Test
    @DisplayName("Без выбранной станции метро показывается ошибка")
    public void emptyMetroStationShowsError() {
        OrderPage orderPage = new OrderPage(driver).open();

        orderPage.setName(VALID_NAME)
                .setSurname(VALID_SURNAME)
                .setAddress(VALID_ADDRESS)
                .setPhone(VALID_PHONE)
                .clickNext();

        assertEquals("Выберите станцию", orderPage.getMetroError());
    }
}
