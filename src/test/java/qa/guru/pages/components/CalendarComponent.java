package qa.guru.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CalendarComponent {
    private final SelenideElement dateOfBirthYear = $("select.react-datepicker__year-select");
    private final SelenideElement dateOfBirthMonth = $(".react-datepicker__month-select");
    private final ElementsCollection dateOfBirthDays = $$(".react-datepicker__day");

    public void setDateOfBirth(String year, String month, String day) {
        dateOfBirthYear.selectOption(year);
        dateOfBirthMonth.selectOption(month);
        dateOfBirthDays.findBy(text(day)).click();
    }
}
