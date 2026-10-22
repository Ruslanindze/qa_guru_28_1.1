package qa.guru.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.config.Environment;
import qa.guru.data.PracticeFormData;
import qa.guru.helpers.BaseTest;
import qa.guru.helpers.TableUiHelper;
import qa.guru.pages.PracticeFormPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static qa.guru.helpers.CustomConditions.invalid;
import static qa.guru.helpers.CustomConditions.valid;


public class TestPracticeForm extends BaseTest {
    private final Environment env = Environment.DEMOQA_FORM;
    private final PracticeFormPage currentPage = new PracticeFormPage();
    private final SelenideElement tableResult = $(".table-responsive");

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
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
        TableUiHelper.getCellByNames(tableResult, "Student Name", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.FIRST_NAME,
                        PracticeFormData.SuccessRegisterWithAllFields.LAST_NAME))
                );
        TableUiHelper.getCellByNames(tableResult, "Student Email", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_EMAIL))
                );
        TableUiHelper.getCellByNames(tableResult, "Gender", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_GENDER))
                );
        TableUiHelper.getCellByNames(tableResult, "Mobile", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.USER_NUMBER))
                );
        TableUiHelper.getCellByNames(tableResult, "Date of Birth", columnName)
                .shouldHave(text("%s %s,%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.DAY_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.MONTH_OF_BIRTH,
                        PracticeFormData.SuccessRegisterWithAllFields.YEAR_OF_BIRTH))
                );
        TableUiHelper.getCellByNames(tableResult, "Subjects", columnName)
                .shouldHave(text("%s".formatted(
                        String.join(", ", PracticeFormData.SuccessRegisterWithAllFields.SUBJECTS)))
                );
        TableUiHelper.getCellByNames(tableResult, "Hobbies", columnName)
                .shouldHave(text("%s".formatted(
                        String.join(", ", PracticeFormData.SuccessRegisterWithAllFields.HOBBIES)))
                );
        TableUiHelper.getCellByNames(tableResult, "Picture", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.PICTURE))
                );
        TableUiHelper.getCellByNames(tableResult, "Address", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.ADDRESS))
                );
        TableUiHelper.getCellByNames(tableResult, "State and City", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithAllFields.STATE,
                        PracticeFormData.SuccessRegisterWithAllFields.CITY))
                );
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        String columnName = "Values";
        TableUiHelper.getCellByNames(tableResult, "Student Name", columnName)
                .shouldHave(text("%s %s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME,
                        PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME))
                );
        TableUiHelper.getCellByNames(tableResult, "Gender", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER))
                );
        TableUiHelper.getCellByNames(tableResult, "Mobile", columnName)
                .shouldHave(text("%s".formatted(
                        PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER))
                );
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - остуствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        currentPage.getLastName().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        currentPage.getFirstName().shouldBe(valid);
        currentPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUserEmail().shouldBe(valid);
        currentPage.getUserNumber().shouldBe(valid);
        currentPage.getDateOfBirthCalendar().shouldBe(valid);
        currentPage.getSubjectsInput().shouldBe(valid);
        currentPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUploadPicture().shouldBe(valid);
        currentPage.getUserAddress().shouldBe(valid);
        currentPage.getUserState().shouldBe(valid);
        currentPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-004. Ошибка - остуствует обязательное поле [Пол]")
    void shouldNotRegisterWhenGenderIsEmpty() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        currentPage.getUserGender().$$("input").forEach(el -> el.shouldBe(invalid));
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        currentPage.getFirstName().shouldBe(valid);
        currentPage.getLastName().shouldBe(valid);
        currentPage.getUserEmail().shouldBe(valid);
        currentPage.getUserNumber().shouldBe(valid);
        currentPage.getDateOfBirthCalendar().shouldBe(valid);
        currentPage.getSubjectsInput().shouldBe(valid);
        currentPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUploadPicture().shouldBe(valid);
        currentPage.getUserAddress().shouldBe(valid);
        currentPage.getUserState().shouldBe(valid);
        currentPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-005. Ошибка - остуствует обязательное поле [Мобильный]")
    void shouldNotRegisterWhenMobileIsEmpty() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        currentPage.getUserNumber().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        currentPage.getFirstName().shouldBe(valid);
        currentPage.getLastName().shouldBe(valid);
        currentPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUserEmail().shouldBe(valid);
        currentPage.getDateOfBirthCalendar().shouldBe(valid);
        currentPage.getSubjectsInput().shouldBe(valid);
        currentPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUploadPicture().shouldBe(valid);
        currentPage.getUserAddress().shouldBe(valid);
        currentPage.getUserState().shouldBe(valid);
        currentPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-006. Ошибка - некорректная почта")
    void shouldNotRegisterWhenIncorrectEmail() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.SuccessRegisterWithRequiredFields.USER_NUMBER)
                .setUserEmail(PracticeFormData.ErrorRegisterWithIncorrectEmail.USER_EMAIL)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        currentPage.getUserEmail().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        currentPage.getFirstName().shouldBe(valid);
        currentPage.getLastName().shouldBe(valid);
        currentPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUserNumber().shouldBe(valid);
        currentPage.getDateOfBirthCalendar().shouldBe(valid);
        currentPage.getSubjectsInput().shouldBe(valid);
        currentPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUploadPicture().shouldBe(valid);
        currentPage.getUserAddress().shouldBe(valid);
        currentPage.getUserState().shouldBe(valid);
        currentPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-007. Ошибка - некорректный номер (9 цифр)")
    void shouldNotRegisterWhenIncorrectMobile() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(PracticeFormData.SuccessRegisterWithRequiredFields.FIRST_NAME)
                .setLastName(PracticeFormData.SuccessRegisterWithRequiredFields.LAST_NAME)
                .setUserGender(PracticeFormData.SuccessRegisterWithRequiredFields.USER_GENDER)
                .setUserNumber(PracticeFormData.ErrorRegisterWithIncorrectNumber.USER_NUMBER)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        currentPage.getUserNumber().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        currentPage.getFirstName().shouldBe(valid);
        currentPage.getLastName().shouldBe(valid);
        currentPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUserEmail().shouldBe(valid);
        currentPage.getDateOfBirthCalendar().shouldBe(valid);
        currentPage.getSubjectsInput().shouldBe(valid);
        currentPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        currentPage.getUploadPicture().shouldBe(valid);
        currentPage.getUserAddress().shouldBe(valid);
        currentPage.getUserState().shouldBe(valid);
        currentPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        currentPage.getTableResult().shouldBe(Condition.hidden);
    }
}
