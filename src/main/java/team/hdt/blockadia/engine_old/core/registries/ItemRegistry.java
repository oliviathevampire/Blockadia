package team.hdt.blockadia.old_engine_code_1.core.registries;

import team.hdt.blockadia.old_engine_code_1.core.item.ItemStack;
import team.hdt.blockadia.old_engine_code_1.core.item.ItemType;
import team.hdt.blockadia.old_engine_code_1.core.util.Identifier;

public class ItemRegistry {

    public static final IdRegistry<ItemType> REGISTRY = new DefaultedHashIdRegistry<ItemType>()
            .withDefaultEntry(ItemStack.EMPTY.getItem());

    public static void register() {

    }

    private static void registerBlockType(String registryName, ItemType type, int id) {
        REGISTRY.register(new Identifier(registryName), type, id);
    }

}
