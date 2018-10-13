package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class ByteDataTypeIntArray extends ByteDataBase {

	private int[] data;

	ByteDataTypeIntArray() {
	}

	public ByteDataTypeIntArray(int[] data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeInt(data.length);
		for (int i = 0; i < data.length; i++) {
			output.writeInt(data[i]);
		}
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = new int[input.readInt()];
		for (int i = 0; i < data.length; i++) {
			this.data[i] = input.readInt();
		}
	}

	public int[] getIntArray() {
		return data;
	}

	@Override
	public byte getId() {
		return 10;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeIntArray(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeIntArray) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Arrays.toString(data);
	}
}