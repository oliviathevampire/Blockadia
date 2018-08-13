package team.hdt.blockadia.game_engine.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileUtils {
	
	public static final String FILE_SEPARATOR = "/";
	public static final String SEPARATOR = ";";
	public static MyFile RES_FOLDER = new MyFile("assets/blockadia");
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	public static boolean readBoolean(String value){
		int boolValue = Integer.parseInt(value);
		return boolValue == TRUE;
	}
	
	public static int booleanToInt(boolean bool){
		return bool ? TRUE : FALSE;
	}
	
	public static void closeBufferedReader(BufferedReader reader){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void renameFile(File original, File newName){
		try {
			Files.move(original.toPath(), newName.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile(File file){
		if(!file.exists()){
			return;
		}
		if(file.isDirectory()){
			for(File innerFile : Objects.requireNonNull(file.listFiles())){
				deleteFile(innerFile);
			}
		}
		file.delete();
	}

}
