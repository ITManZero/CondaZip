package it.archiver.Structure.Tar;

import it.archiver.Structure.Tar.TarStructure.TarEntry;
import it.archiver.Structure.Tar.Operation.TarTools;


import java.io.*;

public class ExtractTarFile extends FilterInputStream{

    private TarEntry currentEntry;
    private long currentFileSize;
    private long bytesRead;
    private String destPath;
    private boolean defaultSkip = false;

    public ExtractTarFile(InputStream in) throws IOException {
        super(in);
        currentEntry = null;
        bytesRead = 0L;
        currentFileSize = 0L;
    }

    public ExtractTarFile(String inputFilePath, String destPath) throws IOException {
        this(new BufferedInputStream(new FileInputStream(inputFilePath)));
        TarTools.checkValidDestination(destPath);
        this.destPath = destPath;
        Extract();
    }


    @Override
    public int read() throws IOException {
        byte[] buf = new byte[1];
        int res = this.read(buf, 0, 1);
        return res != -1 ? buf[0] : res;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (this.currentEntry != null) {
            if (this.currentFileSize == this.currentEntry.getSize()) {
                return -1;
            }
            if (this.currentEntry.getSize() - this.currentFileSize < (long) len) {
                len = (int) (this.currentEntry.getSize() - this.currentFileSize);
            }
        }
        int br = super.read(b, off, len);
        if (br != -1) {
            if (this.currentEntry != null) {
                this.currentFileSize += br;
            }
            this.bytesRead += br;
        }
        return br;
    }

    private void Extract() throws IOException {
        TarEntry entry;
        while ((entry = getNextEntry()) != null) {
            if (entry.isDirectory()) createDirectory(entry);
            else ExtractFile(entry);
            closeEntry();
        }
        closeStream();
    }

    private void ExtractFile(TarEntry entry) throws IOException {
        int count;
        byte data[] = new byte[2048];
        FileOutputStream fos = new FileOutputStream(destPath + File.separatorChar + entry.getName());
        BufferedOutputStream dest = new BufferedOutputStream(fos);
        while ((count = read(data)) != -1)
            dest.write(data, 0, count);
        dest.flush();
        dest.close();
    }

    private void createDirectory(TarEntry entry) {
        new File(destPath + File.separator + entry.getName()).mkdirs();
    }


    public TarEntry getNextEntry() throws IOException {
        byte[] header = new byte[512];
        this.read(header, 0, 512);
        boolean thereIsHeader = true;
        int len = header.length;

        for (int i = 0; i < len; ++i) {
            byte b = header[i];
            if (b != 0) {
                thereIsHeader = false;
                break;
            }
        }
        if (!thereIsHeader)
            this.currentEntry = new TarEntry(header);
        return this.currentEntry;
    }


    protected void skipPad() throws IOException {
        if (this.bytesRead > 0L) {
            int extra = (int) (this.bytesRead % 512L);
            long res;
            if (extra > 0) {
                for (long bs = 0L; bs < (long) (512 - extra); bs += res) {
                    res = this.skip((long) (512 - extra) - bs);
                }
            }
        }
    }

    public long skip(long n) throws IOException {
        long left;
        if (this.defaultSkip) {
            left = super.skip(n);
            this.bytesRead += left;
            return left;
        } else if (n <= 0L) {
            return 0L;
        } else {
            left = n;
            int res;
            for (byte[] sBuff = new byte[2048]; left > 0L; left -= (long) res) {
                res = this.read(sBuff, 0, (int) (left < 2048L ? left : 2048L));
                if (res < 0) {
                    break;
                }
            }
            return n - left;
        }
    }

    public void closeEntry() throws IOException {
        if (this.currentEntry != null) {
            long res;
            if (this.currentEntry.getSize() > this.currentFileSize) {
                for (long bs = 0L; bs < this.currentEntry.getSize() - this.currentFileSize; bs += res) {
                    res = this.skip(this.currentEntry.getSize() - this.currentFileSize - bs);
                    if (res == 0L && this.currentEntry.getSize() - this.currentFileSize > 0L) {
                        throw new IOException("Possible tar file corruption");
                    }
                }
            }
        }
        currentEntry = null;
        currentFileSize = 0L;
        skipPad();
    }

    private void closeStream() throws IOException {
        super.close();
    }
}
