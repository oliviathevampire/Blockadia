package net.timmy.th2.blocks;

import net.thegaminghuskymc.sandboxgame.world.gen.feature.WorldGenAbstractTree;
import net.timmy.th2.blocks.base.ModSapling;
import net.timmy.th2.worldgen.WorldGenThaumaniaTree;
import net.timmy.timmylib.utils.MapHelper;

public class Sapling extends ModSapling {

    Block log, leaf;

    public Sapling(String name, Block log, Block leaf) {
        super(name);
        this.log = log;
        this.leaf = leaf;
    }

    public Sapling() {
        super("");
    }

    /*@Override
    public WorldGenAbstractTree generateTwoByTwoTree(Boolean notify) {
        return new WorldGenThaumaniaTree(notify,
                log,
                leaf
        ).configure(MapHelper.<String, Float>builder()
                .put("girth", 2f)
                .put("minHeight", 5f)
                .put("maxHeight", 30f)
                .put("maxDistanceMag", 1000f)
                .put("minDistanceMag", 2f)
                .put("cubage", 500f)
                .unmodifiable(true)
                .build()
        );
    }

    @Override
    public WorldGenAbstractTree generateSingleTree(Boolean notify) {

        return new WorldGenThaumaniaTree(notify,
                log,
                leaf
        ).configure(MapHelper.<String, Float>builder()
                .put("girth", 1f)
                .put("minHeight", 5f)
                .put("maxHeight", 15f)
                .put("maxDistanceMag", 1000f)
                .put("minDistanceMag", 0f)
                .put("cubage", 300f)
                .unmodifiable(true)
                .build()
        );
    }

    @Override
    public WorldGenAbstractTree generateThreeByThreeTree(Boolean notify) {
        return new WorldGenThaumaniaTree(notify,
                log,
                leaf
        ).configure(MapHelper.<String, Float>builder()
                .put("girth", 3f)
                .put("minHeight", 5f)
                .put("maxHeight", 15f)
                .put("maxDistanceMag", 10000f)
                .put("minDistanceMag", 0f)
                .put("cubage", 500f)
                .unmodifiable(true)
                .build()
        );
    }*/
}
