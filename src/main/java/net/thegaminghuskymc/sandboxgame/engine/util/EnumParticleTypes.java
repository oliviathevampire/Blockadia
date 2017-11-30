package net.thegaminghuskymc.sandboxgame.engine.util;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public enum EnumParticleTypes
{
    EXPLOSION_NORMAL("explode", 0, true),
    EXPLOSION_LARGE("largeexplode", 1, true),
    EXPLOSION_HUGE("hugeexplosion", 2, true),
    FIREWORKS_SPARK("fireworksSpark", 3, false),
    WATER_BUBBLE("bubble", 4, false),
    WATER_SPLASH("splash", 5, false),
    WATER_WAKE("wake", 6, false),
    SMOKE_NORMAL("smoke", 7, false),
    SMOKE_LARGE("largesmoke", 8, false),
    SPELL("spell", 9, false),
    SPELL_INSTANT("instantSpell", 10, false),
    SPELL_MOB("mobSpell", 11, false),
    SPELL_MOB_AMBIENT("mobSpellAmbient", 12, false),
    SPELL_WITCH("witchMagic", 13, false),
    DRIP_WATER("dripWater", 14, false),
    DRIP_LAVA("dripLava", 15, false),
    TOWN_AURA("townaura", 16, false),
    NOTE("note", 18, false),
    PORTAL("portal", 19, false),
    ENCHANTMENT_TABLE("enchantmenttable", 20, false),
    FLAME("flame", 21, false),
    LAVA("lava", 22, false),
    FOOTSTEP("footstep", 23, false),
    CLOUD("cloud", 24, false),
    SNOWBALL("snowballpoof", 25, false),
    SNOW_SHOVEL("snowshovel", 26, false),
    SLIME("slime", 27, false),
    HEART("heart", 28, false),
    BARRIER("barrier", 29, false),
    ITEM_CRACK("iconcrack", 30, false, 2),
    BLOCK_CRACK("blockcrack", 31, false, 1),
    BLOCK_DUST("blockdust", 32, false, 1),
    WATER_DROP("droplet", 33, false),
    ITEM_TAKE("take", 34, false),
    MOB_APPEARANCE("mobappearance", 35, true),
    FALLING_DUST("fallingdust", 36, false, 1),
    SPIT("spit", 37, true);

    private final String particleName;
    private final int particleID;
    private final boolean shouldIgnoreRange;
    private final int argumentCount;
    private static final Map<Integer, EnumParticleTypes> PARTICLES = Maps.newHashMap();
    private static final Map<String, EnumParticleTypes> BY_NAME = Maps.newHashMap();

    EnumParticleTypes(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn, int argumentCountIn)
    {
        this.particleName = particleNameIn;
        this.particleID = particleIDIn;
        this.shouldIgnoreRange = shouldIgnoreRangeIn;
        this.argumentCount = argumentCountIn;
    }

    EnumParticleTypes(String particleNameIn, int particleIDIn, boolean shouldIgnoreRangeIn)
    {
        this(particleNameIn, particleIDIn, shouldIgnoreRangeIn, 0);
    }

    public static Set<String> getParticleNames()
    {
        return BY_NAME.keySet();
    }

    public String getParticleName()
    {
        return this.particleName;
    }

    public int getParticleID()
    {
        return this.particleID;
    }

    public int getArgumentCount()
    {
        return this.argumentCount;
    }

    public boolean getShouldIgnoreRange()
    {
        return this.shouldIgnoreRange;
    }

    /**
     * Gets the relative EnumParticleTypes by id.
     */
    @Nullable
    public static EnumParticleTypes getParticleFromId(int particleId)
    {
        return PARTICLES.get(particleId);
    }

    @Nullable
    public static EnumParticleTypes getByName(String nameIn)
    {
        return BY_NAME.get(nameIn);
    }

    static
    {
        for (EnumParticleTypes enumparticletypes : values())
        {
            PARTICLES.put(enumparticletypes.getParticleID(), enumparticletypes);
            BY_NAME.put(enumparticletypes.getParticleName(), enumparticletypes);
        }
    }
}