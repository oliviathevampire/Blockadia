package team.hdt.sandboxgame.game_engine.common.world.block;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.registry.RegistryEntry;
import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nonnull;

public class BlockType implements RegistryEntry {
    private Identifier registryIdentifier;

    @Override
    public void setIdentifier(Identifier identifier) {
        if (this.registryIdentifier != null) {
            throw new IllegalStateException("cannot set registry identifier twice");
        }
        this.registryIdentifier = identifier;
    }

    @Nonnull
    @Override
    public Identifier getIdentifier() {
        if (this.registryIdentifier == null) {
            throw new IllegalStateException("cannot get identifier of entry not yet registered");
        }
        return this.registryIdentifier;
    }

    @Override
    public String toString() {
        if (this.registryIdentifier == null) {
            return super.toString();
        }
        return "Block{id=" + this.registryIdentifier + "}";
    }
}
