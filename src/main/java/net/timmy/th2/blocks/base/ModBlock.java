package net.timmy.th2.blocks.base;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.util.BlockRenderLayer;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import net.thegaminghuskymc.sandboxgame.util.ResourceLocation;
import net.thegaminghuskymc.sandboxgame.util.math.AxisAlignedBB;
import net.thegaminghuskymc.sandboxgame.util.math.RayTraceResult;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.timmy.th2.Reference;
import net.timmy.timmylib.interf.IModBlock;
import net.timmy.timmylib.items.blocks.ItemModBlock;
import net.timmy.timmylib.utils.LibIndexedAABB;
import net.timmy.timmylib.utils.ProxyRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class ModBlock extends Block implements IModBlock {

    private final String[] variants;
    private final String bareName;

    public ModBlock(Material material, String name, String... variants) {
        super(material);
        if (variants.length == 0) variants = new String[]{name};
        bareName = name;
        this.variants = variants;
        setHardness(2.0F);
        if (registerInConstruction()) setUnlocalizedName(name);
    }

    @Override
    public String getBareName() {
        return bareName;
    }

    @Override
    public String[] getVariants() {
        return variants;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT || layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.SOLID;
    }

    private boolean registerInConstruction() {
        return true;
    }

    @Override
    public String getModNamespace() {
        return Reference.MODID;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Objects.requireNonNull(getRegistryName()).toString().substring(4);
    }
    
    @Override
    public Block setUnlocalizedName(String name) {
        super.setUnlocalizedName(name);
        setRegistryName(getPrefix(), name);
        ProxyRegistry.register(this);
        ProxyRegistry.register(new ItemModBlock(this, new ResourceLocation(this.getPrefix(), name)));
        return this;
    }

    @Override
    public ModBlock setCreativeTab(CreativeTabs tab) {
        super.setCreativeTab(tab);
        return this;
    }

    private void addBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes) {
        boxes.add(state.getBoundingBox(world, pos));
    }

    private void addSelectionBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes) {
        addBoxes(state, world, pos, boxes);
    }

    private void addCollisionBoxes(IBlockState state, World world, BlockPos pos, List<AxisAlignedBB> boxes) {
        addBoxes(state, world, pos, boxes);
    }


    @Override
    @ParametersAreNonnullByDefault
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end) {
        List<AxisAlignedBB> boxes = new ArrayList<>();
        addSelectionBoxes(state, world, pos, boxes);
        return boxes.stream().reduce(null, (prev, box) -> {
            RayTraceResult hit = rayTrace(pos, start, end, box);
            if (hit != null && box instanceof LibIndexedAABB) {
                hit.subHit = ((LibIndexedAABB) box).index;
            }
            return prev != null && (hit == null || start.squareDistanceTo(prev.hitVec) < start.squareDistanceTo(hit.hitVec)) ? prev : hit;
        }, (a, b) -> b);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean p_185477_7_) {
        entityBox = entityBox.offset(new BlockPos(0, 0, 0).subtract(pos));
        List<AxisAlignedBB> list = new ArrayList<>();
        addCollisionBoxes(state, world, pos, list);
        for (AxisAlignedBB box : list) {
            if (box.intersects(entityBox)) {
                collidingBoxes.add(box.offset(pos));
            }
        }
    }

    
    @Deprecated
    @SideOnly(Side.CLIENT)
    @Override
    @ParametersAreNonnullByDefault
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos) {
        List<AxisAlignedBB> list = new ArrayList<>();
        addSelectionBoxes(state, world, pos, list);
        if (!list.isEmpty()) {
            AxisAlignedBB aabb = null;
            for (AxisAlignedBB box : list) {
                if (aabb == null) {
                    aabb = box;
                } else {
                    aabb = aabb.union(box);
                }
            }
            return aabb;
        }
        return state.getBoundingBox(world, pos);
    }

}
