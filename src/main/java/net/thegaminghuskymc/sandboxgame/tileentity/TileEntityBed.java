package net.thegaminghuskymc.sandboxgame.tileentity;

import net.thegaminghuskymc.sandboxgame.block.BlockBed;
import net.thegaminghuskymc.sandboxgame.init.Items;
import net.thegaminghuskymc.sandboxgame.item.EnumDyeColor;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

public class TileEntityBed extends TileEntity {
    private EnumDyeColor color = EnumDyeColor.RED;

    public void setItemValues(ItemStack p_193051_1_) {
        this.setColor(EnumDyeColor.byMetadata(p_193051_1_.getMetadata()));
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (compound.hasKey("color")) {
            this.color = EnumDyeColor.byMetadata(compound.getInteger("color"));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("color", this.color.getMetadata());
        return compound;
    }

    /**
     * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
     */
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    /**
     * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
     * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
     */
    /*public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 11, this.getUpdateTag());
    }*/
    public EnumDyeColor getColor() {
        return this.color;
    }

    public void setColor(EnumDyeColor color) {
        this.color = color;
        this.markDirty();
    }

    @SideOnly(Side.CLIENT)
    public boolean isHeadPiece() {
        return BlockBed.isHeadPiece(this.getBlockMetadata());
    }

    public ItemStack getItemStack() {
        return new ItemStack(Items.BED, 1, this.color.getMetadata());
    }
}