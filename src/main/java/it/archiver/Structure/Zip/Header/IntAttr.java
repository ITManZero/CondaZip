package it.archiver.Structure.Zip.Header;

import it.archiver.Structure.Zip.Operation.ZipArchiver;

public class IntAttr extends HeaderComponent<Long> {

    public IntAttr(long value , int offset , int length) {
        this.value  = value;
        this.offset = offset;
        this.length = length;

    }

    public Object read(byte[] buffer) { return ZipArchiver.read(buffer, offset, length); }

    public void write(byte[] buffer) { ZipArchiver.write(buffer , offset , length , value); }


}
