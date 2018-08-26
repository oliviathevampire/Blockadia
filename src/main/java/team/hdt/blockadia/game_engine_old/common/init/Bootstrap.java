package team.hdt.blockadia.game_engine_old.common.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine.core.init.Biomes;
import team.hdt.blockadia.game_engine.core.init.Blocks;
import team.hdt.blockadia.mod_engine.interfaces.IMod;
import team.hdt.test_mod.TestMod;

import java.io.PrintStream;
import java.util.List;

public class Bootstrap {

    public static final PrintStream SYSOUT;
    private static boolean alreadyRegistered;
    private static final Logger LOGGER;
    private static List<IMod> MODS;

    public static boolean isRegistered() {
        return alreadyRegistered;
    }

    public static void register() {
        if (!alreadyRegistered) {
            alreadyRegistered = true;
            Biomes.register();
            Blocks.register();
            MODS.add(new TestMod());
        }
        for(IMod mods : MODS) {
            LOGGER.info(String.format("Registered a mod called %s", mods.getDisplayName()));
        }
    }

    public static void printToSYSOUT(String p_printToSYSOUT_0_) {
        SYSOUT.println(p_printToSYSOUT_0_);
    }

    static {
        SYSOUT = System.out;
        LOGGER = LogManager.getLogger();
    }

}
