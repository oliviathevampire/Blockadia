package team.hdt.blockadia.old_engine_code_1.core.registries;

import ga.pheonix.utillib.utils.anouncments.Nonnull;
import team.hdt.blockadia.old_engine_code_1.core.util.Identifier;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
