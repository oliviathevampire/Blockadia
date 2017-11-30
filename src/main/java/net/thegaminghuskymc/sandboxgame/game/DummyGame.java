package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.*;
import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.entities.EntityPlayer;
import net.thegaminghuskymc.sandboxgame.engine.graph.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.Texture;
import net.thegaminghuskymc.sandboxgame.engine.graph.lights.DirectionalLight;
import net.thegaminghuskymc.sandboxgame.engine.graph.weather.Fog;
import net.thegaminghuskymc.sandboxgame.engine.item.SkyBox;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundManager;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

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
        player = new EntityPlayer();
        world = new World();
        world.entities.add(player);
    }

    @Override
    public void init(Window window) throws Exception {
        hud.init(window);
        renderer.init(window);

        scene = new Scene();

        float reflectance = 1f;

        float blockScale = 0.5f;
        float skyBoxScale = 100.0f;
        float extension = 1.0f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;
        float incy;

        selectDetector = new MouseBoxSelectionDetector();

        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/assets/sandboxgame/textures/misc/heightmap.png"));
        int w = image.getWidth();
        int h = image.getHeight();
        int pixels[] = new int[w * h];
        image.getRGB(0, 0, w, h, pixels, 0, w);
        ByteBuffer bb = BufferUtils.createByteBuffer(pixels.length * 4);
        for (int p : pixels) {
            bb.put((byte) getRed(p));
            bb.put((byte) getGreen(p));
            bb.put((byte) getBlue(p));
            bb.put((byte) getAlpha(p));
        }
        bb.flip();

        Block block = new Block();
        Mesh mesh = new Mesh(block.getPositions(), block.getTextCoords(), block.getNormals(), block.getIndices());
        mesh.setBoundingRadius(2);
        Texture texture = new Texture("/assets/sandboxgame/textures/blocks/terrain_textures.png", 2, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                block = new Block(mesh);
                block.setScale(blockScale);
                int rgb = HeightMapMesh.getRGB(i, j, w, bb);
                incy = rgb / (10 * 255 * 255);
                int textPos = Math.random() > 0.5f ? 0 : 1;
                block.setTextPos(textPos);
                block.setPosition(posx, starty + incy, posz);
                world.blocks.put(block.getPosition(), block);

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        scene.setGameItems(world.blocks.values());


        // Shadows
        scene.setRenderShadows(true);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.04f));

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

    public Camera getCamera() {
        return camera;
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
        player.update();

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

        if (mouseInput.isLeftButtonPressed()) {
            this.selectDetector.selectGameItem(world.blocks.values(), window, mouseInput.getCurrentPos(), camera);
        }

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
