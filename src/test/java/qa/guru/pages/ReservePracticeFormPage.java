package qa.guru.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.config.Environment;
import qa.guru.pages.components.ResultTableComponent;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Getter
public class ReservePracticeFormPage {
    private static final String PAGE_URL = "/one-page-form/automation-practice-form.html";

    private final SelenideElement firstName = $("#firstName");
    private final SelenideElement lastName = $("#lastName");
    private final SelenideElement userGender = $("#genterWrapper");
    private final SelenideElement userNumber = $("#userNumber");

    private final SelenideElement buttonSubmit = $("button#submit");
    private final ResultTableComponent resultTableComponent = new ResultTableComponent("#resultModal");

    public ReservePracticeFormPage openPage(Environment env) {
        open(env.getBaseUrl() + PAGE_URL);
        return this;
    }

    public ReservePracticeFormPage setFirstName(String firstName) {
        this.firstName.setValue(firstName);
        return this;
    }

    public ReservePracticeFormPage setLastName(String lastName) {
        this.lastName.setValue(lastName);
        return this;
    }

    public ReservePracticeFormPage setUserGender(String gender) {
        this.userGender.$(byText(gender)).click();
        return this;
    }

    public ReservePracticeFormPage setUserNumber(String userNumber) {
        this.userNumber.setValue(userNumber);
        return this;
    }

    public ReservePracticeFormPage submit() {
        this.buttonSubmit.click();
        return this;
    }

    public SelenideElement getTableResult() {
        return resultTableComponent.getTableResult();
    }
}
