package team.hdt.blockadia.game_engine_old.client.saves;

import team.hdt.blockadia.game_engine_old.common.gameManaging.UserConfigs;
import team.hdt.blockadia.game_engine_old.util.BinaryReader;
import team.hdt.blockadia.game_engine_old.util.BinaryWriter;
import team.hdt.blockadia.game_engine_old.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class SaveSlot {

    private String name;
    private int number;
    private File saveFile;
    private SaveSlotInfo info;
    private boolean corrupt = false;

    protected SaveSlot(int number) {
        this.number = number;
        generateDefaultName();
        setUpFile();
        loadInfo();
        UserConfigs.checkCorruption(this);
    }

    protected SaveSlot(int number, String name) {
        this.number = number;
        this.name = name;
        setUpFile();
        loadInfo();
        UserConfigs.checkCorruption(this);
    }

    public void setCorrupt() {
        corrupt = true;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        File oldFile = saveFile;
        setUpFile();
        FileUtils.renameFile(oldFile, saveFile);
    }

    public BinaryWriter getWriter() {
        BinaryWriter writer = new BinaryWriter(saveFile);
        writer.writeBoolean(corrupt);
        info.save(writer);
        return writer;
    }

    public BinaryReader getReader() throws Exception {
        BinaryReader reader = new BinaryReader(saveFile);
        reader.readBoolean();
        info.load(reader);
        return reader;
    }

    public boolean isCorrupt() {
        return corrupt;
    }

    public boolean isEmpty() {
        return !saveFile.exists();
    }

    public void delete() {
        FileUtils.deleteFile(saveFile);
        info = null;
        corrupt = false;
        generateDefaultName();
        setUpFile();
    }

    public SaveSlotInfo getInfo() {
        return info;
    }

    protected void createFile() {
        if (isEmpty()) {
            try {
                saveFile.createNewFile();
                info = new SaveSlotInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void generateDefaultName() {
        this.name = "Save " + (number + 1);
    }

    private void setUpFile() {
        String fileName = name.replaceAll(" ", "_");
        this.saveFile = new File(Saves.SAVES_FOLDER, Saves.SAVE_FILE_NAME + "-" + number + "-" + fileName + Saves.SAVE_FILE_EXT);
    }

    private void loadInfo() {
        if (!isEmpty()) {
            info = new SaveSlotInfo();
            BinaryReader reader = null;
            try {
                reader = new BinaryReader(saveFile);
                reader.readBoolean();
                info.load(reader);
                reader.close();
            } catch (Exception e) {
                reader.close();
                e.printStackTrace();
                this.corrupt = true;
                System.err.println("Couldn't load save slot info for slot " + number);
            }
        }
    }

}
