package pl.com.tt.testgenerator.entieties.enumclasses;

public enum SkillLevel {
    JUNIOR("junior"), MID("mid"), SENIOR("senior");

    private String name;

    SkillLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

