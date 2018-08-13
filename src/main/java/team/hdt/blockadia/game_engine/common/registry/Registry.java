package team.hdt.blockadia.game_engine.common.registry;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nullable;


public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
