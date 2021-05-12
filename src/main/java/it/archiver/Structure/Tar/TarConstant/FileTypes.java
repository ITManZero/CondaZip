package it.archiver.Structure.Tar.TarConstant;

public class FileTypes {

    public static final byte NORMAL = (byte) '0';
    public static final byte HARD_LINK = (byte) '1';
    public static final byte SYMBOLIC_LINK = (byte) '2';
    public static final byte CHARACTER_SPECIAL = (byte) '3';
    public static final byte BLOCK_SPECIAL = (byte) '4';
    public static final byte DIRECTORY = (byte) '5';
    public static final byte FIFO = (byte) '6';
    public static final byte CONTIGUOUS_FILE = (byte) '7';
}
