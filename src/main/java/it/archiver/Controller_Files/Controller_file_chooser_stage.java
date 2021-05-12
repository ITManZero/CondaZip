package it.archiver.Controller_Files;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.archiver.DB;
import it.archiver.FileChooser.Constants;
import it.archiver.FileChooser.KnownPaths.KnowFolders;
import com.sun.jna.platform.win32.KnownFolders;
import com.sun.jna.platform.win32.Shell32Util;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import it.archiver.FileChooser.List.CustomListViewSkin;
import it.archiver.FileChooser.List.EditListCell;
import it.archiver.FileChooser.List.ListTool;
import it.archiver.FileChooser.Tree.CustomItem;
import it.archiver.FileChooser.Tree.EditTreeCell;
import it.archiver.FileChooser.Tree.TreeTool;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller_file_chooser_stage implements Initializable {

    private static final DB db = DB.getDBFile();

    /**
     * Variables for tree_view
     */

    @FXML
    public TreeView<CustomItem> tree_view;
    private TreeItem<CustomItem> roots;
    private TreeItem<CustomItem> QuickAccessRoot;
    private TreeItem<CustomItem> ThisPcRoot;

    /**
     * Variables for list_view
     */
    @FXML
    public ListView list_view;
    private String defaultPath;
    private ListTool listTool;

    @FXML
    public MFXTextField currentPath;
    @FXML
    public MFXButton forwardButton;

    @FXML
    public MFXButton backwardButton;

    @FXML
    public MFXButton cancel_button;

    @FXML
    public MFXButton select_button;

    @FXML
    public Label Title;


    @FXML
    public AnchorPane root;

    private ObservableList<File> selectedItems;
    private static ArrayList<String> pathList;
    private static int indexOfCurrentPath;
    private static TreeItem<CustomItem> lastItemSelectedInTree;
    private boolean isCalled;
    private Stage archive;
    private Controller_archive_stage archiveController;
    private Controller_send_wait_stage sendWaitController;
    private Stage extract;
    private Stage sendWait;
    private Controller_extract_stage extractController;
    private int type;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        archive = new Stage();
        extract = new Stage();
        sendWait = new Stage();
        select_button.setDisable(true);
        listTool = new ListTool();
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/archive_stage.fxml"));
            Parent root_archive = loader.load();
            archiveController = loader.getController();
            Scene scene_archive = new Scene(root_archive);
            if (db.getTheme() == 20) {
                scene_archive.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_archive_stage.css").toExternalForm());
            } else {
                scene_archive.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_archive_stage.css").toExternalForm());
            }
            archive.setScene(scene_archive);
            archive.initStyle(StageStyle.TRANSPARENT);


            loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/extract_stage.fxml"));
            Parent root_extract = loader.load();
            extractController = loader.getController();
            Scene scene_extract = new Scene(root_extract);
            if (db.getTheme() == 20) {
                scene_extract.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_extract_stage.css").toExternalForm());
            } else {
                scene_extract.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_extract_stage.css").toExternalForm());
            }

            extract.setScene(scene_extract);
            extract.initStyle(StageStyle.TRANSPARENT);

            loader = new FXMLLoader(getClass().getResource("/View_Files/FXML_Files/send_wait_stage.fxml"));
            Parent root_sendWait = loader.load();
            sendWaitController = loader.getController();
            Scene scene_sendWait = new Scene(root_sendWait);
            scene_sendWait.setFill(new Color(0.0, 0.0, 0.0, 0.1));
            if (db.getTheme() == 20) {
                scene_sendWait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Light/Style_send_stage.css").toExternalForm());
            } else {
                scene_sendWait.getStylesheets().add(getClass().getResource("/View_Files/Css_Files/Dark/Style_send_stage.css").toExternalForm());
            }

            sendWait.setScene(scene_sendWait);
            sendWait.initStyle(StageStyle.TRANSPARENT);
            sendWait.setX(0.0);
            sendWait.setY(0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
        resetFileChooser();
        /**
         * initialize tree_view
         */
        QuickAccessRoot = new TreeItem<>(new CustomItem("Quick Access", null));
        QuickAccessRoot.setExpanded(true);
        generatePrimaryChildren(QuickAccessRoot);
        ThisPcRoot = new TreeItem<>(new CustomItem("This PC", null));
        ThisPcRoot.setExpanded(true);
        generatePrimaryChildren(ThisPcRoot);
        roots = new TreeItem(null, null);
        roots.getChildren().addAll(QuickAccessRoot, ThisPcRoot);
        tree_view.setRoot(roots);
        tree_view.setId("tree");
        tree_view.setShowRoot(false);
        tree_view.setCellFactory(stringTreeView -> new EditTreeCell());
        /**
         * initialize list_view
         */
        defaultPath = Shell32Util.getKnownFolderPath(KnownFolders.FOLDERID_Recent);
        list_view.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list_view.setSkin(new CustomListViewSkin<>(list_view));

        list_view.setCellFactory(listView1 -> new EditListCell());

        tree_view.getSelectionModel().selectedItemProperty().addListener((observableValue, customItemTreeItem, t1) -> {
            /**
             * handle tree selected item when you select one of the cells which has a new path it should added to list
             * we have to view the content of the new path so we will clear all items and add new items
             */
            if (customItemTreeItem != null && customItemTreeItem.getValue().getPath() != null) {
                this.list_view.getItems().clear();
                this.list_view.getItems().addAll(listTool.fill_ListView(customItemTreeItem.getValue().getPath()));
                addNewPath(customItemTreeItem.getValue().getPath());
                currentPath.setText(customItemTreeItem.getValue().getPath());
                if (customItemTreeItem.getParent().getValue().getName() != "Quick Access") {
                    lastItemSelectedInTree = customItemTreeItem;
                    lastItemSelectedInTree.getValue().setItemThread(TreeTool.checkItems(lastItemSelectedInTree));
                }
            }
        });
    }

    private void generatePrimaryChildren(TreeItem<CustomItem> shellFolder) {
        for (KnowFolders knownFolder : KnowFolders.values())
            if (new File(knownFolder.getPath()).listFiles() != null)
                shellFolder.getChildren().add(new TreeItem(new CustomItem(knownFolder.getName(), knownFolder.getPath()), TreeTool.getIcon(knownFolder.getPath())));

        if (shellFolder == ThisPcRoot) {
            for (File file : File.listRoots())
                shellFolder.getChildren().add(new TreeItem(new CustomItem(file.getPath(), file.getPath()), TreeTool.getIcon(file.getPath())));

            for (Object currentRoot : shellFolder.getChildren())
                TreeTool.generateItem((TreeItem<CustomItem>) currentRoot, ((TreeItem<CustomItem>) currentRoot).getValue().getPath());
        }
    }

    private void addNewPath(String newPath) {
        if (pathList.get(pathList.size() - 1) != newPath)  //1
            pathList.add(newPath); //2
        if (indexOfCurrentPath + 1 < pathList.size())//3
            indexOfCurrentPath++;

        forwardButton.setDisable(true);//4
        backwardButton.setDisable(false);//5

        if (indexOfCurrentPath != pathList.size() - 1) {//6
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < indexOfCurrentPath; i++)
                temp.add(pathList.get(i));
            temp.add(pathList.get(pathList.size() - 1));
            pathList = temp;
        }
    }

    @FXML
    public void click_root() {
        if (!isCalled) {
            isCalled = true;
            Stage stage = (Stage) root.getScene().getWindow();
            stage.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                /**
                 * t1 boolean if stage showing t1 : true
                 * aBoolean boolean if stage hidden aBoolean : true
                 * observableValue i don't know what is this :D -? GoGa GoKa KoGa
                 * handle stage clicked when you click on stage it will get last selected item release a thread to check new items
                 */
                if (lastItemSelectedInTree != null && t1) {
                    if (lastItemSelectedInTree.isExpanded())
                        lastItemSelectedInTree.getValue().setItemThread(TreeTool.checkItems(lastItemSelectedInTree));
                }
            });
        }


    }

    @FXML
    public void click_list_view(MouseEvent mouseEvent) {
        /**
         * handle list clicked item when you click twice one of the cells we have to add new path to list
         * also we have to view the content of the new path so we will clear all items and add new items
         */
        if (mouseEvent.getClickCount() == 2) {
            String path = ((File) this.list_view.getSelectionModel().getSelectedItem()).getAbsolutePath();
            if (new File(path).isDirectory()) {
                if (pathList.get(pathList.size() - 1) != path) {
                    addNewPath(path);
                    currentPath.setText(path);
                }
                this.list_view.getItems().clear();
                this.list_view.setItems(listTool.fill_ListView(path));
            }
        } else if (mouseEvent.getClickCount() == 1)
            select_button.setDisable(false);
        click_root();
    }

    @FXML
    public void click_tree() {
        click_root();
    }

    @FXML
    public void click_backwardButton() {
        /** @1 when you go back you have to delete all item on list and fill it with the new items
         *  @2 also the pointer has to decrement by 1 to point previous path
         *  @3 when we go back the next button should be enabled
         *  @4 if there is no back path disable back button **/
        this.list_view.getItems().clear(); //1
        this.list_view.getItems().addAll(listTool.fill_ListView(pathList.get(indexOfCurrentPath - 1)));//1
        if (pathList.get(indexOfCurrentPath - 1).equals(Shell32Util.getKnownFolderPath(KnownFolders.FOLDERID_Recent)))
            currentPath.setText("Recent Files");
        else
            currentPath.setText(pathList.get(indexOfCurrentPath - 1));
        indexOfCurrentPath--;//2
        forwardButton.setDisable(false);//3
        if (indexOfCurrentPath == 0) //4
            backwardButton.setDisable(true);
    }

    @FXML
    public void click_forwardButton() {
        /** @1 when you go next you have to delete all item on list and fill it with the new items
         *  @2 also the pointer has to increment by 1 to point next path
         *  @3 when we go next the back button should be enabled
         *  @4 if there is no next path disable next button **/
        this.list_view.getItems().clear();//1
        this.list_view.getItems().addAll(listTool.fill_ListView(pathList.get(indexOfCurrentPath + 1)));//1
        currentPath.setText(pathList.get(indexOfCurrentPath + 1));
        indexOfCurrentPath++;//2
        backwardButton.setDisable(false);//3
        if (indexOfCurrentPath + 1 == pathList.size())//4
            forwardButton.setDisable(true);
    }


    @FXML
    public void click_cancel() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        resetFileChooser();
    }

    @FXML
    public void click_select() throws InterruptedException {
        this.selectedItems = this.list_view.getSelectionModel().getSelectedItems();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        List<File> files = selectedItems;
        File[] filesArray = new File[files.size()];
        for (int i = 0; i < filesArray.length; i++)
            filesArray[i] = files.get(i);
        if (type == Constants.archiveType) {
            archiveController.setSelectedFiles(filesArray);
            archive.show();
        } else if (type == Constants.extractType) {
            extractController.setFile(filesArray[0]);
            extract.show();
        } else if (type == Constants.shareType) {
            sendWaitController.setSelectedFiles(filesArray);
            sendWaitController.loading();
            sendWait.show();
        }
        this.list_view.getSelectionModel().clearSelection();
        resetFileChooser();
    }

    public void setType(int type) {
        this.type = type;
    }


    private void resetFileChooser() {
        isCalled = false;
        indexOfCurrentPath = 0;
        pathList = new ArrayList<>();
        pathList.add(Shell32Util.getKnownFolderPath(KnownFolders.FOLDERID_Recent));
        this.list_view.getItems().clear();
        currentPath.setText("Recent Files");
        backwardButton.setDisable(true);
        forwardButton.setDisable(true);
        select_button.setDisable(true);
    }

    public void fillListViewWithDefaultItems() {
        list_view.setItems(listTool.fill_ListView(defaultPath));
    }

    public void setTitle(String title) {
        Title.setText(title);
    }


    public void setFilterExtensions(String... filterExtensions) {
        listTool.setFilterExtensions(filterExtensions);
    }
}
