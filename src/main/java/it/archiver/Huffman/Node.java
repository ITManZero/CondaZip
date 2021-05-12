package it.archiver.Huffman;

public class Node {

    private int Rep;
    private int MyChar;

    private Node Right;
    private Node Left;
    private boolean isLeaf;

    public Node(int Rep, char MyChar, Node Right, Node Left, boolean isLeaf) {
        this.Rep = Rep;
        this.MyChar = MyChar;
        this.Right = Right;
        this.Left = Left;
        this.isLeaf = isLeaf;
    }

    public Node(MyPair P) {
        this.Rep = P.getNumberOfRep();
        this.MyChar = P.getMyChar();
    }

    public Node() {
        Rep = 0;
        MyChar = 0;
        isLeaf = false;
    }

    public Node(char MyChar, Node Right, Node Left, boolean isLeaf) {
        this.MyChar = MyChar;
        this.Right = Right;
        this.Left = Left;
        this.isLeaf = isLeaf;
    }

    public boolean isLeaf() {
        if (Right == null && Left == null) {
            return true;
        } else {
            return false;
        }
    }

    public void setRep(int rep) {
        Rep = rep;
    }

    public void setMyChar(int myChar) {
        MyChar = myChar;
    }

    public void setRight(Node right) {
        Right = right;
    }

    public void setLeft(Node left) {
        Left = left;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getRep() {
        return Rep;
    }

    public int getMyChar() {
        return MyChar;
    }

    public Node getRight() {
        return Right;
    }

    public Node getLeft() {
        return Left;
    }
}
