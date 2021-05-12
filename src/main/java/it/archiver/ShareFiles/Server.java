package it.archiver.ShareFiles;

import javafx.util.Pair;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {


    private ServerSocket serverSocket;
    private ArrayList<Pair<Socket, String>> clients;
    private int port;
    private int timer;
    private int currTimer;
    private byte connectedClients;
    private boolean searching;

    public Server() {
        this.port = 5991;
        timer = currTimer = 5;
        connectedClients = 0;
        clients = new ArrayList<>();
        searching = false;
    }

    public Server(int port) {
        this();
        this.port = port;

    }

    public void createServer()  {

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void searchingForClients() {
        System.out.println("searching for clients");
        try {
            serverSocket.setSoTimeout(currTimer * 1000);
            searching = true;
            timerThread();
            while (currTimer > 0) {
                Socket currClient = serverSocket.accept();
                if (!isExist(currClient)) {
                    ObjectInputStream ois = new ObjectInputStream(currClient.getInputStream());
                    ClientInfo clientInfo = (ClientInfo) ois.readObject();
                    System.out.println(clientInfo.getComputerName() + clientInfo.getIp());
                    clients.add(new Pair<>(currClient, clientInfo.getComputerName() + File.separatorChar + clientInfo.getIp()));
                    connectedClients++;
                }
                serverSocket.setSoTimeout(currTimer * 1000);
            }
        } catch (SocketTimeoutException e) {
            if (clients.size() == 0)
                System.out.println("searching timed out");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            searching = false;
            currTimer = timer;
        }

    }

    private void timerThread() {
        Runnable R = () -> {
            while (currTimer > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currTimer--;
            }
        };
        new Thread(R).start();
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isSearching() {
        return searching;
    }

    public byte getConnectedClients() {
        return connectedClients;
    }

    public int getTimer() {
        return timer;
    }

    public ArrayList<Pair<Socket, String>> getClients() {
        return clients;
    }

    public void closeSocketServer() throws IOException {
        serverSocket.close();
    }

    private boolean isExist(Socket currClient) {
        for (Pair client : clients)
            if (currClient.getInetAddress().equals(((Socket) client.getKey()).getInetAddress()))
                return true;
        return false;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
