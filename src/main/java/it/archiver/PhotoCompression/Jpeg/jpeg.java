package it.archiver.PhotoCompression.Jpeg;


import it.archiver.PhotoCompression.DCT.DCT;
import it.archiver.PhotoCompression.DCT.DCTFactory;
import it.archiver.PhotoCompression.Image.ImageConvert;
import it.archiver.PhotoCompression.Quantization.Quantization;
import it.archiver.PhotoCompression.Tool.Tool;
import it.archiver.PhotoCompression.Tool.ZigZagScan;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;


public class jpeg {

    private int blockSize;
    private ImageConvert imageConvert;
    private int[][] blockArrayY;
    private int[][] blockArrayCb;
    private int[][] blockArrayCr;
    private Tool tool;
    private DCT dct;
    private Quantization quantization;
    private ZigZagScan zigZagScan;
    BufferedImage image;

    public jpeg(String filename, int quality, int blockSize, String bitRate, OutputStream os) {
        // load image and information
        this.imageConvert = new ImageConvert(filename);

        // for better quantization
        String high = "High";
        int bitRate1;
        if (high.equals(bitRate)) {
            bitRate1 = 1;
        } else {
            bitRate1 = 0;
        }

        // is constant 8 for Jpeg
        this.blockSize = blockSize;
        this.blockArrayY = new int[blockSize][blockSize];
        this.blockArrayCb = new int[blockSize][blockSize];
        this.blockArrayCr = new int[blockSize][blockSize];

        // average for pixels in cb and cr and reduce
        this.tool = new Tool((imageConvert.getWidth()), (imageConvert.getHeight()), imageConvert.getCb(), imageConvert.getCr(), imageConvert.getY(), blockSize, os);

        // DCT operation
        this.dct = DCTFactory.FastDct.getInstance();

        // Quantization operation
        if (blockSize == 8) {
            this.quantization = new Quantization(quality, "jpeg", bitRate1);
        }

        // ZigZag Scan
        this.zigZagScan = new ZigZagScan(blockSize);

        // START DIVIDED AND COMPRESS
        Jpeg();

    }

    private void Jpeg() {
        int xPos;
        int yPos;
        for (int i = 0; i < imageConvert.getWidth() / blockSize; i++) {
            for (int j = 0; j < imageConvert.getHeight() / blockSize; j++) {
                xPos = i * blockSize;
                yPos = j * blockSize;
                for (int a = 0; a < blockSize; a++) {
                    for (int b = 0; b < blockSize; b++) {
                        blockArrayY[a][b] = tool.getReduceArrayForY()[xPos + a][yPos + b];

                        if (i < ((imageConvert.getWidth() / blockSize) / 2) && j < ((imageConvert.getHeight() / blockSize) / 2)) {
                            blockArrayCb[a][b] = tool.averageArrayForCb()[xPos + a][yPos + b];
                            blockArrayCr[a][b] = tool.averageArrayForCr()[xPos + a][yPos + b];

                        }
                    }
                }

                dct.DctOperationForY(blockArrayY);
                if (i < ((imageConvert.getWidth() / blockSize) / 2) && j < ((imageConvert.getHeight() / blockSize) / 2)) {
                    dct.DctOperationForCb(blockArrayCb);
                    dct.DctOperationForCr(blockArrayCr);
                }

                quantization.dctQuantizationForYJpeg(dct.getDctBlockY());
                if (i < ((imageConvert.getWidth() / blockSize) / 2) && j < ((imageConvert.getHeight() / blockSize) / 2)) {
                    quantization.dctQuantizationForCbJpeg(dct.getDctBlockCb());
                    quantization.dctQuantizationForCrJpeg(dct.getDctBlockCr());
                }


                zigZagScan.zigZagMatrixNew(quantization.getQuantizationY(), blockSize, blockSize);
                zigZagScan.inverseZitZackMatrixNew(zigZagScan.getD1(), blockSize, blockSize);
                quantization.deQuantizationY(zigZagScan.getD2());
                dct.IDct(quantization.getDeQuantizationY());

                if (i < ((imageConvert.getWidth() / blockSize) / 2) && j < ((imageConvert.getHeight() / blockSize) / 2)) {
                    zigZagScan.zigZagMatrixNew(quantization.getQuantizationCb(), blockSize, blockSize);
                    zigZagScan.inverseZitZackMatrixNew(zigZagScan.getD1(), blockSize, blockSize);
                    quantization.deQuantizationCb(zigZagScan.getD2());
                    dct.IDct(quantization.getDeQuantizationCb());


                    zigZagScan.zigZagMatrixNew(quantization.getQuantizationCr(), blockSize, blockSize);
                    zigZagScan.inverseZitZackMatrixNew(zigZagScan.getD1(), blockSize, blockSize);
                    quantization.deQuantizationCb(zigZagScan.getD2());
                    dct.IDct(quantization.getDeQuantizationCb());


                }
            }
        }
        tool.initializeForDec(dct.getIDctBlocks());
        image = tool.getMyImage();
    }

    public BufferedImage getImage() {
        return image;
    }

    public static boolean isImage(File file) {
        try {
            return ImageIO.read(file) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
