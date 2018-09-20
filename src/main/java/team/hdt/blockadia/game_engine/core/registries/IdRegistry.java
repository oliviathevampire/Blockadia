package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.client.util.Nullable;

public interface IdRegistry<T extends RegistryEntry> extends Registry<T> {

    @Override
    default void register(Identifier identifier, T value) {
        throw new UnsupportedOperationException("Must register entry with id");
    }

    void register(Identifier identifier, T value, int id);

    @Nullable
    T get(int id);

    int getId(Identifier identifier);

}
