package it.archiver.FileChooser.Tree;

public class CustomItem {

    private String name;
    private String path;
    private boolean gotChildren;
    private long fileNumber;
    private Thread itemThread;

    public CustomItem(String name, String path) {
        this.name = name;
        this.path = path;
        gotChildren = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setGotChildren(boolean gotChildren) {
        this.gotChildren = gotChildren;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setItemThread(Thread itemThread) {
        this.itemThread = itemThread;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public boolean isGotChildren() {
        return gotChildren;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public Thread getItemThread() {
        return itemThread;
    }
}
