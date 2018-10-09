package team.hdt.blockadia.bml.side;

public enum Side {

    CLIENT,
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
