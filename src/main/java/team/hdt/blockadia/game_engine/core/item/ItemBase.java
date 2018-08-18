package team.hdt.blockadia.game_engine.core.item;

import team.hdt.blockadia.game_engine.core.entity.BaseEntity;
import team.hdt.blockadia.game_engine_old.client.rendering.TexturedModel;

import java.util.List;

public abstract class ItemBase extends BaseEntity {

    public boolean isInHand;
    public boolean isNotLivingEntity;
    public List enchantments;

    public ItemBase(TexturedModel model, int id, boolean isItem) {
        super(model, id, isItem);
    }

    public ItemBase(TexturedModel model, int id, boolean isItem, boolean canburnInDay, float scale) {
        super(model, id, isItem, canburnInDay, scale);
    }
    //to be able to make items that can attack the player
    public ItemBase(TexturedModel model, int id, boolean canburnInDay,boolean isNotLivingEntity, boolean isinhand) {
        super(model, id, true, canburnInDay, 1.0F);
        this.isNotLivingEntity = isNotLivingEntity;
        this.isInHand = isinhand;
    }

    public void setEnchantments(List enchantments) {
        this.enchantments = enchantments;
    }

    public List getEnchantments() {
        return enchantments;
    }
}