package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.client.util.Nonnull;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
