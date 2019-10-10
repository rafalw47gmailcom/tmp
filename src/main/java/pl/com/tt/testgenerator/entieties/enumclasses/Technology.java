package pl.com.tt.testgenerator.entieties.enumclasses;

public enum Technology {
    BACK_END("back-end"), FRONT_END("front-end"), FULL_STACK("full-stack"), TESTER("tester");

    private String name;

    Technology(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
