package it.archiver.ShareFiles;

import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class FileSender {

    private File[] files;
    private int numberOfFiles;
    private Server server;
    private DataOutputStream dataOutputStream;
    private BufferedOutputStream bufferedOutputStream;


    public FileSender(File... files) {
        this.files = files;
        numberOfFiles = files.length;
        server = new Server();
    }

    public void openServerSocket(){
        server.createServer();
    }

    public void setPort(int port) {
        server = new Server(port);
    }

    public void searching() {
        server.searchingForClients();
    }

    public void setClient(Socket client) throws IOException {
        bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
        dataOutputStream = new DataOutputStream(bufferedOutputStream);
    }

    public void send() throws IOException {
        dataOutputStream.writeInt(numberOfFiles);
        for (File file : files) {
            dataOutputStream.writeLong(file.length());
            dataOutputStream.writeUTF(file.getName());
            if (file.isDirectory())
                writeDirectoryToSocket(file, file.getName());
            else
                writeFileDataToSocket(file);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        dataOutputStream.close();

    }

    private void writeFileDataToSocket(File file) throws IOException {
        int byt;
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        while ((byt = bufferedInputStream.read()) != -1)
            bufferedOutputStream.write(byt);
        bufferedOutputStream.flush();
        bufferedInputStream.close();
        fileInputStream.close();
    }

    private void writeDirectoryToSocket(File dir, String parent) throws IOException {
        if (dir == null)
            return;
        dataOutputStream.writeInt(dir.listFiles().length);
        for (File file : dir.listFiles()) {
            dataOutputStream.writeLong(file.length());
            dataOutputStream.writeUTF(parent + File.separator + file.getName());
            if (file.isDirectory())
                writeDirectoryToSocket(file, parent + File.separator + file.getName());
            else
                writeFileDataToSocket(file);
        }
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public ArrayList<Pair<Socket, String>> getClients() {
        return server.getClients();
    }

    public void setTimer(int timer) {
        server.setTimer(timer);
    }

    public void close() {
        {
            try {
                if (bufferedOutputStream != null)
                    bufferedOutputStream.close();
                if (dataOutputStream != null)
                    dataOutputStream.close();
                if (server.getServerSocket() != null)
                    server.closeSocketServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
