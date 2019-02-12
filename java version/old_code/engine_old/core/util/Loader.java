package team.hdt.blockadia.engine.core.util;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glGenTextures;

/**
 * Contains a lot of methods for VAO and VBO data management, and also keeps
 * track of all currently active VAOs and VBOs.
 *
 * @author Karl
 */
public class Loader {

    private static Map<Integer, List<Integer>> vaoCache = new HashMap<Integer, List<Integer>>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    /**
     * Loads interleaved vertex data into a VBO which is stored in a newly
     * created VAO (without index buffer).
     *
     * @param data    - vertex data.
     * @param lengths - the length (number of floats) of each data element. E.g.
     *                Data for positions, normals and texture coords may have
     *                lengths of 3, 3, 2.
     * @return The ID of the new VAO.
     */
    public static int createInterleavedVAO(float[] data, int... lengths) {
        int vertexArrayID = createVAO();
        storeInterleavedDataInVAO(vertexArrayID, data, lengths);
        return vertexArrayID;
    }

    /**
     * Creates a VAO, interleaves the data, and stores it in the VAO. No index
     * buffer is associated with the VAO.
     *
     * @param vertexCount - the number of vertices whose data is being stored.
     * @param data        - the various sets of data (positions, normals, texture
     *                    coords, etc.)
     * @return The newly created VAO.
     */
    public static int createInterleavedVAO(int vertexCount, float[]... data) {
        int vertexArrayID = createVAO();
        float[] interleavedData = Loader.interleaveFloatData(vertexCount, data);
        int[] lengths = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            lengths[i] = data[i].length / vertexCount;
        }
        storeInterleavedDataInVAO(vertexArrayID, interleavedData, lengths);
        return vertexArrayID;
    }

    /**
     * Loads interleaved vertex data into a VBO which is stored in a newly
     * created VAO (with index buffer).
     *
     * @param interleavedData - vertex data.
     * @param indices         - index buffer data.
     * @param lengths         - the lengths of each data element. E.g. Data for positions,
     *                        normals and texture coords may have lengths of 3, 3, 2.
     * @return The ID of the new VAO.
     */
    public static int createInterleavedVAO(float[] interleavedData, int[] indices, int... lengths) {
        int vertexArrayID = createVAO();
        createIndicesVBO(vertexArrayID, indices);
        storeInterleavedDataInVAO(vertexArrayID, interleavedData, lengths);
        return vertexArrayID;
    }

    /**
     * Cretaes an empty VAO.
     *
     * @return The ID of the VAO.
     */
    public static int createVAO() {
        int vertexArrayID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayID);
        List<Integer> associatedVbos = new ArrayList<Integer>();
        vaoCache.put(vertexArrayID, associatedVbos);
        return vertexArrayID;
    }

    /**
     * Creates an index buffer and binds it to a VAO.
     *
     * @param vaoID   - the ID of the VAO to which the index buffer should be bound.
     * @param indices - the array of indices to be stored in the index buffer.
     * @return The ID of the index buffer VBO.
     */
    public static int createIndicesVBO(int vaoID, int[] indices) {
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        int indicesBufferId = GL15.glGenBuffers();
        vaoCache.get(vaoID).add(indicesBufferId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        return indicesBufferId;
    }

    public static int createInterleavedInstancedVbo(int vaoID, int maxInstanceCount, int startingAttribute,
                                                    int... lengths) {
        return createVbo(vaoID, maxInstanceCount, startingAttribute, true, lengths);
    }

    /**
     * Creates an empty VBO for storing interleaved data and links it with the
     * attribute lists of a VAO.
     *
     * @param vaoID             - the ID of the VAO to which the VBO should be added.
     * @param maxVertexCount    - the maximum number of vertices that would need to be stored
     *                          in the VBO.
     * @param startingAttribute - the first available attribute list of the VAO.
     * @param lengths           - the lengths in floats of each of the data elements
     *                          associated with any given vertex.
     * @return The ID of the newly created VBO.
     */
    public static int createEmptyInterleavedVBO(int vaoID, int maxVertexCount, int startingAttribute, int... lengths) {
        return createVbo(vaoID, maxVertexCount, startingAttribute, false, lengths);
    }

    /**
     * Store float data into part of a VBO. Can be used for updating data in a
     * VBO.
     *
     * @param vbo        - the ID of the VBO.
     * @param buffer     - a float buffer that can be used to store the data in the
     *                   VBO. Must be bigger than {@code data.length}.
     * @param data       - the float data to be stored in the VBO.
     * @param startIndex - the starting index in terms of floats for where the data
     *                   should be stored in the VBO.
     */
    public static void storeDataInVbo(int vbo, FloatBuffer buffer, float[] data, int startIndex) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, startIndex * ByteWork.FLOAT_LENGTH, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Refills an entire VBO with new data.
     *
     * @param vbo    - the ID of the VBO.
     * @param buffer - a float buffer big enough to contain the data.
     * @param data   - the data to be stored in the VBO.
     */
    public static void refillVboWithData(int vbo, FloatBuffer buffer, float[] data) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * ByteWork.FLOAT_LENGTH, GL15.GL_DYNAMIC_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Refills an entire VBO with new data which comes from part of a float[].
     *
     * @param vbo        - the ID of the VBO.
     * @param buffer     - a float buffer big enough to contain the data.
     * @param data       - the data to be stored in the VBO.
     * @param dataLength - the number of floats from thge float[] that should be stored
     *                   in the VBO.
     */
    public static void refillVboWithData(int vbo, FloatBuffer buffer, float[] data, int dataLength) {
        buffer.clear();
        buffer.put(data, 0, dataLength);
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * ByteWork.FLOAT_LENGTH, GL15.GL_DYNAMIC_DRAW);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Deletes all the VBOs and VAOs that are currently stored.
     */
    public static void cleanUpModelMemory() {
        GL20.glDisableVertexAttribArray(0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        for (int vaoID : vaoCache.keySet()) {
            List<Integer> vbos = vaoCache.get(vaoID);
            for (int vbo : vbos) {
                GL15.glDeleteBuffers(vbo);
            }
            GL30.glDeleteVertexArrays(vaoID);
        }
        vaoCache.clear();
    }

    /**
     * Deletes a VAO from memory along with any associated VBOs.
     *
     * @param vao
     */
    public static void deleteVaoFromCache(int vao) {
        List<Integer> vbos = vaoCache.remove(vao);
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        GL30.glDeleteVertexArrays(vao);
    }

    /**
     * Stores interleaved data into a VAO.
     *
     * @param vaoID   - the ID of the VAO.
     * @param data    - the interleaved float data.
     * @param lengths - the lengths in floats of each of the data elements
     *                associated with any given vertex.
     */
    public static void storeInterleavedDataInVAO(int vaoID, float[] data, int... lengths) {
        FloatBuffer interleavedData = storeDataInBuffer(data);
        int bufferObjectID = GL15.glGenBuffers();
        vaoCache.get(vaoID).add(bufferObjectID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, interleavedData, GL15.GL_STATIC_DRAW);

        int total = 0;
        for (int i = 0; i < lengths.length; i++) {
            total += lengths[i];
        }
        int vertexByteCount = ByteWork.FLOAT_LENGTH * total;
        total = 0;
        for (int i = 0; i < lengths.length; i++) {
            GL20.glVertexAttribPointer(i, lengths[i], GL11.GL_FLOAT, false, vertexByteCount,
                    ByteWork.FLOAT_LENGTH * total);
            total += lengths[i];
        }

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    /**
     * Interleaved multiple float arrays of data into one interleaved float
     * array.
     *
     * @param count - the number of data elements (not floats) in the arrays.
     * @param data  - the arrays of un-interleaved data.
     * @return The interleaved data.
     */
    public static float[] interleaveFloatData(int count, float[]... data) {
        int totalSize = 0;
        int[] lengths = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            int elementLength = data[i].length / count;
            lengths[i] = elementLength;
            totalSize += data[i].length;
        }
        float[] interleavedBuffer = new float[totalSize];
        int pointer = 0;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < data.length; j++) {
                int elementLength = lengths[j];
                for (int k = 0; k < elementLength; k++) {
                    interleavedBuffer[pointer++] = data[j][i * elementLength + k];
                }
            }
        }
        return interleavedBuffer;
    }

    private static FloatBuffer storeDataInBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static int createVbo(int vaoID, int maxCount, int startingAttribute, boolean instanced, int... lengths) {
        int bufferObjectID = GL15.glGenBuffers();
        vaoCache.get(vaoID).add(bufferObjectID);

        int total = 0;
        for (int i = 0; i < lengths.length; i++) {
            total += lengths[i];
        }
        int vertexByteCount = ByteWork.FLOAT_LENGTH * total;
        int maxSize = vertexByteCount * maxCount;
        GL30.glBindVertexArray(vaoID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferObjectID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, maxSize, GL15.GL_DYNAMIC_DRAW);

        total = 0;
        for (int i = 0; i < lengths.length; i++) {
            GL20.glVertexAttribPointer(i + startingAttribute, lengths[i], GL11.GL_FLOAT, false, vertexByteCount,
                    ByteWork.FLOAT_LENGTH * total);
            if (instanced) {
                GL33.glVertexAttribDivisor(i + startingAttribute, 1);
            }
            total += lengths[i];
        }

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);

        return bufferObjectID;
    }

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public int loadTexture(String fileName) {

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/textures/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to load Texture: " + fileName);
            System.exit(1);
        }

        int[] pixels = new int[img.getWidth() * img.getHeight()];
        img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(img.getWidth() * img.getHeight() * 4);

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = pixels[y * img.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();

        int texture_id = glGenTextures();

        return texture_id;
    }
}