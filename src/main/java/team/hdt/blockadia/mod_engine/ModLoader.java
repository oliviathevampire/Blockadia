package team.hdt.blockadia.mod_engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.hdt.blockadia.mod_engine.interfaces.IMod;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is made by HuskyTheArtist
 * the 06.09.2018 at 14.30
 */
public class ModLoader {

    private static final Logger LOGGER = LogManager.getLogger();
    private static List<IMod> MODS = new ArrayList<>();

    public static void registerMod(IMod mod) {
        MODS.add(mod);
        LOGGER.info(String.format("Registered a mod called %s with a modid that is %s", mod.getDisplayName(), mod.getModID()));
    }

}
