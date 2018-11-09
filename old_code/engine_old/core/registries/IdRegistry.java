package team.hdt.blockadia.old_engine_code_1.core.registries;

import ga.pheonix.utillib.utils.anouncments.Nullable;
import team.hdt.blockadia.old_engine_code_1.core.util.Identifier;

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
