package it.archiver.ArchiveOptions;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.Model_Files.Model_extract_stage;


import java.io.File;

public class ExtractInNewFolder extends Options<Model_extract_stage> {

    public ExtractInNewFolder(MFXCheckbox checkBoxOption) {
        super(checkBoxOption);
    }

    public ExtractInNewFolder()
    {
    }

    @Override
    public void executeOrder(Model_extract_stage model) {
        model.setDesPath(autoCreateFolder(model.getDesPath()));
    }

    private String autoCreateFolder(String destPath) {
        int copyNum = 1;
        String S = "New Folder";
        String newPath = destPath + S;
        System.out.println(newPath);
        boolean create = new File(newPath + File.separator).mkdir();
        newPath = destPath + S + File.separator;
        while (!create) {
            newPath = destPath + S;
            create = new File(newPath + " (" + copyNum + ")" + File.separator).mkdir();
            newPath = newPath + " (" + copyNum + ")" + File.separator;
            copyNum++;
        }
        return newPath;
    }
}
