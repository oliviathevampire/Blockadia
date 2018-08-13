package team.hdt.sandboxgame.game_engine.client.particles;

import team.hdt.sandboxgame.game_engine.client.particleSpawns.ParticleSpawn;
import team.hdt.sandboxgame.game_engine.common.Main;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;
import team.hdt.sandboxgame.game_engine.util.toolbox.Colour;

import java.util.Random;

public class ParticleSystem {

	private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;

	private float speedError, lifeError, scaleError = 0;
	private boolean randomRotation = false;
	private Vectors4f direction;
	private Vectors4f offset = new Vectors4f();
	private float directionDeviation = 0;

	private ParticleTexture texture;
	private ParticleSpawn spawn;
	private Colour colour;
	private boolean additive;
	private float alpha = 1;
	private float fadeIn = 0;
	private float fadeOut = 1;
	private boolean hasXRotation = false;
	private float xRotSpeed = 0;
	private boolean directionLocalSpace = false;
	
	private Matrix4fs transformation = new Matrix4fs();

	private Random random = new Random();

	public ParticleSystem(ParticleTexture texture, ParticleSpawn spawn, float pps, float speed, float gravityComplient,
						  float lifeLength, float scale) {
		this.spawn = spawn;
		this.pps = pps;
		this.averageSpeed = speed;
		this.gravityComplient = gravityComplient;
		this.averageLifeLength = lifeLength;
		this.averageScale = scale;
		this.texture = texture;
	}

	public ParticleSystem(Colour colour, boolean additive, ParticleSpawn spawn, float pps, float speed,
			float gravityComplient, float lifeLength, float scale) {
		this.colour = colour;
		this.additive = additive;
		this.spawn = spawn;
		this.pps = pps;
		this.averageSpeed = speed;
		this.gravityComplient = gravityComplient;
		this.averageLifeLength = lifeLength;
		this.averageScale = scale;
	}

	/**
	 * @param direction
	 *            - The average direction in which particles are emitted.
	 * @param deviation
	 *            - A value between 0 and 1 indicating how far from the chosen
	 *            direction particles can deviate.
	 */
	public void setDirection(Vectors3f direction, float deviation) {
		this.direction = new Vectors4f(direction.x, direction.y, direction.z, 0f);
		this.directionDeviation = (float) (deviation * Math.PI);
	}
	
	public void setDirectionLocalSpace(){
		directionLocalSpace = true;
	}

	public void setFadeValues(float alpha, float fadeIn, float fadeOut) {
		this.alpha = alpha;
		this.fadeIn = fadeIn;
		this.fadeOut = fadeOut;
	}
	
	public void setOffset(Vectors3f off){
		offset.set(off.x, off.y, off.z, 0);
	}
	
	public void setXRotation(float speed){
		this.hasXRotation = true;
		this.xRotSpeed = speed;
	}

	public void randomizeRotation() {
		randomRotation = true;
	}

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setSpeedError(float error) {
		this.speedError = error * averageSpeed;
	}

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setLifeError(float error) {
		this.lifeError = error * averageLifeLength;
	}

	/**
	 * @param error
	 *            - A number between 0 and 1, where 0 means no error margin.
	 */
	public void setScaleError(float error) {
		this.scaleError = error * averageScale;
	}
	
	public void pulseParticles(Vectors3f center, float scale) {
		pulseParticles(translateMatrix(center), scale);
	}
	
	public void pulseParticles(Vectors3f center, Colour colour, float scale) {
		pulseParticles(translateMatrix(center), colour, scale);
	}

	public void generateParticles(Vectors3f center, float scale) {
		generateParticles(translateMatrix(center), scale);
	}

	public void generateParticles(Vectors3f center, Colour colour, float scale) {
		generateParticles(translateMatrix(center), colour, scale);
	}
	
	private Matrix4fs translateMatrix(Vectors3f center){
		transformation.setIdentity();
		transformation.translate(center);
		return transformation;
	}

	public void pulseParticles(Matrix4fs transform, float scale) {
		pulseParticles(transform, null, scale);
	}
	
	public void pulseParticles(Matrix4fs transform, Colour colour, float scale) {
		for (int i = 0; i < pps; i++) {
			emitParticle(transform, colour, scale);
		}
	}

	public void generateParticles(Matrix4fs transform, float scale) {
		generateParticles(transform, null, scale);
	}

	public void generateParticles(Matrix4fs transform, Colour colour, float scale) {
		float delta = Main.getDeltaSeconds();
		float particlesToCreate = pps * delta;
		int count = (int) Math.floor(particlesToCreate);
		float partialParticle = particlesToCreate % 1;
		for (int i = 0; i < count; i++) {
			emitParticle(transform, colour, scale);
		}
		if (Math.random() < partialParticle) {
			emitParticle(transform, colour, scale);
		}
	}

	private void emitParticle(Matrix4fs transform, Colour overrideColour, float scaleFactor) {
		Vectors3f velocity = null;
		if (direction != null) {
			Vectors4f actualDir = directionLocalSpace ? Matrix4fs.transform(transform, direction, null) : direction ;
			velocity = generateRandomUnitVectorWithinCone(new Vectors3f(actualDir), directionDeviation);
		} else {
			velocity = generateRandomUnitVector();
		}
//		Vectors4f rotatedVelocity = new Vectors4f(velocity.x, velocity.y, velocity.z, 0);
//		Matrix4fs.transform(transform, rotatedVelocity, rotatedVelocity);
//		velocity.set(rotatedVelocity);
		velocity.normalise();
		velocity.scale(generateValue(averageSpeed, speedError) * scaleFactor);
		float scale = generateValue(averageScale, scaleError) * scaleFactor;
		float lifeLength = generateValue(averageLifeLength, lifeError) * scaleFactor;
		Vectors4f spawnPosition = Vectors4f.add(offset, spawn.getBaseSpawnPosition(), null);
		Matrix4fs.transform(transform, spawnPosition, spawnPosition);
		Vectors3f spawnPos = new Vectors3f(spawnPosition);
		Particle p;
		if (this.colour == null) {
			p = new Particle(texture, spawnPos, velocity, gravityComplient, lifeLength, generateRotation(), scale);
		} else {
			p = new Particle(overrideColour == null ? colour : overrideColour, additive, spawnPos, velocity,
					gravityComplient, lifeLength, generateRotation(), scale);
			//p.setFade(alpha, fadeIn, fadeOut);
		}
		p.setFade(alpha, fadeIn, fadeOut);
		if(hasXRotation){
			p.set3dRotation(xRotSpeed);
		}
	}

	private float generateValue(float average, float errorMargin) {
		float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
		return average + offset;
	}

	private float generateRotation() {
		if (randomRotation) {
			return random.nextFloat() * 360f;
		} else {
			return 0;
		}
	}

	private static Vectors3f generateRandomUnitVectorWithinCone(Vectors3f coneDirection, float angle) {
		float cosAngle = (float) Math.cos(angle);
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));

		Vectors4f direction = new Vectors4f(x, y, z, 1);
		if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
			Vectors3f rotateAxis = Vectors3f.cross(coneDirection, new Vectors3f(0, 0, 1), null);
			rotateAxis.normalise();
			float rotateAngle = (float) Math.acos(Vectors3f.dot(coneDirection, new Vectors3f(0, 0, 1)));
			Matrix4fs rotationMatrix = new Matrix4fs();
			rotationMatrix.rotate(-rotateAngle, rotateAxis);
			Matrix4fs.transform(rotationMatrix, direction, direction);
		} else if (coneDirection.z == -1) {
			direction.z *= -1;
		}
		return new Vectors3f(direction);
	}

	private Vectors3f generateRandomUnitVector() {
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = (random.nextFloat() * 2) - 1;
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));
		return new Vectors3f(x, y, z);
	}

}
