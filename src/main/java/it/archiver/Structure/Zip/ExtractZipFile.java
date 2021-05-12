package it.archiver.Structure.Zip;

import it.archiver.Structure.Zip.Factories.Index;
import it.archiver.Structure.Zip.Factories.Values;
import it.archiver.Structure.Zip.Factories.ZipFactory;
import it.archiver.Structure.Zip.Header.FileName;
import it.archiver.Structure.Zip.Header.HeaderComponent;
import it.archiver.Structure.Zip.Operation.HuffmanDecoder;
import it.archiver.Structure.Zip.Operation.ZipArchiver;
import it.archiver.Structure.Zip.ZipStructure.FileHeader;

import java.io.*;

public class ExtractZipFile extends FilterInputStream {

    private String outputFilePath;
    private String inputFilePath;
    private FileHeader fileHeader = new FileHeader();
    private String currentFileName;
    private long count = 0;
    /*
     * outputFilePath : Where To Write Zip File
     */
    public ExtractZipFile(String inputFilePath  // Zip File Location
                        , String outputFilePath // Where To Extract Data
    ) throws IOException {
            super(new BufferedInputStream(new FileInputStream(new File(inputFilePath))));
            this.outputFilePath = outputFilePath;
            this.inputFilePath = inputFilePath;
            while(readHeader() != -1){
                readData();
            }
        this.close();
    }

    /*
    Read File Header Data
     */
    public int readHeader() throws IOException {
        HeaderComponent [] hc = ZipFactory.getFileHeader();
        for(HeaderComponent h : hc){
            if(h instanceof FileName){
                byte [] bytes = new byte[(int) hc[Index.FNL].getValue()];
                this.read(bytes);
                int temp = (int) hc[Index.FNL].getValue();
                currentFileName = (ZipArchiver.parseName(bytes,temp )).toString();
                h.setLength(currentFileName.length());
                h.setValue((int) ZipArchiver.read(bytes,h.getOffset(),h.getLength()));
            }

            else {
                byte [] bytes = new byte[(int) h.getLength()];
                this.read(bytes);
                h.setValue((int) ZipArchiver.read(bytes , h.getOffset() , h.getLength()));
            }
        }
        fileHeader.setHeaderComponents(hc);
        count += fileHeader.getLength();

        if(hc[Index.SIGN].getValue().equals(Values.SIGNATURE_CentralHeader)||hc[Index.SIGN].getValue().equals(0))
            return -1;
        return 1;
    }

    /*
    Read File Data
     */
    public void readData() throws IOException {

        HeaderComponent[] hc = fileHeader.getHeaderComponents();
        int byteToRead = (int) hc[Index.COMP_S].getValue(); // Here Will Return How Much Byte I Have To read
        //Read Folder
        if(fileHeader.isDir()){
            new File(outputFilePath + currentFileName).mkdirs();
        }
        //Read File
        else {
            /* Decoder Call */
            if(((int)fileHeader.getCompMethod()) == 21){ // Re do it to 21
                new HuffmanDecoder().read(inputFilePath,currentFileName,outputFilePath, (int) fileHeader.getCompSize(), count);
                byte [] bytes = new byte[(int)fileHeader.getCompSize()];
                this.read(bytes);
            }
            else{
                FilterOutputStream filterOutputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath + currentFileName));
                for (int i = 0; i < byteToRead; ) {
                    if ((byteToRead - 1024) > i) {
                        byte[] bytes = new byte[1024];
                        this.read(bytes, 0, 1024);
                        i += 1024;
                        filterOutputStream.write(bytes);
                        filterOutputStream.flush();
                    } else {
                        byte[] bytes = new byte[byteToRead - i];
                        this.read(bytes, 0, byteToRead - i);
                        filterOutputStream.write(bytes, 0, byteToRead - i);
                        filterOutputStream.flush();
                        break;
                    }
                }
            filterOutputStream.close();
            }
        }
        count += (int)fileHeader.getCompSize();
    }

    public void readCentral() throws IOException {
        HeaderComponent [] hc = ZipFactory.getCentralHeader();
        for(HeaderComponent h : hc){

            if(h instanceof FileName){
                byte [] bytes = new byte[(int) hc[Index.FNL].getValue()];
                this.read(bytes);
                currentFileName = (ZipArchiver.parseName(bytes, (Long) hc[Index.FNL].getValue())).toString();
                h.setLength(currentFileName.length());
                h.setValue((int) ZipArchiver.read(bytes,h.getOffset(),h.getLength()));
            }

            else {
                byte [] bytes = new byte[(int) h.getLength()];
                this.read(bytes);
                h.setValue((int) ZipArchiver.read(bytes,h.getOffset(),h.getLength()));
            }
        }
        fileHeader.setHeaderComponents(hc);

    }
    public void readTail(){

    }

}
