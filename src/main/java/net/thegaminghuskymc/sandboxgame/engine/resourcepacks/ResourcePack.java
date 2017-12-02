package net.thegaminghuskymc.sandboxgame.engine.resourcepacks;

public class ResourcePack {

    /** the zip file */
    private String pack;
    private String modid;

    public ResourcePack(String modid, String packpath) {
        this.modid = modid.replaceAll("\\s+", "");
        this.pack = packpath;
    }

    public String getModID() {
        return (this.modid);
    }

    public String getPack() {
        return (this.pack);
    }

    @Override
    public String toString() {
        return (this.modid + " ; " + this.pack);
    }

}
