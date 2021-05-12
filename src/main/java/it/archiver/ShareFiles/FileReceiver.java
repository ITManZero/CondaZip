package it.archiver.ShareFiles;

import java.io.*;
import java.net.SocketException;

public class FileReceiver {

    private String desPath;
    private Receiver receiver;
    private BufferedInputStream bufferedInputStream;
    private DataInputStream dataInputStream;

    public FileReceiver(String desPath) {
        if (!desPath.isEmpty() && desPath.charAt(desPath.length() - 1) != File.separatorChar)
            desPath += File.separator;
        this.desPath = desPath;
        receiver = new Receiver();
    }

    public void findingServer() throws IOException {
        System.out.println("waiting");
        receiver.connectWithServer();
        if (receiver.isServerFound()) {
            bufferedInputStream = new BufferedInputStream(receiver.getCurrentClient().getInputStream());
            dataInputStream = new DataInputStream(bufferedInputStream);
        }
        if (!receiver.isServerFound())
            System.out.println("timed out");
    }


    public void setPort(int port) {
        receiver = new Receiver(port);
    }

    public void receive() throws EOFException {
        int filesCount = 0;
        try {
            filesCount = dataInputStream.readInt();
            File[] files = new File[filesCount];

            for (int i = 0; i < filesCount; i++) {
                long fileSize = dataInputStream.readLong();
                String fileName = dataInputStream.readUTF();
                System.out.println(fileName);
                files[i] = new File(desPath + fileName);
                if (fileSize == 0) {
                    if (!files[i].exists())
                        files[i].mkdir();
                    writeFileDirectoryDataToReceiverDevice();
                } else if (fileSize != 0)
                    writeFileDataToReceiverDevice(files[i], fileSize);
            }
        } catch (SocketException e) {
            System.out.println("there is no ..");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("there is no sender");
        }

        System.out.println("done.");
    }

    private void writeFileDataToReceiverDevice(File file, long dataSize) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        for (int i = 0; i < dataSize; i++)
            bufferedOutputStream.write(bufferedInputStream.read());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    private void writeFileDirectoryDataToReceiverDevice() throws IOException {
        int filesCount = dataInputStream.readInt();
        File[] files = new File[filesCount];
        for (int i = 0; i < filesCount; i++) {
            long fileSize = dataInputStream.readLong();
            String fileName = dataInputStream.readUTF();
            files[i] = new File(desPath + fileName);
            if (fileSize == 0 && !files[i].exists()) {
                files[i].mkdir();
                writeFileDirectoryDataToReceiverDevice();
            } else if (fileSize != 0)
                writeFileDataToReceiverDevice(files[i], fileSize);
        }
    }

    public boolean isServerFound() {
        return receiver.isServerFound();
    }

    public void setTimer(int timer) {
        receiver.setTimer(timer);
    }

    public void setDesPath(String desPath) {
        this.desPath = desPath;
    }

    public void close() {
        try {
            if (bufferedInputStream != null)
                bufferedInputStream.close();
            if (dataInputStream != null)
                dataInputStream.close();
            if (receiver.getCurrentClient() != null)
                receiver.closeClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
