package ru.praktikum.scooter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.praktikum.scooter.pages.MainPage;
import ru.praktikum.scooter.pages.OrderPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Заказ самоката")
public class OrderTest extends BaseTest {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    enum EntryPoint { HEADER_BUTTON, BOTTOM_BUTTON }

    static List<Arguments> orderData() {
        Object[][] customers = {
                {"Стас", "Кан", "Ул. Наурызбай батыра, 154а","Черкизовская", "+77771234432",
                        1, "сутки", "black", "Позвоните за час"},
                {"Зухра", "Маликова", "Ул. Шарипова, 100", "Чистые пруды", "+7774324029",
                        5, "семеро суток", "grey", ""},
        };
        List<Arguments> data = new ArrayList<>();
        for (EntryPoint entryPoint : EntryPoint.values()) {
            for (Object[] c : customers) {
                data.add(Arguments.of(entryPoint, c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7], c[8]));
            }
        }
        return data;
    }

    @ParameterizedTest(name = "{index}. {0}: {1} {2}, {6} дн., {7}")
    @DisplayName("Позитивный сценарий оформления заказа")
    @MethodSource("orderData")
    public void orderCanBeCreated(EntryPoint entryPoint, String name, String surname, String address,
                                  String station, String phone, int daysFromToday, String rentalPeriod,
                                  String colorId, String comment) {
        MainPage mainPage = new MainPage(driver).open();
        OrderPage orderPage;
        if (entryPoint == EntryPoint.HEADER_BUTTON) {
            orderPage = mainPage.clickHeaderOrderButton();
        } else {
            orderPage = mainPage.clickBottomOrderButton();
        }

        orderPage.fillCustomerForm(name, surname, address, station, phone);
        assertTrue(orderPage.isRentFormOpened(), "Не открылась форма «Про аренду»");

        String date = LocalDate.now().plusDays(daysFromToday).format(DATE_FORMAT);
        orderPage.fillRentForm(date, rentalPeriod, colorId, comment)
                .confirmOrder();

        assertTrue(orderPage.isOrderCreated(), "Не появилось окно об успешном создании заказа");
    }
}
