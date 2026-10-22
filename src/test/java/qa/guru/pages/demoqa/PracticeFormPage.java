package qa.guru.pages.demoqa;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.helpers.TableUiHelper;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


@Getter
public class PracticeFormPage {
    private final String BASE_URL = "https://demoqa.com";
    private final String BASE_PAGE_URL = "/automation-practice-form";

    private final SelenideElement firstName = $("#firstName");
    private final SelenideElement lastName = $("#lastName");
    private final SelenideElement userEmail = $("#userEmail");
    private final SelenideElement userGender = $("#genterWrapper");
    private final SelenideElement userNumber = $("#userNumber");

    private final SelenideElement dateOfBirthCalendar = $("#dateOfBirthInput");
    private final SelenideElement dateOfBirthYear = $("select.react-datepicker__year-select");
    private final SelenideElement dateOfBirthMonth = $(".react-datepicker__month-select");
    private final ElementsCollection dateOfBirthDays = $$(".react-datepicker__day");

    private final SelenideElement userAddress = $("#currentAddress");
    private final SelenideElement userState = $("#state input");
    private final SelenideElement userCity = $("#city input");

    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final ElementsCollection hobbiesInput = $("#hobbiesWrapper").$$("input");
    private final SelenideElement uploadPicture = $("#uploadPicture");

    private final SelenideElement buttonSubmit = $("button#submit");
    private final SelenideElement tableResult = $(".table-responsive");


    public PracticeFormPage openPage() {
        openPage(BASE_PAGE_URL);
        return this;
    }

    public PracticeFormPage openPage(String pageUrl) {
        open(BASE_URL + pageUrl);
        return this;
    }

    public PracticeFormPage setFirstName(String firstName) {
        this.firstName.setValue(firstName);
        return this;
    }

    public PracticeFormPage setLastName(String lastName) {
        this.lastName.setValue(lastName);
        return this;
    }

    public PracticeFormPage setUserEmail(String email) {
        this.userEmail.setValue(email);
        return this;
    }

    public PracticeFormPage setUserGender(String gender) {
        this.userGender.$(byText(gender)).click();
        return this;
    }

    public PracticeFormPage setUserNumber(String userNumber) {
        this.userNumber.setValue(userNumber);
        return this;
    }

    public PracticeFormPage setUserDateOfBirth(String year, String month, String day) {
        // Заполнение даты рождения через календарь
        dateOfBirthCalendar.click();
        dateOfBirthYear.selectOption(year);
        dateOfBirthMonth.selectOption(month);
        dateOfBirthDays.findBy(text(day)).click();

        return this;
    }

    public PracticeFormPage setSubjects(List<String> subjects) {
        subjects.forEach(subject -> {
            subjectsInput.setValue(subject).pressEnter();
        });
        return this;
    }

    public PracticeFormPage setHobbies(List<String> hobbies) {
        hobbies.forEach(hobby -> {
            hobbiesInput.findBy(text(hobby)).click();
        });
        return this;
    }

    public PracticeFormPage uploadPicture(String resourcePath) {
        uploadPicture.uploadFromClasspath(resourcePath);
        return this;
    }

    public PracticeFormPage setUserAddress(String address) {
        this.userAddress.setValue(address);
        return this;
    }

    public PracticeFormPage setUserState(String state) {
        this.userState.setValue(state).pressEnter();
        return this;
    }

    public PracticeFormPage setUserCity(String city) {
        this.userCity.setValue(city).pressEnter();
        return this;
    }

    public PracticeFormPage submit() {
        this.buttonSubmit.click();
        return this;
    }

    public SelenideElement getCellFromTableResult(String rowName, String columnName) {
        return TableUiHelper.getCellByNames(this.tableResult, rowName, columnName);
    }
}
