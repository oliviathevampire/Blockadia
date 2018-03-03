package net.thegaminghuskymc.sandboxgame.world.storage;

import net.thegaminghuskymc.sandboxgame.util.datafix.DataFixer;
import net.thegaminghuskymc.sandboxgame.util.datafix.FixTypes;
import net.thegaminghuskymc.sandboxgame.world.MinecraftException;
import net.thegaminghuskymc.sandboxgame.world.WorldProvider;
import net.thegaminghuskymc.sandboxgame.world.chunk.storage.IChunkLoader;
import net.thegaminghuskymc.sandboxgame.world.gen.structure.template.TemplateManager;
import net.thegaminghuskymc.sandboxgame.nbt.CompressedStreamTools;
import net.thegaminghuskymc.sandboxgame.nbt.NBTTagCompound;
import net.thegaminghuskymc.sandboxgame.server.MinecraftServer;
import net.thegaminghuskymc.sgf.event.ForgeEventFactory;
import net.thegaminghuskymc.sgf.fml.common.FMLCommonHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.*;

public class SaveHandler implements ISaveHandler, IPlayerFileData {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final DataFixer dataFixer;
    /**
     * The directory in which to save world data.
     */
    private final File worldDirectory;
    /**
     * The directory in which to save player data.
     */
    private final File playersDirectory;
    private final File mapDataDir;
    /**
     * The time in milliseconds when this field was initialized. Stored in the session lock file.
     */
    private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
    /**
     * The directory name of the world
     */
    private final String saveDirectoryName;
    private final TemplateManager structureTemplateManager;

    public SaveHandler(File p_i46648_1_, String saveDirectoryNameIn, boolean p_i46648_3_, DataFixer dataFixerIn) {
        this.dataFixer = dataFixerIn;
        this.worldDirectory = new File(p_i46648_1_, saveDirectoryNameIn);
        this.worldDirectory.mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "playerdata");
        this.mapDataDir = new File(this.worldDirectory, "data");
        this.mapDataDir.mkdirs();
        this.saveDirectoryName = saveDirectoryNameIn;

        if (p_i46648_3_) {
            this.playersDirectory.mkdirs();
            this.structureTemplateManager = new TemplateManager((new File(this.worldDirectory, "structures")).toString(), dataFixerIn);
        } else {
            this.structureTemplateManager = null;
        }

        this.setSessionLock();
    }

    /**
     * Creates a session lock file for this process
     */
    private void setSessionLock() {
        try {
            File file1 = new File(this.worldDirectory, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

            try {
                dataoutputstream.writeLong(this.initializationTime);
            } finally {
                dataoutputstream.close();
            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    /**
     * Gets the File object corresponding to the base directory of this world.
     */
    public File getWorldDirectory() {
        return this.worldDirectory;
    }

    /**
     * Checks the session lock to prevent save collisions
     */
    public void checkSessionLock() throws MinecraftException {
        try {
            File file1 = new File(this.worldDirectory, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

            try {
                if (datainputstream.readLong() != this.initializationTime) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            } finally {
                datainputstream.close();
            }
        } catch (IOException var7) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }

    /**
     * initializes and returns the chunk loader for the specified world provider
     */
    public IChunkLoader getChunkLoader(WorldProvider provider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    /**
     * Loads and returns the world info
     */
    @Nullable
    public WorldInfo loadWorldInfo() {
        File file1 = new File(this.worldDirectory, "level.dat");

        if (file1.exists()) {
            WorldInfo worldinfo = SaveFormatOld.loadAndFix(file1, this.dataFixer, this);

            if (worldinfo != null) {
                return worldinfo;
            }
        }

        FMLCommonHandler.instance().confirmBackupLevelDatUse(this);
        file1 = new File(this.worldDirectory, "level.dat_old");
        return file1.exists() ? SaveFormatOld.loadAndFix(file1, this.dataFixer, this) : null;
    }

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    public void saveWorldInfoWithPlayer(WorldInfo worldInformation, @Nullable NBTTagCompound tagCompound) {
        NBTTagCompound nbttagcompound = worldInformation.cloneNBTCompound(tagCompound);
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        nbttagcompound1.setTag("Data", nbttagcompound);

        FMLCommonHandler.instance().handleWorldDataSave(this, worldInformation, nbttagcompound1);

        try {
            File file1 = new File(this.worldDirectory, "level.dat_new");
            File file2 = new File(this.worldDirectory, "level.dat_old");
            File file3 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));

            if (file2.exists()) {
                file2.delete();
            }

            file3.renameTo(file2);

            if (file3.exists()) {
                file3.delete();
            }

            file1.renameTo(file3);

            if (file1.exists()) {
                file1.delete();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * used to update level.dat from old format to MCRegion format
     */
    public void saveWorldInfo(WorldInfo worldInformation) {
        this.saveWorldInfoWithPlayer(worldInformation, (NBTTagCompound) null);
    }

    /**
     * Writes the player data to disk from the specified PlayerEntityMP.
     */
    public void writePlayerData(EntityPlayer player) {
        try {
            NBTTagCompound nbttagcompound = player.writeToNBT(new NBTTagCompound());
            File file1 = new File(this.playersDirectory, player.getCachedUniqueIdString() + ".dat.tmp");
            File file2 = new File(this.playersDirectory, player.getCachedUniqueIdString() + ".dat");
            CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));

            if (file2.exists()) {
                file2.delete();
            }

            file1.renameTo(file2);
            ForgeEventFactory.firePlayerSavingEvent(player, this.playersDirectory, player.getUniqueID().toString());
        } catch (Exception var5) {
            LOGGER.warn("Failed to save player data for {}", (Object) player.getName());
        }
    }

    /**
     * Reads the player data from disk into the specified PlayerEntityMP.
     */
    @Nullable
    public NBTTagCompound readPlayerData(EntityPlayer player) {
        NBTTagCompound nbttagcompound = null;

        try {
            File file1 = new File(this.playersDirectory, player.getCachedUniqueIdString() + ".dat");

            if (file1.exists() && file1.isFile()) {
                nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
            }
        } catch (Exception var4) {
            LOGGER.warn("Failed to load player data for {}", (Object) player.getName());
        }

        if (nbttagcompound != null) {
            player.readFromNBT(this.dataFixer.process(FixTypes.PLAYER, nbttagcompound));
        }

        ForgeEventFactory.firePlayerLoadingEvent(player, playersDirectory, player.getUniqueID().toString());
        return nbttagcompound;
    }

    public IPlayerFileData getPlayerNBTManager() {
        return this;
    }

    /**
     * Returns an array of usernames for which player.dat exists for.
     */
    public String[] getAvailablePlayerDat() {
        String[] astring = this.playersDirectory.list();

        if (astring == null) {
            astring = new String[0];
        }

        for (int i = 0; i < astring.length; ++i) {
            if (astring[i].endsWith(".dat")) {
                astring[i] = astring[i].substring(0, astring[i].length() - 4);
            }
        }

        return astring;
    }

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    public void flush() {
    }

    /**
     * Gets the file location of the given map
     */
    public File getMapFileFromName(String mapName) {
        return new File(this.mapDataDir, mapName + ".dat");
    }

    public TemplateManager getStructureTemplateManager() {
        return this.structureTemplateManager;
    }

    public NBTTagCompound getPlayerNBT(EntityPlayerMP player) {
        try {
            File file1 = new File(this.playersDirectory, player.getUniqueID().toString() + ".dat");

            if (file1.exists() && file1.isFile()) {
                return CompressedStreamTools.readCompressed(new FileInputStream(file1));
            }
        } catch (Exception exception) {
            LOGGER.warn("Failed to load player data for " + player.getName());
        }
        return null;
    }
}