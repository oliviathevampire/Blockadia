package team.hdt.blockadia.game_engine.core.item;

import team.hdt.blockadia.game_engine.client.util.Identifier;

public class ItemBuilder {

    private String translationKey;
    private Identifier registryName;
    private ItemGroup itemGroup;

    public ItemBuilder withTranslationKey(String translationKey) {
        this.translationKey = translationKey;
        return this;
    }

    public ItemBuilder withRegistryName(Identifier registryName) {
        this.registryName = registryName;
        return this;
    }

    public ItemBuilder withItemGroup(ItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        if (itemGroup == null) {
            this.itemGroup = ItemGroup.BASIC_ITEMS;
        }
        return this;
    }

    public ItemType build() {
        ItemType itemType = new ItemType();
        itemType.setItemGroup(itemGroup);
        return itemType;
    }

}
