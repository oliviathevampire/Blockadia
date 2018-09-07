package team.hdt.blockadia.game_engine.core.init;

import team.hdt.blockadia.mod_engine.ModLoader;
import team.hdt.test_mod.TestMod;

/*
 * This class is made by HuskyTheArtist
 * the 06.09.2018 at 14.29
 */
public class Mods {

    public static void register() {
        ModLoader.registerMod(new TestMod());
    }

}
