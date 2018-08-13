package team.hdt.blockadia.game_engine.common.world.block;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.registry.RegistryEntry;
import team.hdt.blockadia.game_engine.common.util.interfaces.Nonnull;

public class BlockType implements RegistryEntry {

    private Identifier registryIdentifier;

    @Nonnull
    @Override
    public Identifier getIdentifier() {
        if (this.registryIdentifier == null) {
            throw new IllegalStateException("Cannot get identifier of entry not yet registered");
        }
        return this.registryIdentifier;
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.registryIdentifier != null) {
            throw new IllegalStateException("Cannot set registry identifier twice");
        }
        this.registryIdentifier = identifier;
    }

    @Override
    public String toString() {
        if (this.registryIdentifier == null) {
            return super.toString();
        }
        return "Block{id=" + this.registryIdentifier + "}";
    }
}
