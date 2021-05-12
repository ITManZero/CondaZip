package it.archiver.FileChooser.List;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;


public class ListTool {
    private String[] filterExtensions;

    public ObservableList
    fill_ListView(String path) {
        ObservableList items = FXCollections.observableArrayList();

        String ext = null;
        for (File file : new File(path).listFiles()) {
            if (filterExtensions == null) {
                items.add(file);
                continue;
            }
            if (file.getName().equals("desktop.ini")) continue;
            if (filterExtensions[0].equals(".*")) items.add(file);
            else if (filterExtensions[0].equals(".filesOnly") && !file.isDirectory())
                items.add(file);
            else {
                if (!file.isDirectory()) {
                    ext = file.getName().substring(file.getName().lastIndexOf('.'));
                    for (String extension : filterExtensions)
                        if (extension.equals(ext)) {
                            items.add(file);
                            break;
                        }
                }
                if (filterExtensions[0].equals("Dir") && file.isDirectory()) items.add(file);
            }
        }
        return items;
    }

    public void setFilterExtensions(String[] filterExtensions) {
        this.filterExtensions = filterExtensions;
    }
}


