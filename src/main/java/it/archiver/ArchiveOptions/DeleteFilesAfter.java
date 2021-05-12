package it.archiver.ArchiveOptions;


import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_archive_stage;

import java.io.File;

public class DeleteFilesAfter extends Options<Model_archive_stage> {
    public DeleteFilesAfter(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_archive_stage model) {
        for (File file : model.getFiles())
            deleteFiles(file);
    }

    private void deleteFiles(File file) {
        File[] contents = file.listFiles();
        if (contents != null)
            for (File f : contents)
                deleteFiles(f);
        file.delete();
    }

}
