package it.archiver.Structure.Zip.Operation;

import java.io.*;

public class DefaultEncoder implements Encoder {
    private long length;
    public DefaultEncoder(String path){
        length = new File(path).length();
    }
    @Override
    public void write(FilterOutputStream filterOutputStream , String path) throws IOException {
        File f = new File(path);
        if(!f.isDirectory()){
            //read where to read Data
            BufferedInputStream origin = new BufferedInputStream(new FileInputStream(new File(path)));
            int count;
            byte data[] = new byte[1028];

            //write data on stream ( Zip Struct )
            while ((count = origin.read(data)) != -1) {
                filterOutputStream.write(data, 0, count);
            }
            origin.close();
            length = f.length();
        }
        else length=0;
    }

    public long getLength() {
        return length;
    }
}