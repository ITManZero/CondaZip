package it.archiver.ArchiveFormat;

import it.archiver.Controller_Files.Controller_Process;
import it.archiver.Structure.Tar.CreateTarFile;
import it.archiver.Structure.Tar.ExtractTarFile;

import java.io.File;
import java.io.IOException;

public class TarFormat extends ArchiveFormat {


    public TarFormat() {
        super();
    }

    public TarFormat(int mode) {
        super(mode);
    }

    @Override
    public void archive(Controller_Process controller, File... files) throws IOException {
        CreateTarFile createTarFile = new CreateTarFile(outputFileName, usePhotosCompression, mode, desPath,controller,files);
        setOutputFileName(createTarFile.getOutputFileName());
    }
    @Override
    public void extract(String inputFilePath) throws IOException {
        new ExtractTarFile(inputFilePath, desPath);
    }

}
