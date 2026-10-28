package qa.guru.data;

import lombok.Getter;

import java.util.List;

@Getter
public enum State {
    NCR("NCR", List.of("Delhi", "Gurgaon", "Noida")),
    UTTAR_PRADESH("Uttar Pradesh", List.of("Agra", "Lucknow", "Merrut")),
    HARYANA("Haryana", List.of("Karnal", "Panipat")),
    RAJASTHAN("Rajasthan", List.of("Jaipur", "Jaiselmer"));

    private final String state;
    private final List<String> cities;

    State(String state, List<String> cities) {
        this.state = state;
        this.cities = cities;
    }
}
