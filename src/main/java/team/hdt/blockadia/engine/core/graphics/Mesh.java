package team.hdt.blockadia.engine.core.graphics;

import org.lwjgl.opengl.*;
import team.hdt.blockadia.engine.core.Camera;
import team.hdt.blockadia.engine.core.Util;
import team.hdt.blockadia.engine.core.shader.Shader;
import team.hdt.blockadia.engine.core.util.math.Transform;
import team.hdt.blockadia.engine.core.util.math.vectors.Matrix4fs;
import team.hdt.blockadia.engine.core.util.math.vectors.Vectors3f;

public class Mesh {

    private int vao;
    private int vbo;
    private int vboTexture;
    private int vboi;

    private int indicesSize;

    public Mesh() {
        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();
        vboTexture = GL15.glGenBuffers();
        vboi = GL15.glGenBuffers();
    }

    public void add(float[] vertices, float[] texCoords, int[] indices) {
        indicesSize = indices.length;

        GL30.glBindVertexArray(vao);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.flip(vertices), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTexture);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.flip(texCoords), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboi);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.flip(indices), GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void enable() {
        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboi);
    }

    public void render(Shader shader, int modelMatrix, GameObject object, Camera camera) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, object.getTextureID());
        Matrix4fs transformationMatrix = createTransformationMatrix(object.getTransform());
        shader.loadMatrix(modelMatrix, transformationMatrix);
        object.update();
        GL11.glDrawElements(GL11.GL_TRIANGLES, indicesSize, GL11.GL_UNSIGNED_INT, 0);
    }

    private Matrix4fs createTransformationMatrix(Transform transform) {
        Matrix4fs matrix = new Matrix4fs();
        matrix.setIdentity();
        Matrix4fs.translate(transform.position, matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(transform.rotation.x), new Vectors3f(1, 0, 0), matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(transform.rotation.y), new Vectors3f(0, 1, 0), matrix, matrix);
        Matrix4fs.rotate((float) Math.toRadians(transform.rotation.z), new Vectors3f(0, 0, 1), matrix, matrix);
        Matrix4fs.scale(new Vectors3f(transform.scale.x, transform.scale.y, transform.scale.z), matrix, matrix);
        return matrix;
    }

    public void disable() {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void dispose() {
        GL30.glBindVertexArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL15.glDeleteBuffers(vbo);
        GL15.glDeleteBuffers(vboTexture);
        GL15.glDeleteBuffers(vboi);
        GL30.glDeleteVertexArrays(vao);
    }

}