package it.archiver.Structure.Tar.TarStructure;

import java.io.File;
import java.util.Date;

import it.archiver.Structure.Tar.Operation.TarTools;
import it.archiver.Structure.Tar.TarConstant.FileTypes;

/**
 * TarEntry it's representing file and header
 * each file or directory has one entry
 * the structure of entry consisting of header and data of file
 */
public class TarEntry {

    private File file;
    private TarHeader header;

    private TarEntry() {
        this.file = null;
        this.header = new TarHeader();
    }

    public TarEntry(File file, String entryName) {
        this();
        this.file = file;
        this.createEntryHeader(entryName);
    }

    public TarEntry(TarHeader header) {
        this.file = null;
        this.header = header;
    }

    public TarEntry(byte[] headerBuf) {
        this();
        this.parseTarHeader(headerBuf);
    }


    public boolean isDirectory() {
        if (this.file != null)
            return this.file.isDirectory();

        if (this.header != null)
            if (this.header.getFileType().getValue() == FileTypes.DIRECTORY)
                return true;

        return false;
    }

    /**
     * when we create a TarEntry we should create header for this file
     *
     * @param entryName name of file
     */

    private void createEntryHeader(String entryName) {

        header.getLinkName().setValue(new StringBuffer());
        header.getName().setValue(new StringBuffer(entryName));

        if (file.isDirectory()) {
            header.getFileMode().setValue(040755);
            header.getFileType().setValue(FileTypes.DIRECTORY);
            if (header.getName().getValue().charAt(header.getName().getValue().length() - 1) != '/')
                header.getName().getValue().append("/");
            header.getSize().setValue((long) 0);
        } else {
            header.getFileMode().setValue(0100644);
            header.getFileType().setValue(FileTypes.NORMAL);
            header.getSize().setValue(file.length());
        }
        header.getLastModificationTime().setValue(file.lastModified());
        header.getCheckSum().setValue(0);
        header.getDevMajor().setValue(0);
        header.getDevMinor().setValue(0);
    }

    /**
     * converting values of each header component to bytes
     * and storing them in header block  ize [512] with specific order
     *
     * @param outputBuffer output header block it's array of bytes -->byte[512]
     *                     we will store this buffer in tar file
     */
    public void writeEntryHeaderToByteBuffer(byte[] outputBuffer) {
        TarTools.nameToBytes(header.getName(), outputBuffer);
        TarTools.integerOctalToBytes(header.getFileMode(), outputBuffer);
        TarTools.integerOctalToBytes(header.getUserId(), outputBuffer);
        TarTools.integerOctalToBytes(header.getGroupId(), outputBuffer);
        TarTools.longOctalToBytes(header.getSize(), outputBuffer);
        TarTools.longOctalToBytes(header.getLastModificationTime(), outputBuffer);
        for (int i = 0; i < header.getCheckSum().getLength(); ++i)
            outputBuffer[header.getCheckSum().getOffset() + i] = (byte) ' ';
        outputBuffer[header.getFileType().getOffset()] = header.getFileType().getValue();
        TarTools.nameToBytes(header.getLinkName(), outputBuffer);
        TarTools.nameToBytes(header.getMagic(), outputBuffer);
        TarTools.nameToBytes(header.getUserName(), outputBuffer);
        TarTools.nameToBytes(header.getGroupName(), outputBuffer);
        TarTools.integerOctalToBytes(header.getDevMajor(), outputBuffer);
        TarTools.integerOctalToBytes(header.getDevMinor(), outputBuffer);
        int temp = header.getDevMinor().getOffset() + header.getDevMinor().getLength();
        for (; temp < outputBuffer.length; )
            outputBuffer[temp++] = 0;
        header.getCheckSum().setValue((int) TarTools.computeCheckSum(outputBuffer));
        TarTools.getCheckSumOctalBytes(header.getCheckSum(), outputBuffer);
    }

    /**
     * converting header buffer to data it's mean Restore the data to what it was
     *
     * @param inputBuffer input header block it's the read header from tar file
     *                    we will get file name and other components
     */
    public void parseTarHeader(byte[] inputBuffer) {
        header.getName().setValue(TarTools.parseName(inputBuffer, header.getName().getOffset(), header.getName().getLength()));
        header.getFileMode().setValue((int) TarTools.parseOctal(inputBuffer, header.getFileMode().getOffset(), header.getFileMode().getLength()));
        header.getUserId().setValue((int) TarTools.parseOctal(inputBuffer, header.getUserId().getOffset(), header.getUserId().getLength()));
        header.getGroupId().setValue((int) TarTools.parseOctal(inputBuffer, header.getGroupId().getOffset(), header.getGroupId().getLength()));
        header.getSize().setValue(TarTools.parseOctal(inputBuffer, header.getSize().getOffset(), header.getSize().getLength()));
        header.getLastModificationTime().setValue(TarTools.parseOctal(inputBuffer, header.getLastModificationTime().getOffset(), header.getLastModificationTime().getLength()));
        header.getCheckSum().setValue((int) TarTools.parseOctal(inputBuffer, header.getCheckSum().getOffset(), header.getCheckSum().getLength()));
        header.getFileType().setValue(inputBuffer[header.getFileType().getOffset()]);
        header.getLinkName().setValue(TarTools.parseName(inputBuffer, header.getLinkName().getOffset(), header.getLinkName().getLength()));
        header.getMagic().setValue(TarTools.parseName(inputBuffer, header.getMagic().getOffset(), header.getMagic().getLength()));
        header.getUserName().setValue(TarTools.parseName(inputBuffer, header.getUserName().getOffset(), header.getUserName().getLength()));
        header.getGroupName().setValue(TarTools.parseName(inputBuffer, header.getGroupName().getOffset(), header.getGroupName().getLength()));
        header.getDevMajor().setValue((int) TarTools.parseOctal(inputBuffer, header.getDevMajor().getOffset(), header.getDevMajor().getLength()));
        header.getDevMinor().setValue((int) TarTools.parseOctal(inputBuffer, header.getDevMinor().getOffset(), header.getDevMinor().getLength()));
    }


    public TarHeader getHeader() {
        return this.header;
    }

    public String getName() {
        return this.header.getName().getValue().toString();
    }

    public void setName(String name) {
        this.header.getName().setValue(new StringBuffer(name));
    }

    public int getUserId() {
        return this.header.getUserId().getValue();
    }

    public void setUserId(int userId) {
        this.header.getUserId().setValue(userId);
    }

    public int getGroupId() {
        return this.header.getGroupId().getValue();
    }

    public void setGroupId(int groupId) {
        this.header.getGroupId().setValue(groupId);
    }

    public String getUserName() {
        return this.header.getUserName().getValue().toString();
    }

    public void setUserName(String userName) {
        this.header.getUserName().setValue(new StringBuffer(userName));
    }

    public String getGroupName() {
        return this.header.getGroupName().getValue().toString();
    }

    public void setGroupName(String groupName) {
        this.header.getGroupName().setValue(new StringBuffer(groupName));
    }

    public void setIds(int userId, int groupId) {
        this.setUserId(userId);
        this.setGroupId(groupId);
    }

    public Date getLastModificationTime() {
        return new Date(this.header.getLastModificationTime().getValue());
    }

    public void setLastModificationTime(long time) {
        this.header.getLastModificationTime().setValue(time);
    }

    public void setLastModificationTime(Date time) {
        this.header.getLastModificationTime().setValue(time.getTime());
    }

    public File getFile() {
        return this.file;
    }

    public long getSize() {
        return this.header.getSize().getValue();
    }

    public void setSize(long size) {
        this.header.getSize().setValue(size);
    }

}

/*
        for (int i = 0; i < inputBuffer.length; i++) {
            if (i % 10 == 0)
                System.out.println();
            if (i == 0) System.out.println("name");
            if (i == 100) System.out.println("\nfile mode");
            if (i == 108) System.out.println("\nuser id");
            if (i == 116) System.out.println("\ngroup id");
            if (i == 124) System.out.println("\nsize");
            if (i == 136) System.out.println("\ntime");
            if (i == 148) System.out.println("\nchecksum");
            if (i == 156) System.out.println("\nfile type");
            if (i == 157) System.out.println("\nlink name");
            if (i == 256) System.out.println("\nmagic");
            if (i == 265) System.out.println("\nusername");
            if (i == 297) System.out.println("\ngroupName");
            if (i == 329) System.out.println("\nmajor");
            if (i == 337) System.out.println("\nminor");
            System.out.print(inputBuffer[i] + " ");
        }
 */