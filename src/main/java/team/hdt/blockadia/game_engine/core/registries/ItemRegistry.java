package team.hdt.blockadia.game_engine.core.registries;

import team.hdt.blockadia.game_engine.client.util.Identifier;
import team.hdt.blockadia.game_engine.core.item.ItemStack;
import team.hdt.blockadia.game_engine.core.item.ItemType;

public class ItemRegistry {

    public static final IdRegistry<ItemType> REGISTRY = new DefaultedHashIdRegistry<ItemType>()
            .withDefaultEntry(ItemStack.EMPTY.getItem());

    public static void register() {

    }

    private static void registerBlockType(String registryName, ItemType type, int id) {
        REGISTRY.register(new Identifier(registryName), type, id);
    }

}
