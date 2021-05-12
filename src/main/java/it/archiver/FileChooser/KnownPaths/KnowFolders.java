package it.archiver.FileChooser.KnownPaths;

import com.sun.jna.platform.win32.Shell32Util;



public enum KnowFolders {
    Desktop("Desktop", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Desktop)),
    Documents("Documents", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Documents)),
    Download("Download", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Downloads)),
    Music("Music", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Music)),
    Pictures("Pictures", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Pictures)),
    Videos("Videos", Shell32Util.getKnownFolderPath(com.sun.jna.platform.win32.KnownFolders.FOLDERID_Videos));
    //User(System.getProperty("user.name"), "C:/Users/" + System.getProperty("user.name"));

    String name;
    String path;

    KnowFolders(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
