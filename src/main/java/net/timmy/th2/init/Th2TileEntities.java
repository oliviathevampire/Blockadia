package net.timmy.th2.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.timmy.th2.tileentity.TileEntityCandle;

public class Th2TileEntities {

    public static void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityCandle.class, "tile_candle");
    }

}
