package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeDouble extends ByteDataTypePrimitive {

	private double data;

	ByteDataTypeDouble() {
	}

	public ByteDataTypeDouble(double data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeDouble(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readDouble();
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
		return data;
	}

	@Override
	public long getLong() {
		return (long) data;
	}

	@Override
	public byte getId() {
		return 5;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeDouble(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeDouble) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Double.toString(data);
	}
}