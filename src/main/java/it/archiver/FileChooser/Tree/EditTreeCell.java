package it.archiver.FileChooser.Tree;

import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;

public class EditTreeCell extends TreeCell<CustomItem> {

    @Override
    public void startEdit() {
        super.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
    }

    @Override
    public void updateItem(CustomItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setWrapText(true);
            setText(getTreeItem().getValue().getName());
            setGraphic(getTreeItem().getGraphic());


            getDisclosureNode().setOnMouseEntered(mouseEvent -> {
                if (TreeTool.check(getTreeItem()))
                    TreeTool.generateItem(getTreeItem(), getTreeItem().getValue().getPath());
            });

            setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && TreeTool.check(getTreeItem()))
                    TreeTool.generateItem(getTreeItem(), getTreeItem().getValue().getPath());
            });


            setOnMouseEntered(mouseEvent -> {
                if (getTreeItem() != null) setStyle("-fx-background-color: rgba(204,255,255,0.06);");
            });

            setOnMouseExited(mouseEvent -> setStyle("-fx-background-color:transparent;"));


            getTreeView().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                getDisclosureNode().setStyle("-fx-opacity:1.0");
            });
            getTreeView().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                getDisclosureNode().setStyle("-fx-opacity:0.0");
            });

            if (isSelected()) {
                if (getTreeItem().getValue().getPath() != null)
                    setStyle("-fx-background-color: rgba(204,255,255,0.4);");
                getTreeView().getSelectionModel().clearSelection();
            }
        }
    }

}
