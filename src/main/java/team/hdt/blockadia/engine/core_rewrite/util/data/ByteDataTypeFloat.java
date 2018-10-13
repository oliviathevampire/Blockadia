package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeFloat extends ByteDataTypePrimitive {

	private float data;
	
	ByteDataTypeFloat() {
	}

	public ByteDataTypeFloat(float data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeFloat(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readFloat();
	}

	@Override
	public byte getByte() {
		return (byte) data;
	}

	@Override
	public short getShort() {
		return (short) data;
	}

	@Override
	public int getInt() {
		return (int) data;
	}

	@Override
	public float getFloat() {
		return data;
	}

	@Override
	public double getDouble() {
		return data;
	}

	@Override
	public long getLong() {
		return (long) data;
	}

	@Override
	public byte getId() {
		return 4;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeFloat(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeFloat) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Float.toString(data);
	}
}