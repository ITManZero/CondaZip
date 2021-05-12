package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_extract_stage;

public class OpenExtractionOutputPath extends Options<Model_extract_stage> {

    public OpenExtractionOutputPath(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    @Override
    public void executeOrder(Model_extract_stage model) {
        try {
            Runtime.getRuntime().exec("explorer "+model.getDesPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
