package it.archiver.ArchiveFormat;

import it.archiver.Controller_Files.Controller_Process;
import it.archiver.Structure.Zip.CreateZipFile;
import it.archiver.Structure.Zip.ExtractZipFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ZipFormat extends ArchiveFormat {

    private static final int Default_Method = 8;
    private int method;

    public ZipFormat() {
        this(Default_Method, false, false);
    }

    public ZipFormat(int method, boolean hasPassword, boolean hasComments) {
        super();
    }

    public ZipFormat(int mode) {
        this(mode, Default_Method, false, false);
    }

    public ZipFormat(int mode, int method, boolean hasPassword, boolean hasComments) {
        super(mode);
    }


    @Override
    public void archive(Controller_Process controller, File... files) throws IOException {
        ArrayList<String> paths = new ArrayList<>();
        for (File file : files)
            paths.add(file.getPath());
        CreateZipFile createZipFile = new CreateZipFile(paths, outputFileName, usePhotosCompression, controller, desPath);
        setOutputFileName(createZipFile.getOutputFileName());
    }

    @Override
    public void extract(String inputFilePath) throws IOException {
        new ExtractZipFile(inputFilePath, desPath);
    }
}
