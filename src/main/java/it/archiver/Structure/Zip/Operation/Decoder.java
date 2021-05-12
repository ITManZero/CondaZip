package it.archiver.Structure.Zip.Operation;



public interface Decoder {
    void read(String pathZip, String name, String destPath, long size, long skip);
}
