package it.archiver.Structure.Zip.Header;


import it.archiver.Structure.Zip.Operation.ZipArchiver;

/*
The name of the file, with optional relative path.
       The path stored MUST NOT contain a drive or
       device letter, or a leading slash.  All slashes
       MUST be forward slashes '/' as opposed to
       backwards slashes '\' for compatibility with Amiga
       and UNIX file systems etc.  If input came from standard
       input, there is no file name field



 */
public class FileName extends HeaderComponent<String> {

    public FileName(String value, int offset, int length) {
        this.value = value;
        this.offset = offset;
        this.length = length;

    }


    public Object read(byte[] buffer) {

        if (length != -1) return ZipArchiver.read(buffer, offset, length);
        return null;
    }

    public void write(byte[] buffer) {
        byte[] names = new byte[(int) length];
        if (length != -1) {
            ZipArchiver.nameToBytes(names, value);
            ZipArchiver.writeArray(buffer, offset, length, names);
        }
    }

}
