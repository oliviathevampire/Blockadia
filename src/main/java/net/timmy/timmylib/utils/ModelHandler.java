package net.timmy.timmylib.utils;

import net.thegaminghuskymc.sandboxgame.client.Minecraft;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.state.BlockStateContainer;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.modding.ModInfo;
import net.thegaminghuskymc.sgf.fml.relauncher.ReflectionHelper;
import net.thegaminghuskymc.sgf.registries.IRegistryDelegate;
import net.thegaminghuskymc.sandboxgame.util.IStringSerializable;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sgf.fml.common.eventhandler.SubscribeEvent;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.timmy.th2.lib.LibMisc;
import net.timmy.timmylib.api.IModelRegister;
import net.timmy.timmylib.interf.*;
import net.timmy.timmylib.items.ItemMod;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntFunction;

@ModInfo.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public final class ModelHandler {

    public static final HashMap<String, ModelResourceLocation> resourceLocations = new HashMap<>();

    public ModelHandler() {
    }

    @SubscribeEvent
    public static void onRegister(ModelRegistryEvent event) {

        for (Object variantHolder : ItemMod.variantHolders) {
            IVariantHolder holder = (IVariantHolder) variantHolder;
            registerModels(holder);
        }

    }

    private static void registerModels(IVariantHolder holder) {
        String unique = holder.getUniqueModel();
        String prefix = holder.getPrefix();
        Item i = (Item) holder;
        ItemMeshDefinition def = holder.getCustomMeshDefinition();
        if (def != null) {
            ModelLoader.setCustomMeshDefinition((Item) holder, def);
        } else {
            registerModels(i, prefix, holder.getVariants(), unique, false);
        }

        if (holder instanceof IExtraVariantHolder) {
            IExtraVariantHolder extra = (IExtraVariantHolder) holder;
            registerModels(i, prefix, extra.getExtraVariants(), unique, true);
        }

    }

    private static void registerModels(Item item, String prefix, String[] variants, String uniqueVariant, boolean extra) {
        if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof IModBlock) {
            IModBlock hl2Block = (IModBlock) ((ItemBlock) item).getBlock();
            Class clazz = hl2Block.getVariantEnum();
            IProperty variantProp = hl2Block.getVariantProp();
            boolean ignoresVariant = false;
            IStateMapper mapper = hl2Block.getStateMapper();
            IProperty[] ignored = hl2Block.getIgnoredProperties();
            if (mapper != null || ignored != null && ignored.length > 0) {
                if (mapper != null) {
                    ModelLoader.setCustomStateMapper((Block) hl2Block, mapper);
                } else {
                    BlockStateContainer.Builder builder = new BlockStateContainer.Builder();

                    for (IProperty p : ignored) {
                        if (p == variantProp) {
                            ignoresVariant = true;
                        }

                        builder.ignore(p);
                    }

                    ModelLoader.setCustomStateMapper((Block) hl2Block, builder.build());
                }
            }

            if (clazz != null && !ignoresVariant) {
                registerVariantsDefaulted(item, (Block) hl2Block, clazz, variantProp.getName());
                return;
            }
        }

        for (int i = 0; i < variants.length; ++i) {
            String var = variants[i];
            if (!extra && uniqueVariant != null) {
                var = uniqueVariant;
            }

            ModelResourceLocation loc = new ModelResourceLocation(new ResourceLocation(prefix, var), "inventory");
            if (!extra) {
                ModelLoader.setCustomModelResourceLocation(item, i, loc);
                resourceLocations.put(getKey(item, i), loc);
            } else {
                ModelBakery.registerItemVariants(item, loc);
                resourceLocations.put(variants[i], loc);
            }
        }

    }

    private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(Item item, Block b, Class<T> enumclazz, String variantHeader) {
        String baseName = Objects.requireNonNull(b.getRegistryName()).toString();
        Enum[] var5 = enumclazz.getEnumConstants();

        for(Enum aVar5 : var5) {
            T e = (T) aVar5;
            String variantName = variantHeader + "=" + (e).getName();
            ModelResourceLocation loc = new ModelResourceLocation(baseName, variantName);
            int i = e.ordinal();
            ModelLoader.setCustomModelResourceLocation(item, i, loc);
            resourceLocations.put(getKey(item, i), loc);
        }

    }

    private static String getKey(Item item, int meta) {
        return "i_" + item.getRegistryName() + "@" + meta;
    }

    public static void init() {
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();

        for (Object variantHolder : ItemMod.variantHolders) {
            IVariantHolder holder = (IVariantHolder) variantHolder;
            if (holder instanceof IItemColorProvider) {
                itemColors.registerItemColorHandler(((IItemColorProvider) holder).getItemColor(), (Item) holder);
            }

            if (holder instanceof ItemBlock && ((ItemBlock) holder).getBlock() instanceof IBlockColorProvider) {
                Block block = ((ItemBlock) holder).getBlock();
                blockColors.registerBlockColorHandler(((IBlockColorProvider) block).getBlockColor(), block);
                itemColors.registerItemColorHandler(((IBlockColorProvider) block).getItemColor(), block);
            }
        }

    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent evt) {
        OBJLoader.INSTANCE.addDomain(LibMisc.MOD_ID.toLowerCase(Locale.ROOT));

        for(Block block : Block.REGISTRY) {
            if(block instanceof IModelRegister)
                ((IModelRegister) block).registerModels();
        }

        for(Item item : Item.REGISTRY) {
            if(item instanceof IModelRegister)
                ((IModelRegister) item).registerModels();
        }
    }

    public static void registerItemAllMeta(Item item, int range) {
        registerItemMetas(item, range, i -> item.getRegistryName().getResourcePath());
    }

    public static void registerItemAppendMeta(Item item, int maxExclusive, String loc) {
        registerItemMetas(item, maxExclusive, i -> loc + i);
    }

    public static void registerItemMetas(Item item, int maxExclusive, IntFunction<String> metaToName) {
        for (int i = 0; i < maxExclusive; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    item, i,
                    new ModelResourceLocation(LibMisc.MOD_ID + ":" + metaToName.apply(i), "inventory")
            );
        }
    }

    private static final Map<IRegistryDelegate<Block>, IStateMapper> customStateMappers = ReflectionHelper.getPrivateValue(ModelLoader.class, null, "customStateMappers");
    private static final DefaultStateMapper fallbackMapper = new DefaultStateMapper();

    private static ModelResourceLocation getMrlForState(IBlockState state) {
        return customStateMappers
                .getOrDefault(state.getBlock().delegate, fallbackMapper)
                .putStateModelLocations(state.getBlock())
                .get(state);
    }

    public static void registerBlockToState(Block b, int meta, IBlockState state) {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(b),
                meta,
                getMrlForState(state)
        );
    }

    public static void registerBlockToState(Block b, int maxExclusive) {
        for(int i = 0; i < maxExclusive; i++)
            registerBlockToState(b, i, b.getStateFromMeta(i));
    }

    // Registers the ItemBlock to models/item/<registryname>#inventory
    public static void registerInventoryVariant(Block b) {
        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(b), 0,
                new ModelResourceLocation(b.getRegistryName(), "inventory"));
    }

    // Registers the ItemBlock to a custom path in models/item/itemblock/
    public static void registerCustomItemblock(Block b, String path) {
        registerCustomItemblock(b, 1, i -> path);
    }

    public static void registerCustomItemblock(Block b, int maxExclusive, IntFunction<String> metaToPath) {
        Item item = Item.getItemFromBlock(b);
        for (int i = 0; i < maxExclusive; i++) {
            ModelLoader.setCustomModelResourceLocation(
                    item, i,
                    new ModelResourceLocation(LibMisc.MOD_ID + ":itemblock/" + metaToPath.apply(i), "inventory")
            );
        }
    }

}