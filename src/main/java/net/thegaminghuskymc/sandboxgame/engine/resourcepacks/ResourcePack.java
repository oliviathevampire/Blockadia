package net.thegaminghuskymc.sandboxgame.engine.resourcepacks;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;

import java.io.*;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ResourcePack {

    /** the zip file */
    private String pack;
    private String modid;

    public ResourcePack(String modid, String packpath) {
        this.modid = modid.replaceAll("\\s+", "");
        this.pack = packpath;
        Logger.get().log(Logger.Level.DEBUG, "Creating a new AssetsPack", modid, packpath);
    }

    public String getModID() {
        return (this.modid);
    }

    public String getPack() {
        return (this.pack);
    }

    @Override
    public String toString() {
        return (this.modid + " ; " + this.pack);
    }

    /** extract the whole assets pack */
    public void extract() {

        String exportpath = R.getResPath(this.getModID(), "");
        Logger.get().log(Logger.Level.FINE, "Exporting " + this + " to : " + exportpath);
        File dstdir = new File(exportpath);
        if (!dstdir.exists()) {
            dstdir.mkdir();
        }

        File zipfile = new File(this.getPack());
//		this.unzip(zipfile, exportpath);
    }

    private void unzip(File zipfile, String dstdir) {

        try {
            if (!(dstdir.endsWith(File.separator))) {
                dstdir += File.separator;
            }

            // save current file states to the hashmap
            String mappath = dstdir + ".assets_" + this.modid.hashCode();
            HashMap<String, String> map = ResourceManager.getConfigFile(mappath, 1024);
            boolean changed = false;

            // unzip file depending on the hashmap, and update the hashmap
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipfile));
            ZipEntry entry;
            while ((entry = in.getNextEntry()) != null) {
                try {
                    String outfile = dstdir + entry.getName();
                    String entryname = entry.getName();
                    long entrytime = entry.getTime();
                    String value = map.get(entryname);

                    // no need to unzip this file
                    if (value != null && Long.valueOf(value) == entrytime && new File(outfile).exists()) {
                        continue;
                    }

                    Logger.get().log(Logger.Level.DEBUG, "Unzipping", outfile);

                    // the file wasnt into the hashmap or changed, so update it
                    map.put(entryname, String.valueOf(entrytime));
                    changed = true;

                    if (!entry.isDirectory()) {
                        int location = outfile.lastIndexOf('/');
                        if (location >= 0) {
                            new File(outfile.substring(0, location)).mkdirs();
                        }
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outfile));
                        byte[] bytesIn = new byte[4096];
                        int read = 0;
                        while ((read = in.read(bytesIn)) != -1) {
                            bos.write(bytesIn, 0, read);
                        }
                        bos.close();

                    } else {
                        File dir = new File(outfile);
                        dir.mkdirs();
                    }
                    in.closeEntry();
                } catch (IOException exception) {
                    Logger.get().log(Logger.Level.ERROR, "Error while unzipping: " + exception.getLocalizedMessage());
                    continue;
                }
            }
            in.close();

            // export the hashmap
            if (changed) {
                Logger.get().log(Logger.Level.DEBUG,
                        "It looks like the assets files has changed. The .assets file has been updated!", dstdir);
                ResourceManager.exportConfigFile(mappath, map);
            } else {
                Logger.get().log(Logger.Level.DEBUG, "The assets didnt changed", dstdir);
            }

        } catch (IOException exception) {
            Logger.get().log(Logger.Level.ERROR, "Error while unzipping: " + exception.getLocalizedMessage());
        }
    }

}
