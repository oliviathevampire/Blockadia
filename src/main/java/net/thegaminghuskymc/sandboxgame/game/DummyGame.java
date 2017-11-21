package net.thegaminghuskymc.sandboxgame.game;

import net.thegaminghuskymc.sandboxgame.engine.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.*;
import net.thegaminghuskymc.sandboxgame.engine.graph.Texture;
import net.thegaminghuskymc.sandboxgame.engine.graph.lights.DirectionalLight;
import net.thegaminghuskymc.sandboxgame.engine.graph.weather.Fog;
import net.thegaminghuskymc.sandboxgame.engine.items.SkyBox;
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

    private Block[] blocks;

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

        HeightsGenerator heightsGenerator = new HeightsGenerator();

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
        float[] positions = new float[] {
                // V0
                -1f, 1f, 1f,
                // V1
                -1f, -1f, 1f,
                // V2
                1f, -1f, 1f,
                // V3
                1f, 1f, 1f,
                // V4
                -1f, 1f, -1f,
                // V5
                1f, 1f, -1f,
                // V6
                -1f, -1f, -1f,
                // V7
                1f, -1f, -1f,

                // For text coords in top face
                // V8: V4 repeated
                -1f, 1f, -1f,
                // V9: V5 repeated
                1f, 1f, -1f,
                // V10: V0 repeated
                -1f, 1f, 1f,
                // V11: V3 repeated
                1f, 1f, 1f,

                // For text coords in right face
                // V12: V3 repeated
                1f, 1f, 1f,
                // V13: V2 repeated
                1f, -1f, 1f,

                // For text coords in left face
                // V14: V0 repeated
                -1f, 1f, 1f,
                // V15: V1 repeated
                -1f, -1f, 1f,

                // For text coords in bottom face
                // V16: V6 repeated
                -1f, -1f, -1f,
                // V17: V7 repeated
                1f, -1f, -1f,
                // V18: V1 repeated
                -1f, -1f, 1f,
                // V19: V2 repeated
                1f, -1f, 1f,
        };
        float[] textCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 1f,
                1f, 1f,
                1f, 0.0f,

                0.0f, 0.0f,
                1f, 0.0f,
                0.0f, 1f,
                1f, 1f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 1f,

                // For text coords in left face
                1f, 0.0f,
                1f, 1f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };
        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                7, 13, 12, 7, 12, 5,
                // Left face
                15, 14, 6, 6, 14, 4,
                // Bottom face
                19, 18, 16, 19, 16, 17,
                // Back face
                7, 6, 4, 7, 4, 5
        };
        float[] normals = new float[]{
                // V0
                -1.0f, 1.0f, 1.0f,
                // V1
                -1.0f, -1.0f, 1.0f,
                // V2
                1.0f, -1.0f, 1.0f,
                // V3
                1.0f, 1.0f, 1.0f,
                // V4
                -1.0f, 1.0f, -1.0f,
                // V5
                1.0f, 1.0f, -1.0f,
                // V6
                -1.0f, -1.0f, -1.0f,
                // V7
                1.0f, -1.0f, -1.0f,

                // For text coords in top face
                // V8: V4 repeated
                -1.0f, 1.0f, -1.0f,
                // V9: V5 repeated
                1.0f, 1.0f, -1.0f,
                // V10: V0 repeated
                -1.0f, 1.0f, 1.0f,
                // V11: V3 repeated
                1.0f, 1.0f, 1.0f,

                // For text coords in right face
                // V12: V3 repeated
                1.0f, 1.0f, 1.0f,
                // V13: V2 repeated
                1.0f, -1.0f, 1.0f,

                // For text coords in left face
                // V14: V0 repeated
                -1.0f, 1.0f, 1.0f,
                // V15: V1 repeated
                -1.0f, -1.0f, 1.0f,

                // For text coords in bottom face
                // V16: V6 repeated
                -1.0f, -1.0f, -1.0f,
                // V17: V7 repeated
                1.0f, -1.0f, -1.0f,
                // V18: V1 repeated
                -1.0f, -1.0f, 1.0f,
                // V19: V2 repeated
                1.0f, -1.0f, 1.0f,
        };
        Mesh mesh = new Mesh(positions, textCoords, normals, indices);
        mesh.setBoundingRadius(2);
        Texture texture = new Texture("/textures/terrain_textures.png", 2, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        blocks = new Block[instances];
        for (int i = 0; i < heightsGenerator.generateHeight(w, h); i++) {
            for (int j = 0; j < w; j++) {
                OpenGlUtils.goWireframe(true);
                Block block = new Block(mesh);
                block.setScale(blockScale);
                int rgb = HeightMapMesh.getRGB(i, j, w, bb);
                incy = rgb / (10 * 255 * 255);
                block.setPosition(posx, starty + incy, posz);
                int textPos = Math.random() > 0.5f ? 0 : 1;
                block.setTextPos(textPos);
                blocks[i * w + j] = block;

                posx += inc;
            }
            posx = startx;
            posz -= inc;
        }
        scene.setGameItems(blocks);


        // Shadows
        scene.setRenderShadows(true);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.02f));

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

        // Update view matrix
        camera.updateViewMatrix();
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
