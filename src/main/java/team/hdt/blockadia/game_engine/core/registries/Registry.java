package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.client.util.Nullable;


public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
