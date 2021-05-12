package it.archiver.Structure.Zip.Header;

import it.archiver.Structure.Zip.Operation.ZipArchiver;

public class FileComment extends HeaderComponent<Long>{

    public FileComment(long value , int offset , int length) {
        this.value  = value;
        this.offset = offset;
        this.length = length;

    }

    public Object read(byte[] buffer) {

        if(value != -1) return ZipArchiver.read(buffer, offset, length);

        return null;
    }

    public void write(byte[] buffer) {
            ZipArchiver.write(buffer , offset , length , value);
    }

}
