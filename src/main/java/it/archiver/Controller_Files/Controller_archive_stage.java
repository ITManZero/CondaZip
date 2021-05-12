package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.ArchiveFormat.TarFormat;
import it.archiver.ArchiveFormat.ZipFormat;
import it.archiver.ArchiveOptions.*;
import it.archiver.DB;
import it.archiver.Model_Files.Model_archive_stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller_archive_stage implements Initializable {

    private static final DB db = DB.getDBFile();
    private Model_archive_stage model;
    private File[] selectedFiles;
    private String desPath;
    private ArrayList<Options> options;
    private ArrayList<Options> streamOptions;
    @FXML
    public AnchorPane root;
    @FXML
    public MFXTextField textField;
    @FXML
    public MFXRadioButton ZipRadioButton;
    @FXML
    public MFXRadioButton TarRadioButton;
    @FXML
    public MFXButton browseButton;
    @FXML
    public ComboBox<String> method;
    @FXML
    public ComboBox<String> mode;
    @FXML
    public MFXButton password;
    @FXML
    public Tab comments;
    @FXML
    public MFXCheckbox deleteFilesAfterArchiving;
    @FXML
    public MFXCheckbox openOutputPath;
    @FXML
    public MFXCheckbox usePhotosCompression;
    @FXML
    public MFXCheckbox archiveToOriginalPath;

    private Controller_Process controller;

    private Stage stagePro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model_archive_stage();
        stagePro = new Stage();
        stagePro.initStyle(StageStyle.TRANSPARENT);
        stagePro.setAlwaysOnTop(true);
        options = new ArrayList<>();
        streamOptions = new ArrayList<>();
        method.getItems().add("store");
        method.setValue("store");
        mode.getItems().add("New archive (auto rename)");
        mode.getItems().add("Replace archive (if exists)");
        mode.getItems().add("Update archive (if exists)");
        mode.setValue("New archive (auto rename)");
        password.setDisable(true);
        comments.setDisable(true);
        options.add(new DeleteArchiveAfter(deleteFilesAfterArchiving));
        options.add(new OpenArchiveOutputPath(openOutputPath));
        streamOptions.add(new ArchiveToOriginalPath(archiveToOriginalPath));
        streamOptions.add(new UsePhotosCompression(usePhotosCompression));
        resetFields();
    }

    @FXML
    public void click_cancel_button() {
        Stage stage = (Stage) root.getScene().getWindow();
        stagePro.close();
        stage.close();
        resetFields();
    }

    @FXML
    public void click_ZipRadioButton() {
        method.getItems().clear();
        method.getItems().add("dynamic");
        method.setValue("dynamic");
        password.setDisable(false);
        comments.setDisable(false);
    }

    @FXML
    public void click_TarRadioButton() {
        method.getItems().clear();
        method.getItems().add("store");
        method.setValue("store");
        password.setDisable(true);
        comments.setDisable(true);
    }

    @FXML
    public void click_ok_button() {
        Stage stage = (Stage) root.getScene().getWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/process_stage.fxml"));
            Parent root_Bar = loader.load();
            controller = loader.getController();
            Scene scene = new Scene(root_Bar);
            if (db.getTheme() == 20) {
                scene.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_process.css").toExternalForm());
            } else {
                scene.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_process.css").toExternalForm());
            }
            stagePro.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ZipRadioButton.isSelected())
            model.setArchiveFormat(new ZipFormat());
        else if (TarRadioButton.isSelected())
            model.setArchiveFormat(new TarFormat());


        model.setController(controller);
        model.setMode(mode.getSelectionModel().getSelectedIndex());
        model.setOutputFileName(textField.getText());
        model.setFiles(selectedFiles);
        model.setDesPath(desPath.charAt(desPath.length() - 1) != File.separatorChar ? desPath + File.separator : desPath);
        model.setArchiveWOptions(options);
        model.setStreamOptions(streamOptions);
        new Thread(() -> {
            model.getFormattedFile();
            resetFields();
        }).start();
        stage.close();
        stagePro.show();
        controller.getProgressBar().progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 1.0) {
                stagePro.close();
            }
        });
    }

    @FXML
    public void click_browse_button() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try {
            desPath = directoryChooser.showDialog(null).toString();
        } catch (NullPointerException e) {
            desPath = ArchiveFormat.getDefaultPath();
        }
    }

    @FXML
    public void click_ArchiveToOriginalPath() {
        if (browseButton.isDisable())
            browseButton.setDisable(false);
        else
            browseButton.setDisable(true);

    }

    public void setSelectedFiles(File[] selectedFiles) {
        this.selectedFiles = selectedFiles;
        if (selectedFiles.length == 1) {
            String name = selectedFiles[0].isDirectory() ? selectedFiles[0].getName()
                    : selectedFiles[0].getName().substring(0, selectedFiles[0].getName().lastIndexOf('.'));
            textField.setText(name);
        }
    }

    private void resetFields() {
        textField.setText("outputFilename");
        desPath = ArchiveFormat.getDefaultPath();
        TarRadioButton.setSelected(true);
        for (Options option : streamOptions)
            option.getCheckBoxOption().setSelected(false);
        for (Options option : options)
            option.getCheckBoxOption().setSelected(false);
    }

}
