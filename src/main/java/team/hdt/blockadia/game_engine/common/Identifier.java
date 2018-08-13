package team.hdt.blockadia.game_engine.common;

import team.hdt.blockadia.game_engine.common.util.interfaces.Nonnull;

public class Identifier {

    public static final String DEFAULT_NAMESPACE = "default";

    @Nonnull
    private final String namespace;
    @Nonnull
    private final String path;

    public Identifier(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public Identifier(String path) {
        this(DEFAULT_NAMESPACE, path);
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

    @Override
    public String toString() {
        return this.namespace + ":" + this.path;
    }
}
