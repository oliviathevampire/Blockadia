package net.timmy.timmylib.config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.thegaminghuskymc.huskylib2.lib.HuskyLib;
import net.thegaminghuskymc.huskylib2.lib.utils.Refs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jasmine Iwanek
 */
public class Config {

    private static final String CONFIG_FILE = "config/hl2.cfg";
    private static final String GENERAL_CAT = "General";
    private static final String INTEGRATION_CAT = "Mod Integration";
    private static final String MATERIALS_CAT = "Metals";
    private static final String VANILLA_CAT = "Vanilla";
    private static final String HAMMER_RECIPES_CAT = "Crack Hammer Recipies";
    private static final String TOOLS_CAT = "Tools and Items";
    private static final String ALT_CFG_PATH = "config/additional-loot-tables"; // + BaseMetals.MODID;
    private static final String ORESPAWN_CFG_PATH = "config/orespawn";
    private static final List<String> UserCrusherRecipes = new ArrayList<String>();
    private static Configuration configuration;

    public static void init() {
        if (configuration == null) {
            configuration = new Configuration(new File(CONFIG_FILE));
            MinecraftForge.EVENT_BUS.register(new Config());
        }

        // GENERAL
        // enablePotionRecipes = config.getBoolean("enable_potions", "options",
        // enablePotionRecipes,
        // "If true, then some metals can be used to brew potions.");

        // GENERAL
        Options.disableAllHammerRecipes = configuration.getBoolean("disable_crack_hammer", GENERAL_CAT, false,
                "If true, then the crack hammer cannot be crafted.");
        Options.enforceHardness = configuration.getBoolean("enforce_hardness", GENERAL_CAT, true,
                "If true, then the crack hammer cannot crush ingots into powders if that \n"
                        + "crackhammer is not hard enough to crush the ingot's ore.");
        Options.strongHammers = configuration.getBoolean("strong_hammers", GENERAL_CAT, true,
                "If true, then the crack hammer can crush ingots/ores that a pickaxe of the same \n"
                        + "material can harvest. If false, then your crack hammer must be made of a harder \n"
                        + "material than the ore you are crushing.");
        Options.autoDetectRecipes = configuration.getBoolean("automatic_recipes", GENERAL_CAT, true,
                "If true, then Base Metals will scan the Ore Dictionary to automatically add a \n"
                        + "Crack Hammer recipe for every material that has an ore, dust, and ingot.");
        Options.requireMMDOreSpawn = configuration.getBoolean("using_orespawn", GENERAL_CAT, true,
                "If false, then Base Metals will not require MMD Ore Spawn mod. \n"
                        + "Set to false if using another mod to manually handle ore generation.");
        Options.enableAchievements = configuration.getBoolean("achievements", GENERAL_CAT, true,
                "If false, then Base Metals Achievements will be disabled (This is currently required if you disable any metals");
        Options.crackHammerFullStack = configuration.getBoolean("crackhammer_full_stacks", GENERAL_CAT, false,
                "If true then you can crackhammer full stacks of dropped items.");
        Options.enablePlateRepairs = configuration.getBoolean("repair_using_plates", GENERAL_CAT, true,
                "Repair shields and armor with metal plates of the same type");
        Options.enableShieldUpgrades = configuration.getBoolean("upgrade_shields", GENERAL_CAT, true,
                "Upgrade a fully repaired shield to a material at least as hard as the shields current one using a plate of that material in the Anvil");

        // INTEGRATION
        Options.modEnabled.put("enderio", configuration.getBoolean("ender_io_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with Ender IO"));
        Options.modEnabled.put("ic2", configuration.getBoolean("ic2_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with IC2"));
        Options.modEnabled.put("mekanism", configuration.getBoolean("mekanism_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with Mekanism"));
        Options.modEnabled.put("thaumcraft", configuration.getBoolean("thaumcraft_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with Thaumcraft"));
        Options.modEnabled.put("tinkersconstruct", configuration.getBoolean("tinkers_construct_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with Tinkers Construct"));
        Options.modEnabled.put("veinminer", configuration.getBoolean("veinminer_integration", INTEGRATION_CAT, true,
                "If false, then Base Metals will not try and integrate with VeinMiner"));
        Options.modEnabled.put("taiga", configuration.getBoolean("taiga_integration", INTEGRATION_CAT, true,
                "Requires that Tinkers' Construct integration also be on. If false, TAIGA provided materials and traits will not be available in Base Metals"));
        Options.modEnabled.put("denseores", configuration.getBoolean("denseores", INTEGRATION_CAT, true,
                "If DenseOres is available, this will allow automatic integration"));
        Options.modEnabled.put("thermalexpansion", configuration.getBoolean("thermal_expansion", INTEGRATION_CAT, true,
                "If Thermal Expansion is available, this wil automatically integrate materials with the various machines"));

        // RECIPE AMOUNTS/TOOL&ITEM DISABLING
        Options.gearQuantity = configuration.getInt("Gear Quantity", TOOLS_CAT, 4, 1, 64, "Number of Gears per recipe");
        Options.plateQuantity = configuration.getInt("Plate Quantity", TOOLS_CAT, 3, 1, 64,
                "Number of Plates per recipe");
        Options.furnaceCheese = configuration.getBoolean("Furnace Cheese", TOOLS_CAT, true,
                "Melt down armor and tools for full value");
        Options.furnace1112 = configuration.getBoolean("Furnace1112", TOOLS_CAT, true,
                "Mimic 1.11.2 armor and tool melting.\n Overridden by Furnace Cheese");

        Options.thingEnabled.put("Basics", configuration.getBoolean("Enable Basics", TOOLS_CAT, true,
                "Set to false to disable: Nuggets, Ingots, Powders, Blends, Blocks and Ores"));
        Options.thingEnabled.put("BasicTools", configuration.getBoolean("Enable Basic Tools", TOOLS_CAT, true,
                "Set to false to disable: Axe, Hoe, Pickaxe, Shovel and Sword"));
        Options.thingEnabled.put("BowAndArrow", configuration.getBoolean("Enable Bow and Arrow", TOOLS_CAT, true,
                "Set to false to turn off custom bows and arrows"));
        Options.thingEnabled.put("CrossbowAndBolt", configuration.getBoolean("Enable Crossbow and Bolts", TOOLS_CAT, true,
                "Set to false to disable custom crossbows and bolts"));
        Options.thingEnabled.put("Armor", configuration.getBoolean("Enable Armor", TOOLS_CAT, true,
                "Set to false to disable: Helmet, Chestplate, Leggings and Boots"));
        Options.thingEnabled.put("CrackHammer", configuration.getBoolean("Enable Crackhammer", TOOLS_CAT, true,
                "Why would you want to disable a valuable early-game tool ?\nThe question we're all asking though is will it blend?"));
        Options.thingEnabled.put("FishingRod", configuration.getBoolean("Enable Fishing Rod", TOOLS_CAT, true,
                "Set to false to turn off added fishing rods"));
        Options.thingEnabled.put("HorseArmor", configuration.getBoolean("Enable Horse Armor", TOOLS_CAT, true,
                "Set to false to disable extra Horse Armor"));
        Options.thingEnabled.put("Shears", configuration.getBoolean("Enable Shears", TOOLS_CAT, true,
                "I love Shears, do you? If you're a Shears hater, set this to false"));
        Options.thingEnabled.put("SmallDust", configuration.getBoolean("Enable Small Dust", TOOLS_CAT, true,
                "Turn this off to disable nugget-sized piles of dust.\n(Dust is a drug and drugs are bad. Don't do drugs, mmm-kay ?"));
        Options.thingEnabled.put("Rod", configuration.getBoolean("Enable Rod", TOOLS_CAT, true,
                "Spare the Rod... Wait, no, that's Biblical...\nThis controls whether or not rods of various materials (similar to Tinkers' Construct Tool Rod) sare available"));
        Options.thingEnabled.put("Gear", configuration.getBoolean("Enable Gear", TOOLS_CAT, true,
                "A lot of mods have Gears, we can provide them. Turn this off if you think you don't need them."));
        Options.thingEnabled.put("Shield", configuration.getBoolean("Enable Shield", TOOLS_CAT, true,
                "The Combat Update brought Shields to Vanilla Minecraft. Turn this off if you don't want them to multiply"));
        Options.thingEnabled.put("Bars", configuration.getBoolean("Enable Bars", TOOLS_CAT, true,
                "No, not the kind you drink at. The kind you find on jail-cells."));
        Options.thingEnabled.put("Plate", configuration.getBoolean("Enable Plates", TOOLS_CAT, true,
                "IC2, Tech Reborn and a number of other mods require plates of material. This provides recipes for them."));
        Options.thingEnabled.put("Door", configuration.getBoolean("Enable Door", TOOLS_CAT, true,
                "Doors of many wondrous materials"));
        Options.thingEnabled.put("Trapdoor", configuration.getBoolean("Enable Trapdoors", TOOLS_CAT, true,
                "Do you want trapdoors? Because that's how you get trapdoors!"));
        Options.thingEnabled.put("Button", configuration.getBoolean("Enable Buttons", TOOLS_CAT, true,
                "Ooooh, what does this button do?"));
        Options.thingEnabled.put("Slab", configuration.getBoolean("Enable Slab", TOOLS_CAT, true,
                "Slabs of all the materials that get added"));
        Options.thingEnabled.put("Lever", configuration.getBoolean("Enable Lever", TOOLS_CAT, true,
                "Levers of all different materials - they even have different hardness's"));
        Options.thingEnabled.put("PressurePlate", configuration.getBoolean("Enable Pressure-plates", TOOLS_CAT, true,
                "Now your traps can be hidden in even more places!"));
        Options.thingEnabled.put("Stairs", configuration.getBoolean("Enable Stairs", TOOLS_CAT, true,
                "Stairs of our wonderful metals! Come and get your own - or don't. It's up to you."));
        Options.thingEnabled.put("Wall", configuration.getBoolean("Enable Wall", TOOLS_CAT, true,
                "Hey, Teachers! Leave those kids alone!"));

        // DISABLE CRACK HAMMER RECIPES
        String disabledRecipesRaw = configuration.getString("DisabledCrackhammerRecipes", GENERAL_CAT, "",
                "Disable the recipes by putting the input materials ore dictionary name ore registry name in this key.\nThe format is a semicolon (;) separate list of ore dictionary names (ie:  oreGold;oreIron;oreCopper - this would blacklist Gold, Iron and Copper ores from working");
        if (!disabledRecipesRaw.isEmpty() && disabledRecipesRaw.contains(";")) {
            Options.disabledRecipes = disabledRecipesRaw.split(";");
        } else {
            Options.disabledRecipes = new String[]{disabledRecipesRaw};
        }

        // CRACK HAMMER RECIPES
        final ConfigCategory userRecipeCat = configuration.getCategory(HAMMER_RECIPES_CAT);
        userRecipeCat
                .setComment("This section allows you to add your own recipes for the Crack Hammer (and other rock \n"
                        + "crushers). Recipes are specified in semicolon (;) delimited lists of formulas in the \n"
                        + "format modid:name#y->x*modid:name#y, where x is the number of items in a stack and y \n"
                        + "is the metadata value. Note that both x and y are optional, so you can use the \n"
                        + "formula modid:name->modid:name for most items/blocks. \n\n"
                        + "All properties in this section will be parsed for formulas, regardless their name. \n"
                        + "This lets you organize your recipe lists for easier reading.");
        if (userRecipeCat.keySet().isEmpty()) {
            final Property prop = new Property("custom", "", Property.Type.STRING);
            prop.setComment("Example: minecraft:stained_glass#11->minecraft:dye#4; minecraft:wool->4*minecraft:string");
            userRecipeCat.put("custom", prop);
        }
        for (final Property p : userRecipeCat.values()) {
            final String[] recipes = p.getString().split(";");
            for (final String r : recipes) {
                final String recipe = r.trim();
                if (recipe.isEmpty()) {
                    continue;
                }
                if (!(recipe.contains("->"))) {
                    throw new IllegalArgumentException("Malformed hammer recipe expression '" + recipe + "'. Should be in format 'modid:itemname->modid:itemname'");
                }
                UserCrusherRecipes.add(recipe);
            }
        }

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    /**
     * Parses a String in the format (stack-size)*(modid):(item/block
     * name)#(metadata value). The stacksize and metadata value parameters are
     * optional.
     *
     * @param str           A String describing an ItemStack (e.g. "4*minecraft:dye#15" or
     *                      "minecraft:bow")
     * @param allowWildcard If true, then item strings that do not specify a metadata
     *                      value will use the OreDictionary wildcard value. If false,
     *                      then the default meta value is 0 instead.
     * @return An ItemStack representing the item, or null if the item is not
     * found
     */
    public static ItemStack parseStringAsItemStack(String str, final boolean allowWildcard) {
        str = str.trim();
        int count = 1;
        int meta;
        if (allowWildcard) {
            meta = OreDictionary.WILDCARD_VALUE;
        } else {
            meta = 0;
        }
        int nameStart = 0;
        int nameEnd = str.length();
        if (str.contains("*")) {
            count = Integer.parseInt(str.substring(0, str.indexOf('*')).trim());
            nameStart = str.indexOf('*') + 1;
        }
        if (str.contains("#")) {
            meta = Integer.parseInt(str.substring(str.indexOf('#') + 1, str.length()).trim());
            nameEnd = str.indexOf('#');
        }
        final String id = str.substring(nameStart, nameEnd).trim();
        if (Block.getBlockFromName(id) != null) {
            // is a block
            return new ItemStack(Block.getBlockFromName(id), count, meta);
        } else if (Item.getByNameOrId(id) != null) {
            // is an item
            return new ItemStack(Item.getByNameOrId(id), count, meta);
        } else {
            // item not found
            HuskyLib.logger.info("Failed to find item or block for ID '%s'", id);
            return null;
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (e.getModID().equals(Refs.MODID)) {
            init();
        }
    }

    public static class Options {

        // INTEGRATION
        protected static final Map<String, Boolean> modEnabled = new HashMap<>();
        // MATERIALS
        protected static final Map<String, Boolean> materialEnabled = new HashMap<>();
        // THINGS
        protected static final Map<String, Boolean> thingEnabled = new HashMap<>();
        // GENERAL
        protected static boolean disableAllHammerRecipes = false;
        protected static boolean enforceHardness = true;
        protected static boolean strongHammers = true;
        protected static boolean autoDetectRecipes = true;
        protected static boolean requireMMDOreSpawn = true;
        protected static boolean enableAchievements = true;
        protected static boolean crackHammerFullStack = false;
        protected static boolean enableShieldUpgrades = true;
        protected static boolean enablePlateRepairs = true;
        protected static boolean enableModderSupportThings = true;
        protected static String[] disabledRecipes = null;
        // RECIPE AMOUNTS
        protected static int gearQuantity = 4;
        protected static int plateQuantity = 3;
        protected static boolean furnaceCheese = true;
        protected static boolean furnace1112 = true; // Overridden by FURNACE_CHEESE

        /*
                public static boolean enableBasics = true; // Nugget, Ingot, Powder, Blend, Block, Ore
                public static boolean enableBasicTools = true; // Axe, Hoe, Pickaxe, Shovel, Sword
                public static boolean enableCrossbowAndBolt = true; // Crossbows, Bolts
                public static boolean enableBowAndArrow = true; // Bows, Arrows
                public static boolean enableArmor = true; // Helmet, Chestplate, Leggings, Boots
                public static boolean enableCrackHammer = true;
                public static boolean enableFishingRod = true;
                public static boolean enableHorseArmor = true;
                public static boolean enableShears = true;
                public static boolean enableSmallDust = true; // Small Powder, Small Blend
                public static boolean enableRod = true;
                public static boolean enableGear = true;
                public static boolean enableShield = true;

                public static boolean enableAnvil = true;
                public static boolean enableBars = true;
                public static boolean enableBookshelf = true;
                public static boolean enableButton = true;
                public static boolean enableDoor = true; // Also item
                public static boolean enableFlowerPot = true;
                public static boolean enableLadder = true;
                public static boolean enableLever = true;
                public static boolean enablePlate = true;
                public static boolean enablePressurePlate = true;
                public static boolean enableSlab = true; // Also item, double slab
                public static boolean enableStairs = true;
                public static boolean enableTrapdoor = true;
                public static boolean enableTripWire = true;
                public static boolean enableWall = true;
        */
        private Options() {
            throw new IllegalAccessError("Not a instantiable class");
        }

        public static boolean disableAllHammerRecipes() {
            return disableAllHammerRecipes;
        }

        public static boolean enforceHardness() {
            return enforceHardness;
        }

        public static boolean strongHammers() {
            return strongHammers;
        }

        public static boolean autoDetectRecipes() {
            return autoDetectRecipes;
        }

        public static boolean requireMMDOreSpawn() {
            return requireMMDOreSpawn;
        }

        public static boolean enableAchievements() {
            return enableAchievements;
        }

        public static boolean crackHammerFullStack() {
            return crackHammerFullStack;
        }

        public static boolean enableShieldUpgrades() {
            return enableShieldUpgrades;
        }

        public static boolean enablePlateRepairs() {
            return enablePlateRepairs;
        }

        public static boolean enableModderSupportThings() {
            return enableModderSupportThings;
        }

        public static String[] disabledRecipes() {
            return disabledRecipes;
        }

        public static int gearQuantity() {
            return gearQuantity;
        }

        public static int plateQuantity() {
            return plateQuantity;
        }

        public static boolean furnaceCheese() {
            return furnaceCheese;
        }

        public static boolean furnace1112() {
            return furnace1112;
        }

        public static boolean modEnabled(String name) {
            return modEnabled.get(name);
        }

        public static boolean materialEnabled(String name) {
            return materialEnabled.get(name);
        }

        public static boolean thingEnabled(String name) {
            return thingEnabled.get(name);
        }
    }
}
