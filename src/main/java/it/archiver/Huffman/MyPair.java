package it.archiver.Huffman;

public class MyPair {

    private int MyChar;
    private int NumberOfRep;

    public MyPair(int MyChar, int NumberOfRep) {

        this.MyChar = MyChar;
        this.NumberOfRep = NumberOfRep;
    }

    @Override
    public String toString() {
        return "(" + MyChar + " , " + (char) MyChar + " , " + NumberOfRep + ")";
    }

    public void setMyChar(int myChar) {
        MyChar = myChar;
    }

    public void setNumberOfRep(int numberOfRep) {
        NumberOfRep = numberOfRep;
    }

    public int getMyChar() {
        return MyChar;
    }

    public int getNumberOfRep() {
        return NumberOfRep;
    }
}
