package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeLong extends ByteDataTypePrimitive {

	private long data;

	ByteDataTypeLong() {
	}

	public ByteDataTypeLong(long data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeLong(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readLong();
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
		return (float) data;
	}

	@Override
	public double getDouble() {
		return (double) data;
	}

	@Override
	public long getLong() {
		return data;
	}

	@Override
	public byte getId() {
		return 6;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeLong(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeLong) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Long.toString(data);
	}
}