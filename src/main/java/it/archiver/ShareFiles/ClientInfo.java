package it.archiver.ShareFiles;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ClientInfo implements Serializable {
    private String computerName;
    private String ip;

    public ClientInfo() {
        this.computerName = System.getProperty("user.name");
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getComputerName() {
        return computerName;
    }

    public String getIp() {
        return ip;
    }

}
