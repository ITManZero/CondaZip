package it.archiver.RegMenus;


import it.archiver.DB;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Engine extends ContextMenu {

    private static final DB db = DB.getDBFile();

    public static void main(String[] args) {

        stageFileName = "/View_Files/FXML_Files/main_stage.fxml";
        if (db.getTheme() == 20) {
            cssFileName = "/View_Files/Css_Files/Light/Style_main_stage.css";
        } else {
            cssFileName = "/View_Files/Css_Files/Dark/Style_main_stage.css";
        }
        launch(args);

        System.exit(1);
    }

    @Override
    public void start(Stage stage) throws Exception {
        {
            Parent root = FXMLLoader.load(getClass().getResource(stageFileName));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(cssFileName).toExternalForm());
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        }
    }
}


