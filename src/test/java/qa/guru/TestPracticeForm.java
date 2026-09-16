package qa.guru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class TestPracticeForm {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
    }

    private void checkRow(String label, String value) {
        $(".table-responsive").$(byText(label)).parent().shouldHave(text(value));
    }

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Основная информация
        $("input#firstName").setValue("Дебаг");
        $("input#lastName").setValue("Логов");
        $("#userEmail").setValue("please_work_finally@test.ru");
        $("input.form-check-input[value='Male']").click();
        $("#userNumber").setValue("7404500200");

        // Заполнение даты рождения через календарь
        $("#dateOfBirthInput").click();
        $("select.react-datepicker__year-select").selectOption("1990");
        $(".react-datepicker__month-select").selectOption("April");
        $$(".react-datepicker__day").findBy(text("14")).click();

        // Выбор предметов
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#subjectsInput").setValue("Economics").pressEnter();
        $("#subjectsInput").setValue("English").pressEnter();

        // Выбор хобби
        $("#hobbiesWrapper").$$("label").findBy(text("Sports")).click();
        $("#hobbiesWrapper").$$("label").findBy(text("Music")).click();

        // Выбор картинки
        $("#uploadPicture").uploadFromClasspath("alan-wake-2.jpg");

        // Заполнение адреса
        $("#currentAddress").setValue("Room 101, Heart o' the City Hotel, 2200 Cyber Avenue");
        $("#state input").setValue("Uttar Pradesh").pressEnter();
        $("#city input").setValue("Lucknow").pressEnter();

        // Подтверждение
        $("#submit").click();

        // Проверка данных в таблице ответа
        checkRow("Student Name", "Дебаг Логов");
        checkRow("Student Email", "please_work_finally@test.ru");
        checkRow("Gender", "Male");
        checkRow("Mobile", "7404500200");
        checkRow("Date of Birth", "14 April,1990");
        checkRow("Subjects", "Maths, Economics, English");
        checkRow("Hobbies", "Sports, Music");
        checkRow("Picture", "alan-wake-2.jpg");
        checkRow("Address", "Room 101, Heart o' the City Hotel, 2200 Cyber Avenue");
        checkRow("State and City", "Uttar Pradesh Lucknow");
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей
        $("input#firstName").setValue("Лариса");
        $("input#lastName").setValue("Крофтова");
        $("input.form-check-input[value='Female']").click();
        $("#userNumber").setValue("7404500300");

        // Подтверждение
        $("#submit").click();

        // Проверка данных в таблице ответа
        checkRow("Student Name", "Лариса Крофтова");
        checkRow("Gender", "Female");
        checkRow("Mobile", "7404500300");
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - остуствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей - без фамилии
        $("input#firstName").setValue("Лариса");
        $("input.form-check-input[value='Female']").click();
        $("#userNumber").setValue("7404500300");

        // Подтверждение
        $("#submit").click();

        // Проверка невалидных полей (красный восклицательный знак)
        $("#lastName:invalid").shouldBe(Condition.visible);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        $("#firstName:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Male']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Female']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Other']:valid").shouldBe(Condition.visible);
        $("#userEmail:valid").shouldBe(Condition.visible);
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
    @DisplayName("PF-CASE-004. Ошибка - остуствует обязательное поле [Пол]")
    void shouldNotRegisterWhenGenderIsEmpty() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей
        $("input#firstName").setValue("Лариса");
        $("input#lastName").setValue("Крофтова");
        $("#userNumber").setValue("7404500300");

        // Подтверждение
        $("#submit").click();

        // Проверка невалидных полей (красный восклицательный знак)
        $(".form-check-input[value='Male']:invalid").shouldBe(Condition.visible);
        $(".form-check-input[value='Female']:invalid").shouldBe(Condition.visible);
        $(".form-check-input[value='Other']:invalid").shouldBe(Condition.visible);

        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        $("#firstName:valid").shouldBe(Condition.visible);
        $("#lastName:valid").shouldBe(Condition.visible);
        $("#userEmail:valid").shouldBe(Condition.visible);
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
    @DisplayName("PF-CASE-005. Ошибка - остуствует обязательное поле [Мобильный]")
    void shouldNotRegisterWhenMobileIsEmpty() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей - без номера телефона
        $("input#firstName").setValue("Лариса");
        $("input#lastName").setValue("Крофтова");
        $("input.form-check-input[value='Female']").click();

        // Подтверждение
        $("#submit").click();

        // Проверка невалидных полей (красный восклицательный знак)
        $("#userNumber:invalid").shouldBe(Condition.visible);
        // Проверка валидных полей (зелёная галочка) - заполнение по-умолчанию
        $("#firstName:valid").shouldBe(Condition.visible);
        $("#lastName:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Male']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Female']:valid").shouldBe(Condition.visible);
        $(".form-check-input[value='Other']:valid").shouldBe(Condition.visible);
        $("#userEmail:valid").shouldBe(Condition.visible);
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
    @DisplayName("PF-CASE-006. Ошибка - некорректная почта")
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
