package team.hdt.blockadia.engine_rewrite.core.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import ga.pheonix.utillib.utils.anouncments.Nullable;
import team.hdt.blockadia.engine_rewrite.core.entitys.Entity;

import java.util.List;

public class CollisionDitection {

    /**
     * this collision system is not complete and stuff will need to be added later
     */

    public List<AxisAlignedBB3D> getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB3D aabb) {
        List<AxisAlignedBB3D> list = Lists.newArrayList();
        this.func_191504_a(entityIn, aabb, false, list);

        if (entityIn != null) {
            List<Entity> list1 = null; //TODO: can someone help with this origanl code bellow;
            // this.getEntitiesWithinAABBExcludingEntity(entityIn, aabb.expandXyz(0.25D));

            for (int i = 0; i < list1.size(); ++i) {
                Entity entity = list1.get(i);
            }
        }

        return list;
    }


    /**
     * Returns true if the given bbox collides with any block.
     */
    public boolean collidesWithAnyBlock(AxisAlignedBB3D bbox) {
        return this.func_191504_a(null, bbox, true, Lists.newArrayList());
    }

    private boolean func_191504_a(@Nullable Entity p_191504_1_, AxisAlignedBB3D p_191504_2_, boolean p_191504_3_, @Nullable List<AxisAlignedBB3D> p_191504_4_) {
        int i = MathHelper.floor(p_191504_2_.minX) - 1;
        int j = MathHelper.ceil(p_191504_2_.maxX) + 1;
        int k = MathHelper.floor(p_191504_2_.minY) - 1;
        int l = MathHelper.ceil(p_191504_2_.maxY) + 1;
        int i1 = MathHelper.floor(p_191504_2_.minZ) - 1;
        int j1 = MathHelper.ceil(p_191504_2_.maxZ) + 1;

        try {
            for (int k1 = i; k1 < j; ++k1) {
                for (int l1 = i1; l1 < j1; ++l1) {
                    boolean flag2 = k1 == i || k1 == j - 1;
                    boolean flag3 = l1 == i1 || l1 == j1 - 1;


                    if (p_191504_3_ && !p_191504_4_.isEmpty()) {
                        boolean flag5 = true;
                        return flag5;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return !p_191504_4_.isEmpty();
    }

    public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB3D aabb, @Nullable Predicate<? super T> filter) {
        int i = MathHelper.floor((aabb.minX - 2.0D) / 16.0D);
        int j = MathHelper.ceil((aabb.maxX + 2.0D) / 16.0D);
        int k = MathHelper.floor((aabb.minZ - 2.0D) / 16.0D);
        int l = MathHelper.ceil((aabb.maxZ + 2.0D) / 16.0D);
        List<T> list = Lists.newArrayList();

        return list;
    }


    private static class MathHelper {
        public static int floor(double value) {
            int i = (int) value;
            return value < (double) i ? i - 1 : i;
        }

        public static int ceil(double value) {
            int i = (int) value;
            return value > (double) i ? i + 1 : i;
        }
    }
}
