package team.hdt.blockadia.engine.client.util.languages;

public enum Language {

    ENGLISH("English"),
    GERMAN("Deutsch");

    private final String name;

    private Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
