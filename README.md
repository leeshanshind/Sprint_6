# Sprint_6 — автотесты для «Яндекс.Самоката»

UI-автотесты для учебного сервиса https://qa-scooter.education-services.ru/ на Java 11, JUnit 5 и Selenium 4.

## Структура

- `src/main/java/ru/praktikum/scooter/pages` — Page Object:
  - `BasePage` — шапка сайта (логотипы, кнопки «Заказать» и «Статус заказа») и баннер про куки;
  - `MainPage` — главная страница: нижняя кнопка «Заказать», раздел «Вопросы о важном»;
  - `OrderPage` — форма заказа «Для кого самокат», «Про аренду» и модальные окна;
  - `OrderStatusPage` — страница статуса заказа.
- `src/test/java/ru/praktikum/scooter` — тесты:
  - `FaqTest` — выпадающий список «Вопросы о важном» (параметризованный, 8 вопросов);
  - `OrderTest` — позитивный сценарий заказа: 2 точки входа × 2 набора данных;
  - `HeaderTest` — логотипы «Самоката» и Яндекса, поиск несуществующего заказа (доп. задание);
  - `OrderFormValidationTest` — ошибки полей формы заказа (доп. задание).

## Запуск

```bash
mvn clean test                       # Google Chrome (по умолчанию)
mvn clean test -Dbrowser=firefox     # Mozilla Firefox
mvn clean test -Dheadless=true       # без окна браузера
```

Драйверы браузеров скачиваются автоматически через Selenium Manager.

## Известный баг

В Google Chrome заказ оформить нельзя: в окне «Хотите оформить заказ?» кнопка «Да» не срабатывает,
окно «Заказ оформлен» не появляется. Поэтому тесты `OrderTest` в Chrome падают — это ожидаемое поведение.
