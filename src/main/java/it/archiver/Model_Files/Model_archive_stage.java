package it.archiver.Model_Files;

import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.ArchiveOptions.Options;
import it.archiver.Controller_Files.Controller_Process;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model_archive_stage {

    private ArchiveFormat archiveFormat;
    private File[] files;
    private Controller_Process controller;

    public Model_archive_stage() {

    }

    public Model_archive_stage(ArchiveFormat archiveFormat, File[] files) {
        this.archiveFormat = archiveFormat;
        this.files = files;
    }

    public void setOutputFileName(String outputFileName) {
        archiveFormat.setOutputFileName(outputFileName);
    }

    public String getOutputFileName() {
        return archiveFormat.getOutputFileName();
    }

    public void setDesPath(String desPath) {
        archiveFormat.setDesPath(desPath);
    }

    public void setArchiveFormat(ArchiveFormat archiveFormat) {
        this.archiveFormat = archiveFormat;
    }

    public void getFormattedFile()  {
        for (Options option : archiveFormat.getStreamOptions())
            if (option.getCheckBoxOption().isSelected())
                option.executeOrder(this);
        try {
            archiveFormat.archive(controller,files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Options option : archiveFormat.getArchiveWOptions())
            if (option.getCheckBoxOption().isSelected())
                option.executeOrder(this);
    }

    public String getDesPath() {
        return archiveFormat.getDesPath();
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public File[] getFiles() {
        return files;
    }

    public void setArchiveWOptions(ArrayList<Options> archiveWOptions) {
        archiveFormat.setArchiveWOptions(archiveWOptions);
    }


    public void setStreamOptions(ArrayList<Options> streamOptions) {
        archiveFormat.setStreamOptions(streamOptions);
    }

    public void setUsePhotosCompression(boolean usePhotosCompression) {
        archiveFormat.setUsePhotosCompression(usePhotosCompression);
    }
    public void setController(Controller_Process controller) { this.controller = controller;}
    public void setMode(int mode) {
        archiveFormat.setMode(mode);
    }
}
