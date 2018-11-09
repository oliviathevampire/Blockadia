package team.hdt.blockadia.engine.core.util;

public enum GameSide {

    CLIENT,
    CORE,
    SERVER,
    UNIVERSAL;

    public boolean isServer() {
        return !this.isClient();
    }

    public boolean isClient() {
        return this == CLIENT;
    }

    public boolean isUniversal() {
        return this == UNIVERSAL;
    }

}
