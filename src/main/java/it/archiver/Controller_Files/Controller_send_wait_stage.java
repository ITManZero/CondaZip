package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import it.archiver.ShareFiles.FileSender;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_send_wait_stage implements Initializable {


    private LoginTask task;
    private Controller_send_stage sendController;


    @FXML
    public AnchorPane rootWait;
    @FXML
    public MFXProgressSpinner progressBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root_send = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/send_stage.fxml"));
            root_send = loader.load();
            sendController = loader.getController();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Parent finalRoot_send = root_send;

        progressBar.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 1.0) {
                progressBar.setVisible(false);
                rootWait.getChildren().clear();
            }
            rootWait.getChildren().add(finalRoot_send);
        });
//        masker.progressProperty().addListener((obs, oldValue, newValue) -> {
//            if (newValue.doubleValue() >= 1.0) {
//                masker.setVisible(false);
//                rootWait.getChildren().clear();
//            }
//                rootWait.getChildren().add(finalRoot_send);
//        });

    }

    public void setSelectedFiles(File[] selectedFiles) {
        sendController.setSelectedFiles(selectedFiles);
    }

    public void loading() {
        task = new LoginTask();
//        masker.progressProperty().bind(task.progressProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        Thread searching = new Thread(task);
        searching.start();
    }

    class LoginTask extends Task<Boolean> {
        private final int timer = 10;
        private FileSender fileSender;

        public LoginTask() {
            fileSender = new FileSender();
            fileSender.openServerSocket();
        }

        @Override
        protected Boolean call() {
            fileSender.setTimer(timer);
            fileSender.searching();
            updateProgress(1, 1);
            sendController.setUsers(fileSender.getClients());
            fileSender.close();
            return true;
        }

    }


}
