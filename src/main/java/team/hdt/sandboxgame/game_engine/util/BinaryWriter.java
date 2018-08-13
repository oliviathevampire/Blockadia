package team.hdt.sandboxgame.game_engine.util;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryWriter {

	private DataOutputStream writer;

	public BinaryWriter(File file) {
		try {
			this.writer = new DataOutputStream(new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeInt(int value) {
		try {
			writer.writeInt(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeBoolean(boolean bool){
		try {
			writer.writeBoolean(bool);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFloat(float value) {
		try {
			writer.writeFloat(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeLong(long value) {
		try {
			writer.writeLong(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeShort(short value) {
		try {
			writer.writeShort(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeString(String value) {
		try {
			writer.writeUTF(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeVector(Vectors3f vector) {
		writeFloat(vector.x);
		writeFloat(vector.y);
		writeFloat(vector.z);
	}

	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
