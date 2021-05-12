package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;

public abstract class Options<T> {
    private MFXCheckbox checkBoxOption;

    public Options(MFXCheckbox checkBoxOption) {
        this.checkBoxOption = checkBoxOption;
    }

    public Options() {

    }


    public abstract void executeOrder(T model);

    public MFXCheckbox getCheckBoxOption() {
        return checkBoxOption;
    }
}
