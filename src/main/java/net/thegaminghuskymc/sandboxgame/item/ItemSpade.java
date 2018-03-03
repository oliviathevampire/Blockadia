package net.thegaminghuskymc.sandboxgame.item;

import com.google.common.collect.Sets;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.entity.player.EntityPlayer;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.init.SoundEvents;
import net.thegaminghuskymc.sandboxgame.util.EnumActionResult;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.EnumHand;
import net.thegaminghuskymc.sandboxgame.util.SoundCategory;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.world.World;

import java.util.Set;

public class ItemSpade extends ItemTool
{
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);

    public ItemSpade(Item.ToolMaterial material)
    {
        super(1.5F, -3.0F, material, EFFECTIVE_ON);
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        Block block = blockIn.getBlock();

        if (block == Blocks.SNOW_LAYER)
        {
            return true;
        }
        else
        {
            return block == Blocks.SNOW;
        }
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && block == Blocks.GRASS)
            {
                IBlockState iblockstate1 = Blocks.GRASS_PATH.getDefaultState();
//                worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (!worldIn.isRemote)
                {
                    worldIn.setBlockState(pos, iblockstate1, 11);
                    itemstack.damageItem(1, player);
                }

                return EnumActionResult.SUCCESS;
            }
            else
            {
                return EnumActionResult.PASS;
            }
        }
    }
}