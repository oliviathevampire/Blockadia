package team.hdt.blockadia.engine.core.registries;

import ga.pheonix.utillib.utils.anouncments.Nonnull;
import team.hdt.blockadia.engine.core.util.Identifier;

public interface RegistryEntry {
    @Nonnull
    Identifier getIdentifier();

    void setIdentifier(Identifier identifier);
}
