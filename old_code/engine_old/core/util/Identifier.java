package team.hdt.blockadia.engine.core.util;

import ga.pheonix.utillib.utils.anouncments.Nonnull;

import java.io.InputStream;

public class Identifier {

    public static final String DEFAULT_NAMESPACE = "default";

    @Nonnull
    private String namespace;
    @Nonnull
    private String path;

    public Identifier(String location) {
        String[] IdentifierRaw = location.split(":", 2);
        if (IdentifierRaw.length > 1) {
            System.out.println("s");
            this.namespace = IdentifierRaw[0];
            this.path= IdentifierRaw[1];
        } else {
            this.namespace = "blockadia";
            this.path = location;
        }
    }

    public Identifier(String domain, String location) {
        this.namespace = domain;
        this.path = location;
    }

    public Identifier(Identifier location) {
        this.namespace = location.namespace;
        this.path = location.path;
    }

    public Identifier(Identifier folder, String location) {
        this.path = folder.namespace + ":" + folder.path + "/" + location;
    }

    @Nonnull
    public String getNamespace() {
        return this.namespace;
    }

    @Nonnull
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Identifier) {
            Identifier identifier = (Identifier) o;
            return this.namespace.equals(identifier.namespace) && this.path.equals(identifier.path);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int result = this.namespace.hashCode();
        result = 31 * result + this.path.hashCode();
        return result;
    }

    public InputStream getInputStream() {
        return Identifier.class.getResourceAsStream("/assets/" + namespace + "/" + path);
    }

    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }

}