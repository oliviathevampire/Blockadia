package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine_old.common.Identifier;
import team.hdt.blockadia.game_engine_old.common.util.interfaces.Nullable;


public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
