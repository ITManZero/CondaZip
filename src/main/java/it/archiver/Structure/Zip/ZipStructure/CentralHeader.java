package it.archiver.Structure.Zip.ZipStructure;

import it.archiver.Structure.Zip.Factories.Index;
import it.archiver.Structure.Zip.Factories.ZipFactory;
import it.archiver.Structure.Zip.Header.HeaderComponent;
import it.archiver.Structure.Zip.Header.UnCompressedSize;
import it.archiver.Structure.Zip.Operation.DePackData;
import it.archiver.Structure.Zip.Operation.PackData;

import java.io.File;
import java.io.IOException;

public class CentralHeader implements PackData , DePackData {

    private HeaderComponent[] headerComponents;
    private int length;
    //Constructor
    public CentralHeader(String path) {
        headerComponents = ZipFactory.getCentralHeader(path);
    }
    //Constructor
    public CentralHeader(File file) {
        length = 0;
        headerComponents = ZipFactory.getCentralHeader(file.getPath());
    }
    //******Setter
    public void setHeaderComponent(HeaderComponent[] headerComponents) {
        this.headerComponents = headerComponents;
    }
    public void setName(String name){
        this.headerComponents[Index.C_FN].setValue(name);
        this.headerComponents[Index.C_FN].setLength(name.length());
        this.headerComponents[Index.FNL].setValue((long)name.length());
    }
    public void setCompSize(long val){ headerComponents[Index.COMP_S].setValue(val); }
    public void setCompMethod(long value){
        headerComponents[Index.COMP_M].setValue(value);
    }
    //*******Getter
    public HeaderComponent[] getHeaderComponent() {
        return headerComponents;
    }
    public UnCompressedSize getUnCompSize() {
        return (UnCompressedSize) headerComponents[Index.UCOMP_S];
    }
    public int getLength() {
        updateLength();
        return length;
    }
    public long getComsSize(){
        return (long) headerComponents[Index.COMP_S].getValue();
    }
    public int getCompMethod(){return (int) headerComponents[Index.COMP_M].getValue();}

    private void updateLength(){
        length =0;
        for(HeaderComponent hc : headerComponents){
            length +=hc.getLength();
        }
    }

    @Override
    public Object read(byte[] buffer) {
        return null;
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        for(HeaderComponent hc : headerComponents)
            hc.write(buffer);
    }

}
