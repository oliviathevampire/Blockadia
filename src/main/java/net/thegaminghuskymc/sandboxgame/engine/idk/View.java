package net.thegaminghuskymc.sandboxgame.engine.idk;

/** 
 * The View which is responsible for rendering a Model to the screen 
 * 
 * @author Lane Aasen <laneaasen@gmail.com>
 * 
 */

import java.util.HashSet;

import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

public class View {

	Model model;
	int renderDistance;
	
	public HashSet<Chunk> toRender;

	public long window;
	
	/**
	 * Constructs a View
	 * 
	 * Camera position is currently implied; it looks down the z axis by default
	 * 
	 * @param model model, or world to be drawn
	 */
	public View(Model model, int renderDistance) {
		this.model = model;
		this.renderDistance = renderDistance;
	}

	/**
	 * Initializes OpenGL
	 */
	public void init() {
		/*int width = glfwGetVideoMode();
		int height = (int)glfwGetPrimaryMonitor();

		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		//		GLU.gluPerspective(45.0f, ((float) width / (float) height), 0.1f, 256.0f);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glShadeModel(GL_SMOOTH);
		glClearColor(0.55f, 0.804f, 0.97f, 0.0f);
		glClearDepth(1.0f);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		this.run();*/
	}

	/**
	 * Continuously renders the world
	 */
	public void run() {
		while (glfwWindowShouldClose(window) && !this.model.stop) {
			this.render();
		}

		this.cleanUp();	
	}

	/**
	 * Renders the world once
	 */
	public void render() {		
		// check if the model is currently being written to and skip a frame if it is
		if(!model.locked) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	

			// adjust camera angle
			glLoadIdentity();
			/*GLU.gluLookAt(this.model.camera.eye.x, this.model.camera.eye.y, this.model.camera.eye.z,
					this.model.camera.focal.x, this.model.camera.focal.y, this.model.camera.focal.z,
					0.0f, 1.0f, 0.0f);*/

			
//			this.model.requestChunk(new Vector3(0, 0, 0));
			
			for(int i = -this.renderDistance; i <= this.renderDistance; i++) {
				for(int j = -this.renderDistance; j <= this.renderDistance; j++) {
					Chunk chunk = this.model.chunks.getChunk(this.model.camera.eye.add(new Vector3(i, 0, j).scale(16f)));
					chunk.drawBlocks();
				}
			}
		}
	}

	public void cleanUp() {
		glfwDestroyWindow(window);

		System.exit(0);
	}
}