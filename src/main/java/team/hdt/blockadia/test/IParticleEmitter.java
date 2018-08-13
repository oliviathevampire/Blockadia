package team.hdt.blockadia.test;

import java.util.List;

public interface IParticleEmitter {

    void cleanup();
    
    Particle getBaseParticle();
    
    List<GameItem> getParticles();
}