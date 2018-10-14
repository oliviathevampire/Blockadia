package team.hdt.blockadia.engine.core.registries;

import team.hdt.blockadia.engine.core.item.ItemStack;
import team.hdt.blockadia.engine.core.item.ItemType;
import team.hdt.blockadia.engine.core.util.Identifier;

public class ItemRegistry {

    public static final IdRegistry<ItemType> REGISTRY = new DefaultedHashIdRegistry<ItemType>()
            .withDefaultEntry(ItemStack.EMPTY.getItem());

    public static void register() {

    }

    private static void registerBlockType(String registryName, ItemType type, int id) {
        REGISTRY.register(new Identifier(registryName), type, id);
    }

}
