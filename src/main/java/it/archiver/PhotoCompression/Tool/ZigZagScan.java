package it.archiver.PhotoCompression.Tool;


import javafx.util.Pair;

import java.util.ArrayList;

public class ZigZagScan {

    private int[] D1;
    private int[][] D2;
    private ArrayList<Pair<Integer, Integer>> N;


    public ZigZagScan(int blockSize){

        this.D1 = new int[blockSize * blockSize];
        this.D2 = new int[blockSize][blockSize];

        this.N=new ArrayList<>();
        Pair<Integer, Integer> N0=new Pair<>(0,0);
        Pair<Integer, Integer> N1=new Pair<>(0,1);
        Pair<Integer, Integer> N2=new Pair<>(1,0);
        Pair<Integer, Integer> N3=new Pair<>(2,0);
        Pair<Integer, Integer> N4=new Pair<>(1,1);
        Pair<Integer, Integer> N5=new Pair<>(0,2);
        Pair<Integer, Integer> N6=new Pair<>(0,3);
        Pair<Integer, Integer> N7=new Pair<>(1,2);
        Pair<Integer, Integer> N8=new Pair<>(2,1);
        Pair<Integer, Integer> N9=new Pair<>(3,0);
        Pair<Integer, Integer> N10=new Pair<>(4,0);
        Pair<Integer, Integer> N11=new Pair<>(3,1);
        Pair<Integer, Integer> N12=new Pair<>(2,2);
        Pair<Integer, Integer> N13=new Pair<>(1,3);
        Pair<Integer, Integer> N14=new Pair<>(0,4);
        Pair<Integer, Integer> N15=new Pair<>(0,5);
        Pair<Integer, Integer> N16=new Pair<>(1,4);
        Pair<Integer, Integer> N17=new Pair<>(2,3);
        Pair<Integer, Integer> N18=new Pair<>(3,2);
        Pair<Integer, Integer> N19=new Pair<>(4,1);
        Pair<Integer, Integer> N20=new Pair<>(5,0);


        Pair<Integer, Integer> N21=new Pair<>(6,0);
        Pair<Integer, Integer> N22=new Pair<>(5,1);
        Pair<Integer, Integer> N23=new Pair<>(4,2);
        Pair<Integer, Integer> N24=new Pair<>(3,3);
        Pair<Integer, Integer> N25=new Pair<>(2,4);
        Pair<Integer, Integer> N26=new Pair<>(1,5);
        Pair<Integer, Integer> N27=new Pair<>(0,6);


        Pair<Integer, Integer> N28=new Pair<>(0,7);
        Pair<Integer, Integer> N29=new Pair<>(1,6);
        Pair<Integer, Integer> N30=new Pair<>(2,5);
        Pair<Integer, Integer> N31=new Pair<>(3,4);
        Pair<Integer, Integer> N32=new Pair<>(4,3);
        Pair<Integer, Integer> N33=new Pair<>(5,2);
        Pair<Integer, Integer> N34=new Pair<>(6,1);
        Pair<Integer, Integer> N35=new Pair<>(7,0);
        Pair<Integer, Integer> N36=new Pair<>(7,1);
        Pair<Integer, Integer> N37=new Pair<>(6,2);
        Pair<Integer, Integer> N38=new Pair<>(5,3);
        Pair<Integer, Integer> N39=new Pair<>(4,4);
        Pair<Integer, Integer> N40=new Pair<>(3, 5);
        Pair<Integer, Integer> N41=new Pair<>(2, 6);
        Pair<Integer, Integer> N42=new Pair<>(1, 7);
        Pair<Integer, Integer> N43=new Pair<>(2, 7);
        Pair<Integer, Integer> N44=new Pair<>(3, 6);
        Pair<Integer, Integer> N45=new Pair<>(4, 5);
        Pair<Integer, Integer> N46=new Pair<>(5, 4);
        Pair<Integer, Integer> N47=new Pair<>(6, 3);
        Pair<Integer, Integer> N48=new Pair<>(7, 2);
        Pair<Integer, Integer> N49=new Pair<>(7, 3);
        Pair<Integer, Integer> N50=new Pair<>(6, 4);
        Pair<Integer, Integer> N51=new Pair<>(5, 5);
        Pair<Integer, Integer> N52=new Pair<>(4, 6);
        Pair<Integer, Integer> N53=new Pair<>(3, 7);
        Pair<Integer, Integer> N54=new Pair<>(4, 7);
        Pair<Integer, Integer> N55=new Pair<>(5, 6);
        Pair<Integer, Integer> N56=new Pair<>(6, 5);
        Pair<Integer, Integer> N57=new Pair<>(7, 4);
        Pair<Integer, Integer> N58=new Pair<>(7, 5);
        Pair<Integer, Integer> N59=new Pair<>(6, 6);
        Pair<Integer, Integer> N60=new Pair<>(5, 7);
        Pair<Integer, Integer> N61=new Pair<>(6, 7);
        Pair<Integer, Integer> N62=new Pair<>(7, 6);
        Pair<Integer, Integer> N63=new Pair<>(7, 7);


        N.add(N0);
        N.add(N1);
        N.add(N2);
        N.add(N3);
        N.add(N4);
        N.add(N5);
        N.add(N6);
        N.add(N7);
        N.add(N8);
        N.add(N9);

        N.add(N10);
        N.add(N11);
        N.add(N12);
        N.add(N13);
        N.add(N14);
        N.add(N15);
        N.add(N16);
        N.add(N17);
        N.add(N18);
        N.add(N19);

        N.add(N20);
        N.add(N21);
        N.add(N22);
        N.add(N23);
        N.add(N24);
        N.add(N25);
        N.add(N26);
        N.add(N27);
        N.add(N28);
        N.add(N29);

        N.add(N30);
        N.add(N31);
        N.add(N32);
        N.add(N33);
        N.add(N34);
        N.add(N35);
        N.add(N36);
        N.add(N37);
        N.add(N38);
        N.add(N39);

        N.add(N40);
        N.add(N41);
        N.add(N42);
        N.add(N43);
        N.add(N44);
        N.add(N45);
        N.add(N46);
        N.add(N47);
        N.add(N48);
        N.add(N49);

        N.add(N50);
        N.add(N51);
        N.add(N52);
        N.add(N53);
        N.add(N54);
        N.add(N55);
        N.add(N56);
        N.add(N57);
        N.add(N58);
        N.add(N59);

        N.add(N60);
        N.add(N61);
        N.add(N62);
        N.add(N63);

    }

    public void zigZagMatrixNew(int[][] arr, int n, int m){

        for(int i=0;i<(n*m);i++){
        int row=N.get(i).getKey();
        int col=N.get(i).getValue();
        D1[i]=arr[row][col];
        }
    }
    public void inverseZitZackMatrixNew(int[] arr, int n, int m){
        int index=0;
        for(int i=0;i<(n);i++){
            for(int j=0;j<(m);j++){
                int row=N.get(index).getKey();
                int col=N.get(index).getValue();
                D2[row][col]=arr[index];
                index++;
            }
        }
    }
    public int[] getD1() { return D1;}

    public int[][] getD2() { return D2;}
}



