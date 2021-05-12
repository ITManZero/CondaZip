package it.archiver.Structure.Zip.Factories;

import it.archiver.Structure.Zip.Operation.Decoder;
import it.archiver.Structure.Zip.Operation.DefaultDecoder;
import it.archiver.Structure.Zip.Operation.HuffmanDecoder;

public class DecoderFactory {
    public static Decoder getDecoder(int value){

        if (value == 21)
            return new HuffmanDecoder();

//        else if (value == 41))
//            return new JamalDecoder;
//
//        else
        return new DefaultDecoder();
    }
}
