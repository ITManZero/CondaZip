module CondaZiP.main {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;
    requires com.sun.jna.platform;
    requires MaterialFX.materialfx.main;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires javafx.swing;
    requires org.apache.logging.log4j;
    exports it.archiver;
    exports it.archiver.RegMenus;
    exports it.archiver.Controller_Files;
}