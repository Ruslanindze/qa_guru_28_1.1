package qa.guru.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import qa.guru.pages.components.ResultTableComponent;
import qa.guru.config.Environment;
import qa.guru.data.TestData;
import qa.guru.helpers.BaseTest;
import qa.guru.pages.ReservePracticeFormPage;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static qa.guru.helpers.CustomConditions.invalid;


public class ReservePracticeFormTest extends BaseTest {
    private final Environment env = Environment.QA_GURU;
    private final ReservePracticeFormPage reservePracticeFormPage = new ReservePracticeFormPage();

    // ---------- Позитивные тесты
    @Test
    @DisplayName("RPF-CASE-001. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        reservePracticeFormPage.openPage(env);
        executeJavaScript("$('#fixedban').remove();");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // When
        reservePracticeFormPage
                .setFirstName(TestData.FIRST_NAME_3)
                .setLastName(TestData.LAST_NAME_3)
                .setUserGender(TestData.USER_GENDER_3)
                .setUserNumber(TestData.USER_NUMBER_3)
                .submit();

        // Then
        ResultTableComponent resultTable = reservePracticeFormPage.getResultTableComponent();
        String expectedFullName = TestData.FIRST_NAME_3 + " " + TestData.LAST_NAME_3;

        resultTable.shouldHaveRowContainingText("Student Name", expectedFullName);
        resultTable.shouldHaveRowContainingText("Gender", TestData.USER_GENDER_3);
        resultTable.shouldHaveRowContainingText("Mobile", TestData.USER_NUMBER_3);
    }

    // ---------- Негативные тесты
    @DisplayName("RPF-CASE-002. Негативный сценарий")
    @ParameterizedTest(name = "{0}")
    @MethodSource("negativeRegistrationDataProvider")
    void shouldNotRegisterWhenReqFieldIncorrect(String scenarioName,
                                                Function<ReservePracticeFormPage, SelenideElement> fieldProvider,
                                                String value) {
        // Given
        reservePracticeFormPage.openPage(env);
        executeJavaScript("$('#fixedban').remove();");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // When
        // Заполнение обязательных полей
        reservePracticeFormPage
                .setFirstName(TestData.FIRST_NAME_3)
                .setLastName(TestData.LAST_NAME_3)
                .setUserGender(TestData.USER_GENDER_3)
                .setUserNumber(TestData.USER_NUMBER_3);
        // Перезатираем поле и подтверждаем
        SelenideElement field = fieldProvider.apply(reservePracticeFormPage);
        field
                .setValue(value)
                .submit();

        // Then
        field.shouldBe(invalid);
        reservePracticeFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    static Stream<Arguments> negativeRegistrationDataProvider() {
        return Stream.of(
                // Аргументы: Имя сценария | Поле (селектор) | Значение
                Arguments.of(
                        "Пустое имя",
                        (Function<ReservePracticeFormPage, SelenideElement>) ReservePracticeFormPage::getFirstName,
                        ""),
                Arguments.of(
                        "Пустая фамилия",
                        (Function<ReservePracticeFormPage, SelenideElement>) ReservePracticeFormPage::getLastName,
                        ""),
                Arguments.of(
                        "Некорректный телефон (мало цифр)",
                        (Function<ReservePracticeFormPage, SelenideElement>) ReservePracticeFormPage::getUserNumber,
                        "123")
        );
    }
}
