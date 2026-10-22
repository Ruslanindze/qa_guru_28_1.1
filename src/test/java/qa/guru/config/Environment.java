package qa.guru.config;


public enum Environment {
    DEMOQA_FORM("https://demoqa.com", "/automation-practice-form"),
    QA_GURU_FORM("https://qa-guru.github.io", "/one-page-form/automation-practice-form.html");

    private final String baseUrl;
    private final String pageUrl;

    Environment(String baseUrl, String pageUrl) {
        this.baseUrl = baseUrl;
        this.pageUrl = pageUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    // Удобный метод, если нужно получить сразу полный URL
    public String getFullUrl() {
        return baseUrl + pageUrl;
    }
}
