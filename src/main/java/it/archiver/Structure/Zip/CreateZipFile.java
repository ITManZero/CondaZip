package it.archiver.Structure.Zip;

import it.archiver.Controller_Files.Controller_Process;
import it.archiver.Structure.Zip.Factories.Values;
import it.archiver.Structure.Zip.Header.HeaderComponent;
import it.archiver.Structure.Zip.ZipStructure.CentralTail;
import it.archiver.Structure.Zip.ZipStructure.FileEntry;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CreateZipFile extends FilterOutputStream {

    private List<FileEntry> entries = new ArrayList<>();
    private CentralTail tail;
    private int offset;
    private int dicSize;
    private String des;
    private String outputFileName;
    private Controller_Process controller;

    public CreateZipFile(String destPath, String outputFileName) throws FileNotFoundException {
        super(new BufferedOutputStream(new FileOutputStream(outputFileName + ".zip")));
        offset = 0;
        dicSize = 0;
        if (new File(destPath).isDirectory()) {
            entries.add(new FileEntry(destPath));
            createEntries(destPath, entries.get(0).getFileHeader().getName());
        } else createEntries(destPath, "");
        writeToMemory();
        this.outputFileName = outputFileName + ".zip";
    }

    public CreateZipFile(List<String> paths, String outputFileName, boolean usedCompressionPhotos, Controller_Process controller, String desPath) throws FileNotFoundException {
        super(new BufferedOutputStream(new FileOutputStream(desPath + File.separatorChar + outputFileName + ".zip")));
        Values.bakr = usedCompressionPhotos;
        this.controller = controller;
        offset = 0;
        dicSize = 0;
        for (String destPath : paths) {
            if (new File(destPath).isDirectory()) {
                entries.add(new FileEntry(destPath));
                createEntries(destPath, entries.get(entries.size() - 1).getFileHeader().getName());
            } else createEntries(destPath, "");
        }
        System.out.println("Entries Size : " + entries.size());
        for (FileEntry e : entries) {
            System.out.println("new");
            for (HeaderComponent c : e.getFileHeader().getHeaderComponents()) {
//                if (c instanceof CompressedSize)
                System.out.println(c.getClass() + " : " + c.getValue());
            }
        }

        writeToMemory();

        this.outputFileName = outputFileName + ".zip";
    }

    //This Method Will Write At Stream
    public void writeToMemory() {
        byte[] bytes = new byte[100];
        createTail();

        Task task = null;
        //Write File Header First & Data
        for (FileEntry e : entries) {
            try {
                e.writeHeader(bytes);   //-> File Entry Write Header Method
                super.write(bytes, 0, e.getFileHeader().getLength()); //-> Write on stream
                e.writeData(this);  //-> File Entry Write Data Method -> in Turn Will Write on Stream
            } catch (IOException e1) {
                e1.printStackTrace();   //Catch exception like File Not Found .. etc
            }
        }
        // Write Central Header
        int i = 0;
        long by = 0;
        for (FileEntry e : entries) {
            try {
                e.writeCentral(bytes); // -> File Entry Write Central Method
                super.write(bytes, 0, e.getCentralHeader().getLength()); // -> Write On Stream

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            i++;
            by += (long) e.getFileHeader().getCompSize();


            int finalI = i;
            long finalBy = by;
            task = new Task() {
                @Override
                protected Object call() throws Exception {
                    updateProgress((finalI * 100) / entries.size(), 100);
                    updateMessage(text(finalBy));
                    return null;
                }
            };
            Task finalTask = task;
            Platform.runLater(() -> {
                new Thread(finalTask).start();
                controller.getProgressBar().progressProperty().bind(finalTask.progressProperty());
                controller.getText().textProperty().bind(finalTask.messageProperty());
            });
        }



        //write Tail
        try {
            tail.write(bytes); // Write Func In CentralTail
            super.write(bytes, 0, tail.getLength());// Write on Stream
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            super.close(); // Save Changes
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    //Create Entries For Every File In Path
    private void createEntries(String path, String parent) {
        if (new File(path).isDirectory()) {
            File[] files = new File(path).listFiles();
            for (File f : files) {
                entries.add(new FileEntry(f));
                entries.get(entries.size() - 1).setName(parent + "/" + f.getName());
                if (f.isDirectory()) {
                    //for add '/' to dir
                    entries.get(entries.size() - 1).setName(entries.get(entries.size() - 1).getName() + "/");
                    createEntries(f.getPath(), parent + "/" + f.getName());
                }
            }
        } else {
            entries.add(new FileEntry(path));
            entries.get(entries.size() - 1).getFileHeader().setName(new File(path).getName());
        }
    }

    //Create Tail
    private void createTail() {
        countDicSize();
        countOffset();
        tail = new CentralTail(entries.size(), entries.size(), offset, dicSize);
    }

    //Calculate Dic Size
    private void countDicSize() {
        /*
        Directory size = CH1 + CH2 + ... + CHN
         */
        for (FileEntry e : entries) {
            dicSize += e.getCentralHeader().getLength();
        }
    }

    //Calculate Offset
    private void countOffset() {
        /*
        Offset From Start To First CentralHeader = FH1 + FD1 + FH2 + FD2 + .... + FHN + FDN
         */
        for (FileEntry e : entries) {
            offset += e.getFileHeader().getLength() + e.getFileHeader().getLength();
        }
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
