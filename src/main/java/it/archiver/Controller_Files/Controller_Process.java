package it.archiver.Controller_Files;


import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller_Process implements Initializable {

    @FXML
    public MFXProgressBar progressBar;

    @FXML
    public Text text;

    @FXML
    public Rectangle sq;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition fd = new FadeTransition(Duration.millis(3000), sq);
        fd.setFromValue(1.0f);
        fd.setToValue(0.3f);
        fd.setCycleCount(Timeline.INDEFINITE);
        fd.setAutoReverse(true);

        TranslateTransition tr = new TranslateTransition(Duration.millis(2000), sq);
        tr.setFromX(5);
        tr.setToX(270);
        tr.setToY(30);
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

    public MFXProgressBar getProgressBar() {
        return progressBar;
    }

    public Text getText() {
        return text;
    }
}
