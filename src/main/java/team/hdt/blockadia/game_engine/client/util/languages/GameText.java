package team.hdt.blockadia.game_engine.client.util.languages;

import team.hdt.blockadia.game_engine.client.util.CSVReader;
import team.hdt.blockadia.game_engine.client.util.FileUtils;
import team.hdt.blockadia.game_engine.client.util.MyFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameText {

	public static final MyFile LANGUAGE_FILE = new MyFile(FileUtils.RES_FOLDER, "languageSheet.csv");
	
	private static Map<Integer, List<String>> gameTexts = new HashMap<Integer, List<String>>();
	private static int languageId;

	public static void init(int langId) {
		languageId = langId;
		try {
			CSVReader reader = new CSVReader(LANGUAGE_FILE);
			reader.nextLine();
			while (reader.nextLine() != null) {
				int id = reader.getNextInt();
				reader.getNextString();
				List<String> texts = new ArrayList<String>();
				for (int i = 0; i < Language.values().length; i++) {
					texts.add(reader.getNextString());
				}
				gameTexts.put(id, texts);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem loading language text file!");
			System.exit(-1);
		}
	}
	
	public static ComplexString getComplexText(int id){
		return new ComplexString(getText(id));
	}

	public static String getText(int id) {
		List<String> text = gameTexts.get(id);
		if (text != null) {
			return text.get(languageId);
		} else {
			System.err.println("No game text with the ID " + id);
			System.exit(-1);
			return null;
		}
	}

}
