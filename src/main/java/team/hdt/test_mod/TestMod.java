package team.hdt.test_mod;

import team.hdt.blockadia.bml.interfaces.IMod;
import team.hdt.blockadia.bml.interfaces.Mod;

/*
 * This class is made by HuskyTheArtist
 * the 21.08.2018 at 09.14
 */
@Mod(modid = "neutronia", name = "Neutronia", version = "0.0.1")
public class TestMod implements IMod {

    @Override
    public String getDisplayName() {
        return "Neutronia";
    }

    @Override
    public String getModID() {
        return "neutronia";
    }

    @Override
    public String getIdentifier() {
        return "assets/neutronia";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public String getDescription() {
        return "This is my mod";
    }

}