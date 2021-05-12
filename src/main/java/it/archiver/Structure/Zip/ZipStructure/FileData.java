package it.archiver.Structure.Zip.ZipStructure;

import it.archiver.Structure.Zip.CreateZipFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileData{

    //File Stored
    private File file;
    //Place of File
    private String path;
    //Size
    private int length = 0;

    FileData(){
        file = null;
        path = "";
    }
    FileData(File file) {
        this.file= file;
        this.path = file.getPath();
        this.length = (int) file.length();
    }
    FileData(String path){
        this.path = path;
        file = new File(path);
        length = (int) file.length();
    }

    public void editFile(String path){
        //before Editing we Must Check if the file exist or not
        if(new File(path).exists()) {
            file = new File(path);
            this.path = path;
            length = (int) file.length();
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getLength() {
        return length;
    }

    public void write(File file , CreateZipFile createZipFile) throws IOException {
        BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));
        int count;
        byte data[] = new byte[2048];
        while ((count = origin.read(data)) != -1)
            createZipFile.write(data, 0, count);
        origin.close();
    }
}
