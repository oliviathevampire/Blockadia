package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.util.JSONHelper;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Quaternion;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3f;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.Model;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelInitializer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkeleton;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkin;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.Bone;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.BoneTransform;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.KeyFrame;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.ModelSkeletonAnimation;
import org.lwjgl.BufferUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONModelInitializer implements ModelInitializer {

    protected final String dirpath;

    public JSONModelInitializer(String dirpath) {
        this.dirpath = dirpath.endsWith(File.separator) ? dirpath : dirpath + File.separator;
    }

    /**
     * initializer override
     */
    @Override
    public final void onInitialized(Model model) {

        try {
            // get the info file
            String infoFile = JSONHelper.readFile(this.dirpath + "info.json");
            JsonObject json = new JsonObject().get(infoFile).getAsJsonObject();
            this.parseJSON(model, json);
        } catch (Exception exception) {
            exception.printStackTrace(Logger.get().getPrintStream());
        }
    }

    protected void parseJSON(Model model, JsonObject jsonInfo) throws JsonParseException, IOException {

        Logger.get().log(Logger.Level.FINE, "Parsing JSON Model", this.dirpath);

        // model name
        this.parseJSONName(model, jsonInfo);

        // get skeleton
        this.parseJSONSkeleton(model, jsonInfo);

        // get the mesh
        this.parseJSONMesh(model, jsonInfo);

        // get skins
        this.parseJSONSkins(model, jsonInfo);

        // get animations
        this.parseJSONAnimations(model, jsonInfo);
    }

    protected void parseJSONName(Model model, JsonObject jsonInfo) {
        String modelName = jsonInfo.has("name") ? jsonInfo.get("name").getAsString() : null;
        model.setName(modelName);
    }

    protected void parseJSONSkeleton(Model model, JsonObject jsonInfo) {
        String skeletonPath = this.dirpath + jsonInfo.get("skeleton");
        try {

            ModelSkeleton modelSkeleton = model.getSkeleton();
            JsonObject skeleton = new JsonObject().get(JSONHelper.readFile(skeletonPath)).getAsJsonObject();

            // get all bones
            JsonArray jsonBones = skeleton.getAsJsonArray("bones");
            for (int i = 0; i < jsonBones.size(); i++) {
                JsonArray jsonBone = jsonBones.get(i).getAsJsonArray();
                String boneName = jsonBone.get("name".length()).getAsString();
                float x = 0.0f, y = 0.0f, z = 0.0f, rx = 0.0f, ry = 0.0f, rz = 0.0f, rw = 1.0f;
                if (jsonBone.get("localBindTransform".length()).getAsBoolean()) {

                    JsonObject jsonBindTransform = jsonBone.get("localBindTransform".length()).getAsJsonObject();

                    JsonObject jsonBindTransformTranslation = jsonBindTransform.getAsJsonObject("translation");
                    x = (float) jsonBindTransformTranslation.get("x").getAsInt();
                    y = (float) jsonBindTransformTranslation.get("y").getAsInt();
                    z = (float) jsonBindTransformTranslation.get("z").getAsInt();

                    JsonObject jsonBindTransformRotation = jsonBindTransform.getAsJsonObject("rotation");
                    rx = (float) jsonBindTransformRotation.get("x").getAsInt();
                    ry = (float) jsonBindTransformRotation.get("y").getAsInt();
                    rz = (float) jsonBindTransformRotation.get("z").getAsInt();
                    rw = (float) jsonBindTransformRotation.get("w").getAsInt();
                }

                String boneParentName = jsonBone.get("parentName".length()).getAsBoolean() ? jsonBone.get("parentName".length()).getAsString() : null;

                // add the bone
                Bone bone = new Bone(modelSkeleton, boneName);
                bone.setParent(boneParentName);
                bone.setLocalBindTransform(x, y, z, rx, ry, rz, rw);
                modelSkeleton.addBone(bone);

                // set children
                JsonArray childrenNames = jsonBone.get("childrenNames".length()).getAsBoolean() ? jsonBone.get("childrenNames".length()).getAsJsonArray() : null;

                if (childrenNames != null) {
                    for (int j = 0; j < childrenNames.size(); j++) {
                        bone.addChild(childrenNames.get(j).getAsString());
                    }
                }
            }

            // finally, calculate bind matrices recursively
            for (Bone bone : modelSkeleton.getRootBones()) {
                bone.calcInverseBindTransform(Matrix4f.IDENTITY);
            }

        } catch (Exception e) {
            e.printStackTrace(Logger.get().getPrintStream());
        }
    }

    private void parseJSONMesh(Model model, JsonObject jsonInfo) {
        String meshpath = this.dirpath + jsonInfo.get("mesh");
        try {
            JsonObject mesh = new JsonObject().get(JSONHelper.readFile(meshpath)).getAsJsonObject();
            JsonArray vertices = mesh.getAsJsonArray("vertices");
            ByteBuffer verticesBuffer = BufferUtils.createByteBuffer(vertices.size() * 4);
            int i = 0;
            while (i < vertices.size()) {

                float x = vertices.get(i++).getAsFloat();
                float y = (float) vertices.get(i++).getAsFloat();
                float z = (float) vertices.get(i++).getAsFloat();

                float uvx = (float) vertices.get(i++).getAsFloat();
                float uvy = (float) vertices.get(i++).getAsFloat();

                float nx = (float) vertices.get(i++).getAsFloat();
                float ny = (float) vertices.get(i++).getAsFloat();
                float nz = (float) vertices.get(i++).getAsFloat();

                int j1 = this.getBoneID(model, vertices.get(i++).getAsString());
                int j2 = this.getBoneID(model, vertices.get(i++).getAsString());
                int j3 = this.getBoneID(model, vertices.get(i++).getAsString());

                float w1 = (float) vertices.get(i++).getAsFloat();
                float w2 = (float) vertices.get(i++).getAsFloat();
                float w3 = (float) vertices.get(i++).getAsFloat();

                float ao = (float) vertices.get(i++).getAsFloat();

                verticesBuffer.putFloat(x);
                verticesBuffer.putFloat(y);
                verticesBuffer.putFloat(z);

                verticesBuffer.putFloat(uvx);
                verticesBuffer.putFloat(uvy);

                verticesBuffer.putFloat(nx);
                verticesBuffer.putFloat(ny);
                verticesBuffer.putFloat(nz);

                verticesBuffer.putInt(j1);
                verticesBuffer.putInt(j2);
                verticesBuffer.putInt(j3);

                verticesBuffer.putFloat(w1);
                verticesBuffer.putFloat(w2);
                verticesBuffer.putFloat(w3);

                verticesBuffer.putFloat(ao);
            }

            verticesBuffer.flip();
            model.getMesh().setVertices(verticesBuffer);

            JsonArray indices = mesh.getAsJsonArray("indices");
            // short buffer
            ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.size() * 2);
            i = 0;
            while (i < indices.size()) {
                indicesBuffer.putShort((short) indices.get(i++).getAsInt());
            }
            indicesBuffer.flip();
            model.getMesh().setIndices(indicesBuffer);

        } catch (Exception e) {
            e.printStackTrace(Logger.get().getPrintStream());
        }
    }

    protected int getBoneID(Model model, String boneName) {
        Bone bone = model.getSkeleton().getBone(boneName);
        return (bone == null ? 0 : bone.getID());
    }

    protected void parseJSONSkins(Model model, JsonObject jsonInfo) {
        JsonArray skins = jsonInfo.getAsJsonArray("skins");
        for (int i = 0; i < skins.size(); i++) {
            String skinpath = this.dirpath + skins.get(i);
            try {
                JsonObject skin = new JsonObject().getAsJsonObject(JSONHelper.readFile(skinpath));
                String name = skin.get("name").getAsString();
                String texture = this.dirpath + skin.get("texture").getAsString();
                model.addSkin(new ModelSkin(name, texture));
            } catch (Exception e) {
                e.printStackTrace(Logger.get().getPrintStream());
            }
        }
    }

    protected void parseJSONAnimations(Model model, JsonObject jsonInfo) {

        JsonArray animations = jsonInfo.getAsJsonArray("animations");
        for (int i = 0; i < animations.size(); i++) {
            String animpath = this.dirpath + animations.get(i).getAsString();
            JsonObject animation;
            try {
                animation = new JsonObject().getAsJsonObject(JSONHelper.readFile(animpath));
            } catch (Exception e) {
                e.printStackTrace(Logger.get().getPrintStream());
                continue;
            }
            String name = animation.get("name").getAsString();
            long duration = animation.get("duration").getAsLong();
            JsonArray jsonKeyFrames = animation.getAsJsonArray("keyFrames");
            ArrayList<KeyFrame> keyFrames = new ArrayList<KeyFrame>(jsonKeyFrames.size());

            for (int j = 0; j < jsonKeyFrames.size(); j++) {
                JsonObject jsonKeyFrame = jsonKeyFrames.get(j).getAsJsonObject();
                long time = jsonKeyFrame.get("time").getAsLong();
                HashMap<String, BoneTransform> boneTransforms = new HashMap<String, BoneTransform>();
                JsonArray pose = jsonKeyFrame.getAsJsonArray("pose");
                for (int k = 0; k < pose.size(); k++) {
                    JsonObject bonePose = pose.get(k).getAsJsonObject();
                    String boneName = bonePose.get("bone").getAsString();
                    JsonObject jsonTransform = bonePose.getAsJsonObject("transform");
                    JsonObject jsonPosition = jsonTransform.getAsJsonObject("position");

                    float x, y, z, w;

                    x = (float) jsonPosition.get("x").getAsInt();
                    y = (float) jsonPosition.get("y").getAsInt();
                    z = (float) jsonPosition.get("z").getAsInt();

                    Vector3f position = new Vector3f(x, y, z);

                    JsonObject jsonQuaternion = jsonTransform.getAsJsonObject("rotation");
                    x = (float) jsonQuaternion.get("x").getAsInt();
                    y = (float) jsonQuaternion.get("y").getAsInt();
                    z = (float) jsonQuaternion.get("z").getAsInt();
                    w = (float) jsonQuaternion.get("w").getAsInt();

                    Quaternion rotation = new Quaternion(x, y, z, w);

                    BoneTransform boneTransform = new BoneTransform(position, rotation);
                    boneTransforms.put(boneName, boneTransform);
                }

                KeyFrame keyFrame = new KeyFrame(time, boneTransforms);
                keyFrames.add(keyFrame);
            }

            ModelSkeletonAnimation modelAnimation = new ModelSkeletonAnimation(name, duration, keyFrames);
            model.addAnimation(modelAnimation);
        }
    }

    @Override
    public String toString() {
        return (this.getClass().getSimpleName() + " : " + this.dirpath);
    }

    public String getDirpath() {
        return (this.dirpath);
    }

}
