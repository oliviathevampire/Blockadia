package net.timmy.th2.worldgen;

import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.BlockLeaves;
import net.thegaminghuskymc.sandboxgame.block.material.Material;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3d;
import net.thegaminghuskymc.sandboxgame.util.math.Vec3i;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.gen.feature.WorldGenAbstractTree;
import net.timmy.th2.blocks.base.ModLog;
import net.timmy.th2.blocks.base.ModSapling;

import java.util.*;

public class WorldGenThaumaniaTree extends WorldGenAbstractTree {

    protected IBlockState LOG;
    protected IBlockState LEAVES;
    protected WorldGenNorsecraftTreeConfig CONFIG;


    public WorldGenThaumaniaTree(boolean notify, Block log, Block leaves) {
        super(notify);
        LOG = log.getDefaultState().withProperty(ModLog.AXIS, EnumFacing.Axis.Y);
        LEAVES = leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false);
    }

    private double vectorMagnitude(Vec3d vec) {
        return Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z);
    }

    private Vec3d vectorDiv(Vec3d vec, double divider) {
        return new Vec3d(vec.x / divider, vec.y / divider, vec.z / divider);
    }

    private Vec3d vectorMult(Vec3d vec, double scalar) {
        return new Vec3d(vec.x * scalar, vec.y * scalar, vec.z * scalar);
    }

    private Vec3d vectorRand() {
        return new Vec3d(new Random().nextDouble(), new Random().nextDouble(), new Random().nextDouble());
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {

        int i = rand.nextInt(3) + rand.nextInt(2) + 6;
        int k = position.getY();

        if (k >= 1 && k + i + 1 < 256) {
            BlockPos blockpos = position.down();
            this.onPlantGrow(worldIn, blockpos, position);
            new FractalTree(worldIn, position);
            return true;
        }

        return false;
    }

    protected boolean placeTreeOfHeight(World worldIn, BlockPos pos, int height) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int l = 0; l <= height + 1; ++l) {
            int i1 = 1;
            if (l == 0) i1 = 0;
            if (l >= height - 1) i1 = 2;

            for (int j1 = -i1; j1 <= i1; ++j1) {
                for (int k1 = -i1; k1 <= i1; ++k1) {
                    if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(i + j1, j + l, k + k1))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void onPlantGrow(World world, BlockPos pos, BlockPos source) {
        IBlockState state = world.getBlockState(pos);
        state.getBlock().onPlantGrow(state, world, pos, source);
    }

    /**
     * returns whether or not a tree can grow into a block
     * For example, a tree will not grow into stone
     */
    protected boolean canGrowInto(Block blockType) {
        Material material = blockType.getDefaultState().getMaterial();
        return (material == Material.AIR) ||
                (material == Material.LEAVES) ||
                (blockType == Blocks.GRASS) ||
                (blockType == Blocks.DIRT) ||
                (blockType == Blocks.LOG) ||
                (blockType == Blocks.LOG2) ||
                (blockType == Blocks.SAPLING) ||
                (blockType == Blocks.VINE) ||
                (ModSapling.class.isAssignableFrom(blockType.getClass()))
                ;
    }

    @Override
    public boolean isReplaceable(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos) || state.getBlock().isWood(world, pos) || canGrowInto(state.getBlock());
    }

    protected void drawLine(World worldIn, Vec3i a, Vec3i b) {
        int i, dx, dy, dz, l, m, n, x_inc, y_inc, z_inc, err_1, err_2, dx2, dy2, dz2;
        int
                x1 = a.getX(),
                x2 = b.getX(),
                y1 = a.getY(),
                y2 = b.getY(),
                z1 = a.getZ(),
                z2 = b.getZ();
        int[] point = new int[3];

        point[0] = x1;
        point[1] = y1;
        point[2] = z1;
        dx = x2 - x1;
        dy = y2 - y1;
        dz = z2 - z1;
        x_inc = (dx < 0) ? -1 : 1;
        l = Math.abs(dx);
        y_inc = (dy < 0) ? -1 : 1;
        m = Math.abs(dy);
        z_inc = (dz < 0) ? -1 : 1;
        n = Math.abs(dz);
        dx2 = l << 1;
        dy2 = m << 1;
        dz2 = n << 1;

        if ((l >= m) && (l >= n)) {
            err_1 = dy2 - l;
            err_2 = dz2 - l;
            for (i = 0; i < l; i++) {
                placeLogAt(worldIn, new BlockPos(point[0], point[1], point[2]));
                if (err_1 > 0) {
                    point[1] += y_inc;
                    err_1 -= dx2;
                }
                if (err_2 > 0) {
                    point[2] += z_inc;
                    err_2 -= dx2;
                }
                err_1 += dy2;
                err_2 += dz2;
                point[0] += x_inc;
            }
        } else if ((m >= l) && (m >= n)) {
            err_1 = dx2 - m;
            err_2 = dz2 - m;
            for (i = 0; i < m; i++) {
                placeLogAt(worldIn, new BlockPos(point[0], point[1], point[2]));
                if (err_1 > 0) {
                    point[0] += x_inc;
                    err_1 -= dy2;
                }
                if (err_2 > 0) {
                    point[2] += z_inc;
                    err_2 -= dy2;
                }
                err_1 += dx2;
                err_2 += dz2;
                point[1] += y_inc;
            }
        } else {
            err_1 = dy2 - n;
            err_2 = dx2 - n;
            for (i = 0; i < n; i++) {
                placeLogAt(worldIn, new BlockPos(point[0], point[1], point[2]));
                if (err_1 > 0) {
                    point[1] += y_inc;
                    err_1 -= dz2;
                }
                if (err_2 > 0) {
                    point[0] += x_inc;
                    err_2 -= dz2;
                }
                err_1 += dy2;
                err_2 += dx2;
                point[2] += z_inc;
            }
        }
    }

    protected void placeLogAt(World worldIn, BlockPos pos) {
        if (this.canGrowInto(worldIn.getBlockState(pos).getBlock()) && !worldIn.isRemote)
            this.setBlockAndNotifyAdequately(worldIn, pos, LOG);
    }

    protected void placeLeafAt(World worldIn, BlockPos blockpos) {
        IBlockState state = worldIn.getBlockState(blockpos);

        if (state.getBlock().isAir(state, worldIn, blockpos) && !worldIn.isRemote)
            this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAVES);
    }

    public WorldGenThaumaniaTree configure(Map<String, Float> confMap) {
        CONFIG = new WorldGenNorsecraftTreeConfig(
                confMap.get("girth").intValue(),
                confMap.get("minHeight").intValue(),
                confMap.get("maxHeight").intValue(),
                confMap.get("cubage").intValue(),
                confMap.get("maxDistanceMag").intValue(),
                confMap.get("minDistanceMag").intValue()
        );
        return this;
    }

    private class Branch {
        private Vec3d pos;
        private Branch parent;
        private Vec3d dir;
        private int count = 0;
        private Vec3d originalDir;
        private int len = 5;

        public Branch(World worldIn, Branch parent, BlockPos pos, Vec3d dir, int girth) {
            this.originalDir = dir;
            this.pos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
            this.parent = parent;
            this.dir = dir;
            if (girth > 1) {
                switch (girth) {
                    case 2:
                        drawLine(worldIn, pos.add(1, 0, 0), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(0, 0, 1), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(1, 0, 1), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                    case 3:
                        drawLine(worldIn, pos.add(2, 0, 0), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(0, 0, 2), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(2, 0, 2), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(2, 0, 1), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                        drawLine(worldIn, pos.add(1, 0, 2), new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
                }
            }
            drawLine(worldIn, pos, new BlockPos(pos.getX(), pos.getY() * dir.y, pos.getZ()));
        }

        public Branch(World worldIn, Branch parent, Vec3d pos, Vec3d dir, int girth) {
            this.pos = pos;
            this.parent = parent;
            this.dir = dir;
        }

        public Branch(World worldIn, Branch p, int girth) {
            parent = p;
            pos = parent.next(worldIn);
            dir = parent.dir;
            originalDir = dir;
        }

        public Vec3d next(World worldIn) {
            Vec3d v = vectorMult(dir, len);
            Vec3d next = pos.add(v);
            return next;
        }

        public Branch reset() {
            this.dir = this.originalDir;
            this.count = 0;
            return this;
        }
    }

    private class Leaves {
        protected int LEAVES_RADIUS = 4;
        Vec3d pos;
        World worldIn;
        boolean reached = false;

        public Leaves(World worldIn, int x, int y, int z) {
            this.worldIn = worldIn;
            this.pos = new Vec3d(x, y, z);
        }

        public void reached() {
            this.reached = true;
        }

        public Leaves draw() {

            placeLogAt(worldIn, new BlockPos(this.pos));

            if (LEAVES != null) {
                //*
                int r = LEAVES_RADIUS;
                int x1, y1, z1, x2, y2, z2, x0, y0, z0;
                x0 = (int) this.pos.x;
                y0 = (int) this.pos.y;
                z0 = (int) this.pos.z;

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                double r2 = Math.pow(r, 2);

                x1 = x0 - r;

                x2 = x0 + r;
                y2 = y0 + r;
                z2 = z0 + r;
                for (; x1 <= x2; x1++) {
                    y1 = y0 - r;
                    for (; y1 <= y2; y1++) {
                        z1 = z0 - r;
                        for (; z1 <= z2; z1++) {
                            double check = Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2) + Math.pow(z1 - z0, 2);
                            if (check + 8 < r2) {
                                placeLeafAt(worldIn, blockpos$mutableblockpos.setPos(x1, y1, z1).toImmutable());
                            }
                        }
                    }
                }//*/
            }
            return this;
        }
    }

    private class FractalTree {
        private Set<Leaves> leaves;
        private Set<Branch> branches;
        private List<Branch> root;

        public FractalTree(World worldIn, BlockPos origin) {

            leaves = new HashSet<>();
            root = new ArrayList<>();

            int halfWidth, halfDepth;
            halfWidth = halfDepth = (int) Math.sqrt(CONFIG.cubage / CONFIG.height) / 2;

            int xa = origin.getX() - halfWidth;
            int xz = origin.getX() + halfWidth;

            int ya = origin.getY() + CONFIG.minHeight;
            int yz = origin.getY() + CONFIG.maxHeight - CONFIG.minHeight;

            int za = origin.getZ() - halfDepth;
            int zz = origin.getZ() + halfDepth;

            int density = CONFIG.cubage / 50;

            for (int i = 0; i < density; i++) {

                int xp = xa + new Random().nextInt(Math.abs(xz - xa));
                int yp = ya + new Random().nextInt(Math.abs(yz - ya));
                int zp = za + new Random().nextInt(Math.abs(zz - za));

                leaves.add(new Leaves(worldIn, xp, yp, zp).draw());
            }
            root.add(new Branch(worldIn, null, origin, new Vec3d(0, 1, 0), CONFIG.girth));
            root.add(new Branch(worldIn, null, origin, new Vec3d(0, 1, 0), CONFIG.girth));
            root.add(new Branch(worldIn, null, origin, new Vec3d(0, 1, 0), CONFIG.girth));
            root.add(new Branch(worldIn, null, origin, new Vec3d(0, 1, 0), CONFIG.girth));

            this.grow(worldIn);
            this.show(worldIn);


        }

        private void grow(World worldIn) {
            for (Leaves l : leaves) {
                Branch r = root.get(new Random().nextInt(root.size()));
                drawLine(worldIn, new BlockPos(r.pos), new BlockPos(r.next(worldIn)));
                drawLine(worldIn, new BlockPos(r.next(worldIn)), new BlockPos(l.pos));
            }
        }

        void show(World worldIn) {
            BlockPos.MutableBlockPos pointA = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos pointB = new BlockPos.MutableBlockPos();
            if (branches != null)
                for (int i = 0; i < branches.size(); i++) {
                    Branch b = (Branch) branches.toArray()[i];
                    if (b.parent != null) {
                        pointA.setPos(b.pos.x, b.pos.y, b.pos.z);
                        pointB.setPos(b.parent.pos.x, b.parent.pos.y, b.parent.pos.z);
                        drawLine(worldIn, pointA, pointB);
                    }
                }
        }

        boolean closeEnough(Branch b) {
            for (Leaves l : leaves) {
                float d = (float) b.pos.distanceTo(l.pos);
                if (d < CONFIG.maxDistanceMag) {
                    return true;
                }
            }
            return false;
        }
    }

    public class WorldGenNorsecraftTreeConfig {
        int girth;
        int minHeight;
        int maxHeight;
        int height;
        int cubage;
        int maxDistanceMag;
        int minDistanceMag;

        public WorldGenNorsecraftTreeConfig(
                int girth,
                int minHeight,
                int maxHeight,
                int cubage,
                int maxDistanceMag,
                int minDistanceMag
        ) {
            this.girth = girth;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
            this.height = (int) Math.ceil(minHeight + new Random().nextFloat() * (maxHeight - minHeight));
            this.cubage = cubage;
            this.maxDistanceMag = maxDistanceMag + minHeight + (2 * (height / 3));
            this.minDistanceMag = minDistanceMag;
        }
    }
}