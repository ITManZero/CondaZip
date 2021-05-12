package it.archiver.PhotoCompression.DCT;

import java.util.Vector;

public class SlowDCT extends DCT {

    private Vector<int[][]> iDctBlocks;
    private float DefinitionSet(int X)
    {
        return ((X == 0) ? (float) (1 / Math.sqrt(2)) : (float) 1);
    }


    @Override
    public void initialize(int blockSize) {
        this.blockSize=blockSize;
        this.dctBlockY=new float[blockSize][blockSize];
        this.dctBlockCb=new float[blockSize][blockSize];
        this.dctBlockCr=new float[blockSize][blockSize];
        this.iDctBlocks=new Vector<>();
    }

    @Override
    public void DctOperationForY(int[][] matrixY)
    {  float[][] bufferY = new float[blockSize][blockSize];
        for (int u = 0; u < blockSize; u++)
        {
            for (int v = 0; v < blockSize; v++)
            {
                bufferY[u][v] =((float)0.25 )* DefinitionSet(u) *  DefinitionSet( v);
                float innerValY = 0;
                for (int x = 0; x < blockSize; x++)
                {
                    for (int y = 0; y < blockSize; y++)
                    {
                        double cosXU = Math.cos(((2 * x + 1) * u * Math.PI) / 16);
                        double cosYV = Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                        innerValY += matrixY[x][y]*cosXU*cosYV;
                    }
                }
                bufferY[u][v] *= innerValY;
                dctBlockY[u][v]=bufferY[u][v];
            }
        }
    }
    @Override
    public void DctOperationForCb(int[][] matrixCb)
    {  float[][] bufferCb = new float[blockSize][blockSize];
        for (int u = 0; u < blockSize; u++)
        {
            for (int v = 0; v < blockSize; v++)
            {
                bufferCb[u][v] =((float)0.25 )* DefinitionSet(u) *  DefinitionSet( v);
                float innerValCb = 0;
                for (int x = 0; x < blockSize; x++)
                {
                    for (int y = 0; y < blockSize; y++)
                    {
                        double cosXU = Math.cos(((2 * x + 1) * u * Math.PI) / 16);
                        double cosYV = Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                        innerValCb += matrixCb[x][y]*cosXU*cosYV;
                    }
                }
                bufferCb[u][v] *= innerValCb;
                dctBlockCb[u][v]=bufferCb[u][v];
            }
        }
    }
    @Override
    public void DctOperationForCr(int[][] matrixCr)
    {  float[][] bufferCr = new float[blockSize][blockSize];
        for (int u = 0; u < blockSize; u++)
        {
            for (int v = 0; v < blockSize; v++)
            {
                bufferCr[u][v] =((float)0.25 )* DefinitionSet(u) *  DefinitionSet(v);
                float innerValCr = 0;
                for (int x = 0; x < blockSize; x++)
                {
                    for (int y = 0; y < blockSize; y++)
                    {
                        double cosXU = Math.cos(((2 * x + 1) * u * Math.PI) / 16);
                        double cosYV = Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                        innerValCr += matrixCr[x][y]*cosXU*cosYV;
                    }
                }
                bufferCr[u][v] *= innerValCr;
                dctBlockCr[u][v]=bufferCr[u][v];
            }
        }
    }
    @Override
    public void IDct(int[][] matrix)
    {
       float[][] buffer = new float[blockSize][blockSize];
       int[][] iDctBlock=new int[blockSize][blockSize];
        for (int x = 0; x < 8; x++)
        {
            for (int y= 0; y < 8; y++)
            {
                buffer[x][y] =((float)0.25 );
                float innerVal = 0;
                for (int u = 0;u < 8; u++)
                {
                    for (int v = 0; v < 8; v++) {

                        double cosUX= Math.cos((2 * x + 1) * u * Math.PI / (double) 16);
                        double cosVY= Math.cos((2 * y + 1) * v * Math.PI / (double) 16);
                        innerVal  += matrix[u][v]*cosUX*cosVY *DefinitionSet(u) * DefinitionSet(v);
                    }
                }
                buffer[x][y] *= innerVal;
                iDctBlock[x][y]=(Math.round(buffer[x][y]));
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
