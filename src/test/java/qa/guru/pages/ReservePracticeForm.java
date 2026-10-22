package qa.guru.pages;

import static com.codeborne.selenide.Selenide.open;

public class ReservePracticeForm {
    private final String BASE_URL = "https://qa-guru.github.io";
    private final String BASE_PAGE_URL = "/automation-practice-form";




    public ReservePracticeForm openPage() {
        openPage(BASE_PAGE_URL);
        return this;
    }

    public ReservePracticeForm openPage(String pageUrl) {
        open(BASE_URL + pageUrl);
        return this;
    }
}
