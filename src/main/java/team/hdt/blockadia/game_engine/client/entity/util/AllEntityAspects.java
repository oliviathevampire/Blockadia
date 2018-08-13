package team.hdt.blockadia.game_engine.client.entity.util;

import team.hdt.blockadia.game_engine.common.util.interfaces.Nullable;

public class AllEntityAspects {
    //to help set height
    public static float scale = 1.0F;
    @Nullable
    // dose it live in water or not
    public static boolean liveInWater;

    //is it hostile or peaceful
    enum TYPE {
        HOSTILE,
        PEACEFUL
    }
}
