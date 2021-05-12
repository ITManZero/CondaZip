package it.archiver.PhotoCompression.Tool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class Tool {
    private int[][] Y;
    private int[][] aveCb;
    private int[][] aveCr;
    private int[][] Cb;
    private int[][] Cr;
    private int width;
    private int height;
    private int blockSize;
    private BufferedImage myImage;
    private OutputStream os;

    public Tool(int width, int height, int[][] cb, int[][] cr, int[][] y, int blockSize, OutputStream os) {
        this.aveCb = new int[width / 2][height / 2];
        this.aveCr = new int[width / 2][height / 2];
        this.os = os;
        this.Y = y;
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        reduceAndAverage(cb, cr, width, height);
        this.Cb = new int[4][4];
        this.Cr = new int[4][4];
    }

    private void reduceAndAverage(int[][] cb, int[][] cr, int width, int height) {
        int xPos;
        int yPos;
        for (int i = 0; i < (width); i++) {
            for (int j = 0; j < (height); j++) {
                Y[i][j] -= 128;
                if (i < (width / 2) && j < (height / 2)) {
                    xPos = i * 2;
                    yPos = j * 2;
                    for (int a = 0; a < 2; a++) {
                        for (int b = 0; b < 2; b++) {
                            aveCb[i][j] += cb[xPos + a][yPos + b];
                            aveCr[i][j] += cr[xPos + a][yPos + b];
                        }
                    }

                    aveCb[i][j] /= 4;
                    aveCr[i][j] /= 4;

                    aveCb[i][j] -= 128;
                    aveCr[i][j] -= 128;
                }
            }
        }
    }

    public void initializeForDec(Vector<int[][]> iBlocks) {

        int xPosLu;
        int yPosLu;
        int xPosCh;
        int yPosCh;
        int index = 0;
        for (int i = 0; i < width / blockSize; i++) {
            for (int j = 0; j < height / blockSize; j++) {
                xPosLu = i * blockSize;
                yPosLu = j * blockSize;
                xPosCh = i * blockSize;
                yPosCh = j * blockSize;

                for (int a = 0; a < blockSize; a++) {
                    for (int b = 0; b < blockSize; b++) {
                        Y[xPosLu + a][yPosLu + b] = iBlocks.get(index)[a][b];
                        Y[xPosLu + a][yPosLu + b] += 128;
                    }
                }

                index++;
                if (i < ((width / blockSize) / 2) && j < ((height / blockSize) / 2)) {
                    for (int a = 0; a < blockSize; a++) {
                        System.arraycopy(iBlocks.get(index)[a], 0, aveCb[xPosCh + a], yPosCh, blockSize);
                    }
                    index++;
                    for (int a = 0; a < blockSize; a++) {
                        System.arraycopy(iBlocks.get(index)[a], 0, aveCr[xPosCh + a], yPosCh, blockSize);
                    }
                    index++;
                }
            }
        }
        myImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int xPos;
        int yPos;
        for (int i = 0; i < (width / 2); i++) {
            for (int j = 0; j < (height / 2); j++) {
                xPos = i * 2;
                yPos = j * 2;
                aveCb[i][j] += 128;
                aveCr[i][j] += 128;
                for (int a = 0; a < 2; a++) {
                    for (int b = 0; b < 2; b++) {
                        Cb[a][b] = aveCb[i][j];
                        Cr[a][b] = aveCr[i][j];
                    }
                }
                for (int a = 0; a < 2; a++) {
                    for (int b = 0; b < 2; b++) {
                        int R = (int) (((float) Y[xPos + a][yPos + b]) + (1.402 * ((float) Cr[a][b] - 128)));
                        int G = (int) (((float) Y[xPos + a][yPos + b]) + (-0.343 * ((float) Cb[a][b] - 128)) + (-0.711 * ((float) Cr[a][b] - 128)));
                        int B = (int) (((float) Y[xPos + a][yPos + b]) + (1.765 * ((float) Cb[a][b] - 128)));

                        if (R < 0) {
                            R = 0;
                        }
                        if (G < 0) {
                            G = 0;
                        }
                        if (B < 0) {
                            B = 0;
                        }

                        int color = ((R & 0x0ff) << 16) | ((G & 0x0ff) << 8) | ((B & 0x0ff));
                        myImage.setRGB(xPos + a, yPos + b, color);
                    }
                }

            }
        }
        try {
            ImageIO.write(myImage, "JPEG", os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        iBlocks.clear();
    }

    public int[][] averageArrayForCb() {
        return aveCb;
    }

    public int[][] averageArrayForCr() {
        return aveCr;
    }

    public int[][] getReduceArrayForY() {
        return Y;
    }

    public BufferedImage getMyImage() {
        return myImage;
    }
}
