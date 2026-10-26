package qa.guru.tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import qa.guru.config.Environment;
import qa.guru.data.UserTextBoxRecord;
import qa.guru.helpers.BaseTest;
import qa.guru.helpers.UserTextBoxAggregator;
import qa.guru.pages.TextBoxPage;

import static org.assertj.core.api.Assertions.assertThat;
import static qa.guru.helpers.CustomConditions.invalid;


public class TextBoxTest extends BaseTest {
    private final Environment env = Environment.DEMOQA;
    private final TextBoxPage textBoxPage = new TextBoxPage();

    @ParameterizedTest(name = "TB-CASE-001. Заполнены все поля | {0}")
    @CsvFileSource(resources = "/textbox/test_data.csv", numLinesToSkip = 1, nullValues = "null")
    void shouldSubmitFormWithAllFields(@AggregateWith(UserTextBoxAggregator.class) UserTextBoxRecord userTextBox) {
        // Given and When
        textBoxPage
                .openPage(env)
                .typeFromUserTextBox(userTextBox)
                .submitBox();

        // Then
        UserTextBoxRecord actualUserTextBox = textBoxPage.getUserTextBoxRecord();
        assertThat(actualUserTextBox)
                .as("Проверка корректного заполнения TextBox")
                .usingRecursiveComparison()
                .isEqualTo(userTextBox);
    }

    @ParameterizedTest(name = "TB-CASE-002. Заполнено одно поле | {0}, {1}, {2}, {3}")
    @CsvFileSource(resources = "/textbox/test_data_1f.csv", numLinesToSkip = 1)
    void shouldSubmitFormWithOneField(@AggregateWith(UserTextBoxAggregator.class) UserTextBoxRecord userTextBox) {
        // Given and When
        textBoxPage
                .openPage(env)
                .typeFromUserTextBox(userTextBox)
                .submitBox();

        // Then
        UserTextBoxRecord actualUserTextBox = textBoxPage.getUserTextBoxRecord();
        assertThat(actualUserTextBox)
                .as("Проверка корректного заполнения TextBox")
                .usingRecursiveComparison()
                .isEqualTo(userTextBox);
    }

    @ParameterizedTest(name = "TB-CASE-003. Заполнено одно поле | {1}")
    @CsvFileSource(resources = "/textbox/test_data_err_email.csv", numLinesToSkip = 1)
    void shouldNotSubmitFormWithErrEmail(@AggregateWith(UserTextBoxAggregator.class) UserTextBoxRecord userTextBox) {
        // Given and When
        textBoxPage
                .openPage(env)
                .typeFromUserTextBox(userTextBox)
                .submitBox();

        // Then
        textBoxPage.getInputEmail().shouldBe(invalid);
        textBoxPage.getOutputBlock().shouldBe(Condition.hidden);
    }
}
