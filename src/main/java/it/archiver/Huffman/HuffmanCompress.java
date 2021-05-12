package it.archiver.Huffman;

import it.archiver.Algorithms.Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class HuffmanCompress implements Encoder {

    private Dictionary dic;
    private String inputFileName;
    private ArrayList<MyPair> charPair;
    private StringBuffer data;
    private String desPath;
    private int[] counterArray;
    private ArrayList<Integer> charSet;
    private FileOutputStream os;
    private long treeSize;
    private StringBuffer tree;


    public HuffmanCompress(String inputFileName, String desPath) {
        treeSize = 0L;
        tree = new StringBuffer();
        this.dic = new Dictionary();
        this.inputFileName = inputFileName;
        this.desPath = desPath;
        data = new StringBuffer("");
        counterArray = new int[1000000];
        charSet = new ArrayList<>();
        try {
            if (new File(inputFileName + ".huf").exists())
                new File(inputFileName + ".huf").delete();
            os = new FileOutputStream(inputFileName + ".huf", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * (1) reading data and store it in String buffer -->data : StringBuffer
     * and counting repetition of each character and store each character with it's rep -->charPair : ArrayList<MyPair>
     * (2) create pair for each character has  rep
     * (3) Ascending order from least frequent
     * (4) each element (char + Rep) in charPair is leaf we will generate leaves with this function
     * (5) create huffman tree
     * (6) encoding data then writing them to compressed file as bytes
     */

    public File compress() {
//        System.out.println("Reading data and counting characters");
        readData(); //1
//        System.out.println("Reading data and counting characters done ");
//        System.out.println("create pair for each character ");
        createPairs(); //2
//        System.out.println("create pair for each character done ");
        counterArray = null;
        charSet = null;
//        System.out.println("sorting ");
        SortPairArray(charPair);//3
//        System.out.println("sorting done ");
        ArrayList<Node> leaves = dic.createTreeLeaves(charPair);//4
        charPair = null;
        dic.setRoot(dic.BuildTree(leaves)); //5
        dic.DisplayTreeWithByte(dic.getRoot(), "");
        try {
            EncodeNode(dic.getRoot());
            treeSize = tree.toString().getBytes("UTF-8").length;
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(inputFileName + ".huf")));
            System.out.println(treeSize);
            System.out.println(tree);
            dos.writeLong(treeSize);
            dos.close();
            os.write(tree.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        encodeAndWriteData(data); //6
        return new File(inputFileName + ".huf");
    }

    void EncodeNode(Node node) throws IOException {
        if (node.isLeaf()) {
            tree.append('1');
            tree.append((char) node.getMyChar());

        } else {
            tree.append('0');
            EncodeNode(node.getLeft());
            EncodeNode(node.getRight());
        }
    }

    @Override
    public void readData() {
        int count;
        try {
            BufferedInputStream read = new BufferedInputStream(new FileInputStream(inputFileName));
            byte buffer[] = new byte[2048];
            while ((count = read.read(buffer)) != -1) {
                char[] cbuf = new char[count];
                for (int i = 0; i < cbuf.length; i++) {
                    cbuf[i] = (char) buffer[i];
                }
                String dataString = new String(cbuf);
                countOfChars(dataString);
                data.append(dataString);
            }
            read.close();
        } catch (FileNotFoundException e) {
//            System.out.println("File not found" + e);
        } catch (IOException ioe) {
//            System.out.println("Exception while reading file " + ioe);
        }

    }

    @Override
    public void writeData(byte[] bytes) {
        try {
//            System.out.println("writeBuffer" + bytes.length);
            os.write(bytes);
        } catch (Exception e) {
//            System.out.println("Exception: " + e);
        }
    }

    private void countOfChars(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (counterArray[data.charAt(i)] == 0)
                charSet.add((int) data.charAt(i));
            counterArray[data.charAt(i)]++;
        }
    }

    void createPairs() {
        counterArray[1000000 - 1] = 1;
        charSet.add(1000000 - 1);
        charPair = new ArrayList<>(charSet.size());
        Iterator<Integer> ch = charSet.iterator();
        while (ch.hasNext()) {
            int a = ch.next();
            charPair.add(new MyPair(a, counterArray[a]));
        }
    }

    private void SortPairArray(ArrayList<MyPair> pairArray) {
        for (int i = 0; i < pairArray.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < pairArray.size(); j++) {
                if (pairArray.get(j).getNumberOfRep() > pairArray.get(maxIndex).getNumberOfRep()) {
                    maxIndex = j;//searching for lowest index
                }
            }
            MyPair temp = pairArray.get(i);
            pairArray.set(i, pairArray.get(maxIndex));
            pairArray.set(maxIndex, temp);
        }
    }


    private void encodeAndWriteData(StringBuffer data) {
//        System.out.println("encoding characters and writing them to compressed file");
        StringBuffer Ars = new StringBuffer();

        try {
            for (int i = 0; i < data.length(); i++) {
                Ars.append(dic.getCharCode(data.charAt(i)));
                if (Ars.length() > 7 * 10000) {
                    writeData(subStringToBytes(Ars.substring(0, 7 * 10000)));
                    Ars = new StringBuffer(Ars.substring(7 * 10000));
                }
            }
            Ars.append(dic.getCharCode(1000000 - 1));
            writeData(subStringToBytes(Ars.toString()));
//            System.out.println("Successfully" + " byte inserted");
            os.close();
        } catch (Exception e) {
//            System.out.println("Exception: " + e);
        }
//        System.out.println("compress done ");

    }


    private byte[] subStringToBytes(String S) {
        int length = S.length();
        int RemBit = length % 7;
        if (RemBit != 0)
            for (int i = RemBit + 1; i <= 7; i++)
                S = S + "0";
        byte[] ArrayOfByte = new byte[S.length() / 7];
        for (int i = 0; i < S.length() / 7; i++) {
            String Sub = S.substring(i * 7, (i * 7) + 7);
            ArrayOfByte[i] = Byte.parseByte(Sub, 2);
        }
        return ArrayOfByte;
    }

    private void SaveHashMap() {

        final String outputFilePath = "hashMap.txt";
//        HashMap<Integer, String> hMapNumbers = new HashMap<Integer, String>();
        //new file object
        File file = new File(outputFilePath);
        BufferedWriter bf = null;
        try {
            //create new BufferedWriter for the output file
            bf = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<Integer, String> entry : dic.getDic().entrySet()) {
                //put key and value separated by a colon
                bf.write(entry.getKey() + ":" + entry.getValue());
                bf.newLine();
            }
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //always close the writer
                bf.close();
            } catch (Exception e) {
            }
        }
    }

    private void PrintPairArray(MyPair[] pairArray) {
        for (MyPair p : pairArray) {
//            System.out.println(p);
        }
    }

    private void PrintByteArray(byte[] ArrayOfByte) {
        for (int i = 0; i < ArrayOfByte.length; i++) {
//            System.out.println(ArrayOfByte[i]);
        }
    }

    public Dictionary getDic() {
        return dic;
    }

}
