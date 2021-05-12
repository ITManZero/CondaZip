package it.archiver.RegMenus;

import it.archiver.Controller_Files.Controller_send_wait_stage;
import it.archiver.DB;
import it.archiver.FakeMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ShareArchive extends ContextMenu {
    private Controller_send_wait_stage sendWaitController;
    private static String[] args;
    private static final DB db = DB.getDBFile();

    public static void main(String[] args) throws IOException {
        stageFileName = "/View_Files/FXML_Files/send_wait_stage.fxml";
        if (db.getTheme() == 20)
            cssFileName = "/View_Files/Css_Files/Light/Style_wait_stage.css";
        else
            cssFileName = "/View_Files/Css_Files/Dark/Style_wait_stage.css";
        ShareArchive.args = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(stageFileName));
        Parent root_sendWait = loader.load();
        sendWaitController = loader.getController();
        Scene scene_sendWait = new Scene(root_sendWait);
        scene_sendWait.setFill(new Color(0.0, 0.0, 0.0, 0.1));
        scene_sendWait.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
        stage.setScene(scene_sendWait);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(0.0);
        stage.setY(0.0);
        if (args == null) System.exit(0);
        sendWaitController.setSelectedFiles(FakeMain.parseFileFromArgs(args));
        sendWaitController.loading();
        stage.show();
    }
}
