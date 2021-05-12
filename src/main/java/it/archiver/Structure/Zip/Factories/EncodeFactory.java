package it.archiver.Structure.Zip.Factories;

import it.archiver.Structure.Zip.Operation.DefaultEncoder;
import it.archiver.Structure.Zip.Operation.Encoder;
import it.archiver.Structure.Zip.Operation.HuffmanEncoder;
import it.archiver.Structure.Zip.Operation.PhotoEncoder;

public class EncodeFactory {


    public static Encoder getEncoder(String string, String path) {
        try {
            if (string.equals("txt"))
                return new HuffmanEncoder(path);

            if (Values.bakr && (string.equals("jpg") || string.equals("png"))){

                return new PhotoEncoder(path);

            }

            else
                return new DefaultEncoder(path);
        } catch (NullPointerException e) {
            return new DefaultEncoder(path);
        }
    }

}
