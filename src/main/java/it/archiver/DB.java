package it.archiver;

import java.io.*;

public class DB {

    private static DB instance = null;
    private static final String DEFAULT_DWS_PATH = System.getProperty("user.home") + "\\Desktop";
    private static final int DEFAULT_THEM = 10;
    private static final File dbFile = new File("db.crypt");


    private String desPath = DEFAULT_DWS_PATH;
    private int theme = DEFAULT_THEM;

    public static DB getDBFile() {
        return instance != null ? instance : (instance = new DB());
    }

    private DB() {
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
                final FileWriter fw = new FileWriter(dbFile);
                final BufferedWriter bw = new BufferedWriter(fw);
                bw.write(crypto(DEFAULT_DWS_PATH));
                bw.newLine();
                bw.write(crypto(String.valueOf(DEFAULT_THEM)));
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                FileReader fr = new FileReader(dbFile);
                BufferedReader br = new BufferedReader(fr);
                desPath = deCrypto(br.readLine());
                theme = Integer.parseInt(deCrypto(br.readLine()));
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateDB() {
        try {
            final FileWriter fw = new FileWriter(dbFile);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(crypto(desPath));
            bw.newLine();
            bw.write(crypto(String.valueOf(theme)));
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTheme() {
        return theme;
    }

    public String getDesPath() {
        return desPath;
    }

    public void setDesPath(String desPath) {
        this.desPath = desPath;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    private String crypto(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = (char) (data.charAt(i));
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private String deCrypto(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = (char) (data.charAt(i));
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
