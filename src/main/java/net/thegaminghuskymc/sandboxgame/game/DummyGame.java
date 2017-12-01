package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.*;
import net.thegaminghuskymc.sandboxgame.engine.block.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.Texture;
import net.thegaminghuskymc.sandboxgame.engine.graph.lights.DirectionalLight;
import net.thegaminghuskymc.sandboxgame.engine.graph.weather.Fog;
import net.thegaminghuskymc.sandboxgame.engine.item.SkyBox;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundManager;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import net.thegaminghuskymc.sandboxgame.engine.world.biome.BiomeRegistry;
import net.thegaminghuskymc.sandboxgame.engine.world.biome.IBiome;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static net.thegaminghuskymc.sandboxgame.engine.world.gen.Noise.myNoise;
import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    // .5f
    public static final float GRAVITY = 0f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final SoundManager soundMgr;

    protected final Camera camera;

    private Scene scene;

    private Hud hud;

    private static final float CAMERA_POS_STEP = 0.10f;

    private float angleInc;

    private float lightAngle;

    private MouseBoxSelectionDetector selectDetector;

    public Block selected;

    private boolean firstTime;

    private boolean sceneChanged;

    private EntityPlayer player;

    public World world;

    public DummyGame() {
        renderer = new Renderer();
        hud = new Hud();
        soundMgr = new SoundManager();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 90;
    }

    @Override
    public void init(Window window) throws Exception {
        hud.init(window);
        renderer.init(window);

        scene = new Scene();

        Texture terrainTexture = new Texture("/assets/sandboxgame/textures/blocks/terrain_textures.png", 2, 1);
        Material terrainMaterial = new Material(terrainTexture, 1f);

        world = new World(terrainMaterial);

        player = new EntityPlayer();
        world.entities.add(player);

        BiomeRegistry.biomes.add(new BiomePlains());
        BiomeRegistry.biomes.add(new BiomeMountains());

        BlockRegistry.blocks.add(new BlockDirt());
        BlockRegistry.blocks.add(new BlockCobblestone());
        BlockRegistry.blocks.add(new BlockGrass());
        BlockRegistry.blocks.add(new BlockGrassPath());
        BlockRegistry.blocks.add(new BlockStone());

        float blockScale = 0.5f;
        float skyBoxScale = 100.0f;
        float extension = 1.0f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;

        int w = 50;
        int h = 50;

        player.setPosition(startx, starty, startz);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                world.setBlock(new Vector3f(posx, 0, posz), new Block());

                float freq = 1f;
                float exp = 1f;
                for (IBiome biome : BiomeRegistry.biomes) {
                    if (biome.shouldGenerate(1f)) {
                        freq = biome.getNoiseFrequency();
                        exp = biome.getNoiseExponent();
                        break;
                    }
                }

                float maxHeight = myNoise(posx, posz, freq, exp);
                for (float bH = 0; bH < maxHeight; bH++) {
                    world.setBlock(new Vector3f(posx, bH, posz), new Block());
                }

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        world.endAddingBlocks(scene);


        // Shadows
        scene.setRenderShadows(true);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(false, fogColour, 0.04f));

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox("/assets/sandboxgame/models/misc/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 1.0f));
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        camera.getPosition().x = 0.25f;
        camera.getPosition().y = 6.5f;
        camera.getPosition().z = 6.5f;
        camera.getRotation().x = 25;
        camera.getRotation().y = -1;
    }

    private static int getRed(int c)
    {
        return (c >> 16) & 255;
    }

    private static int getGreen(int c)
    {
        return (c >> 8) & 255;
    }

    private static int getBlue(int c)
    {
        return c & 255;
    }

    private static int getAlpha(int c)
    {
        return (c >> 24) & 255;
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        scene.setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        sceneLight.setDirectionalLight(directionalLight);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        sceneChanged = false;
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            sceneChanged = true;
            cameraInc.z = -8;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            sceneChanged = true;
            cameraInc.z = 8;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            sceneChanged = true;
            cameraInc.x = -8;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            sceneChanged = true;
            cameraInc.x = 8;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || window.isKeyPressed(GLFW_KEY_RIGHT_SHIFT)) {
            sceneChanged = true;
            cameraInc.y = -3;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            sceneChanged = true;
            cameraInc.y = 3;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            sceneChanged = true;
            angleInc -= 2;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            sceneChanged = true;
            angleInc += 2;
        } else {
            angleInc = 0;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) {
        if (mouseInput.isRightButtonPressed()) {
            // Update camera based on mouse            
            Vector2f rotVec = mouseInput.getDisplVec();
            //camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            player.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            sceneChanged = true;
        }

        // Update camera position
        //camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        player.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        lightAngle += angleInc;
        if (lightAngle < 0) {
            lightAngle = 0;
        } else if (lightAngle > 180) {
            lightAngle = 180;
        }
        float zValue = (float) Math.cos(Math.toRadians(lightAngle));
        float yValue = (float) Math.sin(Math.toRadians(lightAngle));
        Vector3f lightDirection = this.scene.getSceneLight().getDirectionalLight().getDirection();
        lightDirection.x = 0;
        lightDirection.y = yValue;
        lightDirection.z = zValue;
        lightDirection.normalize();

        // Update view matrix
        camera.updateViewMatrix();

        /*if (mouseInput.isLeftButtonPressed()) {
            this.selectDetector.selectGameItem(world.blocks.values(), window, mouseInput.getCurrentPos(), camera);
        }*/
        /*if (mouseInput.isRightButtonPressed()) {
            this.selectDetector.selectGameItem(world.blocks.values(), window, mouseInput.getCurrentPos(), camera);
            if (selected != null) {
                Vector3f pos = selected.getPosition();
                world.setBlock(new Vector3f(pos.x, pos.y + 1, pos.z), new Block());
                world.endAddingBlocks(scene);
            }
        }*/
        world.update();
    }

    @Override
    public void render(Window window) {
        if (firstTime) {
            sceneChanged = true;
            firstTime = false;
        }
        renderer.render(window, camera, scene, sceneChanged);
        hud.render(window);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        soundMgr.cleanup();

        scene.cleanup();
        if (hud != null) {
            hud.cleanup();
        }
    }
}
