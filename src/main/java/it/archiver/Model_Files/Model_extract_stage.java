package it.archiver.Model_Files;

import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.ArchiveOptions.Options;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Model_extract_stage {
    private ArchiveFormat archiveFormat;
    private String formatFilePath;

    public Model_extract_stage() {

    }

    public Model_extract_stage(ArchiveFormat archiveFormat, String formatFilePath) {
        this.archiveFormat = archiveFormat;
        this.formatFilePath = formatFilePath;
    }


    public void setDesPath(String desPath) {
        try {
            archiveFormat.setDesPath(desPath);
        } catch (NullPointerException e) {
            System.out.println("format Not Supported");
        }

    }

    public void setArchiveFormat(ArchiveFormat archiveFormat) {
        this.archiveFormat = archiveFormat;
    }

    public void setFormatFilePath(String formatFilePath) {
        this.formatFilePath = formatFilePath;
    }

    public void getOriginalFiles() {
        for (Options option : archiveFormat.getStreamOptions())
            if (option.getCheckBoxOption().isSelected())
                option.executeOrder(this);
        try {
            archiveFormat.extract(formatFilePath);
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

    public void setArchiveWOptions(ArrayList<Options> archiveWOptions) {
        archiveFormat.setArchiveWOptions(archiveWOptions);
    }


    public void setStreamOptions(ArrayList<Options> streamOptions) {
        archiveFormat.setStreamOptions(streamOptions);
    }

    public File getArchive() {
        return new File(formatFilePath);
    }

}
