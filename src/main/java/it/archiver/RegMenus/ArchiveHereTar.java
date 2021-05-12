package it.archiver.RegMenus;

import it.archiver.ArchiveFormat.TarFormat;
import it.archiver.FakeMain;
import it.archiver.Model_Files.Model_archive_stage;

import java.io.File;
import java.util.ArrayList;

public class ArchiveHereTar {

    private static Model_archive_stage model;

    public static void main(String[] args) {
        if (args == null) System.exit(0);

        File[] files = FakeMain.parseFileFromArgs(args);
        model = new Model_archive_stage();
        model.setArchiveFormat(new TarFormat());
        model.setMode(0);
        model.setOutputFileName(files.length == 1 ? files[0].getName() : files[0].getParent().substring(files[0].getParent().lastIndexOf(File.separatorChar)));
        model.setFiles(files);
        model.setDesPath(model.getFiles()[0].getPath().substring(0, model.getFiles()[0].getPath().length() - model.getFiles()[0].getName().length()));
        model.setArchiveWOptions(new ArrayList<>());
        model.setStreamOptions(new ArrayList<>());
        new Thread(() -> {
            model.getFormattedFile();
        }).start();
    }

}
