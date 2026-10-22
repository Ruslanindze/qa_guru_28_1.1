package qa.guru.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.data.PracticeFormData;
import qa.guru.helpers.BaseTest;
import qa.guru.pages.demoqa.PracticeFormPage;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static qa.guru.helpers.CustomConditions.invalid;
import static qa.guru.helpers.CustomConditions.valid;


public class TestPracticeForm extends BaseTest {

    private void checkRow(String label, String value) {
        $(".table-responsive").$(byText(label)).parent().shouldHave(text(value));
    }

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Given
        PracticeFormPage practiceFormPage = new PracticeFormPage();

        // When
        practiceFormPage
                .openPage()
                .setFirstName(PracticeFormData.SuccessRegisterWithAllFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithAllFields.LAST_NAME)
                .setUserEmail(PracticeFormData.SuccessRegisterWithAllFields.USER_EMAIL)
                .setUserGender(PracticeFormData.SuccessRegisterWithAllFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithAllFields.USER_NUMBER)
                .setUserDateOfBirth(
                        PracticeFormData.SuccessRegisterWithAllFields.YEAR_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.MONTH_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.DAY_OF_BIRTH)
                .setSubjects(PracticeFormData.SuccessRegisterWithAllFields.SUBJECTS)
                .setHobbies(PracticeFormData.SuccessRegisterWithAllFields.HOBBIES)
                .uploadPicture(PracticeFormData.SuccessRegisterWithAllFields.PICTURE)
                .setUserAddress(PracticeFormData.SuccessRegisterWithAllFields.ADDRESS)
                .setUserState(PracticeFormData.SuccessRegisterWithAllFields.STATE)
                .setUserCity(PracticeFormData.SuccessRegisterWithAllFields.CITY)
                .submit();

        // Then
        String columnName = "Values";
        practiceFormPage.getCellFromTableResult("Student Name", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.FIRST_NAME,
                        PracticeFormData.SuccessRegisterWithAllFields.LAST_NAME))
                );
        practiceFormPage.getCellFromTableResult("Student Email", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_EMAIL))
                );
        practiceFormPage.getCellFromTableResult("Gender", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_GENDER))
                );
        practiceFormPage.getCellFromTableResult("Mobile", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_NUMBER))
                );
        practiceFormPage.getCellFromTableResult("Date of Birth", columnName)
                .shouldHave(text("%s %s,%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.DAY_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.MONTH_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.YEAR_OF_BIRTH))
                );
        practiceFormPage.getCellFromTableResult("Subjects", columnName)
                .shouldHave(text("%s".formatted(
                        String.join(", ", PracticeFormData.SuccessRegisterWithAllFields.SUBJECTS)))
                );
        practiceFormPage.getCellFromTableResult("Hobbies", columnName)
                .shouldHave(text("%s".formatted(
                        String.join(", ", PracticeFormData.SuccessRegisterWithAllFields.HOBBIES)))
                );
        practiceFormPage.getCellFromTableResult("Picture", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.PICTURE))
                );
        practiceFormPage.getCellFromTableResult("Address", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.ADDRESS))
                );
        practiceFormPage.getCellFromTableResult("State and City", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.STATE,
                        PracticeFormData.SuccessRegisterWithAllFields.CITY))
                );
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        PracticeFormPage practiceFormPage = new PracticeFormPage();

        // When
        practiceFormPage
                .openPage()
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        String columnName = "Values";
        practiceFormPage.getCellFromTableResult("Student Name", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME,
                        PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME))
                );
        practiceFormPage.getCellFromTableResult("Gender", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER))
                );
        practiceFormPage.getCellFromTableResult("Mobile", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER))
                );
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - остуствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Given
        PracticeFormPage practiceFormPage = new PracticeFormPage();

        // When
        practiceFormPage
                .openPage()
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        practiceFormPage.getLastName().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        practiceFormPage.getFirstName().shouldBe(valid);
        practiceFormPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUserEmail().shouldBe(valid);
        practiceFormPage.getUserNumber().shouldBe(valid);
        practiceFormPage.getDateOfBirthCalendar().shouldBe(valid);
        practiceFormPage.getSubjectsInput().shouldBe(valid);
        practiceFormPage.getHobbiesInput().forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-004. Ошибка - остуствует обязательное поле [Пол]")
    void shouldNotRegisterWhenGenderIsEmpty() {
        // Given
        PracticeFormPage practiceFormPage = new PracticeFormPage();

        // When
        practiceFormPage
                .openPage()
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        practiceFormPage.getUserGender().$$("input").forEach(el -> el.shouldBe(invalid));
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        practiceFormPage.getFirstName().shouldBe(valid);
        practiceFormPage.getLastName().shouldBe(valid);
        practiceFormPage.getUserEmail().shouldBe(valid);
        practiceFormPage.getUserNumber().shouldBe(valid);
        practiceFormPage.getDateOfBirthCalendar().shouldBe(valid);
        practiceFormPage.getSubjectsInput().shouldBe(valid);
        practiceFormPage.getHobbiesInput().forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-005. Ошибка - остуствует обязательное поле [Мобильный]")
    void shouldNotRegisterWhenMobileIsEmpty() {
        // Given
        PracticeFormPage practiceFormPage = new PracticeFormPage();

        // When
        practiceFormPage
                .openPage()
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        practiceFormPage.getUserNumber().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        practiceFormPage.getFirstName().shouldBe(valid);
        practiceFormPage.getLastName().shouldBe(valid);
        practiceFormPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUserEmail().shouldBe(valid);
        practiceFormPage.getDateOfBirthCalendar().shouldBe(valid);
        practiceFormPage.getSubjectsInput().shouldBe(valid);
        practiceFormPage.getHobbiesInput().forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-006. Ошибка - некорректная почта")
    // TODO!!! Здесь остановился
    void shouldNotRegisterWhenIncorrectEmail() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение полей - обязательные + некорректная почта
        $("input#firstName").setValue("Лариса");
        $("input#lastName").setValue("Крофтова");
        $("input.form-check-input[value='Female']").click();
        $("#userNumber").setValue("7404500300");
        $("#userEmail").setValue("@please_work_finally@test.ru");

        // Подтверждение
        $("#submit").click();

        // Проверка невалидного поля почты
        $("#userEmail:invalid").shouldBe(Condition.visible);
        // Остальные поля валидны
        $("#firstName:valid").shouldBe(Condition.visible);
        $("#lastName:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Male']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Female']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Other']:valid").shouldBe(Condition.visible);
        $("#userNumber:valid").shouldBe(Condition.visible);
        $("#dateOfBirthInput:valid").shouldBe(Condition.visible);
        $("#subjectsInput:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-1:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-2:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-3:valid").shouldBe(Condition.visible);
        $("#uploadPicture:valid").shouldBe(Condition.visible);
        $("#currentAddress:valid").shouldBe(Condition.visible);
        $("#state input:valid").shouldBe(Condition.visible);
        $("#city input").shouldBe(Condition.disabled);

        // Проверка, что таблица не появилась
        $(".table-responsive").shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-007. Ошибка - некорректный номер (9 цифр)")
    void shouldNotRegisterWhenIncorrectMobile() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение полей - обязательные + некорректная почта
        $("input#firstName").setValue("Лариса");
        $("input#lastName").setValue("Крофтова");
        $("input.form-check-input[value='Female']").click();
        $("#userNumber").setValue("740450030");

        // Подтверждение
        $("#submit").click();

        // Проверка невалидного поля почты
        $("#userNumber:invalid").shouldBe(Condition.visible);
        // Остальные поля валидны
        $("#firstName:valid").shouldBe(Condition.visible);
        $("#lastName:valid").shouldBe(Condition.visible);
        $("#userEmail:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Male']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Female']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Other']:valid").shouldBe(Condition.visible);
        $("#dateOfBirthInput:valid").shouldBe(Condition.visible);
        $("#subjectsInput:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-1:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-2:valid").shouldBe(Condition.visible);
        $("#hobbies-checkbox-3:valid").shouldBe(Condition.visible);
        $("#uploadPicture:valid").shouldBe(Condition.visible);
        $("#currentAddress:valid").shouldBe(Condition.visible);
        $("#state input:valid").shouldBe(Condition.visible);
        $("#city input").shouldBe(Condition.disabled);

        // Проверка, что таблица не появилась
        $(".table-responsive").shouldBe(Condition.hidden);
    }
}
