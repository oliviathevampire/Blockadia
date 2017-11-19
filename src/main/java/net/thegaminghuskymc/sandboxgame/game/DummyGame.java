package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.lights.DirectionalLight;
import net.thegaminghuskymc.sandboxgame.engine.items.GameItem;
import net.thegaminghuskymc.sandboxgame.engine.items.SkyBox;
import net.thegaminghuskymc.sandboxgame.engine.loaders.obj.OBJLoader;
import net.thegaminghuskymc.sandboxgame.engine.sound.SoundManager;
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

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final SoundManager soundMgr;

    private final Camera camera;

    private Scene scene;

    private Hud hud;

    private static final float CAMERA_POS_STEP = 0.10f;

    private float angleInc;

    private float lightAngle;

    private MouseBoxSelectionDetector selectDetector;

    private boolean leftButtonPressed;

    private boolean firstTime;

    private boolean sceneChanged;

    private GameItem[] gameItems;

    public DummyGame() {
        renderer = new Renderer();
        hud = new Hud();
        soundMgr = new SoundManager();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        angleInc = 0;
        lightAngle = 90;
        firstTime = true;
    }

    @Override
    public void init(Window window) throws Exception {
        hud.init(window);
        renderer.init(window);
        soundMgr.init();

        leftButtonPressed = false;

        scene = new Scene();

        float reflectance = 1f;

        float blockScale = 1f;
        float skyBoxScale = 100.0f;
        float extension = 2.0f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;
        float incy = 0.0f;

        selectDetector = new MouseBoxSelectionDetector();

        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/textures/heightmap.png"));
        int w = image.getWidth();
        int h = image.getHeight();
        int pixels[] = new int[w * h];
        image.getRGB(0, 0, w, h, pixels, 0, w);
        ByteBuffer bb = BufferUtils.createByteBuffer(pixels.length * 4);
        for (int p : pixels)
        {
            bb.put((byte) getRed(p));
            bb.put((byte) getGreen(p));
            bb.put((byte) getBlue(p));
            bb.put((byte) getAlpha(p));
        }

        bb.flip();

        int instances = w * h;
        Mesh mesh = OBJLoader.loadMesh("/models/cube.obj", instances);
        mesh.setBoundingRadius(2);
        Texture texture = new Texture("/textures/terrain_textures.png", 1, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        gameItems = new GameItem[instances];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                GameItem gameItem = new GameItem(mesh);
                gameItem.setScale(blockScale);
                int rgb = HeightMapMesh.getRGB(i, j, w, bb);
                incy = rgb / (10 * 255 * 255);
                gameItem.setPosition(posx, starty + incy, posz);
                int textPos = Math.random() > 0.5f ? 0 : 1;
                gameItem.setTextPos(textPos);
                gameItems[i * w + j] = gameItem;

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        scene.setGameItems(gameItems);


        // Shadows
        scene.setRenderShadows(true);

        // Fog
        /*Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.02f));*/

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox("/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 1.0f));
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

    public static int getRed(int c)
    {
        return (c >> 16) & 255;
    }

    public static int getGreen(int c)
    {
        return (c >> 8) & 255;
    }

    public static int getBlue(int c)
    {
        return c & 255;
    }

    public static int getAlpha(int c)
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
            cameraInc.z = -4;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            sceneChanged = true;
            cameraInc.z = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            sceneChanged = true;
            cameraInc.x = -4;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            sceneChanged = true;
            cameraInc.x = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            sceneChanged = true;
            cameraInc.y = -4;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            sceneChanged = true;
            cameraInc.y = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            sceneChanged = true;
            angleInc -= 0.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            sceneChanged = true;
            angleInc += 0.05f;
        } else {
            angleInc = 0;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) {
        if (mouseInput.isRightButtonPressed()) {
            // Update camera based on mouse            
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
            sceneChanged = true;
        }

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

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

//        particleEmitter.update((long) (interval * 1000));

        // Update view matrix
        camera.updateViewMatrix();

        // Update sound listener position;
//        soundMgr.updateListenerPosition(camera);
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
