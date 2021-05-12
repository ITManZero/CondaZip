package it.archiver.Structure.Zip.Factories;

import it.archiver.Structure.Zip.Controller.FileHeader.CRC32;
import it.archiver.Structure.Zip.Controller.FileHeader.DateModified;
import it.archiver.Structure.Zip.Header.*;

import java.io.File;


public class ZipFactory {
    // Use For Generate FileHeader
    public static HeaderComponent[] getFileHeader(String path){
        File file = new File(path);
        HeaderComponent[] headerComponent = new HeaderComponent[Values.FH_MEMBERS];

        headerComponent[0] = new Signature( Values.SIGNATURE_FileHeader , HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);

        if (file.isDirectory()){
           headerComponent[1] = new Version(Values.HEADER_Version_Folder , HCV.VERSION_OFFSET , HCV.VERSION_HEADER_LENGTH);
           headerComponent[7] = new CompressedSize(0, HCV.COMPRESSED_HEADER_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
           headerComponent[8] = new UnCompressedSize(0,HCV.UNCOMPRESSED_HEADER_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
           headerComponent[6] = new Checksum(0, HCV.CHECKSUM_HEADER_OFFSET , HCV.CHECKSUM_LENGTH);
        }
        else{
            headerComponent[1] = new Version(Values.HEADER_Version_File , HCV.VERSION_OFFSET , HCV.VERSION_HEADER_LENGTH);
            headerComponent[7] = new CompressedSize(file.length(), HCV.COMPRESSED_HEADER_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
            headerComponent[8] = new UnCompressedSize(file.length(),HCV.UNCOMPRESSED_HEADER_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
            headerComponent[6] = new Checksum(new CRC32(new File(path)).getLongCRC(), HCV.CHECKSUM_HEADER_OFFSET , HCV.CHECKSUM_LENGTH);
        }

        headerComponent[2] = new Flags(0, HCV.FLAGS_HEADER_OFFSET , HCV.FLAGS_LENGTH);

        headerComponent[3] = new CompressionMethod(0, HCV.COMPRESSION_HEADER_OFFSET , HCV.COMPRESSION_LENGTH);

        headerComponent[4] = new ModifiedTime(DateModified.getLongTime(file), HCV.MODTIME_HEADER_OFFSET , HCV.MODTIME_LENGTH);

        headerComponent[5] = new ModifiedDate(DateModified.getLongDate(file), HCV.MODDATE_HEADER_OFFSET , HCV.MODDATE_LENGTH);

        headerComponent[9] = new NameLength(file.getName().length(), HCV.NAME_HEADER_LENGTH_OFFSET , HCV.NAME_LENGTH_LENGTH);

        headerComponent[10]= new ExtraFieldLength(0, HCV.EXTRA_HEADER_FIELD_LENGTH_OFFSET , HCV.EXTRA_FIELD_LENGTH_LENGTH );

        headerComponent[11]= new FileName(file.getName() , HCV.FILE_NAME_HEADER_OFFSET , file.getName().length());

        headerComponent[12]= new ExtraField(-1, HCV.EXTRA_HEADER_FIELD_OFFSET +file.getName().length() , 0);

        return headerComponent;
    }
    public static HeaderComponent[] getFileHeader(){
        HeaderComponent[] headerComponent = new HeaderComponent[Values.FH_MEMBERS];
        headerComponent[0] = new Signature(0, HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);
        headerComponent[1] = new Version(0, HCV.VERSION_OFFSET , HCV.VERSION_HEADER_LENGTH);
        headerComponent[2] = new Flags(0, HCV.FLAGS_HEADER_OFFSET , HCV.FLAGS_LENGTH);
        headerComponent[3] = new CompressionMethod(0, HCV.COMPRESSION_HEADER_OFFSET , HCV.COMPRESSION_LENGTH);
        headerComponent[4] = new ModifiedTime(0, HCV.MODTIME_HEADER_OFFSET , HCV.MODTIME_LENGTH);
        headerComponent[5] = new ModifiedDate(0, HCV.MODDATE_HEADER_OFFSET , HCV.MODDATE_LENGTH);
        headerComponent[6] = new Checksum(0, HCV.CHECKSUM_HEADER_OFFSET , HCV.CHECKSUM_LENGTH);
        headerComponent[7] = new CompressedSize(0, HCV.COMPRESSED_HEADER_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
        headerComponent[8] = new UnCompressedSize(0,HCV.UNCOMPRESSED_HEADER_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
        headerComponent[9] = new NameLength(0, HCV.NAME_HEADER_LENGTH_OFFSET , HCV.NAME_LENGTH_LENGTH);
        headerComponent[10]= new ExtraFieldLength(0, HCV.EXTRA_HEADER_FIELD_LENGTH_OFFSET , HCV.EXTRA_FIELD_LENGTH_LENGTH );
        headerComponent[11]= new FileName("", HCV.FILE_NAME_HEADER_OFFSET , 0);
        headerComponent[12]= new ExtraField(0, HCV.EXTRA_HEADER_FIELD_OFFSET, 0);
        return headerComponent;
    }
    // Use For Generate File Entry Header
    public static HeaderComponent[] getCentralHeader(String path){
        File file = new File(path);
        HeaderComponent[] headerComponent = new HeaderComponent[Values.CH_MEMBERS];
        headerComponent[0] = new Signature( Values.SIGNATURE_CentralHeader , HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);
        //Upper for file mode , Lower Byte : PKZip ver for extract
        if(file.isDirectory()){
            headerComponent[1] = new Version(Values.CENTRAL_Version_Folder , HCV.VERSION_OFFSET, HCV.VERSION_CENTRAL_LENGTH);
            headerComponent[14]= new ExtAttr(Values.External_Folder , HCV.EXTERNAL_ATTR_OFFSET, HCV.EXTERNAL_ATTR_LENGTH);
            headerComponent[7] = new CompressedSize(0,HCV.COMPRESSED_CENTRAL_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
            headerComponent[8] = new UnCompressedSize(0,HCV.UNCOMPRESSED_CENTRAL_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
            headerComponent[6] = new Checksum(0,HCV.CHECKSUM_CENTRAL_OFFSET , HCV.CHECKSUM_LENGTH);

        }
        else {
            headerComponent[1] = new Version(Values.CENTRAL_Version_File, HCV.VERSION_OFFSET, HCV.VERSION_CENTRAL_LENGTH);
            headerComponent[14]= new ExtAttr(Values.External_File , HCV.EXTERNAL_ATTR_OFFSET, HCV.EXTERNAL_ATTR_LENGTH);
            headerComponent[7] = new CompressedSize(file.length(),HCV.COMPRESSED_CENTRAL_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
            headerComponent[8] = new UnCompressedSize(file.length(),HCV.UNCOMPRESSED_CENTRAL_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
            headerComponent[6] = new Checksum(new CRC32(new File(path)).getLongCRC(),HCV.CHECKSUM_CENTRAL_OFFSET , HCV.CHECKSUM_LENGTH);

        }
        headerComponent[2] = new Flags(0, HCV.FLAGS_CENTRAL_OFFSET , HCV.FLAGS_LENGTH);
        headerComponent[3] = new CompressionMethod(0 , HCV.COMPRESSION_CENTRAL_OFFSET , HCV.COMPRESSION_LENGTH);
        headerComponent[4] = new ModifiedTime(DateModified.getLongTime(file) , HCV.MODTIME_CENTRAL_OFFSET , HCV.MODTIME_LENGTH);
        headerComponent[5] = new ModifiedDate(DateModified.getLongDate(file) , HCV.MODDATE_CENTRAL_OFFSET , HCV.MODDATE_LENGTH);
        headerComponent[9] = new NameLength(file.getName().length(),HCV.NAME_CENTRAL_LENGTH_OFFSET , HCV.NAME_LENGTH_LENGTH);
        headerComponent[10]= new ExtraFieldLength(0,HCV.EXTRA_CENTRAL_FIELD_LENGTH_OFFSET , HCV.EXTRA_FIELD_LENGTH_LENGTH);
        headerComponent[11]= new FileCommentLength(0,HCV.FILE_COMMENT_LENGTH_OFFSET, HCV.FILE_COMMENT_LENGTH_LENGTH);
        headerComponent[12]= new DiskStart(0, HCV.DISK_START_OFFSET , HCV.DISK_START_LENGTH);
        headerComponent[13]= new IntAttr(0 , HCV.INTERNAL_ATTR_OFFSET, HCV.INTERNAL_ATTR_LENGTH);
        headerComponent[15]= new OffsetOfLocalHeader(0 , HCV.OFFSET_OF_LOCAL_HEADER_OFFSET, HCV.OFFSET_OF_LOCAL_HEADER_LENGTH);
        headerComponent[16]= new FileName(file.getName() , HCV.FILE_NAME_CENTRAL_OFFSET , file.getName().length());
        headerComponent[17]= new ExtraField(0, HCV.EXTRA_CENTRAL_FIELD_OFFSET + file.getName().length() , HCV.EXTRA_FIELD_LENGTH);
        headerComponent[18]= new FileComment(0,0,0);

        return headerComponent;
    }
    public static HeaderComponent[] getCentralHeader(){
        HeaderComponent[] headerComponent = new HeaderComponent[Values.CH_MEMBERS];
        headerComponent[0] = new Signature( 0 , HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);
        headerComponent[1] = new Version(0, HCV.VERSION_OFFSET, HCV.VERSION_CENTRAL_LENGTH);
        headerComponent[2] = new Flags(0, HCV.FLAGS_CENTRAL_OFFSET , HCV.FLAGS_LENGTH);
        headerComponent[3] = new CompressionMethod(0 , HCV.COMPRESSION_CENTRAL_OFFSET , HCV.COMPRESSION_LENGTH);
        headerComponent[4] = new ModifiedTime(0, HCV.MODTIME_CENTRAL_OFFSET , HCV.MODTIME_LENGTH);
        headerComponent[5] = new ModifiedDate(0 , HCV.MODDATE_CENTRAL_OFFSET , HCV.MODDATE_LENGTH);
        headerComponent[6] = new Checksum(0,HCV.CHECKSUM_CENTRAL_OFFSET , HCV.CHECKSUM_LENGTH);
        headerComponent[7] = new CompressedSize(0,HCV.COMPRESSED_CENTRAL_SIZE_OFFSET , HCV.COMPRESSED_SIZE_LENGTH);
        headerComponent[8] = new UnCompressedSize(0,HCV.UNCOMPRESSED_CENTRAL_SIZE_OFFSET , HCV.UNCOMPRESSED_SIZE_LENGTH);
        headerComponent[9] = new NameLength(0,HCV.NAME_CENTRAL_LENGTH_OFFSET , HCV.NAME_LENGTH_LENGTH);
        headerComponent[10]= new ExtraFieldLength(0,HCV.EXTRA_CENTRAL_FIELD_LENGTH_OFFSET , HCV.EXTRA_FIELD_LENGTH_LENGTH);
        headerComponent[11]= new FileCommentLength(0,HCV.FILE_COMMENT_LENGTH_OFFSET, HCV.FILE_COMMENT_LENGTH_LENGTH);
        headerComponent[12]= new DiskStart(0, HCV.DISK_START_OFFSET , HCV.DISK_START_LENGTH);
        headerComponent[13]= new IntAttr(0 , HCV.INTERNAL_ATTR_OFFSET, HCV.INTERNAL_ATTR_LENGTH);
        headerComponent[14]= new ExtAttr(0, HCV.EXTERNAL_ATTR_OFFSET, HCV.EXTERNAL_ATTR_LENGTH);
        headerComponent[15]= new OffsetOfLocalHeader(0 , HCV.OFFSET_OF_LOCAL_HEADER_OFFSET, HCV.OFFSET_OF_LOCAL_HEADER_LENGTH);
        headerComponent[16]= new FileName("", HCV.FILE_NAME_CENTRAL_OFFSET ,0);
        headerComponent[17]= new ExtraField(0, HCV.EXTRA_CENTRAL_FIELD_OFFSET, HCV.EXTRA_FIELD_LENGTH);
        headerComponent[18]= new FileComment(0,0,0);

        return headerComponent;
    }
    // Use For Generate Tail
    public static HeaderComponent[] getTail(int entND , int entWN , int offset , int dic){
        HeaderComponent [] headerComponent = new HeaderComponent[Values.T_MEMBERS];
        headerComponent[0] = new Signature(Values.SIGNATURE_EndHeader , HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);
        headerComponent[1] = new DiskNumber(0,HCV.DISK_NUMBER_OFFSET,HCV.DISK_NUMBER_LENGTH);
        headerComponent[2] = new Disk(0,HCV.DISK_OFFSET,HCV.DISK_LENGTH);
        headerComponent[3] = new DiskEntries(entND,HCV.DISK_ENTRIES_OFFSET,HCV.DISK_ENTRIES_LENGTH); // Entries number on desk
        headerComponent[4] = new TotalEntries(entWN,HCV.TOTAL_ENTRIES_OFFSET,HCV.TOTAL_ENTRIES_LENGTH); // Entries whole number
        headerComponent[5] = new CentralSize(dic,HCV.CENTRAL_DIC_SIZE_OFFSET,HCV.CENTRAL_DIC_SIZE_LENGTH); // dic size
        headerComponent[6] = new OffsetFromStart(offset,HCV.OFFSET_FROM_START_OFFSET,HCV.OFFSET_FROM_START_LENGTH); // offset
        headerComponent[7] = new FileCommentLength(0,20,2);
        headerComponent[8] = new FileComment(0,0,0);

        return headerComponent;
    }
    public static HeaderComponent[] getTail(){
        HeaderComponent [] headerComponent = new HeaderComponent[Values.T_MEMBERS];
        headerComponent[0] = new Signature(0 , HCV.SIGNATURE_OFFSET , HCV.SIGNATURE_LENGTH);
        headerComponent[1] = new DiskNumber(0,HCV.DISK_NUMBER_OFFSET,HCV.DISK_NUMBER_LENGTH);
        headerComponent[2] = new Disk(0,HCV.DISK_OFFSET,HCV.DISK_LENGTH);
        headerComponent[3] = new DiskEntries(0,HCV.DISK_ENTRIES_OFFSET,HCV.DISK_ENTRIES_LENGTH); // Entries number on desk
        headerComponent[4] = new TotalEntries(0,HCV.TOTAL_ENTRIES_OFFSET,HCV.TOTAL_ENTRIES_LENGTH); // Entries whole number
        headerComponent[5] = new CentralSize(0,HCV.CENTRAL_DIC_SIZE_OFFSET,HCV.CENTRAL_DIC_SIZE_LENGTH); // dic size
        headerComponent[6] = new OffsetFromStart(0,HCV.OFFSET_FROM_START_OFFSET,HCV.OFFSET_FROM_START_LENGTH); // offset
        headerComponent[7] = new FileCommentLength(0,20,2);
        headerComponent[8] = new FileComment(-1,0,0);
        return headerComponent;
    }
}