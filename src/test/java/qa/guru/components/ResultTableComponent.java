package qa.guru.components;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.helpers.TableUiHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class ResultTableComponent {
    private final SelenideElement tableResult;

    public ResultTableComponent(String cssSelector) {
        tableResult = $(cssSelector);
    }

    public void shouldHaveCellText(String rowName, String columnName, String value) {
        TableUiHelper
                .getCellByNames(this.tableResult, rowName, columnName)
                .shouldHave(text(value));
    }

    public void shouldHaveRowContainingText(String rowName, String value) {
        TableUiHelper
                .getRowByName(this.tableResult, rowName)
                .shouldHave(text(value));
    }
}
