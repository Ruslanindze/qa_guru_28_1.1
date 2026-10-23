package qa.guru.config;


public enum Environment {
    DEMOQA("https://demoqa.com"),
    QA_GURU("https://qa-guru.github.io");

    private final String baseUrl;

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
