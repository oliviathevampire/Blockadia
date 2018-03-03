package net.thegaminghuskymc.sandboxgame.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.util.JsonUtils;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

@SideOnly(Side.CLIENT)
public class ItemOverride {
    private final ResourceLocation location;
    private final Map<ResourceLocation, Float> mapResourceValues;

    public ItemOverride(ResourceLocation locationIn, Map<ResourceLocation, Float> propertyValues) {
        this.location = locationIn;
        this.mapResourceValues = propertyValues;
    }

    /**
     * Get the location of the target model
     */
    public ResourceLocation getLocation() {
        return this.location;
    }

    boolean matchesItemStack(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase livingEntity) {
        Item item = stack.getItem();

        for (Entry<ResourceLocation, Float> entry : this.mapResourceValues.entrySet()) {
            IItemPropertyGetter iitempropertygetter = item.getPropertyGetter(entry.getKey());

            if (iitempropertygetter == null || iitempropertygetter.apply(stack, worldIn, livingEntity) < ((Float) entry.getValue()).floatValue()) {
                return false;
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    static class Deserializer implements JsonDeserializer<ItemOverride> {
        public ItemOverride deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "model"));
            Map<ResourceLocation, Float> map = this.makeMapResourceValues(jsonobject);
            return new ItemOverride(resourcelocation, map);
        }

        protected Map<ResourceLocation, Float> makeMapResourceValues(JsonObject p_188025_1_) {
            Map<ResourceLocation, Float> map = Maps.<ResourceLocation, Float>newLinkedHashMap();
            JsonObject jsonobject = JsonUtils.getJsonObject(p_188025_1_, "predicate");

            for (Entry<String, JsonElement> entry : jsonobject.entrySet()) {
                map.put(new ResourceLocation(entry.getKey()), Float.valueOf(JsonUtils.getFloat(entry.getValue(), entry.getKey())));
            }

            return map;
        }
    }
}