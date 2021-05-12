package it.archiver.Structure.Zip.Operation;

import it.archiver.Huffman.HuffmanCompress;

import java.io.*;

public class HuffmanEncoder implements Encoder {
    private File file;

    public HuffmanEncoder(String path) {
        HuffmanCompress huffmanCompress = new HuffmanCompress(path,"");
        file = huffmanCompress.compress();
        getLength();
    }

    @Override
    public void write(FilterOutputStream filterOutputStream, String path) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int count;
            while ((count=bufferedInputStream.read()) != -1){
                filterOutputStream.write(count);
            }
            bufferedInputStream.close();
            file.delete();
    }

    @Override
    public long getLength() {
        return file.length();
    }
}
