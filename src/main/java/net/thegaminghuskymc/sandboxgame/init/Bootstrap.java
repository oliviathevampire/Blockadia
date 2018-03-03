package net.thegaminghuskymc.sandboxgame.init;

import net.thegaminghuskymc.sandboxgame.block.BlockAlt;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import net.thegaminghuskymc.sgf.registries.GameData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintStream;

public class Bootstrap {
    public static final PrintStream SYSOUT = System.out;
    private static final Logger LOGGER = LogManager.getLogger();
    public static boolean hasErrored;
    /**
     * Whether the blocks, items, etc have already been registered
     */
    private static boolean alreadyRegistered;

    /**
     * Is Bootstrap registration already done?
     */
    public static boolean isRegistered() {
        return alreadyRegistered;
    }

    /**
     * Registers blocks, items, stats, etc.
     */
    public static void register() {
        if (!alreadyRegistered) {
            alreadyRegistered = true;
            if (false) // skip redirectOutputToLog, Forge already redirects stdout and stderr output to log so that they print with more context
                redirectOutputToLog();
            SoundEvent.registerSounds();
            BlockAlt.registerBlocks();
            BlockFire.init();
            Potion.registerPotions();
            Enchantment.registerEnchantments();
            Item.registerItems();
            PotionType.registerPotionTypes();
            PotionHelper.init();
            EntityList.init();
            Biome.registerBiomes();
            registerDispenserBehaviors();

            if (!CraftingManager.init()) {
                hasErrored = true;
                LOGGER.error("Errors with built-in recipes!");
            }

            StatList.init();

            if (LOGGER.isDebugEnabled()) {
                if ((new AdvancementManager((File) null)).hasErrored()) {
                    hasErrored = true;
                    LOGGER.error("Errors with built-in advancements!");
                }

                if (!LootTableList.test()) {
                    hasErrored = true;
                    LOGGER.error("Errors with built-in loot tables");
                }
            }

            GameData.vanillaSnapshot();
        }
    }

    /**
     * redirect standard streams to logger
     */
    private static void redirectOutputToLog() {
        if (LOGGER.isDebugEnabled()) {
            System.setErr(new DebugLoggingPrintStream("STDERR", System.err));
            System.setOut(new DebugLoggingPrintStream("STDOUT", SYSOUT));
        } else {
            System.setErr(new LoggingPrintStream("STDERR", System.err));
            System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void printToSYSOUT(String message) {
        SYSOUT.println(message);
    }

}