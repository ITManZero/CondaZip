package it.archiver.PhotoCompression.DCT;

import java.util.Vector;

public class FastDCT extends DCT {

    private double[][] c;
    private double[][] cT;
    private Vector<int[][]> iDctBlocks;

    @Override
    public void initialize(int blockSize) {
        this.blockSize = blockSize;
        this.dctBlockY = new float[blockSize][blockSize];
        this.dctBlockCb = new float[blockSize][blockSize];
        this.dctBlockCr = new float[blockSize][blockSize];
        this.c = new double[blockSize][blockSize];
        this.cT = new double[blockSize][blockSize];
        this.iDctBlocks = new Vector<>();
        initMatrix();
    }
    private void initMatrix(){
        for (int j = 0; j < blockSize; j++)
        {
            c[0][j] = 1.0 / Math.sqrt(blockSize);
            cT[j][0] = c[0][j];
        }
        for (int i = 1; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                c[i][j] = Math.sqrt(2.0 / blockSize) * Math.cos( ((2.0 * j + 1.0) * i * Math.PI) / (2.0 * blockSize) );
                cT[j][i] = c[i][j];
            }
        }
    }

    @Override
    public void DctOperationForY(int[][] matrixY) {

        double[][] temp = new double[blockSize][blockSize];
        double res;

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                temp[i][j] = 0.0;
                for (int k = 0; k < blockSize; k++)
                {
                    temp[i][j] += (((matrixY[i][k])) * cT[k][j]);
                }
            }
        }

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                res = 0.0;

                for (int k = 0; k < blockSize; k++)
                {
                    res += (c[i][k] * temp[k][j]);
                }

                dctBlockY[i][j] =(float) res;
            }
        }
    }

    @Override
    public void DctOperationForCb(int[][] matrixCb) {
        double[][] temp = new double[blockSize][blockSize];
        double res;

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                temp[i][j] = 0.0;
                for (int k = 0; k < blockSize; k++)
                {
                    temp[i][j] += (((matrixCb[i][k])) * cT[k][j]);
                }
            }
        }

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                res = 0.0;

                for (int k = 0; k < blockSize; k++)
                {
                    res += (c[i][k] * temp[k][j]);
                }

                dctBlockCb[i][j] = (float) res;
            }
        }
    }

    @Override
    public void DctOperationForCr(int[][] matrixCr) {
        double[][] temp = new double[blockSize][blockSize];
        double res;

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                temp[i][j] = 0.0;
                for (int k = 0; k < blockSize; k++)
                {
                    temp[i][j] += (((matrixCr[i][k])) * cT[k][j]);
                }
            }
        }

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                res = 0.0;

                for (int k = 0; k < blockSize; k++)
                {
                    res += (c[i][k] * temp[k][j]);
                }

                dctBlockCr[i][j] = (float)res;
            }
        }
    }

    @Override
    public void IDct(int[][] matrix) {
        int[][] iDctBlock = new int[blockSize][blockSize];
        double[][] temp = new double[blockSize][blockSize];
        double res;

        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                temp[i][j] = 0.0;
                for (int k = 0; k < blockSize; k++)
                {
                    temp[i][j] += matrix[i][k] * c[k][j];
                }
            }
        }
        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                res = 0.0;
                for (int k = 0; k < blockSize; k++)
                    res += cT[i][k] * temp[k][j];

                    iDctBlock[i][j] = (int) ((Math.round(res)));
            }
        }
        iDctBlocks.addElement(iDctBlock);
    }


    @Override
    public float[][] getDctBlockY() {return dctBlockY;}
    @Override
    public float[][] getDctBlockCb() {return dctBlockCb;}
    @Override
    public float[][] getDctBlockCr() {return dctBlockCr;}

    @Override
    public Vector<int[][]> getIDctBlocks() {return iDctBlocks;}

}
