package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import it.archiver.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_receive_stage implements Initializable {
    private static final DB db = DB.getDBFile();
    @FXML
    public MaterialDesignIconView replayButton;

    @FXML
    public MFXButton openButton;

    @FXML
    public MFXTextField textField;

    @FXML
    public BorderPane root;

    private Stage receiveWait;
    private Controller_receive_wait_stage receiveController;
    private String desPath;
    private double x;
    private double y;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        receiveWait = new Stage();
        receiveWait.initStyle(StageStyle.TRANSPARENT);
        receiveWait.setX(0.0);
        receiveWait.setY(0.0);
    }

    @FXML
    public void click_replay(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/receive_wait_stage.fxml"));
            Parent root_wait = loader.load();
            receiveController = loader.getController();
            Scene scene_wait = new Scene(root_wait);
            scene_wait.setFill(new Color(0.0, 0.0, 0.0, 0.1));
            if(db.getTheme()==20) {
                scene_wait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_receive_stage.css").toExternalForm());
            } else {
                scene_wait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_receive_stage.css").toExternalForm());
            }
            receiveWait.setScene(scene_wait);

        } catch (IOException e) {
            e.printStackTrace();
        }
        receiveController.setDesPath(desPath);
        receiveController.loading();
        receiveWait.show();

    }

    @FXML
    public void  click_open_in_folder()
    {
        try {
            new ProcessBuilder("explorer.exe", "/select,"+desPath).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDesPath(String desPath) {
        this.desPath = desPath;
    }


    @FXML
    public void press_move(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void press_Drag(MouseEvent event) {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setX(event.getScreenX() - x);
        stage.setY(event.getScreenY() - y);
    }

    public MFXTextField getTextField() {
        return textField;
    }
}
