package it.archiver.Structure.Zip.Operation;

public interface DePackData {
    //if i want to do read inside the obj then i have to change parameters
    Object read(byte[] buffer);
}
