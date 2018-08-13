package team.hdt.blockadia.game_engine.client.saves;

import team.hdt.blockadia.game_engine.client.ClientMain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Saves {

	public static final File SAVES_FOLDER = new File(ClientMain.version + "_Saves");

	protected static String SAVE_FILE_NAME = ClientMain.version;
	protected static String SAVE_FILE_EXT = ".dat";

	private SaveSlot[] slots;

	public Saves(int slotCount) {
		initSavesFolder();
		Map<Integer, String> existingFileNames = getExistingFiles();
		slots = new SaveSlot[slotCount];
		for (int i = 0; i < slotCount; i++) {
			String name = existingFileNames.get(i);
			if (name == null) {
				slots[i] = new SaveSlot(i);
			} else {
				slots[i] = new SaveSlot(i, name);
			}
		}
	}

	public boolean hasFreeSlots() {
		for (SaveSlot slot : slots) {
			if (slot.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public List<SaveSlot> getCorruptSaves() {
		List<SaveSlot> corrupts = new ArrayList<SaveSlot>();
		for (SaveSlot slot : slots) {
			if (slot.isCorrupt()) {
				corrupts.add(slot);
			}
		}
		return corrupts;
	}

	public SaveSlot getFirstWorld() {
		for (SaveSlot slot : slots) {
			if (!slot.isEmpty() && !slot.isCorrupt()) {
				return slot;
			}
		}
		return null;
	}

	public int getSlotCount() {
		return slots.length;
	}

	public void deleteSave(int i) {
		SaveSlot slot = slots[i];
		slot.delete();
	}

	public SaveSlot getWaitingSlot() {
		for (SaveSlot slot : slots) {
			if (slot.isEmpty()) {
				return slot;
			}
		}
		return null;
	}

	public SaveSlot createNewSave() {
		for (SaveSlot slot : slots) {
			if (slot.isEmpty()) {
				slot.createFile();
				return slot;
			}
		}
		return null;
	}

	public SaveSlot getSaveSlot(int number) {
		if(number >= slots.length){
			return null;
		}
		return slots[number];
	}

	private void initSavesFolder() {
		if (!SAVES_FOLDER.exists()) {
			SAVES_FOLDER.mkdir();
		}
	}

	private Map<Integer, String> getExistingFiles() {
		Map<Integer, String> existingFiles = new HashMap<Integer, String>();
		for (File file : SAVES_FOLDER.listFiles()) {
			try {
				String[] fileNameData = file.getName().split("-");
				if (fileNameData[0].equals(SAVE_FILE_NAME)) {
					int number = Integer.parseInt(fileNameData[1]);
					String name = fileNameData[2].replaceAll("_", " ");
					existingFiles.put(number, name.split("\\.")[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Invalid save file name: " + file.getName());
			}
		}
		return existingFiles;
	}

}
