package pl.com.tt.testgenerator.entieties.enumclasses;

public enum RecruitmentState {
    NEW("nowa"), WAITING_FOR_TEST_RESULT("oczekuje na wynik"), WAITING_FOR_REVIEW("oczekuje na sprawdzenie"),
    FINISHED("zakończona");

    private String name;

    RecruitmentState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}