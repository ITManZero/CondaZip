package it.archiver.FileChooser.Tree;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class TreeTool {

    private ArrayList<String> filterExtension;

    public static Thread checkItems(TreeItem<CustomItem> currentItem) {
        Runnable runnable = () -> {
            File[] fileList = thereIsFoldersIn(currentItem.getValue().getPath());
            if (fileList != null) {
                if (fileList.length != currentItem.getValue().getFileNumber()) {
                    currentItem.getChildren().clear();
                    generateItem(currentItem, currentItem.getValue().getPath());
                }
            }
        };

        currentItem.getValue().setItemThread(null);
        Thread currentThread = new Thread(runnable);
        currentThread.start();
        return currentThread;
    }

    public static void generateItem(TreeItem<CustomItem> currentItem, String path) {
        File[] fileList = thereIsFoldersIn(path);
        if (fileList == null)
            return;
        if (currentItem.getChildren().size() > 0)
            currentItem.getChildren().remove(0);
        TreeItem<CustomItem> newItem;
        for (File file : fileList) {
            newItem = new TreeItem(new CustomItem(file.getName(), file.getPath()), getIcon(file.getAbsolutePath()));
            if (thereIsFoldersIn(file.getAbsolutePath()) != null)
                newItem.getChildren().add(new TreeItem());
            currentItem.getChildren().add(newItem);
        }
        currentItem.getValue().setGotChildren(true);
        currentItem.getValue().setFileNumber(fileList.length);
    }

    public static File[] thereIsFoldersIn(String path) {
        ArrayList<File> arrayList = new ArrayList<>();
        File file = new File(path);
        File[] listFile = file.listFiles();
        if (listFile == null)
            return null;
        for (File currentFile : listFile)
            if (currentFile.isDirectory() && currentFile.getAbsoluteFile().isHidden() != true && !currentFile.getAbsolutePath().equals("C:\\Windows\\WinSxS"))
                arrayList.add(currentFile);
        File[] fileList = new File[arrayList.size()];
        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = arrayList.get(i);
        }
        return (fileList.length == 0) ? null : fileList;
    }

    public static ImageView getIcon(String path) {
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(new File(path));
        ImageIcon swingImageIcon = (ImageIcon) icon;
        Image awtImage = swingImageIcon.getImage();
        BufferedImage bufferedImage;
        if (awtImage instanceof BufferedImage)
            bufferedImage = (BufferedImage) awtImage;
        else {
            bufferedImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.drawImage(awtImage, 0, 0, null);
            graphics.dispose();
        }
        return new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    public static boolean check(TreeItem<CustomItem> currentItem) {

        return (currentItem != null && currentItem.getValue().getPath() != null && !currentItem.getValue().isGotChildren() && currentItem.getParent().getValue().getName() != "Quick Access") ? true : false;
    }
}
