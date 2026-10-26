package qa.guru.data;

import java.util.Objects;

public record UserTextBoxRecord(
        String fullName,
        String email,
        String currentAddress,
        String permanentAddress
) {
    public static UserTextBoxRecord fromUiData(String fullName, String email,
                                               String currentAddress, String permanentAddress) {
        return new UserTextBoxRecord(
                Objects.requireNonNullElse(fullName, "")
                        .replace("Name:", "").trim(),
                Objects.requireNonNullElse(email, "")
                        .replace("Email:", "").trim(),
                Objects.requireNonNullElse(currentAddress, "")
                        .replace("Current Address :", "").trim(),
                Objects.requireNonNullElse(permanentAddress, "")
                        .replace("Permananet Address :", "").trim()
        );
    }
}
