package it.archiver.Structure.Zip.Header;

import it.archiver.Structure.Zip.Operation.ZipArchiver;

import java.io.IOException;

public class DiskNumber extends HeaderComponent<Long> {

    public DiskNumber(long value , int offset , int length) {
        this.value  = value;
        this.offset = offset;
        this.length = length;

    }
    @Override
    public Object read(byte[] buffer) {
        return null;
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        ZipArchiver.write(buffer , offset , length , value);
    }
}
