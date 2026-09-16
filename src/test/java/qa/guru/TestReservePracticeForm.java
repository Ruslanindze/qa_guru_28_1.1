package qa.guru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class TestReservePracticeForm {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://qa-guru.github.io";
    }

    // ---------- Позитивные тесты
    @Test
    @DisplayName("RPF-CASE-001. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Открываем форму на весь экран
        open("/one-page-form/automation-practice-form.html");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Убираем баннер
        executeJavaScript("$('#fixedban').remove();");

        // Заполнение обязательных полей
        $("#firstName").shouldBe(Condition.editable).setValue("Чупакабра");
        $("#lastName").shouldBe(Condition.editable).setValue("Редкая");
        $("#genterWrapper [value='Other']").shouldBe(Condition.clickable).click();
        $("#userNumber").shouldBe(Condition.editable).setValue("7404900300");

        // Подтверждение
        $("button#submit").shouldBe(Condition.clickable).click();

        // Проверка данных в таблице ответа
        $("#resultModal").shouldHave(
                text("Student Name %s".formatted("Чупакабра Редкая")),
                text("Gender %s".formatted("Other")),
                text("Mobile %s".formatted("7404900300"))
        );
    }

    // ---------- Негативные тесты
    @DisplayName("RPF-CASE-002. Негативный сценарий")
    @ParameterizedTest(name = "{0}")
    @MethodSource("negativeRegistrationDataProvider")
    void shouldNotRegisterWhenReqFieldIncorrect(String scenarioName, String selector, String value) {
        // Открываем форму на весь экран
        open("/one-page-form/automation-practice-form.html");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Убираем баннер
        executeJavaScript("$('#fixedban').remove();");

        // Заполнение обязательных полей
        $("#firstName").shouldBe(Condition.editable).setValue("Чупакабра");
        $("#lastName").shouldBe(Condition.editable).setValue("Редкая");
        $("#genterWrapper [value='Other']").shouldBe(Condition.clickable).click();
        $("#userNumber").shouldBe(Condition.editable).setValue("7404900300");

        // Перезатираем поле
        $(selector).shouldBe(Condition.editable).setValue(value);

        // Подтверждение
        $("button#submit").shouldBe(Condition.clickable).click();

        // Проверка невалидного статуса поля
        $("%s:invalid".formatted(selector)).shouldBe(Condition.visible);

        // Проверка, что таблица не появилась
        $("#resultModal").shouldBe(Condition.hidden);
    }

    static Stream<Arguments> negativeRegistrationDataProvider() {
        return Stream.of(
                // Аргументы: Имя сценария | Поле (селектор) | Значение | Класс ошибки
                Arguments.of("Пустое имя", "#firstName", ""),
                Arguments.of("Пустая фамилия", "#lastName", ""),
                Arguments.of("Некорректный телефон (мало цифр)", "#userNumber", "123")
        );
    }
}
