package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_extract_stage;


public class DeleteArchiveAfter extends Options<Model_extract_stage> {

    public DeleteArchiveAfter(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_extract_stage model) {
        model.getArchive().delete();
    }
}
