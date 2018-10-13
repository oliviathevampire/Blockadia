package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class ByteDataTypeByteArray extends ByteDataBase {

	private byte[] data;

	ByteDataTypeByteArray() {
	}

	public ByteDataTypeByteArray(byte[] data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeByte(data.length);
		output.write(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = new byte[input.readInt() - 1];
		input.readFully(this.data);
	}

	public byte[] getByteArray() {
		return data;
	}

	@Override
	public byte getId() {
		return 9;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeByteArray(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeByteArray) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}