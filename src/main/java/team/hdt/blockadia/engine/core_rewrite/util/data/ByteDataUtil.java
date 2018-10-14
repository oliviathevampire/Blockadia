package team.hdt.blockadia.engine.core_rewrite.util.data;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;

public class ByteDataUtil {

	public static byte[] readBytesFromData(ByteDataContainer container) {
		try {
			ByteArrayOutputStream io = new ByteArrayOutputStream();
			DataOutput output = new DataOutputStream(io);
			container.write(output);
			return io.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	
	public static ByteDataContainer createContainer(byte[] bytes) {
		ByteDataContainer container = new ByteDataContainer();
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			DataInput input = new DataInputStream(inputStream);
			container.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return container;
	}
}