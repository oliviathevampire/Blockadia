package team.hdt.blockadia.game_engine.client.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import team.hdt.blockadia.game_engine.client.model.RawModel;
import team.hdt.blockadia.game_engine.client.rendering.ModelTexture;
import team.hdt.blockadia.game_engine.client.rendering.TexturedModel;
import team.hdt.blockadia.game_engine.client.shaders.StaticShader;
import team.hdt.blockadia.game_engine.common.entity.BaseEntity;
import team.hdt.blockadia.game_engine.common.util.math.Maths;
import team.hdt.blockadia.game_engine.common.util.math.vectors.Matrix4fs;

import java.util.List;
import java.util.Map;

public class EntityRenderer {
    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4fs projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<BaseEntity>> entities) {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<BaseEntity> batch = entities.get(model);
            for (BaseEntity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(),
                        GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(BaseEntity entity) {
        Matrix4fs transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),
                entity.getRotation().x, entity.getRotation().y, entity.getRotation().z, entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
