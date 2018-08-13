package team.hdt.sandboxgame.game_engine.client.particles;

import team.hdt.sandboxgame.game_engine.common.CameraInterface;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Matrix4fs;

import java.util.*;
import java.util.Map.Entry;

public class ParticleMaster {

	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static List<Particle> colourParticles = new ArrayList<Particle>();
	private static List<Particle> colourAdditiveParticles = new ArrayList<Particle>();
	private static ParticleRenderer renderer;

	public static void init(Matrix4fs projectionMatrix) {
		renderer = new ParticleRenderer(projectionMatrix);
	}

	public static synchronized void update(CameraInterface camera) {
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while (mapIterator.hasNext()) {
			List<Particle> list = mapIterator.next().getValue();
			updateList(list, camera);
			if (list.isEmpty()) {
				mapIterator.remove();
			}
			//InsertionSort.sortHighToLow(list);
		}
		updateList(colourParticles, camera);
		updateList(colourAdditiveParticles, camera);
	}
	
	public static void reset(){
		particles.clear();
		colourParticles.clear();
		colourAdditiveParticles.clear();
	}

	public static void renderParticles(CameraInterface camera) {
		renderer.render(particles, colourParticles, colourAdditiveParticles, camera);
	}

	public static void cleanUp() {
		renderer.cleanUp();
	}

	public static synchronized void addParticle(Particle particle) {
		List<Particle> list = particles.get(particle.getTexture());
		if (list == null) {
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
	
	public static void addColourParticle(Particle colourParticle, boolean additive) {
		if(additive){
			colourAdditiveParticles.add(colourParticle);
		}else{
			colourParticles.add(colourParticle);
		}
	}
	
	private static void updateList(List<Particle> list, CameraInterface camera){
		Iterator<Particle> iterator = list.iterator();
		while (iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.update(camera);
			if (!stillAlive) {
				iterator.remove();
			}
		}
	}

}
