package team.hdt.blockadia.engine.core;

import ga.pheonix.utillib.utils.vectors.Transform;
import ga.pheonix.utillib.utils.vectors.Vectors3f;
import team.hdt.blockadia.engine.core.graphics.GameObject;
import team.hdt.blockadia.engine.core.graphics.Mesh;
import team.hdt.blockadia.engine.core.gui.*;
import team.hdt.blockadia.engine.core.input.Key;
import team.hdt.blockadia.engine.core.shader.Default3DShader;
import team.hdt.blockadia.engine.core.texture.Texture;
import team.hdt.blockadia.engine.core.texture.TextureLoader;

import java.util.ArrayList;

public class EngineApp {

    private Window window;
    private Scene scene;

    private Camera camera;
    private Default3DShader shader;
    private Texture defaultTexture;
    private Mesh mesh;
    private GameObject object;

    private ArrayList<Node> nodes = new ArrayList<Node>();

    private VBox vbox;
    private UIScene uiScene;

    public EngineApp(int width, int height) {
        System.out.println("0.0.1");
        scene = new Scene(width, height);
        window = Window.createWindow(scene, "[CE] CommunityEngine");
        scene.setClearColor(1, 0.5f, 0, 1);
        init();
        loop();
        dispose();
    }

    private void init() {
        window.enableDepthBuffer();
        window.enableStencilBuffer();

        camera = new Camera(window, 70f, 0.1f, 1000f);
        camera.setPosition(new Vectors3f(0, 0, 20));
        shader = new Default3DShader();
        shader.bind();
        shader.loadMatrix(shader.getProjectionMatrix(), camera.getProjectionMatrix());
        shader.unbind();

        defaultTexture = TextureLoader.loadTexture("textures/bricks.png");

        float[] vertices = {
                -0.5f, 0.5f, 0f, //
                -0.5f, -0.5f, 0f, //
                0.5f, -0.5f, 0f, //
                0.5f, 0.5f, 0f //
        };

        float[] texCoords = {
                0, 0, //
                0, 1, //
                1, 1, //
                1, 0, //
        };

        int[] indices = {
                0, 1, 2, //
                2, 3, 0//
        };

        mesh = new Mesh();
        mesh.add(vertices, texCoords, indices);

        object = new GameObject(new Transform(/*new Vectors3f(0), new Vectors3f(0), new Vectors3f(1)*/)) {
            public void update() {
            }
        };
        object.setTextureID(defaultTexture.getID());

        NanoGui.init();
        nodes.add(new InternalWindow(50, 50, 300, 400));

        vbox = new VBox();
        uiScene = new UIScene(vbox);
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
        vbox.add(new Rectangle(10, 10, 100, 25));
    }

    private void loop() {
        while (!window.isCloseRequested()) {
            if (window.input.isKeyReleased(Key.KEY_ESCAPE)) {
                break;
            }

            float SPEED = 0.01f;
            if (window.input.isKeyDown(Key.KEY_W)) {
                camera.getPosition().x += Math.sin(camera.getYaw() * Math.PI / 180) * SPEED; // * Time.getDelta();
                camera.getPosition().z += -Math.cos(camera.getYaw() * Math.PI / 180) * SPEED; // * Time.getDelta();
            }

            if (window.input.isKeyDown(Key.KEY_S)) {
                camera.getPosition().x -= Math.sin(camera.getYaw() * Math.PI / 180) * SPEED; // * Time.getDelta();
                camera.getPosition().z -= -Math.cos(camera.getYaw() * Math.PI / 180) * SPEED; // * Time.getDelta();
            }

            if (window.input.isKeyDown(Key.KEY_A)) {
                camera.getPosition().x += Math.sin((camera.getYaw() - 90) * Math.PI / 180) * SPEED; // * Time.getDelta();
                camera.getPosition().z += -Math.cos((camera.getYaw() - 90) * Math.PI / 180) * SPEED; // * Time.getDelta();
            }

            if (window.input.isKeyDown(Key.KEY_D)) {
                camera.getPosition().x += Math.sin((camera.getYaw() + 90) * Math.PI / 180) * SPEED; // * Time.getDelta();
                camera.getPosition().z += -Math.cos((camera.getYaw() + 90) * Math.PI / 180) * SPEED; // * Time.getDelta();
            }

            camera.update();
            shader.bind();
            shader.loadViewMatrix(camera);
            {
                mesh.enable();
                {
                    mesh.render(shader, shader.getModelMatrix(), object, camera);
                }
                mesh.disable();
            }
            shader.unbind();

            NanoGui.enable(window.getWidth(), window.getHeight());
            uiScene.update();
            NanoGui.disable();

            window.update();
        }
    }

    private void dispose() {
        NanoGui.disable();
        NanoGui.dispose();
        defaultTexture.dispose();
        mesh.disable();
        mesh.dispose();
        shader.unbind();
        shader.dispose();
        window.close();
        window.disposeGLFW();
    }

}