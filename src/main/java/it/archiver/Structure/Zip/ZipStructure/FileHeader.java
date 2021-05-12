package it.archiver.Structure.Zip.ZipStructure;

import it.archiver.Structure.Zip.Factories.Index;
import it.archiver.Structure.Zip.Factories.ZipFactory;
import it.archiver.Structure.Zip.Header.Flags;
import it.archiver.Structure.Zip.Header.HeaderComponent;
import it.archiver.Structure.Zip.Header.UnCompressedSize;
import it.archiver.Structure.Zip.Operation.DePackData;
import it.archiver.Structure.Zip.Operation.PackData;
import it.archiver.Structure.Zip.Operation.ZipArchiver;

import java.io.File;
import java.io.IOException;

public class FileHeader implements PackData , DePackData {

    private int length;
    //When I finish ill store them in array of HeaderComponent
    private HeaderComponent[] headerComponents;
    //Constructor
    public FileHeader(String path) {
        headerComponents = ZipFactory.getFileHeader(path);
    }
    //Constructor
    public FileHeader(File file) {
        headerComponents = ZipFactory.getFileHeader(file.getPath());
    }
    //Constructor
    public FileHeader(HeaderComponent[] headerComponents) {
        this.headerComponents = headerComponents;
    }
    //Constructor
    public FileHeader(){
        headerComponents = ZipFactory.getFileHeader();
    }
    //Sum Length Of FH
    private void createLength(){
        length =0;
        for(HeaderComponent hc : headerComponents){
            length += hc.getLength();
        }
    }

    //********Setter
    //Used When Reading Data
    public void setName(String name){
        this.headerComponents[Index.H_FN].setValue(name);
        this.headerComponents[Index.H_FN].setLength(name.length());
        this.headerComponents[Index.FNL].setValue((long)name.length());
    }
    public void setCompMethod(long value){
        headerComponents[Index.COMP_M].setValue(value);
    }
    public void setHeaderComponents(HeaderComponent[] headerComponents) {
        this.headerComponents = headerComponents;
    }
    public void setCompSize(long val){ headerComponents[Index.COMP_S].setValue(val); }

    //********Getter
    public Flags getFlags() {
        return (Flags) headerComponents[Index.FLAGS];
    }
    public UnCompressedSize getUnCompSize(){
        return (UnCompressedSize) headerComponents[Index.UCOMP_S];
    }
    public HeaderComponent[] getHeaderComponents() {
        return headerComponents;
    }
    public Object getCompMethod(){return headerComponents[Index.COMP_M].getValue();}
    public int getLength() {
        createLength();
        return length;
    }
    public Object getCompSize(){
        return headerComponents[Index.COMP_S].getValue();
    }
    public String getName(){
        return (String) headerComponents[Index.H_FN].getValue();
    }

    //Used To Know If File Is Folder xD
    public boolean isDir(){
        if(headerComponents[Index.COMP_S].getValue().equals(0)) return true;
        return false;
    }

    @Override
    public Object read(byte[] buffer) {
        for(HeaderComponent hc : headerComponents)
            ZipArchiver.read(buffer ,hc.getOffset() , hc.getLength());
        return headerComponents;
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        //write Data in Byte Array
        for(HeaderComponent hc : headerComponents)
            hc.write(buffer);
    }}
