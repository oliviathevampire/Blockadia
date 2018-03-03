package net.thegaminghuskymc.sandboxgame.block;

import net.thegaminghuskymc.sandboxgame.block.material.EnumPushReaction;
import net.thegaminghuskymc.sandboxgame.block.material.MapColor;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.properties.IProperty;
import net.thegaminghuskymc.sandboxgame.block.state.BlockFaceShape;
import net.thegaminghuskymc.sandboxgame.block.state.BlockStateContainer;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.client.util.ITooltipFlag;
import net.thegaminghuskymc.sandboxgame.creativetab.CreativeTabs;
import net.thegaminghuskymc.sandboxgame.entity.Entity;
import net.thegaminghuskymc.sandboxgame.entity.EntityLivingBase;
import net.thegaminghuskymc.sandboxgame.entity.player.EntityPlayer;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.init.Items;
import net.thegaminghuskymc.sandboxgame.item.Item;
import net.thegaminghuskymc.sandboxgame.item.ItemBlock;
import net.thegaminghuskymc.sandboxgame.item.ItemStack;
import net.thegaminghuskymc.sandboxgame.registries.RegistryNamespacedDefaultedByKey;
import net.thegaminghuskymc.sandboxgame.tileentity.TileEntity;
import net.thegaminghuskymc.sandboxgame.util.*;
import net.thegaminghuskymc.sandboxgame.util.math.*;
import net.thegaminghuskymc.sandboxgame.util.text.translation.I18n;
import net.thegaminghuskymc.sandboxgame.world.IBlockAccess;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.WorldServer;
import net.thegaminghuskymc.sgf.event.ForgeEventFactory;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;
import net.thegaminghuskymc.sgf.registries.GameData;
import net.thegaminghuskymc.sgf.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Block extends IForgeRegistryEntry.Impl<Block> {
    public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> REGISTRY = GameData.getWrapperDefaulted(Block.class);
    @Deprecated //Modders: DO NOT use this! Use GameRegistry
    public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = GameData.getBlockStateIDMap();
    public static final AxisAlignedBB FULL_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    @Nullable
    public static final AxisAlignedBB NULL_AABB = null;
    /**
     * ResourceLocation for the Air block
     */
    private static final ResourceLocation AIR_ID = new ResourceLocation("air");
    protected static java.util.Random RANDOM = new java.util.Random(); // Useful for random things without a seed.
    // For Internal use only to capture droped items inside getDrops
    protected static ThreadLocal<Boolean> captureDrops = ThreadLocal.withInitial(() -> false);
    protected static ThreadLocal<NonNullList<ItemStack>> capturedDrops = ThreadLocal.withInitial(NonNullList::create);
    protected final Material blockMaterial;
    /**
     * The Block's MapColor
     */
    protected final MapColor blockMapColor;
    protected final BlockStateContainer blockState;
    public float blockParticleGravity;
    /**
     * Determines how much velocity is maintained while moving on top of this block
     */
    @Deprecated // Forge: State/world/pos/entity sensitive version below
    public float slipperiness;
    protected boolean fullBlock;
    /**
     * How much light is subtracted for going through this block
     */
    protected int lightOpacity;
    protected boolean translucent;
    /**
     * Amount of light emitted
     */
    protected int lightValue;
    /**
     * Flag if block should use the brightest neighbor light value as its own
     */
    protected boolean useNeighborBrightness;
    /**
     * Indicates how many hits it takes to break a block.
     */
    protected float blockHardness;
    /**
     * Indicates how much this block can resist explosions
     */
    protected float blockResistance;
    protected boolean enableStats;
    /**
     * Flags whether or not this block is of a type that needs random ticking. Ref-counted by ExtendedBlockStorage in
     * order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    protected boolean needsRandomTick;
    /**
     * true if the Block contains a Tile Entity
     */
    protected boolean hasTileEntity;
    /* ======================================== FORGE START =====================================*/
    //For ForgeInternal use Only!
    protected ThreadLocal<EntityPlayer> harvesters = new ThreadLocal();
    private CreativeTabs displayOnCreativeTab;
    private IBlockState defaultBlockState;
    private String unlocalizedName;
    private ThreadLocal<IBlockState> silk_check_state = new ThreadLocal();
    private boolean isTileProvider = this instanceof ITileEntityProvider;
    private String[] harvestTool = new String[16];
    private int[] harvestLevel = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    public Block(Material blockMaterialIn, MapColor blockMapColorIn) {
        this.enableStats = true;
        this.blockParticleGravity = 1.0F;
        this.slipperiness = 0.6F;
        this.blockMaterial = blockMaterialIn;
        this.blockMapColor = blockMapColorIn;
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
        this.fullBlock = this.getDefaultState().isOpaqueCube();
        this.lightOpacity = this.fullBlock ? 255 : 0;
        this.translucent = !blockMaterialIn.blocksLight();
    }

    public Block(Material materialIn) {
        this(materialIn, materialIn.getMaterialMapColor());
    }

    public static int getIdFromBlock(Block blockIn) {
        return REGISTRY.getIDForObject(blockIn);
    }

    /**
     * Get a unique ID for the given BlockState, containing both BlockID and metadata
     */
    public static int getStateId(IBlockState state) {
        Block block = state.getBlock();
        return getIdFromBlock(block) + (block.getMetaFromState(state) << 12);
    }

    public static Block getBlockById(int id) {
        return REGISTRY.getObjectById(id);
    }

    /**
     * Get a BlockState by it's ID (see getStateId)
     */
    public static IBlockState getStateById(int id) {
        int i = id & 4095;
        int j = id >> 12 & 15;
        return getBlockById(i).getStateFromMeta(j);
    }

    public static Block getBlockFromItem(@Nullable Item itemIn) {
        return itemIn instanceof ItemBlock ? ((ItemBlock) itemIn).getBlock() : Blocks.AIR;
    }

    @Nullable
    public static Block getBlockFromName(String name) {
        ResourceLocation resourcelocation = new ResourceLocation(name);

        if (REGISTRY.containsKey(resourcelocation)) {
            return REGISTRY.getObject(resourcelocation);
        } else {
            try {
                return REGISTRY.getObjectById(Integer.parseInt(name));
            } catch (NumberFormatException var3) {
                return null;
            }
        }
    }

    protected static void addCollisionBoxToList(BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable AxisAlignedBB blockBox) {
        if (blockBox != NULL_AABB) {
            AxisAlignedBB axisalignedbb = blockBox.offset(pos);

            if (entityBox.intersects(axisalignedbb)) {
                collidingBoxes.add(axisalignedbb);
            }
        }
    }

    /**
     * Spawns the given ItemStack as an EntityItem into the World at the given position
     */
    public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            if (captureDrops.get()) {
                capturedDrops.get().add(stack);
                return;
            }
            float f = 0.5F;
            double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            /*EntityItem entityitem = new EntityItem(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            entityitem.setDefaultPickupDelay();
            worldIn.spawnEntity(entityitem);*/
        }
    }

    public static boolean isEqualTo(Block blockIn, Block other) {
        if (blockIn != null && other != null) {
            return blockIn == other ? true : blockIn.isAssociatedBlock(other);
        } else {
            return false;
        }
    }

    public static void registerBlocks() {
        registerBlock(0, AIR_ID, (new BlockAir()));
        registerBlock(1, "stone_smooth", (new BlockStone("smooth")).setHardness(1.5F).setResistance(10.0F));
        registerBlock(2, "grass_normal", (new BlockGrass("grass_normal")).setHardness(0.6F));
        registerBlock(3, "dirt_normal", (new BlockDirt("dirt_normal")).setHardness(0.5F));
        Block block = (new Block(Material.ROCK)).setHardness(2.0F).setResistance(10.0F).setUnlocalizedName("stone_brick").setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        registerBlock(4, "cobblestone", block);
        registerBlock(5, "oak", new BlockPlanks().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("planks"));
        registerBlock(5, "sapling", new BlockSapling().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("sapling"));
        registerBlock(5, "leaves", new BlockOldLeaf().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("leaves"));
        registerBlock(5, "log", new BlockOldLog().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("log"));
        registerBlock(5, "stripped_log", new BlockOldLog().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("stripped_log"));
        registerBlock(5, "bark", new BlockOldLog().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("bark"));

        registerBlock(5, "oak", new BlockFence("oak").setHardness(2.0F).setResistance(5.0F));
        registerBlock(6, "dark_oak", new BlockFence("dark_oak").setHardness(2.0F).setResistance(5.0F));
        registerBlock(7, "birch", new BlockFence("birch").setHardness(2.0F).setResistance(5.0F));
        registerBlock(8, "acacia", new BlockFence("acacia").setHardness(2.0F).setResistance(5.0F));
        registerBlock(9, "spruce", new BlockFence("spruce").setHardness(2.0F).setResistance(5.0F));
        registerBlock(10, "jungle", new BlockFence("jungle").setHardness(2.0F).setResistance(5.0F));
        registerBlock(11, "redwood", new BlockFence("redwood").setHardness(2.0F).setResistance(5.0F));
        registerBlock(12, "palm", new BlockFence("palm").setHardness(2.0F).setResistance(5.0F));
        REGISTRY.validateKey();

        for (Block block15 : REGISTRY) {
            if (block15.blockMaterial == Material.AIR) {
                block15.useNeighborBrightness = false;
            } else {
                boolean flag = false;
                boolean flag2 = block15 instanceof BlockSlab;
                boolean flag4 = block15.translucent;
                boolean flag5 = block15.lightOpacity == 0;

                if (flag2 || flag4 || flag5) {
                    flag = true;
                }

                block15.useNeighborBrightness = flag;
            }
        }
    }

    private static void registerBlock(int id, ResourceLocation textualID, Block block_) {
        REGISTRY.register(id, textualID, block_);
    }

    private static void registerBlock(int id, String textualID, Block block_) {
        registerBlock(id, new ResourceLocation(textualID), block_);
    }

    /**
     * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
     */
    @Deprecated
    public boolean isTopSolid(IBlockState state) {
        return state.getMaterial().isOpaque() && state.isFullCube();
    }

    /**
     * @return true if the state occupies all of its 1x1x1 cube
     */
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return this.fullBlock;
    }

    @Deprecated
    public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
        return true;
    }

    /*protected static boolean isExceptionBlockForAttaching(Block attachBlock)
    {
        return attachBlock instanceof BlockShulkerBox || attachBlock instanceof BlockLeaves || attachBlock instanceof BlockTrapDoor || attachBlock == Blocks.BEACON || attachBlock == Blocks.CAULDRON || attachBlock == Blocks.GLASS || attachBlock == Blocks.GLOWSTONE || attachBlock == Blocks.ICE || attachBlock == Blocks.SEA_LANTERN || attachBlock == Blocks.STAINED_GLASS;
    }

    protected static boolean isExceptBlockForAttachWithPiston(Block attachBlock)
    {
        return isExceptionBlockForAttaching(attachBlock) || attachBlock == Blocks.PISTON || attachBlock == Blocks.STICKY_PISTON || attachBlock == Blocks.PISTON_HEAD;
    }*/

    @Deprecated
    public int getLightOpacity(IBlockState state) {
        return this.lightOpacity;
    }

    /**
     * Used in the renderer to apply ambient occlusion
     */
    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean isTranslucent(IBlockState state) {
        return this.translucent;
    }

    @Deprecated
    public int getLightValue(IBlockState state) {
        return this.lightValue;
    }

    /**
     * Should block use the brightest neighbor light value as its own
     */
    @Deprecated
    public boolean getUseNeighborBrightness(IBlockState state) {
        return this.useNeighborBrightness;
    }

    /**
     * Get a material of block
     */
    @Deprecated
    public Material getMaterial(IBlockState state) {
        return this.blockMaterial;
    }

    /**
     * Get the MapColor for this Block and the given BlockState
     */
    @Deprecated
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return this.blockMapColor;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        if (state.getPropertyKeys().isEmpty()) {
            return 0;
        } else {
            throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
        }
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Deprecated
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Deprecated
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state;
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Deprecated
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state;
    }

    /**
     * Sets how much light is blocked going through this block. Returns the object for convenience in constructing.
     */
    public Block setLightOpacity(int opacity) {
        this.lightOpacity = opacity;
        return this;
    }

    /**
     * Sets the light value that the block emits. Returns resulting block instance for constructing convenience.
     */
    public Block setLightLevel(float value) {
        this.lightValue = (int) (15.0F * value);
        return this;
    }

    /**
     * Sets the the blocks resistance to explosions. Returns the object for convenience in constructing.
     */
    public Block setResistance(float resistance) {
        this.blockResistance = resistance * 3.0F;
        return this;
    }

    /**
     * Indicate if a material is a normal solid opaque cube
     */
    @Deprecated
    public boolean isBlockNormalCube(IBlockState state) {
        return state.getMaterial().blocksMovement() && state.isFullCube();
    }

    /**
     * Used for nearly all game logic (non-rendering) purposes. Use Forge-provided isNormalCube(IBlockAccess, BlockPos)
     * instead.
     */
    @Deprecated
    public boolean isNormalCube(IBlockState state) {
        return state.getMaterial().isOpaque() && state.isFullCube() && !state.canProvidePower();
    }

    @Deprecated
    public boolean causesSuffocation(IBlockState state) {
        return this.blockMaterial.blocksMovement() && this.getDefaultState().isFullCube();
    }

    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return true;
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return false;
    }

    /**
     * Determines if an entity can path through this block
     */
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return !this.blockMaterial.blocksMovement();
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Whether this Block can be replaced directly by other blocks (true for e.g. tall grass)
     */
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getMaterial().isReplaceable();
    }

    /**
     * Sets how many hits it takes to break a block.
     */
    public Block setHardness(float hardness) {
        this.blockHardness = hardness;

        if (this.blockResistance < hardness * 5.0F) {
            this.blockResistance = hardness * 5.0F;
        }

        return this;
    }

    public Block setBlockUnbreakable() {
        this.setHardness(-1.0F);
        return this;
    }

    @Deprecated
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return this.blockHardness;
    }

    /**
     * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
     * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
     */
    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }

    /**
     * Sets whether this block type will receive random update ticks
     */
    public Block setTickRandomly(boolean shouldTick) {
        this.needsRandomTick = shouldTick;
        return this;
    }

    @Deprecated //Forge: New State sensitive version.
    public boolean hasTileEntity() {
        return hasTileEntity(getDefaultState());
    }

    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
        int i = source.getCombinedLight(pos, state.getLightValue(source, pos));

        if (i == 0 && state.getBlock() instanceof BlockSlab) {
            pos = pos.down();
            state = source.getBlockState(pos);
            return source.getCombinedLight(pos, state.getLightValue(source, pos));
        } else {
            return i;
        }
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(blockAccess, pos);

        switch (side) {
            case DOWN:

                if (axisalignedbb.minY > 0.0D) {
                    return true;
                }

                break;
            case UP:

                if (axisalignedbb.maxY < 1.0D) {
                    return true;
                }

                break;
            case NORTH:

                if (axisalignedbb.minZ > 0.0D) {
                    return true;
                }

                break;
            case SOUTH:

                if (axisalignedbb.maxZ < 1.0D) {
                    return true;
                }

                break;
            case WEST:

                if (axisalignedbb.minX > 0.0D) {
                    return true;
                }

                break;
            case EAST:

                if (axisalignedbb.maxX < 1.0D) {
                    return true;
                }
        }

        return !blockAccess.getBlockState(pos.offset(side)).doesSideBlockRendering(blockAccess, pos.offset(side), side.getOpposite());
    }

    /**
     * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
     * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
     * <p>
     * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
     * does not fit the other descriptions and will generally cause other things not to connect to the face.
     *
     * @return an approximation of the form of the given face
     */
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.SOLID;
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    /*public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (hasTileEntity(state) && !(this instanceof BlockContainer))
        {
            worldIn.removeTileEntity(pos);
        }
    }*/

    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
    }

    @Deprecated
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return blockState.getBoundingBox(worldIn, pos);
    }

    /**
     * Return an AABB (in world coords!) that should be highlighted when the player is targeting this Block
     */
    @Deprecated
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return state.getBoundingBox(worldIn, pos).offset(pos);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return true;
    }

    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return this.isCollidable();
    }

    /**
     * Returns if this block is collidable. Only used by fire, although stairs return that of the block that the stair
     * is made of (though nobody's going to make fire stairs, right?)
     */
    public boolean isCollidable() {
        return true;
    }

    /**
     * Spawns the given amount of experience into the World as XP orb entities
     */
    /*public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount)
    {
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops"))
        {
            while (amount > 0)
            {
                int i = EntityXPOrb.getXPSplit(amount);
                amount -= i;
                worldIn.spawnEntity(new EntityXPOrb(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, i));
            }
        }
    }*/

    /**
     * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
     */
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        this.updateTick(worldIn, pos, state, random);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    }

    /**
     * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
     * of whether the block can receive random update ticks
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    }

    /**
     * Called after a player destroys this Block - the posiiton pos may no longer hold the state indicated.
     */
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Deprecated
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World worldIn) {
        return 10;
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random) {
        return 1;
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    // Forge: use getStateForPlacement

    /**
     * Get the hardness of this Block relative to the ability of the given player
     */
    @Deprecated
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
        return net.thegaminghuskymc.sgf.common.ForgeHooks.blockStrength(state, player, worldIn, pos);
    }

    /**
     * Spawn this Block's drops into the World as EntityItems
     */
    public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int fortune) {
        this.dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, fortune);
    }

    /**
     * Spawns this Block's drops into the World as EntityItems.
     */
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) // do not drop items while restoring blockstates, prevents item dupe
        {
            List<ItemStack> drops = getDrops(worldIn, pos, state, fortune); // use the old method until it gets removed, for backward compatibility
            chance = ForgeEventFactory.fireBlockHarvesting(drops, worldIn, pos, state, fortune, chance, false, harvesters.get());

            for (ItemStack drop : drops) {
                if (worldIn.rand.nextFloat() <= chance) {
                    spawnAsEntity(worldIn, pos, drop);
                }
            }
        }
    }

    /**
     * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It
     * returns the metadata of the dropped item based on the old metadata of the block.
     */
    public int damageDropped(IBlockState state) {
        return 0;
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit.
     */
    @Deprecated
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return this.rayTrace(pos, start, end, blockState.getBoundingBox(worldIn, pos));
    }

    @Nullable
    protected RayTraceResult rayTrace(BlockPos pos, Vec3d start, Vec3d end, AxisAlignedBB boundingBox) {
        Vec3d vec3d = start.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
        Vec3d vec3d1 = end.subtract((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
        RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
        return raytraceresult == null ? null : new RayTraceResult(raytraceresult.hitVec.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ()), raytraceresult.sideHit, pos);
    }

    /**
     * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
     * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
     */
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.SOLID;
    }

    /**
     * Check whether this Block can be placed at pos, while aiming at the specified side of an adjacent block
     */
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
        return this.canPlaceBlockAt(worldIn, pos);
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
    }

    /**
     * Called when the block is right clicked by a player.
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    /**
     * Called when the given entity walks on this Block
     */
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    @Deprecated
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getStateFromMeta(meta);
    }

    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
    }

    public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
        return motion;
    }

    @Deprecated
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 0;
    }

    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    @Deprecated
    public boolean canProvidePower(IBlockState state) {
        return false;
    }

    /**
     * Called When an Entity Collided with the Block
     */
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    }

    @Deprecated
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 0;
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    /*public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);

        if (this.canSilkHarvest(worldIn, pos, state, player) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0)
        {
            java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
            ItemStack itemstack = this.getSilkTouchDrop(state);

            if (!itemstack.isEmpty())
            {
                items.add(itemstack);
            }

            net.thegaminghuskymc.sandboxgameforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
            for (ItemStack item : items)
            {
                spawnAsEntity(worldIn, pos, item);
            }
        }
        else
        {
            harvesters.set(player);
            harvesters.set(null);
        }
    }*/
    @Deprecated //Forge: State sensitive version
    protected boolean canSilkHarvest() {
        return this.getDefaultState().isFullCube() && !this.hasTileEntity(silk_check_state.get());
    }

    protected ItemStack getSilkTouchDrop(IBlockState state) {
        Item item = Item.getItemFromBlock(this);
        int i = 0;

        /*if (item.getHasSubtypes())
        {
            i = this.getMetaFromState(state);
        }*/

        return new ItemStack(item, 1, i);
    }

    /**
     * Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return this.quantityDropped(random);
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    }

    /**
     * Return true if an entity can be spawned inside the block (used to get the player's bed spawn location)
     */
    public boolean canSpawnInBlock() {
        return !this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid();
    }

    /**
     * Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName() {
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     * Returns the unlocalized name of the block with "tile." appended to the front.
     */
    public String getUnlocalizedName() {
        return "block." + this.unlocalizedName;
    }

    public Block setUnlocalizedName(String name) {
        this.unlocalizedName = name;
        return this;
    }

    /**
     * Called on server when World#addBlockEvent is called. If server returns true, then also called on the client. On
     * the Server, this may perform additional changes to the world, like pistons replacing the block with an extended
     * base. On the client, the update may involve replacing tile entities or effects such as sounds or particles
     */
    @Deprecated
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        return false;
    }

    /**
     * Return the state of blocks statistics flags - if the block is counted for mined and placed.
     */
    public boolean getEnableStats() {
        return this.enableStats;
    }

    protected Block disableStats() {
        this.enableStats = false;
        return this;
    }

    @Deprecated
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return this.blockMaterial.getMobilityFlag();
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state) {
        return state.isBlockNormalCube() ? 0.2F : 1.0F;
    }

    /**
     * Block's chance to react to a living entity falling on it.
     */
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.fall(fallDistance, 1.0F);
    }

    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    public void onLanded(World worldIn, Entity entityIn) {
        entityIn.motionY = 0.0D;
    }

    @Deprecated // Forge: Use more sensitive version below: getPickBlock
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(state));
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    /**
     * Returns the CreativeTab to display the given block on.
     */
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }

    public Block setCreativeTab(CreativeTabs tab) {
        this.displayOnCreativeTab = tab;
        return this;
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this block
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    }

    /**
     * Called similar to random ticks, but only when it is raining.
     */
    public void fillWithRain(World worldIn, BlockPos pos) {
    }

    public boolean requiresUpdates() {
        return true;
    }

    public boolean isAssociatedBlock(Block other) {
        return this == other;
    }

    @Deprecated
    public boolean hasComparatorInputOverride(IBlockState state) {
        return false;
    }

    @Deprecated
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[0]);
    }

    public BlockStateContainer getBlockState() {
        return this.blockState;
    }

    public final IBlockState getDefaultState() {
        return this.defaultBlockState;
    }

    protected final void setDefaultState(IBlockState state) {
        this.defaultBlockState = state;
    }

    /**
     * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
     */
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.NONE;
    }

    @Deprecated
    public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        Block.EnumOffsetType block$enumoffsettype = this.getOffsetType();

        if (block$enumoffsettype == Block.EnumOffsetType.NONE) {
            return Vec3d.ZERO;
        } else {
            long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
            return new Vec3d(((double) ((float) (i >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D, block$enumoffsettype == Block.EnumOffsetType.XYZ ? ((double) ((float) (i >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D : 0.0D, ((double) ((float) (i >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D);
        }
    }

    /**
     * Checks if the block is a solid face on the given side, used by placement logic.
     *
     * @param base_state The base state, getActualState should be called first
     * @param world The current world
     * @param pos Block position in world
     * @param side The side to check
     * @return True if the block is solid on the specified side.
     */
    @Deprecated //Use IBlockState.getBlockFaceShape
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        if (base_state.isTopSolid() && side == EnumFacing.UP) // Short circuit to vanilla function if its true
            return true;

        if (this instanceof BlockSlab)
        {
            IBlockState state = this.getActualState(base_state, world, pos);
            return base_state.isFullBlock()
                  || (state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP    && side == EnumFacing.UP  )
                  || (state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.BOTTOM && side == EnumFacing.DOWN);
        }
        return isNormalCube(base_state, world, pos);
    }

    public String toString() {
        return "Block{" + REGISTRY.getNameForObject(this) + "}";
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
    }

    /**
     * Gets the slipperiness at the given location at the given state. Normally
     * between 0 and 1.
     * <p>
     * Note that entities may reduce slipperiness by a certain factor of their own;
     *
     * @param state  state of the block
     * @param world  the world
     * @param pos    the position in the world
     * @param entity the entity in question
     * @return the factor by which the entity's motion should be multiplied
     */
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return slipperiness;
    }

    /**
     * Sets the base slipperiness level. Normally between 0 and 1.
     * <p>
     * <b>Calling this method may have no effect on the function of this block</b>,
     * or may not have the expected result. This block is free to caclculate
     * its slipperiness arbitrarily. This method is guaranteed to work on the
     * base {@code Block} class.
     *
     * @param slipperiness the base slipperiness of this block
     * @see #getSlipperiness(IBlockState, IBlockAccess, BlockPos, Entity)
     */
    public void setDefaultSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
    }

    /**
     * Get a light value for this block, taking into account the given state and coordinates, normal ranges are between 0 and 15
     *
     * @param state Block state
     * @param world The current world
     * @param pos   Block position in world
     * @return The light value
     */
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getLightValue();
    }

    /**
     * Checks if a player or entity can use this block to 'climb' like a ladder.
     *
     * @param state  The current state
     * @param world  The current world
     * @param pos    Block position in world
     * @param entity The entity trying to use the ladder, CAN be null.
     * @return True if the block should act like a ladder
     */
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return false;
    }

    /**
     * Return true if the block is a normal, solid cube.  This
     * determines indirect power state, entity ejection from blocks, and a few
     * others.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return True if the block is a full cube
     */
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.isNormalCube();
    }

    /**
     * Check if the face of a block should block rendering.
     * <p>
     * Faces which are fully opaque should return true, faces with transparency
     * or faces which do not span the full size of the block should return false.
     *
     * @param state The current block state
     * @param world The current world
     * @param pos   Block position in world
     * @param face  The side to check
     * @return True if the block is opaque on the specified side.
     */
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return state.isOpaqueCube();
    }

    /**
     * Determines if this block should set fire and deal fire damage
     * to entities coming into contact with it.
     *
     * @param world The current world
     * @param pos   Block position in world
     * @return True if the block should deal damage
     */
    public boolean isBurning(IBlockAccess world, BlockPos pos) {
        return false;
    }

    /**
     * Determines this block should be treated as an air block
     * by the rest of the code. This method is primarily
     * useful for creating pure logic-blocks that will be invisible
     * to the player and otherwise interact as air would.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return True if the block considered air
     */
    public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getMaterial() == Material.AIR;
    }

    /**
     * Determines if the player can harvest this block, obtaining it's drops when the block is destroyed.
     *
     * @param player The player damaging the block
     * @param pos    The block's current position
     * @return True to spawn the drops
     */
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return net.thegaminghuskymc.sgf.common.ForgeHooks.canHarvestBlock(this, player, world, pos);
    }

    /**
     * Called when a player removes a block.  This is responsible for
     * actually destroying the block, and the block is intact at time of call.
     * This is called regardless of whether the player can harvest the block or
     * not.
     * <p>
     * Return true if the block is actually destroyed.
     * <p>
     * Note: When used in multiplayer, this is called on both client and
     * server sides!
     *
     * @param state       The current state.
     * @param world       The current world
     * @param player      The player damaging the block, may be null
     * @param pos         Block position in world
     * @param willHarvest True if Block.harvestBlock will be called after this, if the return in true.
     *                    Can be useful to delay the destruction of tile entities till after harvestBlock
     * @return True if the block is actually destroyed.
     */
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        this.onBlockHarvested(world, pos, state, player);
        return world.setBlockState(pos, net.thegaminghuskymc.sandboxgame.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }

    /**
     * Called throughout the code as a replacement for block instanceof BlockContainer
     * Moving this to the Block base class allows for mods that wish to extend vanilla
     * blocks, and also want to have a tile entity on that block, may.
     * <p>
     * Return true from this function to specify this block has a tile entity.
     *
     * @param state State of the current block
     * @return True if block has a tile entity, false otherwise
     */
    public boolean hasTileEntity(IBlockState state) {
        return isTileProvider;
    }

    /**
     * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity
     * Return the same thing you would from that function.
     * This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
     *
     * @param state The state of the current block
     * @return A instance of a class extending TileEntity
     */
    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        if (isTileProvider) {
            return ((ITileEntityProvider) this).createNewTileEntity(world, getMetaFromState(state));
        }
        return null;
    }

    /**
     * State and fortune sensitive version, this replaces the old (int meta, Random rand)
     * version in 1.1.
     *
     * @param state   Current state
     * @param fortune Current item fortune level
     * @param random  Random number generator
     * @return The number of items to drop
     */
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return quantityDroppedWithBonus(fortune, random);
    }

    /**
     * @deprecated use {@link #getDrops(NonNullList, IBlockAccess, BlockPos, IBlockState, int)}
     */
    @Deprecated
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        NonNullList<ItemStack> ret = NonNullList.create();
        getDrops(ret, world, pos, state, fortune);
        return ret;
    }

    /**
     * This gets a complete list of items dropped from this block.
     *
     * @param drops   add all items this block drops to this drops list
     * @param world   The current world
     * @param pos     Block position in world
     * @param state   Current state
     * @param fortune Breakers fortune level
     */
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for (int i = 0; i < count; i++) {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR) {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }
    }

    /**
     * Return true from this function if the player with silk touch can harvest this block directly, and not it's normal drops.
     *
     * @param world  The world
     * @param pos    Block position in world
     * @param state  current block state
     * @param player The player doing the harvesting
     * @return True if the block can be directly harvested using silk touch
     */
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        silk_check_state.set(state);
        ;
        boolean ret = this.canSilkHarvest();
        silk_check_state.set(null);
        return ret;
    }

    /**
     * Called when a leaf should start its decay process.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     */
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
    }

    /**
     * Determines if this block can prevent leaves connected to it from decaying.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return true if the presence this block can prevent leaves from decaying.
     */
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    /**
     * Determines if this block is considered a leaf block, used to apply the leaf decay and generation system.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return true if this block is considered leaves.
     */
    public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getMaterial() == Material.LEAVES;
    }

    /**
     * Used during tree growth to determine if newly generated leaves can replace this block.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return true if this block can be replaced by growing leaves.
     */
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isAir(state, world, pos) || isLeaves(state, world, pos); //!state.isFullBlock();
    }

    /**
     * @param world The current world
     * @param pos   Block position in world
     * @return true if the block is wood (logs)
     */
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return false;
    }

    /**
     * Spawn a digging particle effect in the world, this is a wrapper
     * around EffectRenderer.addBlockHitEffects to allow the block more
     * control over the particles. Useful when you have entirely different
     * texture sheets for different sides/locations in the world.
     *
     * @param state The current state
     * @param world The current world
     * @param target The target the player is looking at {x/y/z/side/sub}
     * @param manager A reference to the current particle manager.
     * @return True to prevent vanilla digging particles form spawning.
     */
    /*@SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager)
    {
        return false;
    }*/

    /**
     * Spawn particles for when the block is destroyed. Due to the nature
     * of how this is invoked, the x/y/z locations are not always guaranteed
     * to host your block. So be sure to do proper sanity checks before assuming
     * that the location is this block.
     *
     * @param world The current world
     * @param pos Position to spawn the particle
     * @param manager A reference to the current particle manager.
     * @return True to prevent vanilla break particles from spawning.
     */
    /*@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager)
    {
        return false;
    }*/

    /**
     * Determines if this block can support the passed in plant, allowing it to be planted and grow.
     * Some examples:
     *   Reeds check if its a reed, or if its sand/dirt/grass and adjacent to water
     *   Cacti checks if its a cacti, or if its sand
     *   Nether types check for soul sand
     *   Crops check for tilled soil
     *   Caves check if it's a solid surface
     *   Plains check if its grass or dirt
     *   Water check if its still water
     *
     * @param state The Current state
     * @param world The current world
     * @param pos Block position in world
     * @param direction The direction relative to the given position the plant wants to be, typically its UP
     * @param plantable The plant that wants to check
     * @return True to allow the plant to be planted/stay.
     */
    /*public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
    {
        IBlockState plant = plantable.getPlant(world, pos.offset(direction));
        EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));


        if (plantable instanceof BlockBush && ((BlockBush)plantable).canSustainBush(state))
        {
            return true;
        }

        switch (plantType)
        {
            case Desert: return this == net.thegaminghuskymc.sandboxgame.init.Blocks.HARDENED_CLAY || this == net.thegaminghuskymc.sandboxgame.init.Blocks.STAINED_HARDENED_CLAY;
            case Nether: return this == net.thegaminghuskymc.sandboxgame.init.Blocks.SOUL_SAND;
            case Crop:   return this == net.thegaminghuskymc.sandboxgame.init.Blocks.FARMLAND;
            case Cave:   return state.isSideSolid(world, pos, EnumFacing.UP);
            case Plains: return this == net.thegaminghuskymc.sandboxgame.init.Blocks.GRASS || this == net.thegaminghuskymc.sandboxgame.init.Blocks.DIRT || this == net.thegaminghuskymc.sandboxgame.init.Blocks.FARMLAND;
            case Water:  return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
            case Beach:
                boolean isBeach = this == net.thegaminghuskymc.sandboxgame.init.Blocks.GRASS || this == net.thegaminghuskymc.sandboxgame.init.Blocks.DIRT || this == net.thegaminghuskymc.sandboxgame.init.Blocks.SAND;
                boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
                                    world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
                                    world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
                                    world.getBlockState(pos.south()).getMaterial() == Material.WATER);
                return isBeach && hasWater;
        }

        return false;
    }*/

    /**
     * Determines if the current block is replaceable by Ore veins during world generation.
     *
     * @param state  The current state
     * @param world  The current world
     * @param pos    Block position in world
     * @param target The generic target block the gen is looking for, Standards define stone
     *               for overworld generation, and neatherack for the nether.
     * @return True to allow this block to be replaced by a ore
     */
    public boolean isReplaceableOreGen(IBlockState state, IBlockAccess world, BlockPos pos, com.google.common.base.Predicate<IBlockState> target) {
        return target.apply(state);
    }

    /**
     * Checks if this soil is fertile, typically this means that growth rates
     * of plants on this soil will be slightly sped up.
     * Only vanilla case is tilledField when it is within range of water.
     *
     * @param world The current world
     * @param pos Block position in world
     * @return True if the soil should be considered fertile.
     */
    /*public boolean isFertile(World world, BlockPos pos)
    {
        if (this == net.thegaminghuskymc.sandboxgame.init.Blocks.FARMLAND)
        {
            return ((Integer)world.getBlockState(pos).getValue(BlockFarmland.MOISTURE)) > 0;
        }

        return false;
    }*/

    /**
     * Determine if this block can make a redstone connection on the side provided,
     * Useful to control which sides are inputs and outputs for redstone wires.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @param side  The side that is trying to make the connection, CAN BE NULL
     * @return True to make the connection
     */
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return state.canProvidePower() && side != null;
    }

    /**
     * Determines if this block is can be destroyed by the specified entities normal behavior.
     *
     * @param state The current state
     * @param world The current world
     * @param pos Block position in world
     * @return True to allow the ender dragon to destroy this block
     */
    /*public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
    {
        if (entity instanceof net.thegaminghuskymc.sandboxgame.entity.boss.EntityDragon)
        {
            return this != net.thegaminghuskymc.sandboxgame.init.Blocks.BARRIER &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.OBSIDIAN &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.END_STONE &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.BEDROCK &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.END_PORTAL &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.END_PORTAL_FRAME &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.COMMAND_BLOCK &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.REPEATING_COMMAND_BLOCK &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.CHAIN_COMMAND_BLOCK &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.IRON_BARS &&
                   this != net.thegaminghuskymc.sandboxgame.init.Blocks.END_GATEWAY;
        }
        else if ((entity instanceof net.thegaminghuskymc.sandboxgame.entity.boss.EntityWither) ||
                 (entity instanceof net.thegaminghuskymc.sandboxgame.entity.projectile.EntityWitherSkull))
        {
            return net.thegaminghuskymc.sandboxgame.entity.boss.EntityWither.canDestroyBlock(this);
        }

        return true;
    }*/

    /**
     * Determines if a torch can be placed on the top surface of this block.
     * Useful for creating your own block that torches can be on, such as fences.
     *
     * @param state The current state
     * @param world The current world
     * @param pos   Block position in world
     * @return True to allow the torch to be placed
     */
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (state.isTopSolid() || state.getBlockFaceShape(world, pos, EnumFacing.UP) == BlockFaceShape.SOLID) {
            return this != Blocks.END_GATEWAY && this != Blocks.LIT_PUMPKIN;
        } else {
            return this instanceof BlockFence || this == Blocks.GLASS || this == Blocks.COBBLESTONE_WALL || this == Blocks.STAINED_GLASS;
        }
    }

    /**
     * Called when a user uses the creative pick block button on this block
     *
     * @param target The full target the player is looking at
     * @return A ItemStack to add to the player's inventory, empty itemstack if nothing should be added.
     */
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return getItem(world, pos, state);
    }

    /**
     * Used by getTopSolidOrLiquidBlock while placing biome decorations, villages, etc
     * Also used to determine if the player can spawn on this block.
     *
     * @return False to disallow spawning
     */
    public boolean isFoliage(IBlockAccess world, BlockPos pos) {
        return false;
    }

    /**
     * Allows a block to override the standard EntityLivingBase.updateFallState
     * particles, this is a server side method that spawns particles with
     * WorldServer.spawnParticle
     *
     * @param world             The current Server world
     * @param blockPosition     of the block that the entity landed on.
     * @param iblockstate       State at the specific world/pos
     * @param entity            the entity that hit landed on the block.
     * @param numberOfParticles that vanilla would have spawned.
     * @return True to prevent vanilla landing particles form spawning.
     */
    public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return false;
    }

    /**
     * Allows a block to override the standard vanilla running particles.
     * This is called from {@link Entity#spawnRunningParticles} and is called both,
     * Client and server side, it's up to the implementor to client check / server check.
     * By default vanilla spawns particles only on the client and the server methods no-op.
     *
     * @param state  The BlockState the entity is running on.
     * @param world  The world.
     * @param pos    The position at the entities feet.
     * @param entity The entity running on the block.
     * @return True to prevent vanilla running particles from spawning.
     */
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return false;
    }

    /**
     * Called when a plant grows on this block, only implemented for saplings using the WorldGen*Trees classes right now.
     * Modder may implement this for custom plants.
     * This does not use ForgeDirection, because large/huge trees can be located in non-representable direction,
     * so the source location is specified.
     * Currently this just changes the block to dirt if it was grass.
     * <p>
     * Note: This happens DURING the generation, the generation may not be complete when this is called.
     *
     * @param state  The current state
     * @param world  Current world
     * @param pos    Block position in world
     * @param source Source plant's position in world
     */
    public void onPlantGrow(IBlockState state, World world, BlockPos pos, BlockPos source) {
        if (this == net.thegaminghuskymc.sandboxgame.init.Blocks.GRASS || this == net.thegaminghuskymc.sandboxgame.init.Blocks.FARMLAND) {
            world.setBlockState(pos, net.thegaminghuskymc.sandboxgame.init.Blocks.DIRT.getDefaultState(), 2);
        }
    }

    /**
     * Location aware and overrideable version of the lightOpacity array,
     * return the number to subtract from the light value when it passes through this block.
     * <p>
     * This is not guaranteed to have the tile entity in place before this is called, so it is
     * Recommended that you have your tile entity call relight after being placed if you
     * rely on it for light info.
     *
     * @param state The Block state
     * @param world The current world
     * @param pos   Block position in world
     * @return The amount of light to block, 0 for air, 255 for fully opaque.
     */
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getLightOpacity();
    }

    /**
     * Determines if this block can be used as the base of a beacon.
     *
     * @param world  The current world
     * @param pos    Block position in world
     * @param beacon Beacon position in world
     * @return True, to support the beacon, and make it active with this block.
     */
    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
        return this == net.thegaminghuskymc.sandboxgame.init.Blocks.EMERALD_BLOCK || this == net.thegaminghuskymc.sandboxgame.init.Blocks.GOLD_BLOCK || this == net.thegaminghuskymc.sandboxgame.init.Blocks.DIAMOND_BLOCK || this == net.thegaminghuskymc.sandboxgame.init.Blocks.IRON_BLOCK;
    }

    /**
     * Rotate the block. For vanilla blocks this rotates around the axis passed in (generally, it should be the "face" that was hit).
     * Note: for mod blocks, this is up to the block and modder to decide. It is not mandated that it be a rotation around the
     * face, but could be a rotation to orient *to* that face, or a visiting of possible rotations.
     * The method should return true if the rotation was successful though.
     *
     * @param world The world
     * @param pos   Block position in world
     * @param axis  The axis to rotate around
     * @return True if the rotation was successful, False if the rotation failed, or is not possible
     */
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        IBlockState state = world.getBlockState(pos);
        for (IProperty<?> prop : state.getProperties().keySet()) {
            if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class) {
                Block block = state.getBlock();
                if (!(block instanceof BlockBed)) {
                    IBlockState newState;
                    //noinspection unchecked
                    IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
                    EnumFacing facing = state.getValue(facingProperty);
                    java.util.Collection<EnumFacing> validFacings = facingProperty.getAllowedValues();

                    // rotate horizontal facings clockwise
                    if (validFacings.size() == 4 && !validFacings.contains(EnumFacing.UP) && !validFacings.contains(EnumFacing.DOWN)) {
                        newState = state.withProperty(facingProperty, facing.rotateY());
                    } else {
                        // rotate other facings about the axis
                        EnumFacing rotatedFacing = facing.rotateAround(axis.getAxis());
                        if (validFacings.contains(rotatedFacing)) {
                            newState = state.withProperty(facingProperty, rotatedFacing);
                        } else // abnormal facing property, just cycle it
                        {
                            newState = state.cycleProperty(facingProperty);
                        }
                    }

                    world.setBlockState(pos, newState);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the rotations that can apply to the block at the specified coordinates. Null means no rotations are possible.
     * Note, this is up to the block to decide. It may not be accurate or representative.
     *
     * @param world The world
     * @param pos   Block position in world
     * @return An array of valid axes to rotate around, or null for none or unknown
     */
    @Nullable
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        for (IProperty<?> prop : state.getProperties().keySet()) {
            if ((prop.getName().equals("facing") || prop.getName().equals("rotation")) && prop.getValueClass() == EnumFacing.class) {
                @SuppressWarnings("unchecked")
                java.util.Collection<EnumFacing> values = ((java.util.Collection<EnumFacing>) prop.getAllowedValues());
                return values.toArray(new EnumFacing[values.size()]);
            }
        }
        return null;
    }

    /**
     * Determines the amount of enchanting power this block can provide to an enchanting table.
     *
     * @param world The World
     * @param pos   Block position in world
     * @return The amount of enchanting power this block produces.
     */
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return this == net.thegaminghuskymc.sandboxgame.init.Blocks.BOOKSHELF ? 1 : 0;
    }
    ;

    /**
     * Common way to recolor a block with an external tool
     *
     * @param world The world
     * @param pos   Block position in world
     * @param side  The side hit with the coloring tool
     * @param color The color to change to
     * @return If the recoloring was successful
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, net.thegaminghuskymc.sandboxgame.item.EnumDyeColor color) {
        IBlockState state = world.getBlockState(pos);
        for (IProperty prop : state.getProperties().keySet()) {
            if (prop.getName().equals("color") && prop.getValueClass() == net.thegaminghuskymc.sandboxgame.item.EnumDyeColor.class) {
                net.thegaminghuskymc.sandboxgame.item.EnumDyeColor current = (net.thegaminghuskymc.sandboxgame.item.EnumDyeColor) state.getValue(prop);
                if (current != color && prop.getAllowedValues().contains(color)) {
                    world.setBlockState(pos, state.withProperty(prop, color));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gathers how much experience this block drops when broken.
     *
     * @param state   The current state
     * @param world   The world
     * @param pos     Block position
     * @param fortune
     * @return Amount of XP from breaking this block.
     */
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        return 0;
    }

    /**
     * Called when a tile entity on a side of this block changes is created or is destroyed.
     *
     * @param world    The world
     * @param pos      Block position in world
     * @param neighbor Block position of neighbor
     */
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
    }

    /**
     * Called on an Observer block whenever an update for an Observer is received.
     *
     * @param observerState   The Observer block's state.
     * @param world           The current world.
     * @param observerPos     The Observer block's position.
     * @param changedBlock    The updated block.
     * @param changedBlockPos The updated block's position.
     */
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
    }

    /**
     * Called to determine whether to allow the a block to handle its own indirect power rather than using the default rules.
     *
     * @param world The world
     * @param pos   Block position in world
     * @param side  The INPUT side of the block to be powered - ie the opposite of this block's output side
     * @return Whether Block#isProvidingWeakPower should be called when determining indirect power
     */
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return state.isNormalCube();
    }

    /**
     * If this block should be notified of weak changes.
     * Weak changes are changes 1 block away through a solid block.
     * Similar to comparators.
     *
     * @param world The current world
     * @param pos   Block position in world
     * @return true To be notified of changes
     */
    public boolean getWeakChanges(IBlockAccess world, BlockPos pos) {
        return false;
    }

    /**
     * Sets or removes the tool and level required to harvest this block.
     *
     * @param toolClass Class
     * @param level     Harvest level:
     *                  Wood:    0
     *                  Stone:   1
     *                  Iron:    2
     *                  Diamond: 3
     *                  Gold:    0
     */
    public void setHarvestLevel(String toolClass, int level) {
        java.util.Iterator<IBlockState> itr = getBlockState().getValidStates().iterator();
        while (itr.hasNext()) {
            setHarvestLevel(toolClass, level, itr.next());
        }
    }

    /**
     * Sets or removes the tool and level required to harvest this block.
     *
     * @param toolClass Class
     * @param level     Harvest level:
     *                  Wood:    0
     *                  Stone:   1
     *                  Iron:    2
     *                  Diamond: 3
     *                  Gold:    0
     * @param state     The specific state.
     */
    public void setHarvestLevel(String toolClass, int level, IBlockState state) {
        int idx = this.getMetaFromState(state);
        this.harvestTool[idx] = toolClass;
        this.harvestLevel[idx] = level;
    }

    /**
     * Queries the class of tool required to harvest this block, if null is returned
     * we assume that anything can harvest this block.
     */
    @Nullable
    public String getHarvestTool(IBlockState state) {
        return harvestTool[getMetaFromState(state)];
    }

    /**
     * Queries the harvest level of this item stack for the specified tool class,
     * Returns -1 if this tool is not of the specified type
     *
     * @return Harvest level, or -1 if not the specified tool type.
     */
    public int getHarvestLevel(IBlockState state) {
        return harvestLevel[getMetaFromState(state)];
    }

    /**
     * Checks if the specified tool type is efficient on this block,
     * meaning that it digs at full speed.
     */
    public boolean isToolEffective(String type, IBlockState state) {
        if ("pickaxe".equals(type) && (this == net.thegaminghuskymc.sandboxgame.init.Blocks.REDSTONE_ORE || this == net.thegaminghuskymc.sandboxgame.init.Blocks.LIT_REDSTONE_ORE || this == net.thegaminghuskymc.sandboxgame.init.Blocks.OBSIDIAN))
            return false;
        return type != null && type.equals(getHarvestTool(state));
    }

    /**
     * Can return IExtendedBlockState
     */
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state;
    }

    /**
     * Called when the entity is inside this block, may be used to determined if the entity can breathing,
     * display material overlays, or if the entity can swim inside a block.
     *
     * @param world       that is being tested.
     * @param blockpos    position thats being tested.
     * @param iblockstate state at world/blockpos
     * @param entity      that is being tested.
     * @param yToTest,    primarily for testingHead, which sends the the eye level of the entity, other wise it sends a y that can be tested vs liquid height.
     * @param materialIn  to test for.
     * @param testingHead when true, its testing the entities head for vision, breathing ect... otherwise its testing the body, for swimming and movement adjustment.
     * @return null for default behavior, true if the entity is within the material, false if it was not.
     */
    @Nullable
    public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead) {
        return null;
    }

    /**
     * Called when boats or fishing hooks are inside the block to check if they are inside
     * the material requested.
     *
     * @param world       world that is being tested.
     * @param pos         block thats being tested.
     * @param boundingBox box to test, generally the bounds of an entity that are besting tested.
     * @param materialIn  to check for.
     * @return null for default behavior, true if the box is within the material, false if it was not.
     */
    @Nullable
    public Boolean isAABBInsideMaterial(World world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn) {
        return null;
    }

    /**
     * Called when entities are moving to check if they are inside a liquid
     *
     * @param world       world that is being tested.
     * @param pos         block thats being tested.
     * @param boundingBox box to test, generally the bounds of an entity that are besting tested.
     * @return null for default behavior, true if the box is within the material, false if it was not.
     */
    @Nullable
    public Boolean isAABBInsideLiquid(World world, BlockPos pos, AxisAlignedBB boundingBox) {
        return null;
    }

    /**
     * Queries if this block should render in a given layer.
     */
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return getBlockLayer() == layer;
    }

    protected NonNullList<ItemStack> captureDrops(boolean start) {
        if (start) {
            captureDrops.set(true);
            capturedDrops.get().clear();
            return NonNullList.create();
        } else {
            captureDrops.set(false);
            return capturedDrops.get();
        }
    }

    /**
     * @param state     The state
     * @param world     The world
     * @param pos       The position of this state
     * @param beaconPos The position of the beacon
     * @return A float RGB [0.0, 1.0] array to be averaged with a beacon's existing beam color, or null to do nothing to the beam
     */
    @Nullable
    public float[] getBeaconColorMultiplier(IBlockState state, World world, BlockPos pos, BlockPos beaconPos) {
        return null;
    }

    /* ========================================= FORGE END ======================================*/

    /**
     * Gets the {@link IBlockState} to place
     *
     * @param world  The world the block is being placed in
     * @param pos    The position the block is being placed at
     * @param facing The side the block is being placed on
     * @param hitX   The X coordinate of the hit vector
     * @param hitY   The Y coordinate of the hit vector
     * @param hitZ   The Z coordinate of the hit vector
     * @param placer The entity placing the block
     * @param hand   The player hand used to place this block
     * @return The state to be placed in the world
     */
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer);
    }

    /**
     * Determines if another block can connect to this block
     *
     * @param world  The current world
     * @param pos    The position of this block
     * @param facing The side the connecting block is on
     * @return True to allow another block to connect to this block
     */
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return false;
    }

    /**
     * @param state The state
     * @return true if the block is sticky block which used for pull or push adjacent blocks (use by piston)
     */
    public boolean isStickyBlock(IBlockState state) {
        return state.getBlock() == Blocks.SLIME_BLOCK;
    }

    public enum EnumOffsetType {
        NONE,
        XZ,
        XYZ;
    }
}