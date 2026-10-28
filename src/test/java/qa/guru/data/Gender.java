package qa.guru.data;

import lombok.Getter;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    @Getter
    private final String value;

    Gender(String value) {
        this.value = value;
    }
}
