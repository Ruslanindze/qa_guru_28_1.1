package qa.guru.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import qa.guru.config.Environment;
import qa.guru.data.UserDto;
import qa.guru.pages.components.CalendarComponent;
import qa.guru.pages.components.ResultTableComponent;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@Getter
public class PracticeFormPage {
    private final String PAGE_URL = "/automation-practice-form";

    private final ResultTableComponent resultTableComponent = new ResultTableComponent(".table-responsive");
    private final CalendarComponent calendarComponent = new CalendarComponent();

    private final SelenideElement firstName = $("#firstName");
    private final SelenideElement lastName = $("#lastName");
    private final SelenideElement userEmail = $("#userEmail");
    private final SelenideElement userGender = $("#genterWrapper");
    private final SelenideElement userNumber = $("#userNumber");

    private final SelenideElement userAddress = $("#currentAddress");
    private final SelenideElement userState = $("#state input");
    private final SelenideElement userCity = $("#city input");

    private final SelenideElement subjectsInput = $("#subjectsInput");
    private final SelenideElement hobbiesInput = $("#hobbiesWrapper");
    private final SelenideElement uploadPicture = $("#uploadPicture");
    private final SelenideElement dateOfBirthCalendar = $("#dateOfBirthInput");

    private final SelenideElement buttonSubmit = $("button#submit");

    public PracticeFormPage openPage(Environment env) {
        open(env.getBaseUrl() + PAGE_URL);
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
        dateOfBirthCalendar.click();
        calendarComponent.setDateOfBirth(year, month, day);
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
            hobbiesInput.$$("label").findBy(text(hobby)).click();
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

    public SelenideElement getTableResult() {
        return resultTableComponent.getTableResult();
    }

    public PracticeFormPage registerAs(UserDto userDto) {
        // Блок дня рождения - все поля должны быть заполнены
        if (StringUtils.isNotBlank(userDto.getBirthYear()) &&
                StringUtils.isNotBlank(userDto.getBirthMonth()) &&
                StringUtils.isNotBlank(userDto.getBirthDay())) {

            setUserDateOfBirth(userDto.getBirthYear(), userDto.getBirthMonth(), userDto.getBirthDay());
        }

        // Несвязные блоки
        if (StringUtils.isNotBlank(userDto.getFirstName())) setFirstName(userDto.getFirstName());
        if (StringUtils.isNotBlank(userDto.getLastName())) setLastName(userDto.getLastName());
        if (StringUtils.isNotBlank(userDto.getGender())) setUserGender(userDto.getGender());
        if (StringUtils.isNotBlank(userDto.getPhoneNumber())) setUserNumber(userDto.getPhoneNumber());

        if (StringUtils.isNotBlank(userDto.getEmail())) setUserEmail(userDto.getEmail());
        if (CollectionUtils.isNotEmpty(userDto.getSubjects())) setSubjects(userDto.getSubjects());
        if (CollectionUtils.isNotEmpty(userDto.getHobbies())) setHobbies(userDto.getHobbies());
        if (StringUtils.isNotBlank(userDto.getPicture())) uploadPicture(userDto.getPicture());
        if (StringUtils.isNotBlank(userDto.getCurrentAddress())) setUserAddress(userDto.getCurrentAddress());

        // Установка штата и города
        if (StringUtils.isNotBlank(userDto.getState()) && StringUtils.isNotBlank(userDto.getState())) {
            setUserState(userDto.getState());
            setUserCity(userDto.getCity());
        }

        return this;
    }
}
