package team.hdt.sandboxgame.game_engine.util.toolbox;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Matrix4fs;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors2f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors4f;

import java.util.List;
import java.util.Random;

public class Maths {

	public static final float ROOT_2 = (float) Math.sqrt(2);
	public static final float DEGREES_IN_CIRCLE = 360;
	public static final float DEGREES_IN_HALF_CIRCLE = 180;
	public static final Random RANDOM = new Random();
	public static final float PI_OVER_2 = (float) (Math.PI / 2);
	public static final Vectors3f UP = new Vectors3f(0, 1, 0);

	public static final Vectors3f VEC3 = new Vectors3f();// TODO implement object
	public static final Vectors2f VEC2 = new Vectors2f();													// pool system instead
	public static final Vectors4f VEC4 = new Vectors4f();

	public static float barryCentric(Vectors3f p1, Vectors3f p2, Vectors3f p3, Vectors2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static float radiansToDegrees(float radians) {
		return (float) ((radians * 180f) / Math.PI);
	}

	/**Gets a random number between min and max.
	 * @param min
	 * @param max
	 * @return
	 */
	public static float randomNumberBetween(float min, float max){
		float extra = max - min;
		return min + RANDOM.nextFloat() * extra;
	}

	public static float getRandomVariance(float average, float varianceFactor){
		float variance = average * varianceFactor;
		return (average - variance) + (RANDOM.nextFloat() * variance * 2);
	}

	public static float applyFactor(float original, float factor, float influence){
		return original * (1 - influence) + (original * factor * influence);
	}

	public static float getFactor(float factor, float influence){
		return (1 - influence) + (influence * factor);
	}

	public static boolean chance(float chance) {
		return RANDOM.nextFloat() < chance;
	}

	public static float calculateVectorRotationY(Vectors2f direction2D) {
			direction2D.normalise();
			return -(float) Math.toDegrees(Math.atan2(direction2D.y, direction2D.x) - Maths.PI_OVER_2);

	}

	public static Matrix4fs createProjectionMatrix(float width, float height, float near, float far, float fov){
		Matrix4fs projectionMatrix = new Matrix4fs();
		float aspectRatio = width / height;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((far + near) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * near * far) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}

	public static float calculateVectorAngle(Vectors2f direction2D) {
		direction2D.normalise();
		float angle = -(float) Math.toDegrees(Math.atan2(direction2D.y, direction2D.x) - Maths.PI_OVER_2);
		if (angle < 0) {
			angle = DEGREES_IN_CIRCLE + angle;
		}
		return angle;
	}

	public static float parabola(float progression) {
		float x = (2 * progression - 1);
		return -(x * x) + 1;
	}

	public static Vectors3f getClosestPointLineSegment(Vectors3f line0, Vectors3f line1, Vectors3f point) {
		Vectors3f line0To1 = Vectors3f.sub(line1, line0, null);
		Vectors3f line0ToPoint = Vectors3f.sub(point, line0, null);
		float projectionDistance = Vectors3f.dot(line0To1, line0ToPoint);
		if (projectionDistance <= 0) {
			return new Vectors3f(line0);
		}
		float lineLength = Vectors3f.dot(line0To1, line0To1);
		if (lineLength <= projectionDistance) {
			return new Vectors3f(line1);
		}
		float fraction = projectionDistance / lineLength;
		line0To1.scale(fraction);
		Vectors3f.add(line0, line0To1, line0To1);
		return line0To1;
	}

	public static void createViewMatrix(Matrix4fs viewMatrix, Vectors3f position, float pitch, float yaw) {
		viewMatrix.setIdentity();
		Vectors3f cameraPos = new Vectors3f(-position.x, -position.y, -position.z);
		Matrix4fs.rotate(Maths.degreesToRadians(pitch), new Vectors3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(-yaw), new Vectors3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4fs.translate(cameraPos, viewMatrix, viewMatrix);
	}

	public static float slope(float value, float lowerBound, float upperBound) {
		float inside = (value - lowerBound) / (upperBound - lowerBound);
		return clamp(inside, 0, 1);
	}

	// Hacky code, I know
	public static String toRomanNumeral(int input) {
		    if (input < 1 || input > 3999)
		        return "Z";
		    String s = "";
		    while (input >= 1000) {
		        s += "M";
		        input -= 1000;        }
		    while (input >= 900) {
		        s += "CM";
		        input -= 900;
		    }
		    while (input >= 500) {
		        s += "D";
		        input -= 500;
		    }
		    while (input >= 400) {
		        s += "CD";
		        input -= 400;
		    }
		    while (input >= 100) {
		        s += "C";
		        input -= 100;
		    }
		    while (input >= 90) {
		        s += "XC";
		        input -= 90;
		    }
		    while (input >= 50) {
		        s += "L";
		        input -= 50;
		    }
		    while (input >= 40) {
		        s += "XL";
		        input -= 40;
		    }
		    while (input >= 10) {
		        s += "X";
		        input -= 10;
		    }
		    while (input >= 9) {
		        s += "IX";
		        input -= 9;
		    }
		    while (input >= 5) {
		        s += "V";
		        input -= 5;
		    }
		    while (input >= 4) {
		        s += "IV";
		        input -= 4;
		    }
		    while (input >= 1) {
		        s += "I";
		        input -= 1;
		    }
		    return s;

	}

	public static void storeInArray(byte[] totalData, byte[] smallData, int start, int length){
		for(int i=0;i<length;i++){
			totalData[start + i] = smallData[i];
		}
	}

	public static float cosInterpolate(float a, float b, float blend) {
		double ft = blend * Math.PI;
		float f = (float) ((1f - Math.cos(ft)) * 0.5f);
		return a * (1 - f) + b * f;
	}

	public static float interpolate(float min, float max, float blend) {
		return min + (max - min) * blend;
	}

	public static Vectors3f interpolate(Vectors3f min, Vectors3f max, float blend) {
		Vectors3f.sub(max, min, VEC3);
		VEC3.scale(blend);
		return Vectors3f.add(min, VEC3, null);
	}

	public static float smoothInterpolate(float a, float b, float blend) {
		blend = smoothStep(0, 1, blend);
		return a * (1 - blend) + b * blend;
	}

	public static float rock(float min, float max, float time) {
		return Maths.smoothInterpolate(min, max, Math.abs((time - 0.5f) * 2f));
	}

	public static float fakeSin(float min, float max, float time) {
		float x = 1 - Math.abs((time+0.25f) * 2 % 2 - 1);
		return Maths.smoothInterpolate(min, max, x);
	}

	public static float smoothStep(float edge0, float edge1, float x) {
		float t = clamp((x - edge0) / (edge1 - edge0), 0f, 1f);
		return t * t * (3f - 2f * t);
	}

	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(value, max));
	}

	public static Vectors3f convertToScreenSpace(Vectors3f position, Matrix4fs viewMatrix, Matrix4fs projMatrix) {
		Vectors4f coords = new Vectors4f(position.x, position.y, position.z, 1f);
		Matrix4fs.transform(viewMatrix, coords, coords);
		Matrix4fs.transform(projMatrix, coords, coords);
		Vectors3f screenCoords = clipSpaceToScreenSpace(coords);
		return screenCoords;
	}

	public static float cosify(float input) {
		float theta = (float) (Math.PI * input);
		float cosine = (float) Math.cos(theta);
		return cosine * -0.5f + 0.5f;
	}

	private static Vectors3f clipSpaceToScreenSpace(Vectors4f coords) {
		if (coords.w < 0) {
			return null;
		}
		Vectors3f screenCoords = new Vectors3f(((coords.x / coords.w) + 1) / 2f, 1 - (((coords.y / coords.w) + 1) / 2f),
				coords.z);
		return screenCoords;
	}

	public static Matrix4fs getRotationMatrix(Vectors3f up, Vectors3f forwardish) {
		Vectors3f right = Vectors3f.cross(forwardish, up, null);
		right.normalise();
		Vectors3f.cross(up, right, forwardish);
		forwardish.normalise();
		Matrix4fs rotationMat = new Matrix4fs();
		rotationMat.m00 = right.x;
		rotationMat.m01 = right.y;
		rotationMat.m02 = right.z;
		rotationMat.m10 = up.x;
		rotationMat.m11 = up.y;
		rotationMat.m12 = up.z;
		rotationMat.m20 = -forwardish.x;
		rotationMat.m21 = -forwardish.y;
		rotationMat.m22 = -forwardish.z;
		return rotationMat;
	}

	public static float[] floatListToArray(List<Float> floatList) {
		float[] array = new float[floatList.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = floatList.get(i);
		}
		return array;
	}

	public static Vectors2f generateRandom2dVector() {
		double radians = RANDOM.nextFloat() * Math.PI * 2.0;
		float x = (float) Math.cos(radians);
		float y = (float) Math.sin(radians);
		return new Vectors2f(x, y);
	}

	public static float distanceFromLine(Vectors2f entityCenter, float gradient, Vectors2f lineOrigin) {
		float a = gradient;
		float c = lineOrigin.getY() - (a * lineOrigin.getX());
		float distance = (float) (Math.abs(-a * entityCenter.x + entityCenter.y - c) / Math.sqrt((a * a) + 1));
		return distance;
	}

	/*public static void createTexCoordTransform(Matrix3f transform, float x, float y, float rot, float scale) {
		transform.setIdentity();

		translateMatrix(transform, -0.5f, -0.5f);
		Matrix3f rotation = new Matrix3f();
		float cosAlpha = (float) Math.cos(Math.toRadians(rot));
		float sinAlpha = (float) Math.sin(Math.toRadians(rot));
		rotation.m00 = cosAlpha;
		rotation.m11 = cosAlpha;
		rotation.m10 = sinAlpha;
		rotation.m01 = -sinAlpha;
		Matrix3f.mul(rotation, transform, transform);
		translateMatrix(transform, 0.5f, 0.5f);

		scaleMatrix(transform, scale);
		translateMatrix(transform, x, y);
	}

	public static void translateMatrix(Matrix3f matrix, float x, float y) {
		Matrix3f translate = new Matrix3f();
		translate.m20 = x;
		translate.m21 = y;
		Matrix3f.mul(translate, matrix, matrix);
	}*/

	public static float[] concatenateArrays(List<float[]> arrays) {
		int totalLength = 0;
		for (float[] array : arrays) {
			totalLength += array.length;
		}
		return concatenateArrays(arrays, totalLength);
	}

	public static float[] concatenateArrays(List<float[]> arrays, int floatCount) {
		float[] bigArray = new float[floatCount];
		int pointer = 0;
		for (float[] array : arrays) {
			for (int i = 0; i < array.length; i++) {
				bigArray[pointer++] = array[i];
			}
		}
		return bigArray;
	}

	public static float[] concatenateArrays(float[]... arrays) {
		int totalLength = 0;
		for (float[] array : arrays) {
			totalLength += array.length;
		}
		float[] bigArray = new float[totalLength];
		int pointer = 0;
		for (float[] array : arrays) {
			for (int i = 0; i < array.length; i++) {
				bigArray[pointer++] = array[i];
			}
		}
		return bigArray;
	}

	public static int[] concatenateArrays(int[]... arrays) {
		int totalLength = 0;
		for (int[] array : arrays) {
			totalLength += array.length;
		}
		int[] bigArray = new int[totalLength];
		int pointer = 0;
		for (int[] array : arrays) {
			for (int i = 0; i < array.length; i++) {
				bigArray[pointer++] = array[i];
			}
		}
		return bigArray;
	}

	/*public static void scaleMatrix(Matrix3f matrix, float scale) {
		Matrix3f scaleMatrix = new Matrix3f();
		scaleMatrix.m00 = scale;
		scaleMatrix.m11 = scale;
		Matrix3f.mul(scaleMatrix, matrix, matrix);
	}*/

	public static boolean pointInTriangle(Vectors3f p, Vectors3f a1, Vectors3f b1, Vectors3f c1) {
		Vectors3f a = new Vectors3f(a1.x, 0f, a1.z);
		Vectors3f b = new Vectors3f(b1.x, 0f, b1.z);
		Vectors3f c = new Vectors3f(c1.x, 0f, c1.z);
		if (sameSide(p, a, b, c) && (sameSide(p, b, a, c)) && (sameSide(p, c, a, b))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean sameSide(Vectors3f p1, Vectors3f p2, Vectors3f a, Vectors3f b) {
		Vectors3f cp1 = Vectors3f.cross(Vectors3f.sub(b, a, null), Vectors3f.sub(p1, a, null), null);
		Vectors3f cp2 = Vectors3f.cross(Vectors3f.sub(b, a, null), Vectors3f.sub(p2, a, null), null);
		if (Vectors3f.dot(cp1, cp2) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean testPointInTriangle(Vectors3f p, Vectors3f a, Vectors3f b, Vectors3f c) {
		Vectors3f v0 = Vectors3f.sub(c, a, null);
		Vectors3f v1 = Vectors3f.sub(b, a, null);
		Vectors3f v2 = Vectors3f.sub(p, a, null);

		float dot00 = Vectors3f.dot(v0, v0);
		float dot01 = Vectors3f.dot(v0, v1);
		float dot02 = Vectors3f.dot(v0, v2);
		float dot11 = Vectors3f.dot(v1, v1);
		float dot12 = Vectors3f.dot(v1, v2);

		float invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
		float u = (dot11 * dot02 - dot01 * dot12) * invDenom;
		float v = (dot00 * dot12 - dot01 * dot02) * invDenom;

		return (u >= 0) && (v >= 0) && (u + v < 1);
	}

	/*public static Matrix3f getRotationMatrix(Matrix4fs mat4) {
		Matrix3f mat3 = new Matrix3f();
		mat3.m00 = mat4.m00;
		mat3.m01 = mat4.m01;
		mat3.m02 = mat4.m02;
		mat3.m10 = mat4.m10;
		mat3.m11 = mat4.m11;
		mat3.m12 = mat4.m12;
		mat3.m20 = mat4.m20;
		mat3.m21 = mat4.m21;
		mat3.m22 = mat4.m22;
		return mat3;
	}*/

	public static Vectors3f generateRandomUnitVector() {
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = (random.nextFloat() * 2) - 1;
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));
		return new Vectors3f(x, y, z);
	}

	public static Vectors3f randomPointInSquare(float centerX, float centerZ, float halfSize){
		float fullSize = halfSize + halfSize;
		Vectors3f result = new Vectors3f();
		result.x = (centerX - halfSize) + RANDOM.nextFloat() * fullSize;
		result.z = (centerZ - halfSize) + RANDOM.nextFloat() * fullSize;
		return result;
	}
	
	public static Vectors3f randomPointOnSquare(float centerX, float centerZ, float halfSize){
		float fullSize = halfSize + halfSize;
		Vectors3f result = new Vectors3f();
		boolean side = Maths.RANDOM.nextBoolean();
		float sideValue = Maths.RANDOM.nextBoolean() ? 1 : 0;
		result.x = (centerX - halfSize) + (side ? RANDOM.nextFloat() : sideValue) * fullSize;
		result.z = (centerZ - halfSize) + (!side ? RANDOM.nextFloat() : sideValue) * fullSize;
		return result;
	}
	
	public static Vectors3f randomPointOnCircle(Vectors3f normal, float radius) {
		Random random = new Random();
		Vectors3f randomPerpendicular = new Vectors3f();
		do {
			Vectors3f randomVector = Maths.generateRandomUnitVector();
			Vectors3f.cross(randomVector, normal, randomPerpendicular);
		} while (randomPerpendicular.length() == 0);
		randomPerpendicular.normalise();
		randomPerpendicular.scale(radius);
		float a = random.nextFloat();
		float b = random.nextFloat();
		if (a > b) {
			float temp = a;
			a = b;
			b = temp;
		}
		float randX = (float) (b * Math.cos(2 * Math.PI * (a / b)));
		float randY = (float) (b * Math.sin(2 * Math.PI * (a / b)));
		float distance = new Vectors2f(randX, randY).length();
		randomPerpendicular.scale(distance);
		return randomPerpendicular;
	}

	public static Vectors3f generateVectorWithinCone(Vectors3f coneDirection, float angle, float scale){
		Vectors3f velocity = Maths.generateRandomUnitVectorWithinCone(coneDirection, angle);
		velocity.scale(scale);
		return velocity;
	}
	
	public static Vectors3f generateRandomUnitVectorWithinCone(Vectors3f coneDirection, float angle) {
		float cosAngle = (float) Math.cos(angle);
		Random random = new Random();
		float theta = (float) (random.nextFloat() * 2f * Math.PI);
		float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
		float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
		float x = (float) (rootOneMinusZSquared * Math.cos(theta));
		float y = (float) (rootOneMinusZSquared * Math.sin(theta));

		Vectors4f direction = new Vectors4f(x, y, z, 1);
		if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
			Vectors3f rotateAxis = Vectors3f.cross(coneDirection, new Vectors3f(0, 0, 1), null);
			rotateAxis.normalise();
			float rotateAngle = (float) Math.acos(Vectors3f.dot(coneDirection, new Vectors3f(0, 0, 1)));
			Matrix4fs rotationMatrix = new Matrix4fs();
			rotationMatrix.setIdentity();
			rotationMatrix.rotate(-rotateAngle, rotateAxis);
			Matrix4fs.transform(rotationMatrix, direction, direction);
		} else {
			if (coneDirection.z == -1) {
				direction.z *= -1;
			}
		}
		return new Vectors3f(direction.x, direction.y, direction.z);

	}

	public static boolean isRayIntersectingWithSphere(Vectors3f unitRay, Vectors3f start, Vectors3f spherePosition,
			float radius) {
		Vectors3f toSphere = Vectors3f.sub(spherePosition, start, null);
		if (Vectors3f.dot(toSphere, unitRay) < 0) {
			return false;
		}
		float projectedVectorLength = Vectors3f.dot(unitRay, toSphere);
		Vectors3f projectedVector = new Vectors3f(unitRay.x * projectedVectorLength, unitRay.y * projectedVectorLength,
				unitRay.z * projectedVectorLength);
		Vectors3f projectedPoint = Vectors3f.add(start, projectedVector, null);
		Vectors3f projectedToCenter = Vectors3f.sub(spherePosition, projectedPoint, null);
		if (projectedToCenter.length() <= radius) {
			return true;
		} else {
			return false;
		}
	}

	public static float getAverageOfList(List<Float> numbers) {
		float total = 0;
		for (Float number : numbers) {
			total += number;
		}
		return total / numbers.size();
	}

	public static Vectors3f rotateVector(Vectors3f direction, float rotX, float rotY, float rotZ) {
		Matrix4fs matrix = updateTransformationMatrix(new Matrix4fs(), 0, 0, 0, rotX, rotY, rotZ, 1);
		Vectors4f direction4 = new Vectors4f(direction.x, direction.y, direction.z, 1.0f);
		Matrix4fs.transform(matrix, direction4, direction4);
		return new Vectors3f(direction4.x, direction4.y, direction4.z);
	}

	public static String toStringWithLimitedDigits(float number, int numberDigits) {
		String fullNumber = Float.toString(number);
		char[] digits = fullNumber.toCharArray();
		if (digits.length < numberDigits) {
			return fullNumber;
		}
		String limitedNumber = "";
		for (int i = 0; i < numberDigits; i++) {
			limitedNumber += digits[i];
		}
		return limitedNumber;
	}

	public static float coTangent(float angle) {
		return (float) (1f / Math.tan(angle));
	}

	public static float degreesToRadians(float degrees) {
		return degrees * (float) (Math.PI / 180d);
	}

	public static int[] calculateGridSquaresCrazy(int terrain, int section, int sectionCount, int increase) {
		int terrainChange = increase / sectionCount;
		int sectionChange = increase % sectionCount;

		int terrainResult = terrain + terrainChange;
		int sectionResult = section + sectionChange;
		if (sectionResult >= sectionCount) {
			sectionResult -= sectionCount;
			terrainResult += 1;
		} else if (sectionResult < 0) {
			sectionResult += sectionCount;
			terrainResult -= 1;
		}
		return new int[] { terrainResult, sectionResult };
	}

	public static float getDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		float distance = (float) Math.sqrt((dX * dX) + (dY * dY));
		return distance;
	}

	public static float getDistanceBetweenPoints(float x1, float y1, float z1, float x2, float y2, float z2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		float dZ = z1 - z2;
		float distance = (float) Math.sqrt((dX * dX) + (dY * dY) + (dZ * dZ));
		return distance;
	}

	public static float getComparitableDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		float dZ = z1 - z2;
		float distance = (dX * dX) + (dY * dY) + (dZ * dZ);
		return distance;
	}

	public static float getComparitableDistance(float x1, float y1, float x2, float y2) {
		float dX = x1 - x2;
		float dY = y1 - y2;
		float distance = (dX * dX) + (dY * dY);
		return distance;
	}

	public static float getComparitableDistance(Vectors3f p1, Vectors3f p2) {
		return getComparitableDistance(p1.x, p1.y, p1.z, p2.x, p2.y, p2.z);
	}

	public static float getVectorLength(float x, float y, float z) {
		float length = (float) Math.sqrt((x * x) + (y * y) + (z * z));
		return length;
	}

	public static double dotProductOfQuaternions(double[] qA, double[] qB) {
		return qA[0] * qB[0] + qA[1] * qB[1] + qA[2] * qB[2] + qA[3] * qB[3];
	}

	public static double dotProductOfQuaternions(Quaternion qA, Quaternion qB) {
		return qA.getW() * qB.getW() + qA.getX() * qB.getX() + qA.getY() * qB.getY() + qA.getZ() * qB.getZ();
	}

	public static Matrix4fs quaternionToMatrix(double w, double x, double y, double z) {
		double xSquared = x * x;
		double twoXY = 2 * x * y;
		double twoXZ = 2 * x * z;
		double twoXW = 2 * x * w;
		double ySquared = y * y;
		double twoYZ = 2 * y * z;
		double twoYW = 2 * y * w;
		double twoZW = 2 * z * w;
		double zSquared = z * z;
		double wSquared = w * w;

		Matrix4fs matrix = new Matrix4fs();
		matrix.m00 = (float) (wSquared + xSquared - ySquared - zSquared);
		matrix.m01 = (float) (twoXY - twoZW);
		matrix.m02 = (float) (twoXZ + twoYW);
		matrix.m03 = 0;
		matrix.m10 = (float) (twoXY + twoZW);
		matrix.m11 = (float) (wSquared - xSquared + ySquared - zSquared);
		matrix.m12 = (float) (twoYZ - twoXW);
		matrix.m13 = 0;
		matrix.m20 = (float) (twoXZ - twoYW);
		matrix.m21 = (float) (twoYZ + twoXW);
		matrix.m22 = (float) (wSquared - xSquared - ySquared + zSquared);
		matrix.m23 = 0;
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		matrix.m33 = 1;
		return matrix;
	}

	public static Matrix4fs updateTransformationMatrix(Matrix4fs transformationMatrix, float x, float y, float z,
			float rotX, float rotY, float rotZ, float scale) {
		// TODO perhaps only rotY needed for most entities? Save 2 rotation
		// calcs.
		transformationMatrix.setIdentity();
		Matrix4fs.translate(new Vectors3f(x, y, z), transformationMatrix, transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotY), new Vectors3f(0, 1, 0), transformationMatrix,
				transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotZ), new Vectors3f(0, 0, 1), transformationMatrix,
				transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotX), new Vectors3f(1, 0, 0), transformationMatrix,
				transformationMatrix);
		Matrix4fs.scale(new Vectors3f(scale, scale, scale), transformationMatrix, transformationMatrix);
		return transformationMatrix;
	}

	public static Matrix4fs updateTransformationMatrix(Matrix4fs transformationMatrix, Vectors3f position, float rotX,
			float rotY, float rotZ, float scale) {
		return updateTransformationMatrix(transformationMatrix, position.x, position.y, position.z, rotX, rotY, rotZ,
				scale);
	}

	public static Matrix4fs updateModelMatrix(Matrix4fs matrix, Vectors3f position, float rotX, float rotY, float rotZ,
			Vectors3f scale) {
		matrix.setIdentity();
		Matrix4fs.translate(position, matrix, matrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotX), new Vectors3f(1, 0, 0), matrix, matrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotY), new Vectors3f(0, 1, 0), matrix, matrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotZ), new Vectors3f(0, 0, 1), matrix, matrix);
		Matrix4fs.scale(scale, matrix, matrix);
		return matrix;
	}

	public static Matrix4fs createTransformationMatrix(float x, float y, float z, float rotX, float rotY, float rotZ,
			float scaleX, float scaleY, float scaleZ) {
		Matrix4fs transformationMatrix = new Matrix4fs();
		Matrix4fs.translate(new Vectors3f(x, y, z), transformationMatrix, transformationMatrix);
		Matrix4fs.scale(new Vectors3f(scaleX, scaleY, scaleZ), transformationMatrix, transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotX), new Vectors3f(1, 0, 0), transformationMatrix,
				transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotY), new Vectors3f(0, 1, 0), transformationMatrix,
				transformationMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotZ), new Vectors3f(0, 0, 1), transformationMatrix,
				transformationMatrix);
		return transformationMatrix;
	}

	public static Matrix4fs createModelMatrix(Vectors3f pos, float rotY, float scale) {
		Matrix4fs modelMatrix = new Matrix4fs();
		Matrix4fs.translate(pos, modelMatrix, modelMatrix);
		Matrix4fs.rotate(Maths.degreesToRadians(rotY), new Vectors3f(0, 1, 0), modelMatrix, modelMatrix);
		Matrix4fs.scale(new Vectors3f(scale, scale, scale), modelMatrix, modelMatrix);
		return modelMatrix;
	}

	public static float getLowestPositiveRoot(float a, float b, float c) {// -1
																			// if
																			// no
																			// valid
																			// root
		float determinant = b * b - (4.0f * a * c);
		if (determinant < 0) {
			return -1;
		}
		float sqrtD = (float) Math.sqrt(determinant);
		float r1 = (-b - sqrtD) / (2 * a);
		float r2 = (-b + sqrtD) / (2 * a);
		if (r2 < r1) {
			float temp = r1;
			r1 = r2;
			r2 = temp;
		}
		if (r1 > 0) {
			return r1;
		} else if (r2 > 0) {
			return r2;
		} else {
			return -1;
		}
	}

	public static Vectors3f calculateNormal(Vectors3f point0, Vectors3f point1, Vectors3f point2) {// anticlockwise
																								// order
		Vectors3f vectorA = Vectors3f.sub(point1, point0, null);
		Vectors3f vectorB = Vectors3f.sub(point2, point0, null);
		Vectors3f normal = Vectors3f.cross(vectorA, vectorB, null);
		normal.normalise();
		return normal;
	}

	public static String formatNumber(long amount) {
		String cash = "";
		boolean negative = amount < 0;
		amount = Math.abs(amount);
		String number = String.valueOf(amount);
		char[] digits = number.toCharArray();
		int count = 0;
		cash += digits[count++];
		while (count < digits.length) {
			if ((digits.length - count) % 3 == 0) {
				cash += ",";
			}
			cash += digits[count++];
		}
		String result = negative ? "-" : "";
		return result + cash;
	}

	public static float biggestOf(float a, float b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	public static String formatAbreviatedNumber(long count) {
		String suffix;
		String number;
		if (count >= 10000000000l) {
			number = String.valueOf(count / 1000000000l);
			suffix = "B";
		} else if (count >= 1000000000) {
			number = String.format("%.1f", (float) count / 1000000000);
			suffix = "B";
		} else if (count >= 10000000) {
			number = String.valueOf(count / 1000000);
			suffix = "M";
		} else if (count >= 1000000) {
			number = String.format("%.1f", (float) count / 1000000);
			suffix = "M";
		} else if (count >= 10000) {
			number = String.valueOf(count / 1000);
			suffix = "K";
		} else if (count >= 1000) {
			number = String.format("%.1f", (float) count / 1000);
			suffix = "K";
		} else {
			suffix = "";
			number = String.valueOf(count);
		}
		String countString = number + suffix;
		return countString;
	}

}
