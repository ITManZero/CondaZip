package it.archiver.RegMenus;

import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.Controller_Files.Controller_archive_stage;
import it.archiver.DB;
import it.archiver.FakeMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddToArchiveMenu extends ContextMenu {
    private static final DB db = DB.getDBFile();
    private Controller_archive_stage controller;
    private static String[] args;

    public static void main(String[] args) {
        ArchiveFormat.setDefaultPath(db.getDesPath());
        stageFileName = "/View_Files/FXML_Files/archive_stage.fxml";
        if (db.getTheme() == 20)
            cssFileName = "/View_Files/Css_Files/Light/Style_archive_stage.css";
        else
            cssFileName = "/View_Files/Css_Files/Dark/Style_archive_stage.css";
        AddToArchiveMenu.args = args;
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(stageFileName));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        if (args == null) System.exit(0);
        controller.setSelectedFiles(FakeMain.parseFileFromArgs(args));
        stage.show();
    }
}
