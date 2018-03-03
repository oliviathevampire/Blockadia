package net.timmy.timmylib.utils;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.modding.ModInfo;
import net.thegaminghuskymc.sgf.registries.IForgeRegistryEntry;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;

import java.util.HashMap;

@ModInfo.EventBusSubscriber(modid = Refs.MODID)
public class ProxyRegistry {

    private static Multimap<Class<?>, RegistryEntry> entries = MultimapBuilder.hashKeys().arrayListValues().build();

    private static HashMap<Block, Item> temporaryItemBlockMap = new HashMap();

    public static <T extends IForgeRegistryEntry<T>> void register(IForgeRegistryEntry<T> obj) {
        entries.put(obj.getRegistryType(), new RegistryEntry(obj.getRegistryName(), obj));

        if (obj instanceof ItemBlock) {
            ItemBlock iblock = (ItemBlock) obj;
            Block block = iblock.getBlock();
            temporaryItemBlockMap.put(block, iblock);
        }
    }

    private static Item getItemMapping(Block block) {
        Item i = Item.getItemFromBlock(block);
        if ((i == null || i == Item.getItemFromBlock(Blocks.AIR)) && temporaryItemBlockMap.containsKey(block))
            return temporaryItemBlockMap.get(block);

        return i;
    }

    public static ItemStack newStack(Block block) {
        return newStack(block, 1);
    }

    public static ItemStack newStack(Block block, int size) {
        return newStack(block, size, 0);
    }

    public static ItemStack newStack(Block block, int size, int meta) {
        return newStack(getItemMapping(block), size, meta);
    }

    public static ItemStack newStack(Item item) {
        return newStack(item, 1);
    }

    public static ItemStack newStack(Item item, int size) {
        return newStack(item, size, 0);
    }

    public static ItemStack newStack(Item item, int size, int meta) {
        return new ItemStack(item, size, meta);
    }

    /*@SubscribeEvent
    public static void onRegistryEvent(RegistryEvent.Register event) {
        Class<?> type = event.getRegistry().getRegistrySuperType();
        if (entries.containsKey(type)) {
            Collection<RegistryEntry> ourEntries = entries.get(type);
            for (RegistryEntry entry : ourEntries)
                event.getRegistry().register(entry.entry);
        }
    }*/

    private static class RegistryEntry {

        public final ResourceLocation res;
        public final IForgeRegistryEntry<?> entry;

        public RegistryEntry(ResourceLocation res, IForgeRegistryEntry<?> entry) {
            this.res = res;
            this.entry = entry;
        }

    }

}