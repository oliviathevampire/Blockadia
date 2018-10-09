package team.hdt.blockadia.engine.core.registries;

import team.hdt.blockadia.engine.client.util.Identifier;
import team.hdt.blockadia.engine.client.util.Nullable;


public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
