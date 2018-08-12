package team.hdt.sandboxgame.game_engine.common.registry;

import team.hdt.sandboxgame.game_engine.common.Identifier;

import javax.annotation.Nonnull;

public interface RegistryEntry {
    void setIdentifier(Identifier identifier);

    @Nonnull
    Identifier getIdentifier();
}
