package it.archiver.RegMenus;

import it.archiver.ArchiveFormat.TarFormat;
import it.archiver.ArchiveFormat.ZipFormat;
import it.archiver.FakeMain;
import it.archiver.Model_Files.Model_extract_stage;

import java.io.File;
import java.util.ArrayList;

public class ExtractHere {

    private static Model_extract_stage model;

    public static void main(String[] args) {
        if (args == null) System.exit(0);

        File[] files = FakeMain.parseFileFromArgs(args);
        model = new Model_extract_stage();
        String ext = files[0].getName().substring(files[0].getName().lastIndexOf('.'));
        if (ext.equals(".tar"))
            model.setArchiveFormat(new TarFormat());
        else if (ext.equals(".zip"))
            model.setArchiveFormat(new ZipFormat());
        model.setFormatFilePath(files[0].getPath());
        model.setDesPath(files[0].getPath().substring(0, files[0].getPath().length() - files[0].getName().length()));
        model.setArchiveWOptions(new ArrayList<>());
        model.setStreamOptions(new ArrayList<>());
        new Thread(() -> {
            model.getOriginalFiles();
        }).start();
    }
}
