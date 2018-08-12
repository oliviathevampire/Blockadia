package team.hdt.sandboxgame.game_engine.common.registry;

import team.hdt.sandboxgame.game_engine.common.Identifier;

import javax.annotation.Nullable;

public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
