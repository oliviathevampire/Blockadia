package team.hdt.blockadia.engine_rewrite.core.utils;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import team.hdt.blockadia.engine_rewrite.core.entities.Camera;

public class Maths {


	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f Matrix = new Matrix4f();
		Matrix.translate(translation.x, translation.y, translation.z);
		Matrix.rotate((float) Math.toRadians(rx), 1, 0, 0);
		Matrix.rotate((float) Math.toRadians(ry), 0, 1, 0);
		Matrix.rotate((float) Math.toRadians(rz),0, 0, 1);
		Matrix.scale(scale, scale, scale);
		return Matrix;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f Matrix = new Matrix4f();
		Matrix.rotate((float) Math.toRadians(camera.getPitch()),1, 0, 0);
		Matrix.rotate((float) Math.toRadians(camera.getYaw()),0, 1, 0);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix.translate(negativeCameraPos.x,negativeCameraPos.y , negativeCameraPos.z);
		return Matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.translate(translation.x, translation.y, 0);
		matrix.scale(scale.x, scale.y, 1f);
		return matrix;
	}
}
