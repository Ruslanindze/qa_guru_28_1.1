package qa.guru.data;

import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

public enum Hobby {
    SPORTS("Sports"),
    READING("Reading"),
    MUSIC("Music");

    @Getter
    private final String value;

    Hobby(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Stream.of(Hobby.values())
                .map(Hobby::getValue)
                .toList();
    }
}
