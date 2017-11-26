package net.thegaminghuskymc.sandboxgame.engine.graph.particles;


import net.thegaminghuskymc.sandboxgame.engine.block.Block;

import java.util.List;

public interface IParticleEmitter {

    void cleanup();
    
    Particle getBaseParticle();
    
    List<Block> getParticles();
}
