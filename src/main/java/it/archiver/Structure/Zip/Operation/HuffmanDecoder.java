package it.archiver.Structure.Zip.Operation;

import it.archiver.Huffman.HuffmanDeCompress;

public class HuffmanDecoder implements Decoder {
    @Override
    public void read(String pathZip, String name, String destPath, long size , long skip) {
        HuffmanDeCompress huffmanDeCompress = new HuffmanDeCompress(pathZip,size,skip,name,destPath);
        huffmanDeCompress.decompress();
    }
}