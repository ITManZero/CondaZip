package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_archive_stage;

public class UsePhotosCompression extends Options<Model_archive_stage> {

    public UsePhotosCompression(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_archive_stage model) {
        model.setUsePhotosCompression(true);
    }
}
