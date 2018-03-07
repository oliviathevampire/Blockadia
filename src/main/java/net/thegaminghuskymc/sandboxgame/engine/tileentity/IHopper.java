package net.thegaminghuskymc.sandboxgame.engine.tileentity;


import net.thegaminghuskymc.sandboxgame.engine.world.World;

public interface IHopper {
    /**
     * Returns the worldObj for this tileEntity.
     */
    World getWorld();

    /**
     * Gets the world X position for this hopper entity.
     */
    double getXPos();

    /**
     * Gets the world Y position for this hopper entity.
     */
    double getYPos();

    /**
     * Gets the world Z position for this hopper entity.
     */
    double getZPos();
}