package it.archiver.PhotoCompression.Quantization;

public class Quantization {
    private int quality;
    private int blockSize;
    private int[][] quantizationTableLum;
    private int[][] quantizationTableCho;
    private int[][]  quantizationY;
    private int[][] quantizationCb;
    private int[][] quantizationCr;
    private int[][] deQuantizationY;
    private int[][] deQuantizationCb;
    private int[][] deQuantizationCr;
    private int bitRate;


    public Quantization(int quality, String algorithm, int bitRate) {
        this.quality = quality;
        if (algorithm.equals("jpeg")) {
            this.blockSize = 8;
        }
        this.quantizationTableLum = new int[blockSize][blockSize];
        this.quantizationTableCho = new int[blockSize][blockSize];
        this.bitRate=bitRate;
        this.quantizationY = new int[blockSize][blockSize];
        this.quantizationCb = new int[blockSize][blockSize];
        this.quantizationCr = new int[blockSize][blockSize];
        this.deQuantizationY = new int[blockSize][blockSize];
        this.deQuantizationCb = new int[blockSize][blockSize];
        this.deQuantizationCr = new int[blockSize][blockSize];
        createQuantizationTable();
    }


    private void createQuantizationTable() {
        if (bitRate == 0) {
            if ((quality >1) && (quality <= 9)) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        quantizationTableLum[x][y] =1+((x+y+1)*quality);
                        quantizationTableCho[x][y] =1+((x+y+1)*quality);
                    }
                }
            }
            if (quality == 1) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        quantizationTableLum[x][y] = 1;
                        quantizationTableCho[x][y] = 1;
                    }
                }
            }
        }
        if (bitRate == 1) {
            if ((quality >1) && (quality <= 9)) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        quantizationTableLum[x][y] =2*(1+((x+y+1)*quality));
                        quantizationTableCho[x][y] =2*(1+((x+y+1)*quality));
                    }
                }
            }
            if (quality == 1) {
                for (int x = 0; x < blockSize; x++) {
                    for (int y = 0; y < blockSize; y++) {
                        quantizationTableLum[x][y] = 1;
                        quantizationTableCho[x][y] = 1;
                    }
                }
            }
        }
    }


    public void dctQuantizationForYJpeg(float[][] MatrixY){
        for (int x = 0; x < blockSize; x++)
        {
            for (int y = 0; y < blockSize; y++)
            {
                quantizationY[x][y]=Math.round(MatrixY[x][y]/quantizationTableLum[x][y]);
            }
        }
    }
    public void dctQuantizationForCbJpeg(float[][] MatrixCb){
        for (int x = 0; x < blockSize; x++)
        {
            for (int y = 0; y < blockSize; y++)
            {
                quantizationCb[x][y]=Math.round(MatrixCb[x][y]/quantizationTableCho[x][y]);
            }
        }
    }
    public void dctQuantizationForCrJpeg(float[][] MatrixCr){
        for (int x = 0; x < blockSize; x++)
        {
            for (int y = 0; y < blockSize; y++)
            {
                quantizationCr[x][y]=Math.round(MatrixCr[x][y]/quantizationTableCho[x][y]);
            }
        }
    }


    public void deQuantizationY(int [][] MatrixQuantization){
        for (int x = 0; x < blockSize; x++){
            for (int y = 0; y < blockSize; y++){
                deQuantizationY[x][y]=Math.round(quantizationTableLum[x][y]*MatrixQuantization[x][y]);
            }
        }
    }
    public void deQuantizationCb(int [][] MatrixQuantization){
        for (int x = 0; x < blockSize; x++){
            for (int y = 0; y < blockSize; y++){
                deQuantizationCb[x][y]=Math.round(quantizationTableCho[x][y]*MatrixQuantization[x][y]);
            }
        }
    }
    public void deQuantizationCr(int [][] MatrixQuantization){
        for (int x = 0; x < blockSize; x++){
            for (int y = 0; y < blockSize; y++){
                deQuantizationCr[x][y]=Math.round(quantizationTableCho[x][y]*MatrixQuantization[x][y]);
            }
        }
    }

    public int[][] getQuantizationY() {return quantizationY;}
    public int[][] getQuantizationCb() {return quantizationCb;}
    public int[][] getQuantizationCr() {return quantizationCr;}
    public int[][] getDeQuantizationY() {return deQuantizationY;}
    public int[][] getDeQuantizationCb() {return deQuantizationCb;}
    public int[][] getDeQuantizationCr() {return deQuantizationCr;}

}
