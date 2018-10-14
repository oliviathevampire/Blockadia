package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeByte extends ByteDataTypePrimitive {

	private byte data;

	ByteDataTypeByte() {
	}

	public ByteDataTypeByte(byte data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeByte(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readByte();
	}

	@Override
	public byte getByte() {
		return data;
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
		return 1;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeByte(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeByte) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Byte.toString(data);
	}
}