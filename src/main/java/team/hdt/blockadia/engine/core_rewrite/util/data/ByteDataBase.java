package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class ByteDataBase {

	public abstract void write(DataOutput output) throws IOException;

	public abstract void read(DataInput input) throws IOException;

	public abstract byte getId();

	public abstract ByteDataBase copy();

	@Override
	public abstract String toString();

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ByteDataBase) {
			return this.getId() == ((ByteDataBase) obj).getId();
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.copy();
	}

	public static ByteDataBase createNewByType(byte id) {
		switch (id) {
		case 1:
			return new ByteDataTypeByte();
		case 2:
			return new ByteDataTypeShort();
		case 3:
			return new ByteDataTypeInt();
		case 4:
			return new ByteDataTypeFloat();
		case 5:
			return new ByteDataTypeDouble();
		case 6:
			return new ByteDataTypeLong();
		case 7:
			return new ByteDataTypeBoolean();
		case 8:
			return new ByteDataTypeString();
		case 9:
			return new ByteDataTypeByteArray();
		case 10:
			return new ByteDataTypeIntArray();
		case 11:
			return new ByteDataContainer();
		}
		return null;
	}
}