package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteDataTypeBoolean extends ByteDataBase {

	private boolean data;
	
	ByteDataTypeBoolean() {
	}

	public ByteDataTypeBoolean(boolean data) {
		this.data = data;
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeBoolean(data);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.data = input.readBoolean();
	}
	
	public boolean getBoolean() {
		return data;
	}

	@Override
	public byte getId() {
		return 7;
	}

	@Override
	public ByteDataBase copy() {
		return new ByteDataTypeBoolean(data);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((ByteDataTypeBoolean) obj).data == this.data;
	}

	@Override
	public String toString() {
		return Boolean.toString(data);
	}
}