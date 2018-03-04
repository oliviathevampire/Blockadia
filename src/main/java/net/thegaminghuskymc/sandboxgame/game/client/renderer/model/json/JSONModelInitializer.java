package net.thegaminghuskymc.sandboxgame.game.client.renderer.model.json;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

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


public class JSONModelInitializer implements ModelInitializer {

	protected final String dirpath;

	public JSONModelInitializer(String dirpath) {
		this.dirpath = dirpath.endsWith(File.separator) ? dirpath : dirpath + File.separator;
	}

	/** initializer override */
	@Override
	public final void onInitialized(Model model) {

		try {
			// get the info file
			String infoFile = JSONHelper.readFile(this.dirpath + "info.json");
			JsonObject json = new JsonObject();
			json.get(infoFile);
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
		String skeletonPath = this.dirpath + jsonInfo.get("skeleton").getAsString();
		try {

			ModelSkeleton modelSkeleton = model.getSkeleton();
			JsonObject skeleton = new JsonObject();
			skeleton.get(JSONHelper.readFile(skeletonPath));

			// get all bones
			JsonArray jsonBones = skeleton.getAsJsonArray("bones");
			for (int i = 0; i < jsonBones.getAsInt(); i++) {
				JsonObject jsonBone = jsonBones.getAsJsonObject();
				jsonBone.equals(i);
				String boneName = jsonBone.get("name").getAsString();
				float x = 0.0f, y = 0.0f, z = 0.0f, rx = 0.0f, ry = 0.0f, rz = 0.0f, rw = 1.0f;
				if (jsonBone.has("localBindTransform")) {

					JsonObject jsonBindTransform = jsonBone.getAsJsonObject("localBindTransform");

					JsonObject jsonBindTransformTranslation = jsonBindTransform.getAsJsonObject("translation");
					x = (float) jsonBindTransformTranslation.get("x").getAsDouble();
					y = (float) jsonBindTransformTranslation.get("y").getAsDouble();
					z = (float) jsonBindTransformTranslation.get("z").getAsDouble();

					JsonObject jsonBindTransformRotation = jsonBindTransform.getAsJsonObject("rotation");
					rx = (float) jsonBindTransformRotation.get("x").getAsDouble();
					ry = (float) jsonBindTransformRotation.get("y").getAsDouble();
					rz = (float) jsonBindTransformRotation.get("z").getAsDouble();
					rw = (float) jsonBindTransformRotation.get("w").getAsDouble();
				}

				String boneParentName = jsonBone.has("parentName") ? jsonBone.get("parentName").getAsString() : null;

				// add the bone
				Bone bone = new Bone(modelSkeleton, boneName);
				bone.setParent(boneParentName);
				bone.setLocalBindTransform(x, y, z, rx, ry, rz, rw);
				modelSkeleton.addBone(bone);

				// set children
				JsonArray childrenNames = jsonBone.has("childrenNames") ? jsonBone.getAsJsonArray("childrenNames") : null;

				if (childrenNames != null) {
					for (int j = 0; j < childrenNames.getAsInt(); j++) {
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
		String meshpath = this.dirpath + jsonInfo.get("mesh").getAsString();
		try {
			JsonObject mesh = new JsonObject();
			mesh.get(JSONHelper.readFile(meshpath));
			JsonArray vertices = mesh.getAsJsonArray("vertices");
			ByteBuffer verticesBuffer = BufferUtils.createByteBuffer(vertices.getAsInt() * 4);
			int i = 0;
			while (i < vertices.getAsInt()) {

				float x = (float) vertices.get(i++).getAsDouble();
				float y = (float) vertices.get(i++).getAsDouble();
				float z = (float) vertices.get(i++).getAsDouble();

				float uvx = (float) vertices.get(i++).getAsDouble();
				float uvy = (float) vertices.get(i++).getAsDouble();

				float nx = (float) vertices.get(i++).getAsDouble();
				float ny = (float) vertices.get(i++).getAsDouble();
				float nz = (float) vertices.get(i++).getAsDouble();

				int j1 = this.getBoneID(model, vertices.get(i++).getAsString());
				int j2 = this.getBoneID(model, vertices.get(i++).getAsString());
				int j3 = this.getBoneID(model, vertices.get(i++).getAsString());

				float w1 = (float) vertices.get(i++).getAsDouble();
				float w2 = (float) vertices.get(i++).getAsDouble();
				float w3 = (float) vertices.get(i++).getAsDouble();

				float ao = (float) vertices.get(i++).getAsDouble();

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
			ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.getAsInt() * 2);
			i = 0;
			while (i < indices.getAsInt()) {
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
		for (int i = 0; i < skins.getAsInt(); i++) {
			String skinpath = this.dirpath + skins.get(i).getAsString();
			try {
				JsonObject skin = new JsonObject();
				skin.get(JSONHelper.readFile(skinpath));
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
		for (int i = 0; i < animations.getAsInt(); i++) {
			String animpath = this.dirpath + animations.get(i).getAsString();
			JsonObject animation;
			try {
				animation = new JsonObject();
				animation.get(JSONHelper.readFile(animpath));
			} catch (Exception e) {
				e.printStackTrace(Logger.get().getPrintStream());
				continue;
			}
			String name = animation.get("name").getAsString();
			JsonArray jsonKeyFrames = animation.getAsJsonArray("keyFrames");
			ModelSkeletonAnimation modelAnimation = new ModelSkeletonAnimation(name);

			for (int j = 0; j < jsonKeyFrames.getAsInt(); j++) {
				KeyFrame keyFrame = new KeyFrame();

				JsonObject jsonKeyFrame = jsonKeyFrames.get(j).getAsJsonObject();
				long time = jsonKeyFrame.get("time").getAsLong();
				keyFrame.setTime(time);

				JsonArray pose = jsonKeyFrame.getAsJsonArray("pose");
				for (int k = 0; k < pose.getAsInt(); k++) {
					JsonObject bonePose = pose.get(k).getAsJsonObject();
					String boneName = bonePose.get("bone").getAsString();
					JsonObject jsonTransform = bonePose.get("transform").getAsJsonObject();
					JsonObject jsonPosition = jsonTransform.get("position").getAsJsonObject();

					float x, y, z, w;

					x = (float) jsonPosition.get("x").getAsDouble();
					y = (float) jsonPosition.get("y").getAsDouble();
					z = (float) jsonPosition.get("z").getAsDouble();

					Vector3f position = new Vector3f(x, y, z);

					JsonObject jsonQuaternion = jsonTransform.getAsJsonObject("rotation");
					x = (float) jsonQuaternion.get("x").getAsDouble();
					y = (float) jsonQuaternion.get("y").getAsDouble();
					z = (float) jsonQuaternion.get("z").getAsDouble();
					w = (float) jsonQuaternion.get("w").getAsDouble();

					Quaternion rotation = new Quaternion(x, y, z, w);

					BoneTransform boneTransform = new BoneTransform(position, rotation);
					keyFrame.setBoneTransform(boneName, boneTransform);
				}

				modelAnimation.addKeyFrame(keyFrame);
			}

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
