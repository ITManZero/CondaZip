package it.archiver.Huffman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Dictionary {

    private final HashMap<Integer, String> Dic;
    private Node root;

    public Dictionary() {
        Dic = new HashMap<>();
        root = null;
    }

    public Dictionary(HashMap<Integer, String> dic, Node root) {
        Dic = dic;
        this.root = root;
    }

    public ArrayList<Node> createTreeLeaves(ArrayList<MyPair> ArrayWithoutZeros) {
        ArrayList<Node> ArrayNods = new ArrayList<>();
        for (MyPair P : ArrayWithoutZeros) {
            Node N = new Node();
            N.setRep(P.getNumberOfRep());
            N.setMyChar(P.getMyChar());
            N.setLeaf(true);
            ArrayNods.add(N);
        }
        Collections.reverse(ArrayNods);
        return ArrayNods;
    }

    public Node BuildTree(ArrayList<Node> ArrayNods) { // inner Huffman.Node
        Node c = null;
        while (ArrayNods.size() > 1) {

            Node a;
            Node b;
            c = new Node();

            a = ArrayNods.get(0);
            b = ArrayNods.get(1);

            c.setRep(a.getRep() + b.getRep());
            c.setLeaf(false);

            c.setRight(b);
            c.setLeft(a);

            ArrayNods.remove(0);
            ArrayNods.remove(0);

            int i = 0;
            for (i = 0; i < ArrayNods.size(); i++) {
                if (c.getRep() < ArrayNods.get(i).getRep()) {
                    ArrayNods.add(i, c);
                    break;
                }
            }

            if (i == ArrayNods.size()) {
                ArrayNods.add(c);
            }

        }
        return c;
    }

    public String getCharCode(int Char) {
        return Dic.get(Char);
    }

    public void DisplayTreeWithByte(Node Curr, String S) {
        if (Curr == null) {
            return;
        } else if (Curr.isLeaf() == true) {
            System.out.println((char) Curr.getMyChar() + " , " + Curr.getRep() + " , " + S);
            Dic.put(Curr.getMyChar(), S);
            return;
        } else {
            DisplayTreeWithByte(Curr.getLeft(), S + "0");
            DisplayTreeWithByte(Curr.getRight(), S + "1");
        }
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public HashMap<Integer, String> getDic() {
        return Dic;
    }
}
