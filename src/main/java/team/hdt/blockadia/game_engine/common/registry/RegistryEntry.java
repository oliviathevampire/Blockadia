package team.hdt.blockadia.game_engine.common.registry;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nonnull;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
