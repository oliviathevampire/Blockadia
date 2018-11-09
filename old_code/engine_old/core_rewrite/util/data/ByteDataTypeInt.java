package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeInt extends ByteDataTypePrimitive {

	private int data;

	ByteDataTypeInt() {
	}

	public ByteDataTypeInt(int data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeInt(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readInt();
	}

	@Override
	public byte getByte() {
		return (byte) (data & 256);
	}

	@Override
	public short getShort() {
		return (short) (data & 65535);
	}

	@Override
	public int getInt() {
		return data;
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
		return data;
	}

	@Override
	public byte getId() {
		return 3;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeInt(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeInt) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Integer.toString(data);
	}
}