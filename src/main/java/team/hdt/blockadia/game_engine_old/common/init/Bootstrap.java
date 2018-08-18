package team.hdt.blockadia.game_engine_old.common.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.game_engine.core.init.Biomes;
import team.hdt.blockadia.game_engine.core.init.Blocks;

import java.io.PrintStream;

public class Bootstrap {

    public static final PrintStream SYSOUT;
    private static boolean alreadyRegistered;
    private static final Logger LOGGER;

    public static boolean isRegistered() {
        return alreadyRegistered;
    }

    public static void register() {
        if (!alreadyRegistered) {
            alreadyRegistered = true;
            Biomes.register();
            Blocks.register();
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
