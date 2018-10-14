package team.hdt.blockadia.engine.core.registries;

import ga.pheonix.utillib.utils.anouncments.Nullable;
import team.hdt.blockadia.engine.core.util.Identifier;


public interface Registry<T extends RegistryEntry> {
    void register(Identifier identifier, T value);

    @Nullable
    T get(Identifier identifier);
}
