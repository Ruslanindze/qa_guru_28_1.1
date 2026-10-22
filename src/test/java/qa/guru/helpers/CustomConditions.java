package qa.guru.helpers;

import com.codeborne.selenide.*;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.CheckResult.Verdict.ACCEPT;
import static com.codeborne.selenide.CheckResult.Verdict.REJECT;

public final class CustomConditions {

    private CustomConditions() {
        throw new IllegalStateException("Utility class");
    }

    public static final WebElementCondition valid = new WebElementCondition("valid") {
        @Override
        public CheckResult check(Driver driver, WebElement element) {
            boolean isValid = Boolean.TRUE.equals(Selenide.executeJavaScript(
                    "return arguments[0].checkValidity();", element
            ));
            return new CheckResult(isValid ? ACCEPT : REJECT, null);
        }
    };

    public static final WebElementCondition invalid = new WebElementCondition("valid") {
        @Override
        public CheckResult check(Driver driver, WebElement element) {
            boolean isValid = Boolean.TRUE.equals(Selenide.executeJavaScript(
                    "return arguments[0].checkValidity();", element
            ));
            return new CheckResult(!isValid ? ACCEPT : REJECT, null);
        }
    };
}