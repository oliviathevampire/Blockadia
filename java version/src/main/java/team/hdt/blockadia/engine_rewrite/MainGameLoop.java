package team.hdt.blockadia.engine_rewrite;



import org.joml.Vector3f;
import team.hdt.blockadia.engine_rewrite.client.Display;
import team.hdt.blockadia.engine_rewrite.client.render.Loader;
import team.hdt.blockadia.engine_rewrite.client.render.MasterRenderer;
import team.hdt.blockadia.engine_rewrite.core.entities.Camera;
import team.hdt.blockadia.engine_rewrite.core.entities.Entity;
import team.hdt.blockadia.engine_rewrite.core.entities.Player;
import team.hdt.blockadia.engine_rewrite.core.models.ModelTexture;
import team.hdt.blockadia.engine_rewrite.core.models.RawModel;
import team.hdt.blockadia.engine_rewrite.core.models.TexturedModel;
import team.hdt.blockadia.engine_rewrite.core.objConverter.ModelData;
import team.hdt.blockadia.engine_rewrite.core.objConverter.OBJFileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainGameLoop {

	public static Display display;
	public static long window;
	public static boolean running = true;

	public static void main(String[] args) {
		display.createDisplay("test",680 , 1100);
		window = display.window ;
		Loader loader = new Loader();


		ModelData data = OBJFileLoader.loadOBJ("tree");
		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		data = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		data = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		data = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());
		
		
		TexturedModel staticModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grassTModel = new TexturedModel(grassModel, new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel lamp = new TexturedModel(lampModel, new ModelTexture(loader.loadTexture("lamp")));
		grassTModel.getTexture().setHasTransparency(true);
		grassTModel.getTexture().setUseFakeLighting(true);

		ModelTexture fernMT = new ModelTexture(loader.loadTexture("fern"));
		fernMT.setHasTransparency(true);
		fernMT.setUseFakeLighting(true);
		fernMT.setNumberOfRows(2);
		TexturedModel fernTModel = new TexturedModel(fernModel, fernMT);

		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			float x = random.nextFloat() * 800 - 400;
			float z = random.nextFloat() * -600;
			float y = 0;

		}/*

		List<Light> lights = new ArrayList<>();
		Light light = new Light(new Vector3f(0, 10000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
		lights.add(light);
		lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));

		Entity lampEntity = new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1);
		entities.add(lampEntity);
		entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
		entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));*/
		
		MasterRenderer renderer = new MasterRenderer(loader);

		data = OBJFileLoader.loadOBJ("bunny");
		RawModel bunny = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),data.getIndices());
		TexturedModel bunnyModel = new TexturedModel(bunny, new ModelTexture(loader.loadTexture("white")));
		Player player = new Player(bunnyModel, new Vector3f(100, 0, -50), 0, 0, 0, .6f);
		Camera camera = new Camera(player);
		/*
		List<GuiTexture> guis = new ArrayList<>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, .5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		guis.add(new GuiTexture(loader.loadTexture("health"), new Vector2f(0.3f, .7f), new Vector2f(0.25f, 0.25f)));
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);*/

		
		while (running) {
			player.move();
			camera.move();

			renderer.processEntity(player);
			
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			renderer.render(/*lights,*/ camera);
			
			/*guiRenderer.render(guis);*/
			
			Display.updateDisplay();

		}
		/*guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();*/
		Display.closeDisplay();
	}
}
