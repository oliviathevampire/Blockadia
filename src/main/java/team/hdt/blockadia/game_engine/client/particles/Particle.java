package team.hdt.blockadia.game_engine.client.particles;

import team.hdt.blockadia.game_engine.common.CameraInterface;
import team.hdt.blockadia.game_engine.common.Main;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.blockadia.game_engine.common.world.misc.EnvironmentVariables;
import team.hdt.blockadia.game_engine.util.toolbox.Colour;
import team.hdt.blockadia.game_engine.util.toolbox.Maths;

public class Particle {

	private Vectors3f position;
	private Vectors3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	private float fadeIn = 0;
	private float fadeOut = 1;
	private float normalAlpha = 1;
	private Colour colour;
	
	private boolean rotate3D = false;
	private float rotX = 0;
	private float rotXSpeed = 0;

	private float elapsedTime = 0;

	private ParticleTexture texture;

	private Vectors2f texOffset1 = new Vectors2f();
	private Vectors2f texOffset2 = new Vectors2f();
	private float blend;
	private float distance;
	private boolean manualStages = false;

	private boolean decays = true;
	private float heightOffset = 0;

	public Particle(ParticleTexture texture, Vectors3f position, Vectors3f velocity, float gravityEffect,
			float lifeLength, float rotation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
		ParticleMaster.addParticle(this);
	}

	public Particle(Colour colour, boolean additive, Vectors3f position, Vectors3f velocity, float gravityEffect,
			float lifeLength, float rotation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.colour = colour;
		ParticleMaster.addColourParticle(this, additive);
	}

	public Particle(ParticleTexture texture, Vectors3f position, float scale, float deathAnimationTime) {
		decays = false;
		this.position = new Vectors3f(position);
		this.velocity = new Vectors3f();
		this.gravityEffect = 0;
		this.lifeLength = deathAnimationTime;
		this.rotation = 0;
		this.scale = scale;
		this.texture = texture;
		ParticleMaster.addParticle(this);
	}

	public Particle(ParticleTexture texture, float scale, float deathAnimationTime,
			float heightOffset) {
		decays = false;
		this.position = new Vectors3f();
		this.velocity = new Vectors3f();
		this.gravityEffect = 0;
		this.lifeLength = deathAnimationTime;
		this.rotation = 0;
		this.scale = scale;
		this.texture = texture;
		this.heightOffset = heightOffset;
		ParticleMaster.addParticle(this);
	}

	public void setHeightOffset(float offset) {
		this.heightOffset = offset;
	}
	
	public void set3dRotation(float speed){
		this.rotate3D = true;
		this.rotX = Maths.RANDOM.nextFloat() * Maths.DEGREES_IN_CIRCLE;
		this.rotXSpeed = speed;
	}

	public void setPosition(Vectors3f pos) {
		this.position.set(pos);
	}

	public void kill() {
		decays = true;
	}

	public void setFade(float normalAlpha, float fadeIn, float fadeOut) {
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
		this.normalAlpha = normalAlpha;
	}
	
	public void setManualStages(boolean manual){
		this.manualStages = manual;
	}
	
	public void setStage(int index){
		manualStages = true;
		setTextureOffset(texOffset1, index);
		setTextureOffset(texOffset2, index);
	}
	
	public void setStages(int prevIndex, int nextIndex, float blend){
		manualStages = true;
		setTextureOffset(texOffset1, prevIndex);
		setTextureOffset(texOffset2, nextIndex);
		this.blend = blend;
	}

	public Colour getColour() {
		return colour;
	}

	public float getTransparency() {
		float lifeFactor = elapsedTime / lifeLength;
		if (lifeFactor < fadeIn) {
			float factor = lifeFactor / fadeIn;
			return factor * normalAlpha;
		} else if (lifeFactor > fadeOut) {
			float factor = 1 - (lifeFactor - fadeOut) / (1 - fadeOut);
			return factor * normalAlpha;
		} else {
			return 1;
		}
	}

	protected float getDistance() {
		return distance;
	}

	protected Vectors2f getTexOffset1() {
		return texOffset1;
	}

	protected Vectors2f getTexOffset2() {
		return texOffset2;
	}

	protected float getBlend() {
		return blend;
	}

	protected ParticleTexture getTexture() {
		return texture;
	}

	protected Vectors3f getPosition() {
		return position;
	}

	protected float getRotation() {
		return rotation;
	}
	
	protected float getRotX(){
		return rotX;
	}

	protected float getScale() {
		return scale;
	}

	protected boolean update(CameraInterface camera) {
		moveParticleNaturally();
		rotate();
		if (colour == null && !manualStages) {
			updateTextureCoordInfo();
		}
		distance = Vectors3f.sub(camera.getPosition(), position, null).lengthSquared();
		if (decays) {
			elapsedTime += Main.getGameSeconds();
		}
		return !decays || elapsedTime < lifeLength;
	}

	private void moveParticleNaturally() {
		velocity.y += EnvironmentVariables.GRAVITY * gravityEffect * Main.getGameSeconds();
		Vectors3f change = new Vectors3f(velocity);
		change.scale(Main.getGameSeconds());
		Vectors3f.add(change, position, position);
	}
	
	private void rotate(){
		if(rotate3D){
			rotX += Main.getGameSeconds() * rotXSpeed;
		}
	}

	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}

	private void setTextureOffset(Vectors2f offset, int index) {
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

}
