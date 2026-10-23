package qa.guru.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.components.ResultTableComponent;
import qa.guru.config.Environment;
import qa.guru.data.TestData;
import qa.guru.helpers.BaseTest;
import qa.guru.pages.PracticeFormPage;

import static qa.guru.helpers.CustomConditions.invalid;
import static qa.guru.helpers.CustomConditions.valid;


public class TestPracticeForm extends BaseTest {
    private final Environment env = Environment.DEMOQA;
    private final PracticeFormPage currentPage = new PracticeFormPage();

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(TestData.FIRST_NAME)
                .setLastName(TestData.LAST_NAME)
                .setUserEmail(TestData.USER_EMAIL)
                .setUserGender(TestData.USER_GENDER)
                .setUserNumber(TestData.USER_NUMBER)
                .setUserDateOfBirth(
                        TestData.YEAR_OF_BIRTH,
                        TestData.MONTH_OF_BIRTH,
                        TestData.DAY_OF_BIRTH)
                .setSubjects(TestData.SUBJECTS)
                .setHobbies(TestData.HOBBIES)
                .uploadPicture(TestData.PICTURE)
                .setUserAddress(TestData.ADDRESS)
                .setUserState(TestData.STATE)
                .setUserCity(TestData.CITY)
                .submit();

        // Then
        String columnName = "Values";
        ResultTableComponent resultTable = currentPage.getResultTableComponent();
        String expectedFullName = TestData.FIRST_NAME + " " + TestData.LAST_NAME;
        String expectedDateOfBirth = TestData.DAY_OF_BIRTH + " " + TestData.MONTH_OF_BIRTH + "," + TestData.YEAR_OF_BIRTH;
        String expectedStateAndCity = TestData.STATE + " " + TestData.CITY;

        resultTable.shouldHaveCellText("Student Name", columnName, expectedFullName);
        resultTable.shouldHaveCellText("Student Email", columnName, TestData.USER_EMAIL);
        resultTable.shouldHaveCellText("Gender", columnName, TestData.USER_GENDER);
        resultTable.shouldHaveCellText("Mobile", columnName, TestData.USER_NUMBER);
        resultTable.shouldHaveCellText("Date of Birth", columnName, expectedDateOfBirth);
        resultTable.shouldHaveCellText("Subjects", columnName, String.join(", ", TestData.SUBJECTS));
        resultTable.shouldHaveCellText("Hobbies", columnName, String.join(", ", TestData.HOBBIES));
        resultTable.shouldHaveCellText("Picture", columnName, TestData.PICTURE);
        resultTable.shouldHaveCellText("Address", columnName, TestData.ADDRESS);
        resultTable.shouldHaveCellText("State and City", columnName, expectedStateAndCity);
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(TestData.FIRST_NAME_2)
                .setLastName(TestData.LAST_NAME_2)
                .setUserGender(TestData.USER_GENDER_2)
                .setUserNumber(TestData.USER_NUMBER_2)
                .submit();

        // Then
        String columnName = "Values";
        ResultTableComponent resultTable = currentPage.getResultTableComponent();
        String expectedFullName = TestData.FIRST_NAME_2 + " " + TestData.LAST_NAME_2;

        resultTable.shouldHaveCellText("Student Name", columnName, expectedFullName);
        resultTable.shouldHaveCellText("Gender", columnName, TestData.USER_GENDER_2);
        resultTable.shouldHaveCellText("Mobile", columnName, TestData.USER_NUMBER_2);
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - остуствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Given
        currentPage.openPage(env);

        // When
        currentPage
                .setFirstName(TestData.FIRST_NAME_2)
                .setUserGender(TestData.USER_GENDER_2)
                .setUserNumber(TestData.USER_NUMBER_2)
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
                .setFirstName(TestData.FIRST_NAME_2)
                .setLastName(TestData.LAST_NAME_2)
                .setUserNumber(TestData.USER_NUMBER_2)
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
                .setFirstName(TestData.FIRST_NAME_2)
                .setLastName(TestData.LAST_NAME_2)
                .setUserGender(TestData.USER_GENDER_2)
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
                .setFirstName(TestData.FIRST_NAME_2)
                .setLastName(TestData.LAST_NAME_2)
                .setUserGender(TestData.USER_GENDER_2)
                .setUserNumber(TestData.USER_NUMBER_2)
                .setUserEmail(TestData.USER_EMAIL_BAD)
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
                .setFirstName(TestData.FIRST_NAME_2)
                .setLastName(TestData.LAST_NAME_2)
                .setUserGender(TestData.USER_GENDER_2)
                .setUserNumber(TestData.USER_NUMBER_BAD)
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
