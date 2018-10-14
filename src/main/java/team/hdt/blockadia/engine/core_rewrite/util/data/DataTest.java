package team.hdt.blockadia.engine.core_rewrite.util.data;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.*;
import java.util.Arrays;

public class DataTest {

	public static void main(String[] args) throws Exception {
		ByteDataContainer data = new ByteDataContainer();
		data.setShort("test", (short) 0);
		data.setShort("test1", (short) 2);

		ByteArrayOutputStream io = new ByteArrayOutputStream();
		DataOutput output = new DataOutputStream(io);
		data.write(output);
		System.out.println(Arrays.toString(io.toByteArray()));

		ByteArrayInputStream inputStream = new ByteArrayInputStream(io.toByteArray());
		DataInput input = new DataInputStream(inputStream);
		data.read(input);
		System.out.println(data.keySet());
	}
}