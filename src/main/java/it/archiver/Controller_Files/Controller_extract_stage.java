package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.ArchiveFormat.TarFormat;
import it.archiver.ArchiveFormat.ZipFormat;
import it.archiver.ArchiveOptions.*;
import it.archiver.Model_Files.Model_extract_stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_extract_stage implements Initializable {

    @FXML
    public AnchorPane root;
    @FXML
    public MFXButton browseButton;
    @FXML
    public MFXTextField textField;
    @FXML
    public MFXCheckbox deleteArchivesAfterExtraction;
    @FXML
    public MFXCheckbox openOutputPath;
    @FXML
    public MFXCheckbox extractInNewFolder;
    @FXML
    public MFXCheckbox extractToOriginalPath;
    @FXML
    public ComboBox<String> mode;
    private Model_extract_stage model;
    private String desPath;
    private File file;
    private ArrayList<Options> options;
    private ArrayList<Options> streamOptions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model = new Model_extract_stage();
        options = new ArrayList<>();
        streamOptions = new ArrayList<>();
        textField.setText(ArchiveFormat.getDefaultPath());
        mode.getItems().add("overwrite existing files");
        mode.getItems().add("Auto rename existing files");
        mode.setValue("overwrite existing files");
        options.add(new DeleteArchiveAfter(deleteArchivesAfterExtraction));
        options.add(new OpenExtractionOutputPath(openOutputPath));
        streamOptions.add(new ExtractToOriginalPath(extractToOriginalPath));
        streamOptions.add(new ExtractInNewFolder(extractInNewFolder));
        desPath = ArchiveFormat.getDefaultPath();
    }

    @FXML
    public void click_cancel_button() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        resetFields();
    }

    @FXML
    public void click_ok_button() {
        String ext = file.getName().substring(file.getName().lastIndexOf('.'));
        if (ext.equals(".tar"))
            model.setArchiveFormat(new TarFormat());
        else if (ext.equals(".zip"))
            model.setArchiveFormat(new ZipFormat());
        model.setFormatFilePath(file.getPath());
        model.setDesPath(desPath.charAt(desPath.length() - 1) != File.separatorChar ? desPath + File.separator : desPath);
        model.setArchiveWOptions(options);
        model.setStreamOptions(streamOptions);
        new Thread(() -> {
            model.getOriginalFiles();
            resetFields();
        }).start();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

    }

    @FXML
    public  void extractToOriginalPath_licked()
    {
        if (browseButton.isDisable()) {
            browseButton.setDisable(false);
            textField.setText(desPath.charAt(desPath.length() - 1) != File.separatorChar ? desPath + File.separator : desPath);
        }
        else {
            browseButton.setDisable(true);
            textField.setText(file.getPath().substring(0, file.getPath().length() - file.getName().length()));
        }
    }


    @FXML
    public void click_browse_button() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try {
            desPath = directoryChooser.showDialog(null).toString();
        } catch (NullPointerException e) {
            desPath = ArchiveFormat.getDefaultPath();
            textField.setText(ArchiveFormat.getDefaultPath());
        }
        textField.setText(desPath + File.separator);
    }

    public void resetFields() {
        textField.setText(ArchiveFormat.getDefaultPath());
        desPath = ArchiveFormat.getDefaultPath();
        for (Options option : streamOptions)
            option.getCheckBoxOption().setSelected(false);
        for (Options option : options)
            option.getCheckBoxOption().setSelected(false);
    }

    public void setFile(File file) {
        this.file = file;
    }
}
