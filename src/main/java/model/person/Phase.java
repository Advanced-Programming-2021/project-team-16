package model.person;

public enum Phase {
    DRAW("draw phase"),
    STANDBY("standby phase"),
    MAIN_1("main phase 1"),
    BATTLE("battle phase"),
    MAIN_2("main phase 2"),
    END("end phase");

    private final String phaseName;

    Phase(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getPhaseName() {
        return this.phaseName;
    }
}