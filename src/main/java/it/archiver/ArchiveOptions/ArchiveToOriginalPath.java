package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_archive_stage;

public class ArchiveToOriginalPath extends Options<Model_archive_stage> {
    public ArchiveToOriginalPath(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_archive_stage model) {
        model.setDesPath(model.getFiles()[0].getPath().substring(0, model.getFiles()[0].getPath().length() - model.getFiles()[0].getName().length()));
    }
}
