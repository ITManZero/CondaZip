package it.archiver.Structure.Tar;

import it.archiver.Controller_Files.Controller_Process;
import it.archiver.PhotoCompression.Jpeg.jpeg;
import it.archiver.Structure.Tar.TarConstant.TarBlockSize;
import it.archiver.Structure.Tar.TarStructure.TarEntry;
import it.archiver.Structure.Tar.Operation.TarTools;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Structure.Tar File consisting of list of entries
 * also each entry contains header of current file and date of current file
 */
public class CreateTarFile extends FilterOutputStream {

    private String outputFileName;
    private TarEntry currentEntry;
    private long bytesWritten;
    private long currentFileSize;
    private boolean entryIsDir;
    private int mode;
    private boolean usePhotosCompression;
    private Controller_Process controller;

    private CreateTarFile(String outputFileName, boolean usePhotosCompression, String destPath, Controller_Process controller) throws FileNotFoundException {
        super(new BufferedOutputStream(new FileOutputStream(destPath + outputFileName + ".tar.temp")));
        currentEntry = null;
        bytesWritten = 0;
        currentFileSize = 0;
        entryIsDir = false;
        this.controller = controller;
        this.usePhotosCompression = usePhotosCompression;
    }

    /**
     * @param outputFileName name of tar file
     * @param destPath       position of tar file
     * @param fileList       selected files to archive
     * @throws IOException
     */

    public CreateTarFile(String outputFileName, boolean usePhotosCompression, int mode, String destPath, Controller_Process controller, File... fileList) throws IOException {
        this(outputFileName, usePhotosCompression, destPath, controller);
        this.mode = mode;
        switch (mode) {
            case 0:
                addFilesToTar(false, fileList);
                this.outputFileName = TarTools.autoRename(new File(destPath + outputFileName + ".tar.temp"), destPath);
                break;
            case 1:
                String fixedName = TarTools.fixOutputFileName(destPath + outputFileName + ".tar.temp");
                if (new File(fixedName).exists())
                    new File(fixedName).delete();
                this.outputFileName = outputFileName + ".tar";
                addFilesToTar(false, fileList);
                TarTools.renameTarFile(new File(destPath + outputFileName + ".tar.temp"), destPath);
                break;
            case 2:
                this.outputFileName = outputFileName + ".tar";
                fixedName = TarTools.fixOutputFileName(destPath + outputFileName + ".tar.temp");
                updateTarFile(destPath, fileList);
                if (new File(fixedName).exists())
                    new File(fixedName).delete();
                TarTools.renameTarFile(new File(destPath + outputFileName + ".tar.temp"), destPath);
                break;
        }

    }

    /**
     * calculate how many bytes written - bytesWritten
     * calculate bytes written for current file - currentFileSize
     *
     * @param b The byte written on the file
     * @throws IOException
     */

    public void write(int b) throws IOException {
        super.write(b);
        bytesWritten += 1;
        if (currentEntry != null) {
            currentFileSize += 1;
        }
    }

    public void write(byte[] b) throws IOException {
        super.write(b);
    }

    /**
     * @param b   byte array
     * @param off Where to start reading in array
     * @param len how much to read
     * @throws IOException
     */

    public void write(byte[] b, int off, int len) throws IOException {
        if (this.currentEntry != null && !this.currentEntry.isDirectory() && this.currentEntry.getSize() < this.currentFileSize + (long) len) {
            throw new IOException("The current entry[" + this.currentEntry.getName() + "] size[" + this.currentEntry.getSize() + "] is smaller than the bytes[" + (this.currentFileSize + (long) len) + "] being written.");
        } else {
            super.write(b, off, len);
        }
    }

    /**
     * creating entry for each file
     * if file was directory we have to close the entry only there is no data to write but we will check if there is files in this directory
     * else writing the data of file then closing the entry
     * note : we are creating for each file or directory entry but we are closing it in different time
     *
     * @param fileList selected files
     * @throws IOException
     */
    private void addFilesToTar(boolean append, File... fileList) throws IOException {
        int i = 0;
        long bytes = 0;
        for (File file : fileList) {
            if (usePhotosCompression && jpeg.isImage(file)) {
                writeCompressedPhotoToTar(new TarEntry(file, file.getName()), file.getPath());
            } else {
                addNewEntry(new TarEntry(file, file.getName()));
                if (entryIsDir) {
                    closeEntry();
                    writeDirectoryToTar(file, "" + file.getName());
                } else {
                    writeFileToTar(file);
                    closeEntry();
                }
                bytes += file.length();
            }
            i++;
            bytes += bytesWritten;
            try {
                int finalI = i;
                long finalBytes = bytes;
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        updateProgress((((double) finalI * 100) / fileList.length), 100);
                        updateMessage(text(finalBytes));
                        return null;
                    }
                };
                Platform.runLater(() -> {
                    new Thread(task).start();
                    controller.getProgressBar().progressProperty().bind(task.progressProperty());
                    controller.getText().textProperty().bind(task.messageProperty());
                });
            } catch (IllegalStateException e) {
                continue;
            }
        }
        if (append == false)
            closeStream();
    }

    private String text(double x) {
        DecimalFormat df = new DecimalFormat("#.##");
        if ((x / 1024) < 1) {
            return df.format(x) + "\\Bytes";
        } else if ((x / 1024) < 1024 && (x / 1024) > 1) {
            return df.format(x / 1024) + "\\Kb";
        } else {
            return df.format((x / 1024) / 1024) + "\\Mb";
        }

    }

    private void updateTarFile(String desPath, File... fileList) throws IOException {
        if (!new File(desPath + this.outputFileName).exists())
            addFilesToTar(false, fileList);
        else {
            int i;
            TarEntry entry;
            ExtractTarFile getData = new ExtractTarFile(new BufferedInputStream(new FileInputStream(desPath + this.outputFileName)));
            List<File> files = new LinkedList<>(Arrays.asList(fileList));
            while ((entry = getData.getNextEntry()) != null) {
                boolean found = false;
                for (i = 0; i < files.size(); i++) {
                    if (files.get(i).getName().equals(entry.getName())) {
                        found = true;
                        addFilesToTar(true, files.get(i));
                        files.remove(i);
                        break;
                    }
                }
                if (!found)
                    readFromExistTar(entry, getData);
                getData.closeEntry();
            }
            getData.close();
            addFilesToTar(false, files.toArray(new File[files.size()]));
        }
    }

    private void readFromExistTar(TarEntry entry, ExtractTarFile getData) throws IOException {
        int count;
        byte[] header = new byte[512];
        byte data[] = new byte[2048];
        entry.writeEntryHeaderToByteBuffer(header);
        write(header);
        while ((count = getData.read(data)) != -1)
            write(data, 0, count);
        closeEntry();
    }

    /**
     * writing data of file to tar file
     *
     * @param file
     * @throws IOException
     */
    private void writeFileToTar(File file) throws IOException {
        BufferedInputStream origin = new BufferedInputStream(new FileInputStream(file));
        int count;
        byte data[] = new byte[2048];
        while ((count = origin.read(data)) != -1)
            write(data, 0, count);
        flush();
        origin.close();
    }

    /**
     * we will check the directory if has files also for each file or directory will create entry
     * and for each file will write data then close entry
     * for directory the func will call it self for checking the current directory
     *
     * @param directory
     * @param parent    path to parent of file /Folder/1.txt parent of 1.txt folder
     * @throws IOException
     */
    private void writeDirectoryToTar(File directory, String parent) throws IOException {
        if (directory.listFiles() == null)
            return;
        for (File file : directory.listFiles()) {
            if (usePhotosCompression && jpeg.isImage(file))
                writeCompressedPhotoToTar(new TarEntry(file, parent + File.separator + file.getName()), file.getPath());
            else {
                addNewEntry(new TarEntry(file, parent + File.separator + file.getName()));
                if (entryIsDir) {
                    closeEntry();
                    writeDirectoryToTar(file, parent + File.separator + file.getName());
                } else {
                    writeFileToTar(file);
                    closeEntry();
                }
            }
        }

    }

    /**
     * adding new entry to tar file it's mean writing the header of entry to tar file
     *
     * @param entry current entry
     * @throws IOException
     */

    private void addNewEntry(TarEntry entry) throws IOException {
        byte[] headerBuffer = new byte[TarBlockSize.HEADER_BLOCK];
        entry.writeEntryHeaderToByteBuffer(headerBuffer);
        write(headerBuffer);
        currentEntry = entry;
        if (currentEntry.isDirectory())
            entryIsDir = true;
    }

    private void writeCompressedPhotoToTar(TarEntry entry, String path) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        jpeg jpeg = new jpeg(path, 2, 8, "low", baos);
        byte[] bytes = baos.toByteArray();
        entry.setSize(bytes.length);
        entry.setName(entry.getName().substring(0, entry.getName().lastIndexOf('.')) + ".JPEG");
        addNewEntry(entry);
        write(bytes);
        closeEntry();

    }

    private void pad() throws IOException {
        if (bytesWritten > 0) {
            int extra = (int) (bytesWritten % TarBlockSize.DATA_BLOCK);
            if (extra > 0)
                write(new byte[TarBlockSize.DATA_BLOCK - extra]);
        }
    }

    private void closeEntry() throws IOException {
        if (this.currentEntry != null) {
            if (this.currentEntry.getSize() > this.currentFileSize) {
                throw new IOException("The current entry[" + this.currentEntry.getName() + "] of size[" + this.currentEntry.getSize() + "] has not been fully written.");
            }
        }
        entryIsDir = false;
        currentEntry = null;
        currentFileSize = 0;
        pad();
    }

    /**
     * closing stream
     * writing EOF block
     *
     * @throws IOException
     */

    private void closeStream() throws IOException {
        closeEntry();
        write(new byte[TarBlockSize.EOF_BLOCK]);
        close();
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
