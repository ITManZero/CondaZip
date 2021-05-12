package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_extract_stage;

public class ExtractToOriginalPath extends Options<Model_extract_stage> {

    public ExtractToOriginalPath(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }


    @Override
    public void executeOrder(Model_extract_stage model) {
        model.setDesPath(model.getArchive().getPath().substring(0, model.getArchive().getPath().length() - model.getArchive().getName().length()));
    }
}
