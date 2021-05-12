package it.archiver.Structure.Tar.TarStructure;

import it.archiver.Structure.Tar.TarConstant.FileTypes;

public class TarHeader {
    /**
     * The magic tag representing a POSIX tar archive.
     */
    private static final String POSIX_MAGIC = "ustar" + (char) 0 + "00";

    /**
     * The magic tag representing a GNU tar archive.
     */
    private static final String GNU_MAGIC = "ustar  ";

    /**
     * Header of a Structure.Tar FILE
     * Size     Length      Description
     * 100        0         Name of file
     * 8         100        File mode in octal
     * 8         108        User ID of file owner in octal
     * 8         116        Group ID of file owner in octal
     * 12        124        File size in octal
     * 12        136        File date in octal. Seconds since 0:00 January 1, 1970
     * 8         148        Checksum of header
     * 1         156        Type of link
     * 100       157        Name of linked file
     * 8         257        Magic value ustar followed by 2 blanks and a null
     * 32        265        User name
     * 32        297        Group name
     * 8         329        Device major number in octal
     * 8         337        Device minor number in octal
     **/
    private HeaderComponent<StringBuffer> name;
    private HeaderComponent<Integer> fileMode;
    private HeaderComponent<Integer> userId;
    private HeaderComponent<Integer> groupId;
    private HeaderComponent<Long> size;
    private HeaderComponent<Long> lastModificationTime;
    private HeaderComponent<Integer> checkSum;
    private HeaderComponent<Byte> fileType; //or linkFlag
    private HeaderComponent<StringBuffer> linkName;
    private HeaderComponent<StringBuffer> magic;
    private HeaderComponent<StringBuffer> userName;
    private HeaderComponent<StringBuffer> groupName;
    private HeaderComponent<Integer> devMajor;
    private HeaderComponent<Integer> devMinor;

    /**
     * initializing header Component
     * Each has length and offset
     * and value
     * we will convert this value to byte array so we can store it
     */
    public TarHeader() {
        this.name = new HeaderComponent<>((byte) 100, 0, new StringBuffer());
        this.fileMode = new HeaderComponent<>((byte) 8, 100, null);
        this.userId = new HeaderComponent<>((byte) 8, 108, 0);
        this.groupId = new HeaderComponent<>((byte) 8, 116, 0);
        this.size = new HeaderComponent<>((byte) 12, 124, null);
        this.lastModificationTime = new HeaderComponent<>((byte) 12, 136, null);
        this.checkSum = new HeaderComponent<>((byte) 8, 148, null);
        this.fileType = new HeaderComponent<>((byte) 1, 156, null);
        this.linkName = new HeaderComponent<>((byte) 100, 157, new StringBuffer());
        this.magic = new HeaderComponent<>((byte) 8, 257, new StringBuffer(TarHeader.POSIX_MAGIC));
        String user = System.getProperty("user.name", "");
        if (user.length() > 31)
            user = user.substring(0, 31);
        this.userName = new HeaderComponent<>((byte) 32, 265, new StringBuffer(user));
        this.groupName = new HeaderComponent<>((byte) 32, 297, new StringBuffer());
        this.devMajor = new HeaderComponent<>((byte) 8, 329, null);
        this.devMinor = new HeaderComponent<>((byte) 8, 337, null);
    }


    /**
     * create file header with specific size and ModificationTime and name
     *
     * @param fileName
     * @param fileSize
     * @param lastModificationTime
     * @return
     */

    public static TarHeader createFileHeader(String fileName, long fileSize, long lastModificationTime) {
        TarHeader header = new TarHeader();
        header.getLinkName().setValue(new StringBuffer());
        header.getName().setValue(new StringBuffer(fileName));
        header.getFileMode().setValue(0100644);
        header.getFileType().setValue(FileTypes.NORMAL);
        header.getSize().setValue(fileSize);
        header.getLastModificationTime().setValue(lastModificationTime);
        header.getCheckSum().setValue(0);
        header.getDevMajor().setValue(0);
        header.getDevMinor().setValue(0);
        return header;
    }

    /**
     * create directory header with specific ModificationTime and name
     *
     * @param dirName
     * @param lastModificationTime
     * @return
     */
    public static TarHeader createDirHeader(String dirName, long lastModificationTime) {
        TarHeader header = new TarHeader();
        header.getLinkName().setValue(new StringBuffer());
        header.getName().setValue(new StringBuffer(dirName));
        header.getFileMode().setValue(040755);
        header.getFileType().setValue(FileTypes.DIRECTORY);
        if (header.getName().getValue().charAt(header.getName().getValue().length() - 1) != '/')
            header.getName().getValue().append("/");
        header.getSize().setValue((long) 0);
        header.getLastModificationTime().setValue(lastModificationTime);
        header.getCheckSum().setValue(0);
        header.getDevMajor().setValue(0);
        header.getDevMinor().setValue(0);
        return header;
    }

    /**
     * class representing header Component
     * each component has constant of length offset
     * and a value of T type
     *
     * @param <T>
     */
    public class HeaderComponent<T> {

        private final byte length;
        private final int offset;
        private T value;

        public HeaderComponent(byte length, int offset, T value) {
            this.length = length;
            this.offset = offset;
            this.value = value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public byte getLength() {
            return length;
        }

        public int getOffset() {
            return offset;
        }

        public T getValue() {
            return value;
        }
    }

    public HeaderComponent<StringBuffer> getName() {
        return name;
    }

    public HeaderComponent<Integer> getFileMode() {
        return fileMode;
    }

    public HeaderComponent<Integer> getUserId() {
        return userId;
    }

    public HeaderComponent<Integer> getGroupId() {
        return groupId;
    }

    public HeaderComponent<Long> getSize() {
        return size;
    }

    public HeaderComponent<Long> getLastModificationTime() {
        return lastModificationTime;
    }

    public HeaderComponent<Integer> getCheckSum() {
        return checkSum;
    }

    public HeaderComponent<Byte> getFileType() {
        return fileType;
    }

    public HeaderComponent<StringBuffer> getLinkName() {
        return linkName;
    }

    public HeaderComponent<StringBuffer> getMagic() {
        return magic;
    }

    public HeaderComponent<StringBuffer> getUserName() {
        return userName;
    }

    public HeaderComponent<StringBuffer> getGroupName() {
        return groupName;
    }

    public HeaderComponent<Integer> getDevMajor() {
        return devMajor;
    }

    public HeaderComponent<Integer> getDevMinor() {
        return devMinor;
    }
}
