package qa.guru.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import qa.guru.config.Environment;
import qa.guru.data.ReservePracticeFormData;
import qa.guru.helpers.BaseTest;
import qa.guru.helpers.TableUiHelper;
import qa.guru.pages.PracticeFormPage;

import java.util.function.Function;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static qa.guru.helpers.CustomConditions.invalid;


public class TestReservePracticeForm extends BaseTest {
    private final Environment env = Environment.QA_GURU_FORM;
    private final PracticeFormPage currentPage = new PracticeFormPage();
    private final SelenideElement tableResult = $("#resultModal");

    // ---------- Позитивные тесты
    @Test
    @DisplayName("RPF-CASE-001. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        currentPage.openPage(env);
        executeJavaScript("$('#fixedban').remove();");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // When
        currentPage
                .setFirstName(ReservePracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(ReservePracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        TableUiHelper.getRowByName(tableResult, "Student Name")
                .shouldHave(text("%s %s".formatted(
                        ReservePracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME,
                        ReservePracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME))
                );
        TableUiHelper.getRowByName(tableResult, "Gender")
                .shouldHave(text("%s".formatted(
                        ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER))
                );
        TableUiHelper.getRowByName(tableResult, "Mobile")
                .shouldHave(text("%s".formatted(
                        ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER))
                );
    }

    // ---------- Негативные тесты
    @DisplayName("RPF-CASE-002. Негативный сценарий")
    @ParameterizedTest(name = "{0}")
    @MethodSource("negativeRegistrationDataProvider")
    void shouldNotRegisterWhenReqFieldIncorrect(String scenarioName,
                                                Function<PracticeFormPage, SelenideElement> fieldProvider,
                                                String value) {
        // Given
        currentPage.openPage(env);
        executeJavaScript("$('#fixedban').remove();");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // When
        // Заполнение обязательных полей
        currentPage
                .setFirstName(ReservePracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(ReservePracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(ReservePracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER);
        // Перезатираем поле
        SelenideElement field = fieldProvider.apply(currentPage);
        field
                .setValue(value)
                .submit();

        // Then
        field.shouldBe(invalid);
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }

    static Stream<Arguments> negativeRegistrationDataProvider() {
        return Stream.of(
                // Аргументы: Имя сценария | Поле (селектор) | Значение
                Arguments.of(
                        "Пустое имя",
                        (Function<PracticeFormPage, SelenideElement>) PracticeFormPage::getFirstName,
                        ""),
                Arguments.of(
                        "Пустая фамилия",
                        (Function<PracticeFormPage, SelenideElement>) PracticeFormPage::getLastName,
                        ""),
                Arguments.of(
                        "Некорректный телефон (мало цифр)",
                        (Function<PracticeFormPage, SelenideElement>) PracticeFormPage::getUserNumber,
                        "123")
        );
    }
}
