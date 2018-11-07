package team.hdt.blockadia.engine_rewrite.core.render;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class EntityRenderer {

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
