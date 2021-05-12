package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_archive_stage;

import java.io.IOException;

public class OpenArchiveOutputPath extends Options<Model_archive_stage> {
    public OpenArchiveOutputPath(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_archive_stage model) {

        try {

            Runtime.getRuntime().exec("explorer.exe /select,\"" + model.getDesPath() + model.getOutputFileName() + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
