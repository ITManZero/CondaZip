package it.archiver.FileChooser.List;

import it.archiver.FileChooser.Tree.TreeTool;
import javafx.scene.control.ListCell;

import java.io.File;

public class EditListCell extends ListCell<File> {

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(getItem().getName());
            setGraphic(TreeTool.getIcon(getItem().getPath()));
        }
    }



}
