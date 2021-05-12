package it.archiver.Controller_Files;

import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.DB;
import it.archiver.FileChooser.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_main_stage implements Initializable {
    @FXML
    public AnchorPane root;


    private double x;
    private double y;
    private Stage archiveFileChooser;
    private Stage extractFileChooser;
    private Stage shareFiles;
    private Stage settings;
    private Controller_file_chooser_stage archiveFileChooserController;
    private Controller_file_chooser_stage extractFileChooserController;
    private Controller_setting_stage settingController;
    private static final DB db = DB.getDBFile();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArchiveFormat.setDefaultPath(db.getDesPath());
        archiveFileChooser = new Stage();
        extractFileChooser = new Stage();
        settings = new Stage();
        shareFiles = new Stage();

        archiveFileChooser.initStyle(StageStyle.TRANSPARENT);
        extractFileChooser.initStyle(StageStyle.TRANSPARENT);
        shareFiles.initStyle(StageStyle.TRANSPARENT);
        settings.initStyle(StageStyle.TRANSPARENT);
    }

    @FXML
    public void click_close() {
        System.exit(0);
    }

    @FXML
    public void click_min() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void press_move(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void drag_move(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    @FXML
    public void open_archive() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/file_chooser_stage.fxml"));
            Parent root_fileChooser = loader.load();
            archiveFileChooserController = loader.getController();
            Scene scene_fileChooser = new Scene(root_fileChooser);
            if (db.getTheme() == 20) {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_file_chooser.css").toExternalForm());
            } else {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_file_chooser.css").toExternalForm());
            }
            archiveFileChooser.setScene(scene_fileChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!archiveFileChooser.isShowing()) {
            archiveFileChooserController.setType(Constants.archiveType);
            archiveFileChooserController.setTitle("Archive FileChooser");
            archiveFileChooserController.setFilterExtensions(new String[]{".*"});
            archiveFileChooserController.fillListViewWithDefaultItems();
            archiveFileChooser.show();
        }
    }

    @FXML
    public void open_extract() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/file_chooser_stage.fxml"));
            Parent root_fileChooser = loader.load();
            extractFileChooserController = loader.getController();
            Scene scene_fileChooser = new Scene(root_fileChooser);
            if (db.getTheme() == 20) {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_file_chooser.css").toExternalForm());
            } else {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_file_chooser.css").toExternalForm());
            }
            extractFileChooser.setScene(scene_fileChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!extractFileChooser.isShowing()) {
            extractFileChooserController.setType(Constants.extractType);
            extractFileChooserController.setTitle("Extract FileChooser");
            extractFileChooserController.setFilterExtensions(new String[]{"Dir", ".tar", ".zip"});
            extractFileChooserController.fillListViewWithDefaultItems();
            extractFileChooser.show();
        }
    }

    @FXML
    public void open_share() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/Share_Stage.fxml"));
            Parent root_fileChooser = loader.load();
            Scene scene_fileChooser = new Scene(root_fileChooser);
            if (db.getTheme() == 20) {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_process_for_share.css").toExternalForm());
            } else {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_process_for_share.css").toExternalForm());
            }
            shareFiles.setScene(scene_fileChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setIconified(true);
        shareFiles.show();
    }

    @FXML
    public void open_setting() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/setting_stage.fxml"));
            Parent root_fileChooser = loader.load();
            settingController = loader.getController();
            Scene scene_fileChooser = new Scene(root_fileChooser);
            if (db.getTheme() == 20) {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_setting.css").toExternalForm());
            } else {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_setting.css").toExternalForm());
            }
            settings.setScene(scene_fileChooser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        settings.show();
    }
}
