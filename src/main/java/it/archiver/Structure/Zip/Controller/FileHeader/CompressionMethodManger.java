package it.archiver.Structure.Zip.Controller.FileHeader;

import it.archiver.Structure.Zip.Factories.DecoderFactory;
import it.archiver.Structure.Zip.Factories.EncodeFactory;
import it.archiver.Structure.Zip.Operation.*;

public class CompressionMethodManger {
    public static Encoder getEncoder(String path){
        return EncodeFactory.getEncoder(getType(path) , path);
    }

    public static Decoder getDecoder(int  value){
        return DecoderFactory.getDecoder(value);
    }

    private static String getType(String fileName){
        String type = null;
        for(int i = fileName.length()-1 ; i > 0 ; i --){
            if (fileName.charAt(i) == '.'){
              type = fileName.substring(i+1);
              break;
            }
        }
        return type;
    }

    public static int getCompMethod(Encoder encoder){
        if(encoder instanceof HuffmanEncoder)
            return 21;
        if(encoder instanceof DefaultDecoder || encoder instanceof PhotoEncoder)
            return 0;
        return 0;
    }
}
