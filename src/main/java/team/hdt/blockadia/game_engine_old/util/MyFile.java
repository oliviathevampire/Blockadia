package team.hdt.blockadia.game_engine_old.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MyFile {

    private String path;
    private String name;

    public MyFile(String path) {
        this.path = path;
        String[] dirs = path.split(FileUtils.FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public MyFile(String... paths) {
        this.path = "";
        for (String part : paths) {
            this.path += (FileUtils.FILE_SEPARATOR + part);
        }
        String[] dirs = path.split(FileUtils.FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public MyFile(MyFile file, String subFile) {
        this.path = file.path + FileUtils.FILE_SEPARATOR + subFile;
        this.name = subFile;
    }

    public MyFile(MyFile file, String... subFiles) {
        this.path = file.path;
        for (String part : subFiles) {
            this.path += (FileUtils.FILE_SEPARATOR + part);
        }
        String[] dirs = path.split(FileUtils.FILE_SEPARATOR);
        this.name = dirs[dirs.length - 1];
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return getPath();
    }

    public InputStream getInputStream() {
        return this.getClass().getResourceAsStream(path);
    }

    public URL getUrl() {
        return this.getClass().getResource(path);
    }

    public BufferedReader getReader() {
        try {
            InputStreamReader isr = new InputStreamReader(getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            return reader;
        } catch (Exception e) {
            System.err.println("Couldn't get reader for " + path);
            throw e;
        }
    }

    public String getName() {
        return name;
    }

}
