package team.hdt.blockadia.engine.core.item;

import team.hdt.blockadia.engine.client.util.Identifier;
import team.hdt.blockadia.engine.client.util.Nonnull;
import team.hdt.blockadia.engine.core.registries.RegistryEntry;
import team.hdt.blockadia.engine.core.util.IItemProvider;

public class ItemType implements RegistryEntry, IItemProvider {

    private Identifier registryIdentifier;

    public ItemType() {

    }

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
        return "Item{id=" + this.registryIdentifier + "}";
    }

    public ItemGroup setItemGroup(ItemGroup group) {
        return group;
    }

    @Override
    public ItemType getItem() {
        return this;
    }

}