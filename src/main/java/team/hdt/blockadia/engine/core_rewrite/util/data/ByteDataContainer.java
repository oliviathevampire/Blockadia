package team.hdt.blockadia.engine.core_rewrite.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nullable;

public class ByteDataContainer extends ByteDataBase {

	private Map<String, ByteDataBase> dataMap;

	public ByteDataContainer() {
		this.dataMap = new HashMap<String, ByteDataBase>();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		for (String key : dataMap.keySet()) {
			writeEntry(key, dataMap.get(key), output);
		}

		output.writeByte(0);
	}

	@Override
	public void read(DataInput input) throws IOException {
		this.dataMap.clear();
		byte id;

		while ((id = input.readByte()) != 0) {
			String key = input.readUTF();
			this.dataMap.put(key, readData(id, key, input));
		}
	}

	private static void writeEntry(String name, ByteDataBase data, DataOutput output) throws IOException {
		output.writeByte(data.getId());

		if (data.getId() != 0) {
			output.writeUTF(name);
			data.write(output);
		}
	}

	private static ByteDataBase readData(byte id, String key, DataInput input) throws IOException {
		ByteDataBase nbtbase = ByteDataBase.createNewByType(id);

		try {
			nbtbase.read(input);
			return nbtbase;
		} catch (IOException e) {
			throw new RuntimeException("Error when loading Byte Data Type \'" + key + "\' id \'" + Byte.valueOf(id) + "\'", e);
		}
	}

	public int getSize() {
		return this.dataMap.size();
	}

	public Set<String> keySet() {
		return this.dataMap.keySet();
	}

	public Set<Entry<String, ByteDataBase>> entrySet() {
		return this.dataMap.entrySet();
	}

	@Override
	public byte getId() {
		return 11;
	}

	@Override
	public ByteDataBase copy() {
		ByteDataContainer container = new ByteDataContainer();
		container.dataMap = this.dataMap;
		return container;
	}

	@Nullable
	public byte getByte(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypeByte) dataMap.get(key)).getByte() : null;
	}

	@Nullable
	public short getShort(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypePrimitive) dataMap.get(key)).getShort() : null;
	}

	@Nullable
	public int getInt(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypePrimitive) dataMap.get(key)).getInt() : null;
	}

	@Nullable
	public float getFloat(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypePrimitive) dataMap.get(key)).getFloat() : null;
	}

	@Nullable
	public double getDouble(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypePrimitive) dataMap.get(key)).getDouble() : null;
	}

	@Nullable
	public boolean getBoolean(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypeBoolean) dataMap.get(key)).getBoolean() : null;
	}

	@Nullable
	public long getLong(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypePrimitive) dataMap.get(key)).getLong() : null;
	}

	@Nullable
	public String getString(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypeString) dataMap.get(key)).getString() : null;
	}

	@Nullable
	public byte[] getByteArray(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypeByteArray) dataMap.get(key)).getByteArray() : null;
	}

	@Nullable
	public int[] getIntArray(String key) {
		return dataMap.containsKey(key) ? ((ByteDataTypeIntArray) dataMap.get(key)).getIntArray() : null;
	}

	public ByteDataBase getTag(String key) {
		return dataMap.get(key);
	}

	@Nullable
	public ByteDataContainer getByteContainer(String key) {
		return (ByteDataContainer) dataMap.get(key);
	}

	@Nullable
	public UUID getUUID(String key) {
		return new UUID(this.getLong(key + "Most"), this.getLong(key + "Least"));
	}

	public void setByte(String key, byte data) {
		dataMap.put(key, new ByteDataTypeByte(data));
	}

	public void setShort(String key, short data) {
		dataMap.put(key, new ByteDataTypeShort(data));
	}

	public void setInt(String key, int data) {
		dataMap.put(key, new ByteDataTypeInt(data));
	}

	public void setFloat(String key, float data) {
		dataMap.put(key, new ByteDataTypeFloat(data));
	}

	public void setDouble(String key, double data) {
		dataMap.put(key, new ByteDataTypeDouble(data));
	}

	public void setLong(String key, long data) {
		dataMap.put(key, new ByteDataTypeLong(data));
	}

	public void setBoolean(String key, boolean data) {
		dataMap.put(key, new ByteDataTypeBoolean(data));
	}

	public void setString(String key, String data) {
		dataMap.put(key, new ByteDataTypeString(data));
	}

	public void setByteArray(String key, byte[] data) {
		dataMap.put(key, new ByteDataTypeByteArray(data));
	}

	public void setIntArray(String key, int[] data) {
		dataMap.put(key, new ByteDataTypeIntArray(data));
	}

	public void setTag(String key, ByteDataBase data) {
		dataMap.put(key, data);
	}

	public void setUUID(String key, UUID id) {
		this.setLong(key + "Most", id.getMostSignificantBits());
		this.setLong(key + "Least", id.getLeastSignificantBits());
	}

	public boolean hasKey(String key, int type) {
		if (type > 0 && type < 6) {
			return this.dataMap.get(key) instanceof ByteDataTypePrimitive;
		} else if (type == 6) {
			return this.dataMap.get(key) instanceof ByteDataTypeBoolean;
		} else if (type == 7) {
			return this.dataMap.get(key) instanceof ByteDataTypeString;
		} else if (type == 8) {
			return this.dataMap.get(key) instanceof ByteDataTypeByteArray;
		} else if (type == 9) {
			return this.dataMap.get(key) instanceof ByteDataTypeIntArray;
		} else if (type == 10) {
			return this.dataMap.get(key) instanceof ByteDataContainer;
		} else if (type == 99) {
			return true;
		}
		return false;
	}

	public boolean hasKey(String key) {
		return dataMap.containsKey(key);
	}

	@Override
	public String toString() {
		return this.entrySet().toString();
	}
}