package team.hdt.blockadia.game_engine.util;

import team.hdt.blockadia.game_engine.common.util.math.vectors.Vectors3f;

import java.io.BufferedReader;
import java.io.IOException;

public class CSVReader {

	private final String SEPARATOR;
	private BufferedReader reader;
	private LineSplitter splitter;
	
	public CSVReader(MyFile file) throws Exception {
		this.reader = file.getReader();
		SEPARATOR = FileUtils.SEPARATOR;
	}

	public String nextLine() {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (line != null) {
			this.splitter = new LineSplitter(line, SEPARATOR);
			return line;
		} else {
			return null;
		}
	}

	public String getNextString() {
		return splitter.getNextString();
	}
	
	public String getNextLabelString() {
		getNextString();
		return getNextString();
	}
	
	public float getNextLabelFloat() {
		getNextString();
		return getNextFloat();
	}

	public int getNextInt() {
		return splitter.getNextInt();
	}

	public long getNextLong() {
		return splitter.getNextLong();
	}

	public float getNextFloat() {
		return splitter.getNextFloat();
	}
	
	public int[] getNextLabelIntArray(){
		getNextString();
		int count = getNextInt();
		int[] array = new int[count];
		for(int i=0;i<count;i++){
			array[i] = getNextInt();
		}
		return array;
	}
	
	public float[] getNextLabelFloatArray(){
		getNextString();
		int count = getNextInt();
		float[] array = new float[count];
		for(int i=0;i<count;i++){
			array[i] = getNextFloat();
		}
		return array;
	}
	
	public Vectors3f getNextLabelVector(){
		getNextString();
		return getNextVector();
	}
	
	public Vectors3f getNextVector(){
		float x = splitter.getNextFloat();
		float y = splitter.getNextFloat();
		float z = splitter.getNextFloat();
		return new Vectors3f(x, y, z);
	}

	public boolean isEndOfLine() {
		return !splitter.hasMoreValues();
	}

	public boolean getNextBool() {
		return splitter.getNextBool();
	}
	
	public boolean getNextLabelBool() {
		getNextString();
		return getNextBool();
	}
	
	public int getNextLabelInt() {
		getNextString();
		return getNextInt();
	}

	public void close() {
		FileUtils.closeBufferedReader(reader);
	}

}
