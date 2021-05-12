package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import it.archiver.DB;
import it.archiver.FileChooser.Constants;
import javafx.animation.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Share_Stage implements Initializable {
    private static final DB db = DB.getDBFile();
    @FXML
    public MFXButton sendButton;
    @FXML
    public MFXButton receiveButton;
    @FXML
    public Rectangle sq;
    @FXML
    public MFXProgressBar progress;
    @FXML
    public Pane rootPane;
    @FXML
    public Text testAfterProgress;
    @FXML
    public AnchorPane root;

    private Stage fileChooser;
    private String desPath;
    private static final String defaultPath = "C:\\Users\\" + System.getProperty("user.name", "") + "\\Desktop\\";
    private Controller_file_chooser_stage fileChooserController;
    private Stage receiveWait;
    private Controller_receive_wait_stage receiveWaitController;
    private double x;
    private double y;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        desPath = defaultPath;
        fileChooser = new Stage();
        receiveWait = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/file_chooser_stage.fxml"));
            Parent root_fileChooser = loader.load();
            fileChooserController = loader.getController();
            Scene scene_fileChooser = new Scene(root_fileChooser);
            if (db.getTheme() == 20) {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_file_chooser.css").toExternalForm());
            } else {
                scene_fileChooser.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_file_chooser.css").toExternalForm());
            }
            fileChooser.setScene(scene_fileChooser);
            fileChooser.initStyle(StageStyle.TRANSPARENT);


            loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/receive_wait_stage.fxml"));
            Parent root_receive = loader.load();
            receiveWaitController = loader.getController();
            Scene scene_receive = new Scene(root_receive);
            if (db.getTheme() == 20) {
                scene_receive.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_receive_stage.css").toExternalForm());
            } else {
                scene_receive.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_receive_stage.css").toExternalForm());
            }
            scene_receive.setFill(new Color(0.0, 0.0, 0.0, 0.1));
            receiveWait.setScene(scene_receive);
            receiveWait.initStyle(StageStyle.TRANSPARENT);
            receiveWait.setX(0.0);
            receiveWait.setY(0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i <= 100; i += 5) {
                    updateProgress(i, 100);
                    Thread.sleep(50);
                }
                updateProgress(-1, 100);
                return null;
            }
        };
        new Thread(task).start();
        progress.progressProperty().bind(task.progressProperty());

        progress.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 1.0) {
                sendButton.setDisable(false);
                receiveButton.setDisable(false);
                rootPane.getChildren().remove(progress);
                testAfterProgress.setStyle("-fx-opacity:1.0;");
            }
        });
        FadeTransition fd = new FadeTransition(Duration.millis(3000), sq);
        fd.setFromValue(1.0f);
        fd.setToValue(0.3f);
        fd.setCycleCount(Timeline.INDEFINITE);
        fd.setAutoReverse(true);

        TranslateTransition tr = new TranslateTransition(Duration.millis(2000), sq);
        tr.setFromX(5);
        tr.setToX(260);
        tr.setToY(-150);
        tr.setCycleCount(Timeline.INDEFINITE);
        tr.setAutoReverse(true);


        RotateTransition rt = new RotateTransition(Duration.millis(3000), sq);
        rt.setByAngle(180f);
        rt.setCycleCount(Timeline.INDEFINITE);
        rt.setAutoReverse(true);

        ScaleTransition st = new ScaleTransition(Duration.millis(2000), sq);
        st.setToX(2f);
        st.setToY(2f);
        st.setCycleCount(Timeline.INDEFINITE);
        st.setAutoReverse(true);

        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(fd, tr, rt, st);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();

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
    public void press_close(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clicked_sendButton() {
        fileChooserController.setType(Constants.shareType);
        fileChooserController.setTitle("share FileChooser");
        fileChooserController.setFilterExtensions(new String[]{".filesOnly"});
        fileChooserController.fillListViewWithDefaultItems();
        fileChooser.show();
    }

    @FXML
    public void clicked_receiveButton() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try {
            desPath = directoryChooser.showDialog(null).toString();
            desPath = desPath.charAt(desPath.length() - 1) != File.separatorChar ? desPath + File.separator : desPath;
            receiveWaitController.setDesPath(desPath);
            receiveWaitController.loading();
            receiveWait.show();
        } catch (NullPointerException e) {
            desPath = defaultPath;
            desPath = desPath.charAt(desPath.length() - 1) != File.separatorChar ? desPath + File.separator : desPath;
            receiveWaitController.setDesPath(desPath);
        }
    }

}
