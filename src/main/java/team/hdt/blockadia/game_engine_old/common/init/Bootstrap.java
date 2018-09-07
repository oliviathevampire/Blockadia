package team.hdt.blockadia.game_engine_old.common.init;

import team.hdt.blockadia.game_engine.core.init.Biomes;
import team.hdt.blockadia.game_engine.core.init.Blocks;
import team.hdt.blockadia.game_engine.core.init.Mods;

import java.io.PrintStream;

public class Bootstrap {

    public static final PrintStream SYSOUT;
    private static boolean alreadyRegistered;

    public static boolean isRegistered() {
        return alreadyRegistered;
    }

    public static void register() {
        if (!alreadyRegistered) {
            alreadyRegistered = true;
            Biomes.register();
            Blocks.register();
            Mods.register();
        }
    }

    public static void printToSYSOUT(String p_printToSYSOUT_0_) {
        SYSOUT.println(p_printToSYSOUT_0_);
    }

    static {
        SYSOUT = System.out;
    }

}
