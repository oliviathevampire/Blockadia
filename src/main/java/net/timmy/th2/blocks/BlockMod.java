package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Items;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import net.timmy.th2.Thaumania2;
import net.timmy.th2.lib.LibMisc;
import net.timmy.timmylib.api.IModelRegister;
import net.timmy.timmylib.utils.ModelHandler;
import net.timmy.timmylib.utils.ProxyRegistry;

public abstract class BlockMod extends Block implements IModelRegister {

	public BlockMod(Material par2Material, String name) {
		super(par2Material);
		setUnlocalizedName(name);
		setRegistryName(LibMisc.MOD_ID, name);
		if(registerInCreative())
			setCreativeTab(Thaumania2.th2Blocks);
	}

	protected boolean registerInCreative() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModels() {
		if(Item.getItemFromBlock(this) != Items.AIR)
			ModelHandler.registerBlockToState(this, 0, getDefaultState());
	}

	public Block setUnlocalizedName(String name) {
		super.setUnlocalizedName(name);
		this.setRegistryName(LibMisc.MOD_ID, name);
		ProxyRegistry.register(this);
		ProxyRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
		return this;
	}

	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
		super.eventReceived(state, world, pos, id, param);
		TileEntity tileentity = world.getTileEntity(pos);
		return tileentity != null ? tileentity.receiveClientEvent(id, param) : false;
	}
}