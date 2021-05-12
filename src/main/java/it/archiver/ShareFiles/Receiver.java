package it.archiver.ShareFiles;

import java.io.*;
import java.net.*;

public class Receiver {

    private Socket currentClient;
    private int port;
    private int timer;
    private boolean serverFound;


    public Receiver() {
        timer = 5;
        serverFound = false;
        this.port = 5991;
    }

    public Receiver(int port) {
        this();
        this.port = port;
    }

    public void connectWithServer() {
        int timer = this.timer;
        while (timer > 0) {
            try {
                Thread.sleep(1000);
                currentClient = new Socket("127.0.0.1", port);
                ObjectOutputStream oos = new ObjectOutputStream(currentClient.getOutputStream());
                oos.writeObject(new ClientInfo());
                serverFound = true;
                break;
            } catch (ConnectException e) {
                timer--;
                serverFound = false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setServerFound(boolean serverFound) {
        this.serverFound = serverFound;
    }

    public Socket getCurrentClient() {
        return currentClient;
    }

    public Socket getSocket() {
        return currentClient;
    }

    public boolean isServerFound() {
        return serverFound;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void closeClientSocket() throws IOException {
        currentClient.close();
    }
}
