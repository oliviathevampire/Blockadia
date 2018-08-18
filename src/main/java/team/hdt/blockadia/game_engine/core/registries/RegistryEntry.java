package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine_old.common.Identifier;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nonnull;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
