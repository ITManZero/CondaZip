package it.archiver.Structure.Zip.Operation;

import java.io.FilterOutputStream;
import java.io.IOException;

public interface Encoder {
    void write(FilterOutputStream filterOutputStream, String path) throws IOException;
    long getLength();
}
