package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.thegaminghuskymc.sandboxgame.engine.util.JSONHelper;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.game.client.opengl.ImageUtils;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelMesh;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.ModelSkin;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.Bone;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.BoneTransform;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.KeyFrame;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.animation.ModelSkeletonAnimation;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModel;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.EditableModelLayer;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.model.editor.mesher.ModelBlockData;

import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class JSONModelExporter {

    /**
     * export the given model to the given dirpath
     *
     * @throws Exception
     */
    public static final void export(EditableModel editableModel, String dirPath) throws Exception {

        if (editableModel == null) {
            throw new Exception("null Model in export()");
        }

        JsonObject jsonInfo = new JsonObject();

        // name
        jsonInfo.addProperty("name", editableModel.getName());

        // mesh
        String meshPath = "mesh/mesh.json";
        ModelMesh mesh = editableModel.getMesh();
        JsonObject jsonMesh = new JsonObject();

        ByteBuffer vertices = mesh.getVertices();
        JsonArray jsonVertices = new JsonArray();
        float precision = 1000.0f;
        while (vertices.hasRemaining()) {
            float x = Maths.approximatate(vertices.getFloat() - editableModel.getOrigin().x, precision);
            float y = Maths.approximatate(vertices.getFloat() - editableModel.getOrigin().y, precision);
            float z = Maths.approximatate(vertices.getFloat() - editableModel.getOrigin().z, precision);
            float u = Maths.approximatate(vertices.getFloat(), precision);
            float v = Maths.approximatate(vertices.getFloat(), precision);
            float nx = Maths.approximatate(vertices.getFloat(), precision);
            float ny = Maths.approximatate(vertices.getFloat(), precision);
            float nz = Maths.approximatate(vertices.getFloat(), precision);
            String b1 = editableModel.getSkeleton().getBoneName(vertices.getInt());
            String b2 = editableModel.getSkeleton().getBoneName(vertices.getInt());
            String b3 = editableModel.getSkeleton().getBoneName(vertices.getInt());
            float w1 = Maths.approximatate(vertices.getFloat(), precision);
            float w2 = Maths.approximatate(vertices.getFloat(), precision);
            float w3 = Maths.approximatate(vertices.getFloat(), precision);
            float ao = Maths.approximatate(vertices.getFloat(), precision);

            jsonVertices.add(x);
            jsonVertices.add(y);
            jsonVertices.add(z);
            jsonVertices.add(u);
            jsonVertices.add(v);
            jsonVertices.add(nx);
            jsonVertices.add(ny);
            jsonVertices.add(nz);
            jsonVertices.add(b1);
            jsonVertices.add(b2);
            jsonVertices.add(b3);
            jsonVertices.add(w1);
            jsonVertices.add(w2);
            jsonVertices.add(w3);
            jsonVertices.add(ao);
        }
        jsonMesh.add("vertices", jsonVertices);

        ByteBuffer indices = mesh.getIndices();
        JsonArray jsonIndices = new JsonArray();
        while (indices.hasRemaining()) {
            short i = indices.getShort();
            jsonIndices.add(i);
        }
        jsonMesh.add("indices", jsonIndices);
        JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, meshPath).toFile(), jsonMesh);

        jsonInfo.addProperty("mesh", meshPath);

        // skeleton
        String skeletonPath = "skeleton/skeleton.json";
        jsonInfo.addProperty("skeleton", skeletonPath);
        JsonObject jsonSkeleton = new JsonObject();
        JsonArray jsonBones = new JsonArray();
        jsonSkeleton.add("bones", jsonBones);
        for (Bone bone : editableModel.getSkeleton().getBones()) {
            JsonObject jsonBone = new JsonObject();

            jsonBone.addProperty("name", bone.getName());

            if (bone.hasParent()) {
                jsonBone.addProperty("parentName", bone.getParentName());
            }

            if (bone.hasChildren()) {
                JsonArray jsonChildren = new JsonArray();
                for (String childName : bone.getChildrens()) {
                    jsonChildren.add(childName);
                }
                jsonBone.add("childrenNames", jsonChildren);
            }

            JsonObject jsonLocalBindTransform = new JsonObject();

            JsonObject jsonTranslation = new JsonObject();
            jsonTranslation.addProperty("x", bone.getLocalTranslation().x);
            jsonTranslation.addProperty("y", bone.getLocalTranslation().y);
            jsonTranslation.addProperty("z", bone.getLocalTranslation().z);
            jsonLocalBindTransform.add("translation", jsonTranslation);

            JsonObject jsonRotation = new JsonObject();
            jsonRotation.addProperty("x", bone.getLocalRotation().getX());
            jsonRotation.addProperty("y", bone.getLocalRotation().getY());
            jsonRotation.addProperty("z", bone.getLocalRotation().getZ());
            jsonRotation.addProperty("w", bone.getLocalRotation().getW());
            jsonLocalBindTransform.add("rotation", jsonRotation);

            jsonBone.add("localBindTransform", jsonLocalBindTransform);

            jsonBones.add(jsonBone);
        }
        JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, skeletonPath).toFile(), jsonSkeleton);

        // blocks
        String blocksPath = "blocks/blocks.json";
        jsonInfo.addProperty("blocks", blocksPath);

        JsonObject jsonBlocks = new JsonObject();
        JsonObject jsonBlocksOrigin = new JsonObject();
        jsonBlocksOrigin.addProperty("x", editableModel.getOrigin().x);
        jsonBlocksOrigin.addProperty("y", editableModel.getOrigin().y);
        jsonBlocksOrigin.addProperty("z", editableModel.getOrigin().z);
        jsonBlocks.add("origin", jsonBlocksOrigin);

        JsonArray jsonLayers = new JsonArray();
        JsonArray jsonLayersData = new JsonArray();
        for (EditableModelLayer editableModelLayer : editableModel.getRawLayers().values()) {
            JsonObject jsonLayer = new JsonObject();
            jsonLayer.addProperty("name", editableModelLayer.getName());
            jsonLayer.addProperty("sizeUnit", editableModelLayer.getBlockSizeUnit());
            jsonLayers.add(jsonLayer);

            JsonObject jsonLayerData = new JsonObject();
            JsonArray jsonLayerBlocks = new JsonArray();
            for (ModelBlockData blockData : editableModelLayer.getRawBlockDatas().values()) {
                jsonLayerBlocks.add(blockData.getX());
                jsonLayerBlocks.add(blockData.getY());
                jsonLayerBlocks.add(blockData.getZ());
                jsonLayerBlocks.add(String.valueOf(blockData.getBone(0)));
                jsonLayerBlocks.add(String.valueOf(blockData.getBone(1)));
                jsonLayerBlocks.add(String.valueOf(blockData.getBone(2)));
                jsonLayerBlocks.add(blockData.getBoneWeight(0));
                jsonLayerBlocks.add(blockData.getBoneWeight(1));
                jsonLayerBlocks.add(blockData.getBoneWeight(2));
            }
            jsonLayerData.addProperty("layer", editableModelLayer.getName());
            jsonLayerData.add("blocks", jsonLayerBlocks);

            jsonLayersData.add(jsonLayerData);
        }
        jsonBlocks.add("layers", jsonLayers);
        jsonBlocks.add("layersData", jsonLayersData);
        JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, blocksPath).toFile(), jsonBlocks);

        // animations
        JsonArray jsonAnimations = new JsonArray();
        for (ModelSkeletonAnimation animation : editableModel.getAnimations()) {
            String animationPath = "animations/" + animation.getName().toLowerCase() + ".json";
            jsonAnimations.add(animationPath);

            JsonObject jsonAnimation = new JsonObject();
            jsonAnimation.addProperty("name", animation.getName());

            JsonArray jsonKeyFrames = new JsonArray();
            for (KeyFrame keyFrame : animation.getKeyFrames()) {
                JsonObject jsonKeyFrame = new JsonObject();
                jsonKeyFrame.addProperty("time", keyFrame.getTime());

                JsonArray jsonPoses = new JsonArray();
                for (String boneName : keyFrame.getBoneKeyFrames().keySet()) {
                    JsonObject jsonPose = new JsonObject();

                    jsonPose.addProperty("bone", boneName);

                    BoneTransform boneTransform = keyFrame.getBoneKeyFrames().get(boneName);
                    JsonObject jsonTransform = new JsonObject();
                    JsonObject jsonPosition = new JsonObject();
                    jsonPosition.addProperty("x", boneTransform.getTranslation().x);
                    jsonPosition.addProperty("y", boneTransform.getTranslation().y);
                    jsonPosition.addProperty("z", boneTransform.getTranslation().z);
                    jsonTransform.add("position", jsonPosition);

                    JsonObject jsonRotation = new JsonObject();
                    jsonRotation.addProperty("x", boneTransform.getRotation().getX());
                    jsonRotation.addProperty("y", boneTransform.getRotation().getY());
                    jsonRotation.addProperty("z", boneTransform.getRotation().getZ());
                    jsonRotation.addProperty("w", boneTransform.getRotation().getW());
                    jsonTransform.add("rotation", jsonRotation);

                    jsonPose.add("transform", jsonTransform);

                    jsonPoses.add(jsonPose);
                }
                jsonKeyFrame.add("pose", jsonPoses);
                jsonKeyFrames.add(jsonKeyFrame);
            }
            jsonAnimation.add("keyFrames", jsonKeyFrames);
            JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, animationPath).toFile(), jsonAnimation);
        }
        jsonInfo.add("animations", jsonAnimations);

        // skins
        JsonArray jsonSkins = new JsonArray();
        for (ModelSkin skin : editableModel.getSkins()) {
            String rawPath = "skins/" + skin.getName().toLowerCase();
            String skinPath = rawPath + ".json";
            jsonSkins.add(skinPath);
            JsonObject jsonSkin = new JsonObject();
            jsonSkin.addProperty("name", skin.getName());
            String texturePath = rawPath + ".png";
            ImageUtils.exportPNGImage(Paths.get(dirPath, texturePath).toFile(), skin.getGLTexture().getData());
            jsonSkin.addProperty("texture", texturePath);
            JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, skinPath).toFile(), jsonSkin);

        }
        jsonInfo.add("skins", jsonSkins);

        // finally, write file
        JSONHelper.writeJSONObjectToFile(Paths.get(dirPath, "info.json").toFile(), jsonInfo);

    }
}
