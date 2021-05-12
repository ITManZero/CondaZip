package it.archiver.ArchiveFormat;

import it.archiver.ArchiveOptions.Options;
import it.archiver.Controller_Files.Controller_Process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ArchiveFormat {

    private static String defaultPath;
    protected String desPath;
    protected String outputFileName;
    protected int mode;
    protected boolean usePhotosCompression;
    protected ArrayList<Options> archiveWOptions;
    protected ArrayList<Options> StreamOptions;

    protected ArchiveFormat() {
        outputFileName = "";
        desPath = defaultPath;
        usePhotosCompression = false;
    }

    protected ArchiveFormat(int mode) {
        this();
        this.mode = mode;
    }


    public abstract void archive(Controller_Process controller, File... files) throws IOException;

    public abstract void extract(String inputFilePath) throws IOException;

    public void setDesPath(String desPath) {
        if (desPath != null) this.desPath = desPath;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getDesPath() {
        return desPath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public static String getDefaultPath() {
        return defaultPath;
    }

    public static void setDefaultPath(String defaultPaths) { defaultPath=defaultPaths;}

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    public void setArchiveWOptions(ArrayList<Options> archiveWOptions) {
        this.archiveWOptions = archiveWOptions;
    }

    public ArrayList<Options> getArchiveWOptions() {
        return archiveWOptions;
    }

    public void setStreamOptions(ArrayList<Options> streamOptions) {
        StreamOptions = streamOptions;
    }

    public ArrayList<Options> getStreamOptions() {
        return StreamOptions;
    }

    public boolean isUsePhotosCompression() {
        return usePhotosCompression;
    }

    public void setUsePhotosCompression(boolean usePhotosCompression) {
        this.usePhotosCompression = usePhotosCompression;
    }
}
