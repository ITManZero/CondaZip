package it.archiver.Structure.Zip.Operation;


import it.archiver.PhotoCompression.Jpeg.jpeg;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

public class PhotoEncoder implements Encoder {
    private long length;
    public PhotoEncoder(String path){

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        jpeg jpeg = new jpeg(path , 1 , 8 , "low" , os);
        length = os.size();

    }
    public PhotoEncoder(){

    }
    @Override
    public void write(FilterOutputStream filterOutputStream, String path) throws IOException {
        jpeg jpeg = new jpeg(path , 1 , 8 , "low" , filterOutputStream);
    }
    public long getLength(){
        return length;
    }
}
