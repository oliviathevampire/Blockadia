package team.hdt.blockadia.engine_rewrite.core.entities;


import core.utils.Keyboard;
import org.joml.Vector3f;
import team.hdt.blockadia.engine_rewrite.MainGameLoop;
import team.hdt.blockadia.engine_rewrite.client.Display;
import team.hdt.blockadia.engine_rewrite.core.models.TexturedModel;

public class Player extends Entity{


	private long window = MainGameLoop.window;
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move() {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * Display.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * Display.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz); 
		upwardsSpeed += GRAVITY * Display.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * Display.getFrameTimeSeconds(), 0);
		float terrainHeight = 0;
		
		if(super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void jump() {
		if(!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(window,Keyboard.KEY_W)) {
			currentSpeed = RUN_SPEED;
		} else if(Keyboard.isKeyDown(window,Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(window,Keyboard.KEY_D)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else if(Keyboard.isKeyDown(window,Keyboard.KEY_A)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(window,Keyboard.KEY_SPACE)) {
			jump();
		}
	}
}
