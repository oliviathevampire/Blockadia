package team.hdt.sandboxgame.game_engine.common.registry;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nonnull;

public interface RegistryEntry {
    void setIdentifier(Identifier identifier);

    @Nonnull
    Identifier getIdentifier();
}
