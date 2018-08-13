package team.hdt.sandboxgame.game_engine.util.toolbox;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

import java.nio.ByteBuffer;

public class ByteWork {
	
	public static final int FLOAT_LENGTH = 4;
	public static final int INT_LENGTH = 4;
	public static final int SHORT_LENGTH = 2;
	public static final int LONG_LENGTH = 8;
	
	public static byte[] longToBytes(long number){
		ByteBuffer buffer = ByteBuffer.allocate(LONG_LENGTH);
		buffer.putLong(number);
		return buffer.array();
	}
	
	public static int encodeShortIntoArray(short naughtyShort, byte[] array, int pointer){
		byte[] shortInBytes = shortToBytes(naughtyShort);
		for (int i = 0; i < shortInBytes.length; i++) {
			array[pointer++] = shortInBytes[i];
		}
		return pointer;
	}
	
	public static short getNextShortFromArray(byte[] array, int pointer) {
		byte[] shortInBytes = new byte[SHORT_LENGTH];
		for (int i = 0; i < shortInBytes.length; i++) {
			shortInBytes[i] = array[pointer++];
		}
		return bytesToShort(shortInBytes);
	}
	
	public static long bytesToLong(byte[] bytes){
		ByteBuffer buffer = ByteBuffer.allocate(LONG_LENGTH);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getLong();
	}
	
	public static byte booleansToByte(boolean... bools){
		int pointer = 0;
		byte result = 0;
		for(boolean b : bools){
			if(pointer == 8){
				return result;
			}
			if(b){
				result|=(byte)Math.pow(2, pointer);
			}
			pointer++;
		}
		return result;
	}
	
	public static boolean[] byteToBooleans(byte b){
		boolean[] result = new boolean[8];
		for(int i=0;i<8;i++){
			byte mask = (byte) Math.pow(2, i);
			result[i] = (b & mask) == mask;
		}
		return result;
	}
	
	public static int encodeByteArrayIntoArray(byte[] data,byte[] additional,int pointer){
		for(int i=0;i<additional.length;i++){
			data[pointer++] = additional[i];
		}
		return pointer;
	}
	
	public static long getNextLongFromArray(byte[] array,int pointer){
		byte[] longInBytes = new byte[LONG_LENGTH];
		for(int i=0;i<LONG_LENGTH;i++){
			longInBytes[i] = array[pointer++];
		}
		return bytesToLong(longInBytes);
	}
	
	public static int encodeLongIntoArray(long naughtyLong,byte[] array, int pointer){
		byte[] longInBytes = longToBytes(naughtyLong);
		for(int i=0;i<LONG_LENGTH;i++){
			array[pointer++] = longInBytes[i];
		}
		return pointer;
	}
	
	public static int encodeFloatIntoArray(float naughtyFloat, byte[] array, int pointer) {
		byte[] floatInBytes = floatToBytes(naughtyFloat);
		for (int i = 0; i < FLOAT_LENGTH; i++) {
			array[pointer++] = floatInBytes[i];
		}
		return pointer;
	}
	
	public static int encodeVectorIntoArray(Vectors3f vector, byte[] array, int pointer){
		pointer = encodeFloatIntoArray(vector.x, array, pointer);
		pointer = encodeFloatIntoArray(vector.y, array, pointer);
		pointer = encodeFloatIntoArray(vector.z, array, pointer);
		return pointer;
	}

	public static int encodeIntIntoArray(int naughtyInt, byte[] array, int pointer) {
		byte[] intInBytes = intToBytes(naughtyInt);
		for (int i = 0; i < INT_LENGTH; i++) {
			array[pointer++] = intInBytes[i];
		}
		return pointer;
	}

	public static float getNextFloatFromArray(byte[] array, int pointer) {
		byte[] floatInBytes = new byte[FLOAT_LENGTH];
		for (int i = 0; i < FLOAT_LENGTH; i++) {
			floatInBytes[i] = array[pointer++];
		}
		return bytesToFloat(floatInBytes);
	}

	public static int getNextIntFromArray(byte[] array, int pointer) {
		byte[] intInBytes = new byte[INT_LENGTH];
		for (int i = 0; i < INT_LENGTH; i++) {
			intInBytes[i] = array[pointer++];
		}
		return bytesToInt(intInBytes);
	}

	public static boolean testByteWithMask(byte test, byte mask) {
		if ((test & mask) == mask) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int bytesToInt(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getInt();
	}

	public static byte[] intToBytes(int number) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(number);
		return buffer.array();
	}

	public static float bytesToFloat(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getFloat();
	}

	public static byte[] floatToBytes(float number) {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putFloat(number);
		return buffer.array();
	}

	public static short bytesToShort(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.put(bytes);
		buffer.flip();
		return buffer.getShort();
	}

	public static byte[] shortToBytes(short number) {
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.putShort(number);
		return buffer.array();
	}

	public static int shortsToInt(short x, short y) {
		int total = x;
		total = total << 16;
		total += y;
		return total;
	}

}
