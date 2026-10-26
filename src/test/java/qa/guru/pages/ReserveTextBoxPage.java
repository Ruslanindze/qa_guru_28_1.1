package qa.guru.pages;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import qa.guru.config.Environment;
import qa.guru.data.UserTextBoxRecord;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


@Getter
public class ReserveTextBoxPage {
    private final String PAGE_URL = "/one-page-form/text-box.html";

    private final SelenideElement inputFullName = $("#userName");
    private final SelenideElement inputEmail = $("#userEmail");
    private final SelenideElement inputCurrentAddress = $("#currentAddress");
    private final SelenideElement inputPermanentAddress = $("#permanentAddress");
    private final SelenideElement buttonSubmit = $("#submit");

    private final SelenideElement outputBlock = $("#output");
    private final SelenideElement outputName = outputBlock.$("#name");
    private final SelenideElement outputEmail = outputBlock.$("#email");
    private final SelenideElement outputCurrentAddress = outputBlock.$("#currentAddress");
    private final SelenideElement outputPermanentAddress = outputBlock.$("#permanentAddress");

    public ReserveTextBoxPage openPage(Environment env) {
        open(env.getBaseUrl() + PAGE_URL);
        return this;
    }

    public ReserveTextBoxPage typeFullName(String fullName) {
        inputFullName.setValue(fullName);
        return this;
    }

    public ReserveTextBoxPage typeEmail(String email) {
        inputEmail.setValue(email);
        return this;
    }

    public ReserveTextBoxPage typeCurrentAddress(String address) {
        inputCurrentAddress.setValue(address);
        return this;
    }

    public ReserveTextBoxPage typePermanentAddress(String address) {
        inputPermanentAddress.setValue(address);
        return this;
    }

    public ReserveTextBoxPage typeFromUserTextBox(UserTextBoxRecord userTextBox) {
        typeFullName(userTextBox.fullName());
        typeEmail(userTextBox.email());
        typeCurrentAddress(userTextBox.currentAddress());
        typePermanentAddress(userTextBox.permanentAddress());

        return this;
    }

    public UserTextBoxRecord getUserTextBoxRecord() {
        return UserTextBoxRecord.fromUiData(
                getOutputName().exists() ? getOutputName().text() : null,
                getOutputEmail().exists() ? getOutputEmail().text() : null,
                getOutputCurrentAddress().exists() ? getOutputCurrentAddress().text() : null,
                getOutputPermanentAddress().exists() ? getOutputPermanentAddress().text() : null
        );
    }

    public ReserveTextBoxPage submitBox() {
        buttonSubmit.click();
        return this;
    }
}
