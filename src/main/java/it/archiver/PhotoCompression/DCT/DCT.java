package it.archiver.PhotoCompression.DCT;

import java.util.Vector;

public abstract class DCT {
    int blockSize;
    float[][] dctBlockY;
    float[][] dctBlockCb;
    float[][] dctBlockCr;

    protected abstract void initialize(int blockSize);

    public abstract void DctOperationForY(int[][] matrixY);

    public abstract void DctOperationForCb(int[][] matrixCb);

    public abstract void DctOperationForCr(int[][] matrixCr);

    public abstract void IDct(int[][] matrix);

    public abstract float[][] getDctBlockY();

    public abstract float[][] getDctBlockCb();

    public abstract float[][] getDctBlockCr();

    public abstract Vector<int[][]> getIDctBlocks();


}