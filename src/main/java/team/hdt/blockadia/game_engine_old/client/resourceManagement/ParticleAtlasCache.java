package team.hdt.blockadia.game_engine_old.client.resourceManagement;

import team.hdt.blockadia.game_engine_old.client.particles.ParticleTexture;
import team.hdt.blockadia.game_engine_old.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine_old.util.FileUtils;
import team.hdt.blockadia.game_engine_old.util.MyFile;

import java.util.HashMap;
import java.util.Map;

public class ParticleAtlasCache {

    private static final MyFile ATLAS_FOLDER = new MyFile(FileUtils.RES_FOLDER, "particleAtlases");
    public static final Texture TRIANGLE = Texture.newTexture(new MyFile(ATLAS_FOLDER, "triangle.png")).clampEdges().create();

    public static Map<Integer, ParticleTexture> particleTextures = new HashMap<Integer, ParticleTexture>();

    public static ParticleTexture getAtlas(int id) {
        return particleTextures.get(id);
    }

    public static void loadAll() {
        loadAtlas(1, "cosmic.png", 4, true);
        loadAtlas(2, "blueHeal.png", 3, true);
        loadAtlas(3, "diseased2.png", 2, false);
        loadAtlas(4, "poison.png", 2, false);
        loadAtlas(5, "pollen.png", 4, true);
        loadAtlas(6, "snow.png", 4, true);
        loadAtlas(7, "leaf.png", 2, false);
        loadAtlas(8, "dust.png", 3, false);
        loadAtlas(9, "rock.png", 1, false);
        loadAtlas(10, "swamp.png", 2, false);
        loadAtlas(11, "snore.png", 2, false);
        loadAtlas(12, "floaty.png", 4, true);
        loadAtlas(13, "fireFly.png", 2, true);
    }

    private static void loadAtlas(int id, String textureFile, int rows, boolean additive) {
        Texture texture = Texture.newTexture(new MyFile(ATLAS_FOLDER, textureFile)).clampEdges().create();
        ParticleTexture atlas = new ParticleTexture(texture, rows, additive);
        particleTextures.put(id, atlas);
    }

}
