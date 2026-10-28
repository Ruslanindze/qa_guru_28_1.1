package qa.guru.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import qa.guru.config.Environment;
import qa.guru.data.TestData;
import qa.guru.data.UserDto;
import qa.guru.helpers.BaseTest;
import qa.guru.pages.PracticeFormPage;
import qa.guru.pages.components.ResultTableComponent;

import static qa.guru.helpers.CustomConditions.invalid;
import static qa.guru.helpers.CustomConditions.valid;


public class PracticeFormTest extends BaseTest {
    private final Environment env = Environment.DEMOQA;
    private final PracticeFormPage practiceFormPage = new PracticeFormPage();

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Given
        UserDto userDto = TestData.getRegistrationUser();

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
                .submit();

        // Then
        String columnName = "Values";
        ResultTableComponent resultTable = practiceFormPage.getResultTableComponent();
        String expectedDateOfBirth = "%02d %s,%s".formatted(
                Integer.parseInt(userDto.getBirthDay()), userDto.getBirthMonth(), userDto.getBirthYear());

        resultTable.shouldHaveCellText("Student Name", columnName, userDto.getFullName());
        resultTable.shouldHaveCellText("Student Email", columnName, userDto.getEmail());
        resultTable.shouldHaveCellText("Gender", columnName, userDto.getGender());
        resultTable.shouldHaveCellText("Mobile", columnName, userDto.getPhoneNumber());
        resultTable.shouldHaveCellText("Date of Birth", columnName, expectedDateOfBirth);
        resultTable.shouldHaveCellText("Subjects", columnName, String.join(", ", userDto.getSubjects()));
        resultTable.shouldHaveCellText("Hobbies", columnName, String.join(", ", userDto.getHobbies()));
        resultTable.shouldHaveCellText("Picture", columnName, userDto.getPicture());
        resultTable.shouldHaveCellText("Address", columnName, userDto.getCurrentAddress());
        resultTable.shouldHaveCellText("State and City", columnName, userDto.getStateAndCity());
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Given
        UserDto userDto = TestData.getRegistrationUserShort();

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
                .submit();

        // Then
        String columnName = "Values";
        ResultTableComponent resultTable = practiceFormPage.getResultTableComponent();

        resultTable.shouldHaveCellText("Student Name", columnName, userDto.getFullName());
        resultTable.shouldHaveCellText("Gender", columnName, userDto.getGender());
        resultTable.shouldHaveCellText("Mobile", columnName, userDto.getPhoneNumber());
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - отсутствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Given
        UserDto userDto = TestData.getRegistrationUserShort();
        userDto.setLastName(null);

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
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
        practiceFormPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
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
        UserDto userDto = TestData.getRegistrationUserShort();
        userDto.setGender(null);

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
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
        practiceFormPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
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
        UserDto userDto = TestData.getRegistrationUserShort();
        userDto.setPhoneNumber(null);

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
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
        practiceFormPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-006. Ошибка - некорректная почта")
    void shouldNotRegisterWhenIncorrectEmail() {
        // Given
        UserDto userDto = TestData.getRegistrationUserWithBadEmail();

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
                .submit();

        // Then
        // Проверка невалидных полей (красный восклицательный знак)
        practiceFormPage.getUserEmail().shouldBe(invalid);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        practiceFormPage.getFirstName().shouldBe(valid);
        practiceFormPage.getLastName().shouldBe(valid);
        practiceFormPage.getUserGender().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUserNumber().shouldBe(valid);
        practiceFormPage.getDateOfBirthCalendar().shouldBe(valid);
        practiceFormPage.getSubjectsInput().shouldBe(valid);
        practiceFormPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }

    @Test
    @DisplayName("PF-CASE-007. Ошибка - некорректный номер (9 цифр)")
    void shouldNotRegisterWhenIncorrectMobile() {
        // Given
        UserDto userDto = TestData.getRegistrationUserShort();
        userDto.setPhoneNumber(userDto.getPhoneNumber().substring(0, 9));

        // When
        practiceFormPage
                .openPage(env)
                .registerAs(userDto)
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
        practiceFormPage.getHobbiesInput().$$("input").forEach(el -> el.shouldBe(valid));
        practiceFormPage.getUploadPicture().shouldBe(valid);
        practiceFormPage.getUserAddress().shouldBe(valid);
        practiceFormPage.getUserState().shouldBe(valid);
        practiceFormPage.getUserCity().shouldBe(valid);
        // Проверка, что таблица не появилась
        practiceFormPage.getTableResult().shouldBe(Condition.hidden);
    }
}
