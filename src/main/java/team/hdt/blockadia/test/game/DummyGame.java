package team.hdt.blockadia.test.game;

import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import team.hdt.blockadia.game_engine.common.world.World;
import team.hdt.blockadia.game_engine.common.world.biomes.Biomes;
import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;
import team.hdt.blockadia.game_engine.common.world.gen.factory.WorldFactory;
import team.hdt.blockadia.test.engine.*;
import team.hdt.blockadia.test.engine.graph.*;
import team.hdt.blockadia.test.engine.graph.item.GameItem;
import team.hdt.blockadia.test.engine.graph.item.SkyBox;
import team.hdt.blockadia.test.engine.graph.light.DirectionalLight;
import team.hdt.blockadia.test.engine.graph.weather.Fog;

import java.io.File;
import java.io.FileWriter;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.5f;
    private static final float CAMERA_POS_STEP = 0.05f;
    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private Scene scene;
    private Hud hud;
    private GameItem[] gameItems;
    private World world;
    private float angleInc;

    private float lightAngle;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        angleInc = 0;
        lightAngle = 45;
    }

    @Override
    public void init(Window window) throws Exception {
        BlockTypes.register();
        Biomes.register();
        world = WorldFactory.generate();
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        File f = new File("world.xml");
        f.createNewFile();
        outputter.output(world.toXML(), new FileWriter(f));

        renderer.init(window);

        scene = new Scene();

        float reflectance = 1f;

        float blockScale = 0.5f;
        float skyBoxScale = 100.0f;

        float startX = 0;
        float startZ = 0;
        float startY = -1.0f;

        float inc = blockScale * 1;

        float posX = startX;
        float posZ = startZ;
        float incy = 0.0f;

        int x = 10000;
        int z = 10000;

        int instances = x * z;
        Mesh mesh = OBJLoader.loadMesh("/models/cube.obj", instances);
        Texture texture = new Texture("/textures/terrain_textures.png", 2, 1);
        Material material = new Material(texture, reflectance);
        mesh.setMaterial(material);
        gameItems = new GameItem[instances];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < z; j++) {
                GameItem grassBlock = new GameItem(mesh);
                grassBlock.setScale(blockScale);
                grassBlock.setPosition(posX, startY + incy, posZ);
                gameItems[i * z + j] = grassBlock;
                posX += inc;
            }
            posX = startX;
            posZ -= inc;
        }
        scene.setGameItems(gameItems);

        // Shadows
        scene.setRenderShadows(false);

        // Fog
        Vector3f fogColour = new Vector3f(0.5f, 0.5f, 0.5f);
        scene.setFog(new Fog(true, fogColour, 0.02f));

        // Setup  SkyBox
        SkyBox skyBox = new SkyBox("/models/skybox.obj", new Vector4f(0.65f, 0.65f, 0.65f, 10.f));
        skyBox.setScale(skyBoxScale);
        scene.setSkyBox(skyBox);

        // Setup Lights
        setupLights();

        camera.getPosition().x = 0.25f;
        camera.getPosition().y = 6.5f;
        camera.getPosition().z = 6.5f;
        camera.getRotation().x = 25;
        camera.getRotation().y = -1;

        hud = new Hud("DEMO");
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
        directionalLight.setShadowPosMult(10);
        directionalLight.setOrthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f);
        sceneLight.setDirectionalLight(directionalLight);
    }


    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT) || window.isKeyPressed(GLFW_KEY_RIGHT_SHIFT)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            angleInc -= 0.05f;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
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
        if (hud != null) {
            hud.updateSize(window);
        }
        renderer.render(window, camera, scene, hud);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        scene.cleanup();
        if (hud != null) {
            hud.cleanup();
        }
    }

}