package it.archiver.Huffman;

import it.archiver.Algorithms.Decoder;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

public class HuffmanDeCompress implements Decoder {
    boolean debugMode = false;

    private Dictionary dic;
    private String zipPath;
    private byte[] bytesInFile;
    private String data;
    private BufferedInputStream bis;
    private String desPath;
    private String outputFileName;
    private long skip;
    private long sizeToRead;
    private long treeSize;
    private String tree;
    private int index;

    public HuffmanDeCompress(String zipPath, long sizeToRead, long skip, String outputFileName, String desPath) {
        this.skip = skip;
        index = 0;
        this.desPath = desPath;
        this.outputFileName = outputFileName;
        dic = new Dictionary();
        this.sizeToRead = sizeToRead;
        this.zipPath = zipPath;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(zipPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decompress() {
//        System.out.println("Decompress Started");
        //getHashMapFromTextFile("hashMap.txt");
        try {
            bis.skip(skip);
            byte[] bytes = new byte[8];
            bis.read(bytes);
            treeSize = ByteBuffer.wrap(bytes).getLong();
            bytes = new byte[(int) treeSize];
            System.out.println(treeSize);
            bis.read(bytes);
            tree = new String(bytes);
            System.out.println(tree);
            tree+='2';
            dic.setRoot(ReadNode());
            dic.DisplayTreeWithByte(dic.getRoot(), "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        readData();

        String OpenCompressFile = decodeBinary();
        getOriginalData(OpenCompressFile);
        System.out.println("**********************");
        //System.out.println(Decompress);
        byte[] bytes = new byte[data.length()];
        for (int i = 0; i < data.length(); i++) {
            bytes[i] = (byte) data.charAt(i);
        }

        writeData(bytes);
        //GetHashFileSize();
    }

    Node ReadNode() throws IOException {
        char c = tree.charAt(index);
        index++;
        if (c == '1') {
            index++;
            char ch = tree.charAt(index-1);
            return new Node(ch, null, null, true);
        } else if (c == '0') {
            Node rightChild = ReadNode();
            Node leftChild = ReadNode();
            return new Node((char) 0, leftChild, rightChild, false);
        }
        return null;
    }

    @Override
    public void readData() {


        System.out.println("OpenCompressedFile start");
        try {
            // create FileInputStream object
            int count;
            byte[] bytes = new byte[50];
            bytesInFile = new byte[(int) ((int) sizeToRead - treeSize - 8)];
            // Reads up to certain bytes of data from this input stream into an array of bytes.
            bis.read(bytesInFile);
            bis.close();




        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        } finally {
            /*try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }*/
        }
    }

    @Override
    public void writeData(byte[] bytes) {
        try {
            System.out.println("WriteDecompressFile start");
            // converting back
            // for (byte b : out) System.out.print(b+",");

            File newTextFile = new File(desPath + outputFileName);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newTextFile));
            bos.write(bytes);
            bos.close();

        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }

    private String decodeBinary() {
        StringBuffer Ars = new StringBuffer();
        for (byte By : bytesInFile) {
            if (debugMode) {
//                System.out.println(toBinary(By, 7));
            }


            Ars.append(toBinary(By, 7));
        }
        String S = Ars.toString();

        return S;
    }

    private void getOriginalData(String S) {
        System.out.println("Decompress Start");
        Node Curr = dic.getRoot();
        ArrayList<Character> Ars = new ArrayList<>();
        // String NewAfterCompress = "";
        int len = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '0') {
                Curr = Curr.getLeft();
                //System.out.print(S.charAt(i) + "L");

            } else if (S.charAt(i) == '1') {
                Curr = Curr.getRight();
                //System.out.print(S.charAt(i) + "R");
            }

            if (Curr.isLeaf()) {
                if (Curr.getMyChar() == 16959) {
                    break;
                }
                //   NewAfterCompress = NewAfterCompress + (char) Curr.MyChar;
                char ch = (char) Curr.getMyChar();
                Ars.add(ch);
                Curr = dic.getRoot();
                // System.out.print("|");
            }
        }
        data = Ars.toString().substring(1, 3 * Ars.size() - 1)
                .replaceAll(", ", "");
    }

    public String toBinary(int x, int len) {

        if (len > 0) {
            return String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
        }

        return null;
    }


    public void getHashMapFromTextFile(String filePath) {
        BufferedReader br = null;

        try {

            //create file object
            File file = new File(filePath);

            //create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line = null;

            //read file line by line
            while ((line = br.readLine()) != null) {

                //split the line by :
                String[] parts = line.split(":");

                String val = parts[1];
                Integer key = Integer.parseInt(parts[0].trim());
                dic.getDic().put(key, val);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
                ;
            }
        }
        BuildTreeFromDicFile();
    }

    public void BuildTreeFromDicFile() {
        //System.out.println(Dictionary.Dic);
        dic.setRoot(new Node());
        int charr;
        String Code;
        Node Curr = dic.getRoot();
        char cc;

        for (Map.Entry<Integer, String> line : dic.getDic().entrySet()) {
            charr = line.getKey();
            Code = line.getValue();

            for (int i = 0; i < Code.length(); i++) {
                cc = Code.charAt(i);

                if (cc == '0') {
                    if (Curr.getLeft() == null) {
                        Curr.setLeft(new Node());
                    }
                    Curr = Curr.getLeft();
                } else {
                    if (Curr.getRight() == null) {
                        Curr.setRight(new Node());
                    }
                    Curr = Curr.getRight();
                }
            }

            Curr.setMyChar(charr);
            Curr.setLeaf(true);
            Curr = dic.getRoot();
        }
    }


    private long GetHashFileSize() {

        File f = new File("hashmap.txt");
//        System.out.println("length: " + f.length());

        return f.length();
    }

    public void setDic(Dictionary dic) {
        this.dic = dic;
    }
}
