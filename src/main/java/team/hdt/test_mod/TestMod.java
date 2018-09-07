package team.hdt.test_mod;

import team.hdt.blockadia.mod_engine.interfaces.IMod;

/*
 * This class is made by HuskyTheArtist
 * the 21.08.2018 at 09.14
 */
public class TestMod implements IMod {

    @Override
    public String getDisplayName() {
        return "Test Mod";
    }

    @Override
    public String getModID() {
        return "test_mod";
    }

    @Override
    public String getResourceLocation() {
        return "assets/testmod";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String getDescription() {
        return "This is a test mod";
    }

}