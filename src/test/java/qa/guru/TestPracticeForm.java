package qa.guru;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class TestPracticeForm {

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
    }

    // ---------- Позитивные тесты
    @Test
    @DisplayName("PF-CASE-001. Успешное заполнение всех полей формы регистрации")
    void shouldRegisterUserWhenAllFieldsAreValid() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Основная информация
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Дебаг");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Логов");
        $("#userEmail")
                .shouldBe(Condition.editable).setValue("please_work_finally@test.ru");
        $("input.form-check-input[value='Male']")
                .shouldBe(Condition.clickable).click();
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("7404500200");

        // Заполнение даты рождения через календарь
        $("#dateOfBirthInput")
                .shouldBe(Condition.clickable).click();
        $("select.react-datepicker__year-select")
                .shouldBe(Condition.interactable).selectOption("1990");
        $(".react-datepicker__month-select")
                .shouldBe(Condition.interactable).selectOption("April");
        $$(".react-datepicker__day").findBy(text("14")).click();

        // Выбор предметов
        $("#subjectsInput")
                .shouldBe(Condition.editable).setValue("Maths").pressEnter();
        $("#subjectsInput")
                .shouldBe(Condition.editable).setValue("Economics").pressEnter();
        $("#subjectsInput")
                .shouldBe(Condition.editable).setValue("English").pressEnter();

        // Выбор хобби
        $x("//label [text()='Sports']")
                .shouldBe(Condition.clickable).click();
        $x("//label [text()='Music']")
                .shouldBe(Condition.clickable).click();

        // Выбор картинки
        $("#uploadPicture").uploadFromClasspath("alan-wake-2.jpg");

        // Заполнение адреса
        $("#currentAddress")
                .shouldBe(Condition.editable).setValue("Room 101, Heart o' the City Hotel, 2200 Cyber Avenue");
        $("#state input")
                .shouldBe(Condition.interactable).setValue("Uttar Pradesh").pressEnter();
        $("#city input")
                .shouldBe(Condition.interactable).setValue("Lucknow").pressEnter();

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

        // Проверка данных в таблице ответа
        $(".table-responsive").shouldHave(
                text("Student Name %s".formatted("Дебаг Логов")),
                text("Student Email %s".formatted("please_work_finally@test.ru")),
                text("Gender %s".formatted("Male")),
                text("Mobile %s".formatted("7404500200")),
                text("Date of Birth %s %s,%s".formatted("14", "April", "1990")),
                text("Subjects %s, %s, %s".formatted("Maths", "Economics", "English")),
                text("Hobbies %s, %s".formatted("Sports", "Music")),
                text("Picture %s".formatted("alan-wake-2.jpg")),
                text("Address %s".formatted("Room 101, Heart o' the City Hotel, 2200 Cyber Avenue")),
                text("State and City %s %s".formatted("Uttar Pradesh", "Lucknow"))
        );
    }

    @Test
    @DisplayName("PF-CASE-002. Успешное заполнение обязательных полей формы регистрации")
    void shouldRegisterUserWhenARequiredFieldsAreValid() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Крофтова");
        $("input.form-check-input[value='Female']")
                .shouldBe(Condition.clickable).click();
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("7404500300");

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

        // Проверка данных в таблице ответа
        $(".table-responsive").shouldHave(
                text("Student Name %s".formatted("Лариса Крофтова")),
                text("Gender %s".formatted("Female")),
                text("Mobile %s".formatted("7404500300"))
        );
    }

    // ---------- Негативные тесты
    @Test
    @DisplayName("PF-CASE-003. Ошибка - остуствует обязательное поле [Фамилия]")
    void shouldNotRegisterWhenLastNameIsEmpty() {
        // Открываем форму на весь экран
        open("/automation-practice-form");
        WebDriverRunner.getWebDriver().manage().window().maximize();

        // Заполнение обязательных полей - без фамилии
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input.form-check-input[value='Female']")
                .shouldBe(Condition.clickable).click();
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("7404500300");

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

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
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Крофтова");
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("7404500300");

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

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
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Крофтова");
        $("input.form-check-input[value='Female']")
                .shouldBe(Condition.clickable).click();

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

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
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Крофтова");
        $("input.form-check-input[value='Female']")
                .shouldBe(Condition.clickable).click();
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("7404500300");
        $("#userEmail")
                .shouldBe(Condition.editable).setValue("@please_work_finally@test.ru");

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

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
        $("input#firstName")
                .shouldBe(Condition.editable).setValue("Лариса");
        $("input#lastName")
                .shouldBe(Condition.editable).setValue("Крофтова");
        $("input.form-check-input[value='Female']")
                .shouldBe(Condition.clickable).click();
        $("#userNumber")
                .shouldBe(Condition.editable).setValue("740450030");

        // Подтверждение
        $("#submit")
                .shouldBe(Condition.clickable).click();

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
