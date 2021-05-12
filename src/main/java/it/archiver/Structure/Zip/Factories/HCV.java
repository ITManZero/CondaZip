package it.archiver.Structure.Zip.Factories;

public class HCV {
    //SAME AT FH AND CD
    public static int SIGNATURE_OFFSET = 0;
    public static int SIGNATURE_LENGTH = 4;

    //LENGTH DIFF FROM FH & CD
    public static int VERSION_OFFSET = 4;
    public static int VERSION_HEADER_LENGTH = 2;
    public static int VERSION_CENTRAL_LENGTH = 4;

    //FLAGS
    public static int FLAGS_HEADER_OFFSET = 6;
    public static int FLAGS_CENTRAL_OFFSET = 8;
    public static int FLAGS_LENGTH = 2;

    //CM
    public static int COMPRESSION_HEADER_OFFSET = 8;
    public static int COMPRESSION_CENTRAL_OFFSET = 10;
    public static int COMPRESSION_LENGTH = 2;

    //MT
    public static int MODTIME_HEADER_OFFSET = 10;
    public static int MODTIME_CENTRAL_OFFSET = 12;
    public static int MODTIME_LENGTH = 2;

    //MD
    public static int MODDATE_HEADER_OFFSET = 12;
    public static int MODDATE_CENTRAL_OFFSET = 14;
    public static int MODDATE_LENGTH = 2;

    //CHECKSUM
    public static int CHECKSUM_HEADER_OFFSET = 14;
    public static int CHECKSUM_CENTRAL_OFFSET = 16;
    public static int CHECKSUM_LENGTH = 4;

    //CS
    public static int COMPRESSED_HEADER_SIZE_OFFSET   = 18;
    public static int COMPRESSED_CENTRAL_SIZE_OFFSET   = 20;
    public static int COMPRESSED_SIZE_LENGTH   =  4;

    //UCS
    public static int UNCOMPRESSED_HEADER_SIZE_OFFSET = 22;
    public static int UNCOMPRESSED_CENTRAL_SIZE_OFFSET = 24;
    public static int UNCOMPRESSED_SIZE_LENGTH =  4;

    //FNL
    public static int NAME_HEADER_LENGTH_OFFSET       = 26;
    public static int NAME_CENTRAL_LENGTH_OFFSET      = 28;
    public static int NAME_LENGTH_LENGTH       =  2;

    //EFL
    public static int EXTRA_HEADER_FIELD_LENGTH_OFFSET = 28;
    public static int EXTRA_CENTRAL_FIELD_LENGTH_OFFSET = 30;
    public static int EXTRA_FIELD_LENGTH_LENGTH=  2;

    //FNL
    public static int FILE_NAME_HEADER_OFFSET         = 30;
    public static int FILE_NAME_CENTRAL_OFFSET         = 46;

    //EXF
    public static int EXTRA_HEADER_FIELD_OFFSET        = 30 /*+VAR*/;
    public static int EXTRA_CENTRAL_FIELD_OFFSET       = 64 /*+VAR*/;
    public static int EXTRA_FIELD_LENGTH       =  0;

    //FCL
    public static int FILE_COMMENT_LENGTH_LENGTH = 2;
    public static int FILE_COMMENT_LENGTH_OFFSET = 32;

    //DS
    public static int DISK_START_LENGTH = 2;
    public static int DISK_START_OFFSET = 32;

    //INA
    public static int INTERNAL_ATTR_LENGTH = 2;
    public static int INTERNAL_ATTR_OFFSET = 36;

    //EXA
    public static int EXTERNAL_ATTR_LENGTH = 4;
    public static int EXTERNAL_ATTR_OFFSET = 38;

    //OLH
    public static int OFFSET_OF_LOCAL_HEADER_LENGTH = 4;
    public static int OFFSET_OF_LOCAL_HEADER_OFFSET = 42;

    //DN
    public static int DISK_NUMBER_OFFSET = 4;
    public static int DISK_NUMBER_LENGTH = 2;

    //D
    public static int DISK_OFFSET = 6;
    public static int DISK_LENGTH = 2;

    //DEN
    public static int DISK_ENTRIES_OFFSET = 8;
    public static int DISK_ENTRIES_LENGTH = 2;

    //TEN
    public static int TOTAL_ENTRIES_OFFSET = 10;
    public static int TOTAL_ENTRIES_LENGTH = 2;

    //CDS
    public static int CENTRAL_DIC_SIZE_OFFSET = 12;
    public static int CENTRAL_DIC_SIZE_LENGTH = 4;

    //OFS
    public static int OFFSET_FROM_START_OFFSET = 16;
    public static int OFFSET_FROM_START_LENGTH = 4;

}
