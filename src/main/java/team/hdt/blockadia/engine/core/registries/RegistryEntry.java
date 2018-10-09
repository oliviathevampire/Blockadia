package team.hdt.blockadia.engine.core.registries;

import team.hdt.blockadia.engine.client.util.Identifier;
import team.hdt.blockadia.engine.client.util.Nonnull;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
