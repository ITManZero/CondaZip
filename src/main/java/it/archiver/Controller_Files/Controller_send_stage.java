package it.archiver.Controller_Files;


import io.github.palexdev.materialfx.controls.MFXButton;
import it.archiver.DB;
import it.archiver.ShareFiles.FileSender;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.animation.PathTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_send_stage implements Initializable {

    private static final DB db = DB.getDBFile();
    private ArrayList<Pair<Socket, String>> users;
    private File[] selectedFiles;
    private Controller_send_wait_stage waitController;
    private Stage sendWait;

    @FXML
    public BorderPane root;
    @FXML
    public MFXButton cancel;
    @FXML
    public MaterialDesignIconView replay;
    @FXML
    public ListView listview;
    @FXML
    public Text name;
    @FXML
    public Text best;
    private double x;
    private double y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listview.setFixedCellSize(50);
        users = new ArrayList<>();
        sendWait = new Stage();
        Path path = new Path();
        path.getElements().add(new MoveTo(0, 0));
        path.getElements().add(new HLineTo(-50));
        path.getElements().add(new VLineTo(-50));
        path.getElements().add(new HLineTo(+100));
        path.getElements().add(new VLineTo(+100));
        path.getElements().add(new HLineTo(-150));
        path.getElements().add(new VLineTo(-150));
        path.getElements().add(new HLineTo(+190));
        path.getElements().add(new VLineTo(+140));
        path.getElements().add(new HLineTo(-1));
        path.getElements().add(new ClosePath());
        PathTransition pathTransitionN = new PathTransition();
        PathTransition pathTransitionB = new PathTransition();
        pathTransitionN.setNode(name);
        pathTransitionN.setDuration(Duration.seconds(10));
        pathTransitionN.setPath(path);
        pathTransitionN.setCycleCount(PathTransition.INDEFINITE);
        pathTransitionB.setNode(best);
        pathTransitionB.setDuration(Duration.seconds(10));
        pathTransitionB.setPath(path);
        pathTransitionB.setCycleCount(PathTransition.INDEFINITE);
        pathTransitionB.play();
        pathTransitionN.play();
        sendWait.initStyle(StageStyle.TRANSPARENT);
        sendWait.setX(0.0);
        sendWait.setY(0.0);
    }

    @FXML
    public void press_cancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
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
    public void click_replay(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/send_wait_stage.fxml"));
            Parent root_wait = loader.load();
            waitController = loader.getController();
            Scene scene_wait = new Scene(root_wait);
            scene_wait.setFill(new Color(0.0, 0.0, 0.0, 0.1));
            if (db.getTheme() == 20) {
                scene_wait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_send_stage.css").toExternalForm());
            } else {
                scene_wait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_send_stage.css").toExternalForm());
            }
            sendWait.setScene(scene_wait);

        } catch (IOException e) {
            e.printStackTrace();
        }
        waitController.setSelectedFiles(selectedFiles);
        waitController.loading();
        sendWait.show();
    }

    @FXML
    public void clicked_select() throws IOException {
        FileSender fileSender = new FileSender(selectedFiles);
        fileSender.openServerSocket();
        fileSender.setFiles(selectedFiles);
        fileSender.setClient(users.get(listview.getSelectionModel().getSelectedIndex()).getKey());
        fileSender.send();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public void setUsers(ArrayList<Pair<Socket, String>> users) {
        listview.getItems().clear();
        ArrayList<String> namesOfClients = new ArrayList<>();
        for (Pair client : users)
            namesOfClients.add((String) client.getValue());
        listview.setItems(FXCollections.observableArrayList(namesOfClients));
        this.users = users;

    }

    public void setSelectedFiles(File[] selectedFiles) {
        this.selectedFiles = selectedFiles;
    }
}
