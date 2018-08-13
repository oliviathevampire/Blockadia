package team.hdt.blockadia.game_engine.common.util;

import team.hdt.blockadia.game_engine.client.saves.SaveSlot;
import team.hdt.blockadia.game_engine.common.Main;
import team.hdt.blockadia.game_engine.util.BinaryReader;
import team.hdt.blockadia.game_engine.util.BinaryWriter;
import team.hdt.blockadia.game_engine.util.languages.Language;

import java.io.File;

public class UserConfigs {

	private static int saveSlotId = 0;
	private static Language language = Language.ENGLISH;
	private static int presetId = 3;
	private static int[] corruptSlots = new int[0];

	private static final File CONFIGS_FILE = new File(Main.version + "_UserConfigs.dat");

	public static void loadConfigs() {
		try {
			if (!CONFIGS_FILE.createNewFile()) {
				BinaryReader reader = new BinaryReader(CONFIGS_FILE);
				corruptSlots = new int[reader.readInt()];
				for(int i = 0; i < corruptSlots.length; i++){
					corruptSlots[i] = reader.readInt();
				}
				saveSlotId = reader.readInt();
				language = Language.values()[reader.readInt()];
				presetId = reader.readInt();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading user configs.");
		}
	}

    public static void checkCorruption(SaveSlot slot){
        for(int slotNumber : corruptSlots){
            if(slotNumber == slot.getNumber()){
                slot.setCorrupt();
                return;
            }
        }
    }

	public static int getPresetId(){
		return presetId;
	}
	
	public static void setPresetId(int id){
		presetId = id;
	}
	
	public static int getSaveSlotId() {
		return saveSlotId;
	}
	
	public static void setLanguage(Language lan){
		language = lan;
	}
	
	public static Language getLanguage(){
		return language;
	}

	public static void saveConfigs() {
		BinaryWriter writer = new BinaryWriter(CONFIGS_FILE);
		writer.writeInt(language.ordinal());
		writer.writeInt(presetId);
		writer.close();
	}

}
