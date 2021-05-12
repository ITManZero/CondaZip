package it.archiver.Structure.Zip.Operation;

import java.io.IOException;

public interface PackData {

    //if i want to do write inside the obj then i have to change parameters
    void write(byte[] buffer) throws IOException;
}
