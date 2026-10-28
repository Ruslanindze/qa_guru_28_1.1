package qa.guru.data;

import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

public enum Subject {
    ENGLISH("English"),
    MATHS("Maths"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    COMPUTER_SCIENCE("Computer Science"),
    COMMERCE("Commerce"),
    ACCOUNTING("Accounting"),
    ECONOMICS("Economics"),
    ARTS("Arts"),
    SOCIAL("Social Studies"),
    HISTORY("History"),
    CIVICS("Civics");

    @Getter
    private final String value;

    Subject(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Stream.of(Subject.values())
                .map(Subject::getValue)
                .toList();
    }
}
