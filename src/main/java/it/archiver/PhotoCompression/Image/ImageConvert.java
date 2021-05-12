package it.archiver.PhotoCompression.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageConvert {

    private int [][] Y;
    private int [][] Cb;
    private int [][] Cr;
    private int width;
    private int height;
    private BufferedImage bufferedImage;



    public ImageConvert(String fileName){

        try {
            File file = new File(fileName);
            bufferedImage= ImageIO.read(file);
            this.width=bufferedImage.getWidth();
            this.height=bufferedImage.getHeight();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }


        this.Y = new int[width][height];
        this.Cb = new int[width][height];
        this.Cr = new int[width][height];

        convert(bufferedImage);

    }
    private void convert(BufferedImage bufferedImage) {
       int getIntegerPixels,redPixels,bluePixels,greenPixels;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                getIntegerPixels = bufferedImage.getRGB(i, j);

                redPixels = ((getIntegerPixels & 0xff0000) >> 16);
                greenPixels = ((getIntegerPixels & 0x00ff00) >> 8);
                bluePixels = ((getIntegerPixels & 0x0000ff));

                Y[i][j] = (int) ((0.299 * (float) redPixels) + (0.587 * (float) greenPixels) + (0.114 * (float) bluePixels));
                Cb[i][j] = (int) ((-0.169 * (float) redPixels) + (-0.331 * (float) greenPixels) + (0.500 * (float) bluePixels)) + 128;
                Cr[i][j] = (int) ((0.500 * (float) redPixels) + (-0.419 * (float) greenPixels) + (-0.081 * (float) bluePixels)) + 128;
            }
        }
    }
    public int[][] getY() { return Y;}
    public int[][] getCb() {return Cb; }
    public int[][] getCr() { return Cr;}

    public int getWidth(){return width;}
    public int getHeight(){return height;}

}
