package it.archiver.RegMenus;

import javafx.application.Application;
import javafx.stage.Stage;


public abstract class ContextMenu extends Application {

    protected static String stageFileName;
    protected static String cssFileName;

    @Override
    public abstract void start(Stage stage) throws Exception;

}
