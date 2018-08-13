package team.hdt.blockadia.game_engine.util;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryReader {
	
	private DataInputStream reader;
	
	public BinaryReader(File file){
		try{
		reader = new DataInputStream(new FileInputStream(file));
		}catch(Exception e){
			System.err.println("Couldn't open read file "+file);
		}
	}
	
	public int readInt() throws Exception{
		return reader.readInt();
	}
	
	public float readFloat() throws Exception{
		return reader.readFloat();
	}
	
	public boolean readBoolean() throws Exception{
		return reader.readBoolean();
	}
	
	public String readString() throws Exception{
		return reader.readUTF();
	}
	
	public long readLong() throws Exception{
		return reader.readLong();
	}
	
	public short readShort() throws Exception{
		return reader.readShort();
	}
	
	public Vectors3f readVector() throws Exception{
		float x = readFloat();
		float y = readFloat();
		float z = readFloat();
		return new Vectors3f(x, y, z);
	}
	
	public void close(){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
