package team.hdt.blockadia.game_engine.common.util.collision;


import team.hdt.blockadia.game_engine.common.util.interfaces.Nullable;
import team.hdt.blockadia.game_engine.common.util.math.BlockPos;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

public class AxisAlignedBB
{
    public final float minX;
    public final float minY;
    public final float minZ;
    public final float maxX;
    public final float maxY;
    public final float maxZ;

    public AxisAlignedBB(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public AxisAlignedBB(BlockPos pos)
    {
        this((float)pos.getX(), (float)pos.getY(), (float)pos.getZ(), (float)(pos.getX() + 1), (float)(pos.getY() + 1), (float)(pos.getZ() + 1));
    }

    public AxisAlignedBB(BlockPos pos1, BlockPos pos2)
    {
        this((float)pos1.getX(), (float)pos1.getY(), (float)pos1.getZ(), (float)pos2.getX(), (float)pos2.getY(), (float)pos2.getZ());
    }
    
    public AxisAlignedBB(Vectors3f min, Vectors3f max)
    {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public AxisAlignedBB setMaxY(float y2)
    {
        return new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof AxisAlignedBB))
        {
            return false;
        }
        else
        {
            AxisAlignedBB axisalignedbb = (AxisAlignedBB)p_equals_1_;
            return Float.compare(axisalignedbb.minX, this.minX) != 0 ? false : (Float.compare(axisalignedbb.minY, this.minY) != 0 ? false : (Float.compare(axisalignedbb.minZ, this.minZ) != 0 ? false : (Float.compare(axisalignedbb.maxX, this.maxX) != 0 ? false : (Float.compare(axisalignedbb.maxY, this.maxY) != 0 ? false : Float.compare(axisalignedbb.maxZ, this.maxZ) == 0))));
        }
    }

    /*public int hashCode()
    {
        long i = Float.floatToLongBits(this.minX);
        int j = (int)(i ^ i >>> 32);
        i = Float.floatToLongBits(this.minY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Float.floatToLongBits(this.minZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Float.floatToLongBits(this.maxX);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Float.floatToLongBits(this.maxY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Float.floatToLongBits(this.maxZ);
        j = 31 * j + (int)(i ^ i >>> 32);
        return j;
    }*/

    public AxisAlignedBB contract(float p_191195_1_, float p_191195_3_, float p_191195_5_)
    {
        float d0 = this.minX;
        float d1 = this.minY;
        float d2 = this.minZ;
        float d3 = this.maxX;
        float d4 = this.maxY;
        float d5 = this.maxZ;

        if (p_191195_1_ < 0.0D)
        {
            d0 -= p_191195_1_;
        }
        else if (p_191195_1_ > 0.0D)
        {
            d3 -= p_191195_1_;
        }

        if (p_191195_3_ < 0.0D)
        {
            d1 -= p_191195_3_;
        }
        else if (p_191195_3_ > 0.0D)
        {
            d4 -= p_191195_3_;
        }

        if (p_191195_5_ < 0.0D)
        {
            d2 -= p_191195_5_;
        }
        else if (p_191195_5_ > 0.0D)
        {
            d5 -= p_191195_5_;
        }

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Adds a coordinate to the bounding box, extending it if the point lies outside the current ranges.
     */
    public AxisAlignedBB addCoord(float x, float y, float z)
    {
        float d0 = this.minX;
        float d1 = this.minY;
        float d2 = this.minZ;
        float d3 = this.maxX;
        float d4 = this.maxY;
        float d5 = this.maxZ;

        if (x < 0.0D)
        {
            d0 += x;
        }
        else if (x > 0.0D)
        {
            d3 += x;
        }

        if (y < 0.0D)
        {
            d1 += y;
        }
        else if (y > 0.0D)
        {
            d4 += y;
        }

        if (z < 0.0D)
        {
            d2 += z;
        }
        else if (z > 0.0D)
        {
            d5 += z;
        }

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Creates a new bounding box that has been expanded. If negative values are used, it will shrink.
     */
    public AxisAlignedBB expand(float x, float y, float z)
    {
        float d0 = this.minX - x;
        float d1 = this.minY - y;
        float d2 = this.minZ - z;
        float d3 = this.maxX + x;
        float d4 = this.maxY + y;
        float d5 = this.maxZ + z;
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB expandXyz(float value)
    {
        return this.expand(value, value, value);
    }

    public AxisAlignedBB intersect(AxisAlignedBB hitbox)
    {
        float d0 = Math.max(this.minX, hitbox.minX);
        float d1 = Math.max(this.minY, hitbox.minY);
        float d2 = Math.max(this.minZ, hitbox.minZ);
        float d3 = Math.min(this.maxX, hitbox.maxX);
        float d4 = Math.min(this.maxY, hitbox.maxY);
        float d5 = Math.min(this.maxZ, hitbox.maxZ);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB union(AxisAlignedBB other)
    {
        float d0 = Math.min(this.minX, other.minX);
        float d1 = Math.min(this.minY, other.minY);
        float d2 = Math.min(this.minZ, other.minZ);
        float d3 = Math.max(this.maxX, other.maxX);
        float d4 = Math.max(this.maxY, other.maxY);
        float d5 = Math.max(this.maxZ, other.maxZ);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    /**
     * Offsets the current bounding box by the specified amount.
     */
    public AxisAlignedBB offset(float x, float y, float z)
    {
        return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public AxisAlignedBB offset(BlockPos pos)
    {
        return new AxisAlignedBB(this.minX + (float)pos.getX(), this.minY + (float)pos.getY(), this.minZ + (float)pos.getZ(), this.maxX + (float)pos.getX(), this.maxY + (float)pos.getY(), this.maxZ + (float)pos.getZ());
    }

    public AxisAlignedBB move(Vectors3f p_191194_1_)
    {
        return this.offset(p_191194_1_.x, p_191194_1_.y, p_191194_1_.z);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public float calculateXOffset(AxisAlignedBB other, float offsetX)
    {
        if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ)
        {
            if (offsetX > 0.0D && other.maxX <= this.minX)
            {
                float d1 = this.minX - other.maxX;

                if (d1 < offsetX)
                {
                    offsetX = d1;
                }
            }
            else if (offsetX < 0.0D && other.minX >= this.maxX)
            {
                float d0 = this.maxX - other.minX;

                if (d0 > offsetX)
                {
                    offsetX = d0;
                }
            }

            return offsetX;
        }
        else
        {
            return offsetX;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public float calculateYOffset(AxisAlignedBB other, float offsetY)
    {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ)
        {
            if (offsetY > 0.0D && other.maxY <= this.minY)
            {
                float d1 = this.minY - other.maxY;

                if (d1 < offsetY)
                {
                    offsetY = d1;
                }
            }
            else if (offsetY < 0.0D && other.minY >= this.maxY)
            {
                float d0 = this.maxY - other.minY;

                if (d0 > offsetY)
                {
                    offsetY = d0;
                }
            }

            return offsetY;
        }
        else
        {
            return offsetY;
        }
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public float calculateZOffset(AxisAlignedBB other, float offsetZ)
    {
        if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY)
        {
            if (offsetZ > 0.0D && other.maxZ <= this.minZ)
            {
                float d1 = this.minZ - other.maxZ;

                if (d1 < offsetZ)
                {
                    offsetZ = d1;
                }
            }
            else if (offsetZ < 0.0D && other.minZ >= this.maxZ)
            {
                float d0 = this.maxZ - other.minZ;

                if (d0 > offsetZ)
                {
                    offsetZ = d0;
                }
            }

            return offsetZ;
        }
        else
        {
            return offsetZ;
        }
    }

    /**
     * Checks if the bounding box intersects with another.
     */
    public boolean intersectsWith(AxisAlignedBB other)
    {
        return this.intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public boolean intersects(float x1, float y1, float z1, float x2, float y2, float z2)
    {
        return this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1;
    }
    
    public boolean intersects(Vectors3f min, Vectors3f max)
    {
        return this.intersects(Math.min(min.x, max.x), Math.min(min.y, max.y), Math.min(min.z, max.z), Math.max(min.x, max.x), Math.max(min.y, max.y), Math.max(min.z, max.z));
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean isVecInside(Vectors3f vec)
    {
        return vec.x > this.minX && vec.x < this.maxX ? (vec.y > this.minY && vec.y < this.maxY ? vec.z > this.minZ && vec.z < this.maxZ : false) : false;
    }

    /**
     * Returns the average length of the edges of the bounding box.
     */
    public float getAverageEdgeLength()
    {
        float d0 = this.maxX - this.minX;
        float d1 = this.maxY - this.minY;
        float d2 = this.maxZ - this.minZ;
        return (d0 + d1 + d2) / 3.0F;
    }

    public AxisAlignedBB contract(float value)
    {
        return this.expandXyz(-value);
    }

    /*@Nullable
    public RayTraceResult calculateIntercept(Vectors3f vecA, Vectors3f vecB)
    {
        Vectors3f vec3d = this.collideWithXPlane(this.minX, vecA, vecB);
        EnumFacing enumfacing = EnumFacing.WEST;
        Vectors3f vec3d1 = this.collideWithXPlane(this.maxX, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.EAST;
        }

        vec3d1 = this.collideWithYPlane(this.minY, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.DOWN;
        }

        vec3d1 = this.collideWithYPlane(this.maxY, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.UP;
        }

        vec3d1 = this.collideWithZPlane(this.minZ, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.NORTH;
        }

        vec3d1 = this.collideWithZPlane(this.maxZ, vecA, vecB);

        if (vec3d1 != null && this.isClosest(vecA, vec3d, vec3d1))
        {
            vec3d = vec3d1;
            enumfacing = EnumFacing.SOUTH;
        }

        return vec3d == null ? null : new RayTraceResult(vec3d, enumfacing);
    }*/
    
    boolean isClosest(Vectors3f p_186661_1_, @Nullable Vectors3f p_186661_2_, Vectors3f p_186661_3_)
    {
        return null;
    }

    @Nullable
    Vectors3f collideWithXPlane(float p_186671_1_, Vectors3f p_186671_3_, Vectors3f p_186671_4_)
    {
        Vectors3f vec3d = p_186671_3_.getIntermediateWithXValue(p_186671_4_, p_186671_1_);
        return vec3d != null && this.intersectsWithYZ(vec3d) ? vec3d : null;
    }

    @Nullable
    Vectors3f collideWithYPlane(float p_186663_1_, Vectors3f p_186663_3_, Vectors3f p_186663_4_)
    {
        Vectors3f vec3d = p_186663_3_.getIntermediateWithYValue(p_186663_4_, p_186663_1_);
        return vec3d != null && this.intersectsWithXZ(vec3d) ? vec3d : null;
    }

    @Nullable
    Vectors3f collideWithZPlane(float p_186665_1_, Vectors3f p_186665_3_, Vectors3f p_186665_4_)
    {
        Vectors3f vec3d = p_186665_3_.getIntermediateWithZValue(p_186665_4_, p_186665_1_);
        return vec3d != null && this.intersectsWithXY(vec3d) ? vec3d : null;
    }
    
    public boolean intersectsWithYZ(Vectors3f vec)
    {
        return vec.y >= this.minY && vec.y <= this.maxY && vec.z >= this.minZ && vec.z <= this.maxZ;
    }
    public boolean intersectsWithXZ(Vectors3f vec)
    {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.z >= this.minZ && vec.z <= this.maxZ;
    }
    
    public boolean intersectsWithXY(Vectors3f vec)
    {
        return vec.x >= this.minX && vec.x <= this.maxX && vec.y >= this.minY && vec.y <= this.maxY;
    }

    public String toString()
    {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
    
    public boolean hasNaN()
    {
        return Float.isNaN(this.minX) || Float.isNaN(this.minY) || Float.isNaN(this.minZ) || Float.isNaN(this.maxX) || Float.isNaN(this.maxY) || Float.isNaN(this.maxZ);
    }
    
    public Vectors3f getCenter()
    {
        return new Vectors3f(this.minX + (this.maxX - this.minX) * 0.5F, this.minY + (this.maxY - this.minY) * 0.5F, this.minZ + (this.maxZ - this.minZ) * 0.5F);
    }
