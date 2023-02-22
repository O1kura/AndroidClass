package com.example.folderreader;

import java.io.File;

public class FileInfo {
    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    boolean isFile;
    File path;

    public FileInfo(boolean isFile, File path){
        this.isFile = isFile;
        this.path = path;
    }
}
