package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.registries.GameData;
import net.thegaminghuskymc.sandboxgame.engine.registries.IForgeRegistryEntry;
import net.thegaminghuskymc.sandboxgame.engine.registry.RegistryNamespacedDefaultedByKey;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

public class Block extends IForgeRegistryEntry.Impl<Block> {

    public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> REGISTRY = GameData.getWrapperDefaulted(Block.class);

    private final short id;

    private String unlocalizedName;
    private ResourceLocation registryName;

    public Block(int blockID) {
        this.id = (short) blockID;
    }

    public String toString() {
        return ("Block: " + this.getRegistryName());
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public String setUnlocolizedName(String name) {
        this.unlocalizedName = name;
        return name;
    }

    public boolean isVisible() {
        return true;
    }

    public boolean isOpaque() {
        return true;
    }

    public final boolean isTransparent() {
        return (!this.isOpaque() || this.hasTransparency());
    }

    public boolean hasTransparency() {
        return true;
    }

    public void update(Terrain terrain, int x, int y, int z){

    }

    public final short getID() {
        return (this.id);
    }

    public BlockInstance createBlockInstance(Terrain terrain, int index) {
        return (null);
    }

    public void onSet(Terrain terrain, int x, int y, int z) {

    }

    public void onUnset(Terrain terrain, int x, int y, int z) {

    }

    public boolean isCrossable() {
        return (false);
    }

    public boolean bypassRaycast() {
        return (true);
    }

    public static int getIdFromBlock(Block blockIn)
    {
        return REGISTRY.getIDForObject(blockIn);
    }

    public static Block getBlockById(int id)
    {
        return REGISTRY.getObjectById(id);
    }

}