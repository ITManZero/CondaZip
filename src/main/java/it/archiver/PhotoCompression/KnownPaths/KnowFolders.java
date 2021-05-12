package it.archiver.PhotoCompression.KnownPaths;

import com.sun.jna.platform.win32.Shell32Util;


public enum KnowFolders {
    Desktop("Desktop", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Desktop));


    String name;
    String path;

    KnowFolders(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
