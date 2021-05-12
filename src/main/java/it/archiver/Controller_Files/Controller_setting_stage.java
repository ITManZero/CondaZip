package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.DB;
import it.archiver.FileChooser.KnownPaths.KnowFolders;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller_setting_stage implements Initializable {
    private static final DB db = DB.getDBFile();
    @FXML
    public MFXTextField textfield;
    @FXML
    public AnchorPane root;
    @FXML
    public Rectangle sq;

    private double x;
    private double y;
    private int color;
    private String desPath;
    private Stage stageClose;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stageClose = new Stage();
        try {
            textfield.setText(db.getDesPath());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/convert_stage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stageClose.setScene(scene);
            stageClose.initStyle(StageStyle.TRANSPARENT);
            stageClose.setAlwaysOnTop(true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        FadeTransition fd = new FadeTransition(Duration.millis(3000), sq);
        fd.setFromValue(1.0f);
        fd.setToValue(0.3f);
        fd.setCycleCount(Timeline.INDEFINITE);
        fd.setAutoReverse(true);

        TranslateTransition tr = new TranslateTransition(Duration.millis(2000), sq);
        tr.setFromX(5);
        tr.setToX(230);
        tr.setToY(140);
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
    public void press_edit(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        try {
            desPath = directoryChooser.showDialog(null).toString();
        } catch (NullPointerException e) {
            desPath = KnowFolders.Desktop.getPath();
            textfield.setText(ArchiveFormat.getDefaultPath() + File.separator);
        }
        textfield.setText(desPath + File.separator);
    }

    @FXML
    public void press_cancel(ActionEvent event) {

        textfield.setText(ArchiveFormat.getDefaultPath());
        setColor(color);
//TODO: here
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void press_save(ActionEvent event) {
        ArchiveFormat.setDefaultPath(textfield.getText());
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();

        if (color != db.getTheme()) {
            stageClose.show();
            db.setDesPath(textfield.getText());
            db.setTheme(color);
            db.updateDB();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(runnable).start();
        }
    }

    @FXML
    public void press_light(MouseEvent event) {
        setColor(20);
    }

    @FXML
    public void press_dark(MouseEvent event) {

        setColor(10);
    }

    public void setColor(int color) {
        this.color = color;
    }
}
