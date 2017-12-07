package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.instance;

import net.thegaminghuskymc.sandboxgame.engine.world.entity.Entity;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelMesh;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

public class ModelInstance {
    /** entity reference */
    private final Entity entity;

    /** model reference */
    private final Model model;

    /** the skeleton instance */
    private final ModelSkeletonInstance skeleton;

    /** the animation list for this model */
    private final ArrayList<AnimationInstance> animationInstances;

    /** curent skin id */
    private int skinID;

    private long lastUpdate;

    public ModelInstance(Model model, Entity entity) {
        this(model, entity, true);
    }

    public ModelInstance(Model model, Entity entity, boolean entitySizeMatchModel) {
        this.entity = entity;
        if (entitySizeMatchModel) {

            // TODO : make it cleaner, maybe add a size attribute to the model
            ByteBuffer vertices = model.getMesh().getVertices();
            if (vertices != null) {
                float mx = Float.POSITIVE_INFINITY, my = Float.POSITIVE_INFINITY, mz = Float.POSITIVE_INFINITY;
                float Mx = Float.NEGATIVE_INFINITY, My = Float.NEGATIVE_INFINITY, Mz = Float.NEGATIVE_INFINITY;
                int position = 0;
                while (position < vertices.capacity()) {
                    vertices.position(position);
                    float x = vertices.getFloat();
                    float y = vertices.getFloat();
                    float z = vertices.getFloat();
                    if (x < mx) {
                        mx = x;
                    } else if (x > Mx) {
                        Mx = x;
                    }

                    if (y < my) {
                        my = y;
                    } else if (y > My) {
                        My = y;
                    }

                    if (z < mz) {
                        mz = z;
                    } else if (z > Mz) {
                        Mz = z;
                    }
                    position += ModelMesh.BYTES_PER_VERTEX;
                }

                float w = Math.max(Mx - mx, Mz - mz);
                float eps = w * 0.05f;
                entity.setSizeX(w - eps);
                entity.setSizeY(My - my - eps);
                entity.setSizeZ(w - eps);
            }
        }
        this.skinID = 0;
        this.model = model;
        this.skeleton = new ModelSkeletonInstance(model.getSkeleton());
        this.animationInstances = new ArrayList<>();

    }

    /** get model from this model instance */
    public Model getModel() {
        return (this.model);
    }

    public void toggleSkin(int skinID) {
        this.skinID = skinID;
    }

    public int getSkinID() {
        return (this.skinID);
    }

    /** update the model */
    public void update() {
        long curr = System.currentTimeMillis();
        long dt = curr - this.lastUpdate;

        this.updateAnimations(dt);
        this.skeleton.update(this.animationInstances);

        this.lastUpdate = curr;
    }

    private void updateAnimations(long dt) {
        // update animation
        Iterator<AnimationInstance> iterator = this.animationInstances.iterator();
        while (iterator.hasNext()) {
            AnimationInstance animationInstance = iterator.next();
            animationInstance.update(dt);
            if (animationInstance.isStopped()) {
                iterator.remove();
            }
        }
    }

    /** @return : get this model instance entity */
    public Entity getEntity() {
        return (this.entity);
    }

    /** get the skeleton instance */
    public ModelSkeletonInstance getSkeleton() {
        return (this.skeleton);
    }

    /** get the animations this model can play */
    public ArrayList<AnimationInstance> getAnimationInstances() {
        return (this.animationInstances);
    }
}
