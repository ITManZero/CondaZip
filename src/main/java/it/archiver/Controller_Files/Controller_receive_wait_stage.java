package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import it.archiver.ShareFiles.FileReceiver;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_receive_wait_stage implements Initializable {


    private LoginTask task;
    private Controller_receive_stage receiveController;
    private String desPath;

    @FXML
    public AnchorPane rootWait;
    @FXML
    public MFXProgressSpinner progressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root_send = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/receive_stage.fxml"));
            root_send = loader.load();
            receiveController = loader.getController();
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

    }

    public void loading() {
        task = new LoginTask();
        progressBar.progressProperty().bind(task.progressProperty());
        Thread searching = new Thread(task);
        searching.start();
    }

    class LoginTask extends Task<Boolean> {

        private final int timer = 2;
        private FileReceiver fileReceiver;

        public LoginTask() {
            fileReceiver = new FileReceiver(desPath);
        }

        @Override
        protected Boolean call() throws Exception {
            fileReceiver.setTimer(timer);
            fileReceiver.findingServer();
            updateProgress(1, 1);
            if (fileReceiver.isServerFound()) {
                int t = 5;
                receiveController.getTextField().setText("Server Found Waiting Server To send...");
                while (t > 0) {
                    try {
                        Thread.sleep(1000);
                        fileReceiver.receive();
                        receiveController.getTextField().setText("Receiving...");
                        break;

                    } catch (InterruptedException e) {

                    } catch (EOFException e) {
                        t--;
                    }
                }
                receiveController.getTextField().setText("done...");
            }
            fileReceiver.close();
            //Stage stage = (Stage) root_send.getScene().getWindow();
            //stage.close();
            return true;
        }

    }

    public void setDesPath(String desPath) {
        this.desPath = desPath;
        receiveController.setDesPath(desPath);
    }
}
