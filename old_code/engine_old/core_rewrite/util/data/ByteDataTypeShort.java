package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeShort extends ByteDataTypePrimitive {

	private short data;

	ByteDataTypeShort() {
	}

	public ByteDataTypeShort(short data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeShort(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readShort();
	}

	@Override
	public byte getByte() {
		return (byte) (data & 256);
	}

	@Override
	public short getShort() {
		return data;
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
		return 2;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeShort(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeShort) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Short.toString(data);
	}
}