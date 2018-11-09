package net.hdt.neutronia;


public class Neutronia implements IMod {

    @Override
    public String getModVersion() {
        return LibMisc.VERSION;
    }

    @Override
    public String getModName() {
        return LibMisc.MOD_NAME;
    }

    @Override
    public String getGameVersionForMod() {
        return Blockadia.VERSION;
    }

    @Override
    public void preInit() {
        Display.setTitle(String.format("%s %s | %s %s", LibMisc.MOD_NAME, LibMisc.VERSION, Blockadia.TITLE, Blockadia.VERSION));
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {

    }

}