package it.archiver.RegMenus;

import it.archiver.ArchiveFormat.ArchiveFormat;
import it.archiver.Controller_Files.Controller_extract_stage;
import it.archiver.DB;
import it.archiver.FakeMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExtractFiles extends ContextMenu {

    private Controller_extract_stage controller;
    private static String[] args;
    private static final DB db = DB.getDBFile();

    public static void main(String[] args) {
        ArchiveFormat.setDefaultPath(db.getDesPath());

        stageFileName = "/View_Files/FXML_Files/extract_stage.fxml";
        if (db.getTheme() == 20)
            cssFileName = "/View_Files/Css_Files/Light/Style_extract_stage.css";
        else
            cssFileName = "/View_Files/Css_Files/Dark/Style_extract_stage.css";
        ExtractFiles.args = args;
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

        controller.setFile(FakeMain.parseFileFromArgs(args)[0]);
        stage.show();
    }
}
