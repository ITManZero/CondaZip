package it.archiver.Structure.Zip.Operation;

import java.nio.charset.Charset;
import java.util.Arrays;

public class ZipArchiver {


    public static void write(byte[] buffer, long offset, long length , long value) {
        /*
         * Offset : Here Offset will play role like Counter cause offset put the point at the place that i can store my value at
         * Length : How many byte i can use to store :D
         * Buffer : Where we save value.
         * I Got This Idea From : Mark Nelson In his book The Data Compression , Section : Archive Package , Page :259
         */
        while(length-- > 0){
            buffer[(int) offset++] = (byte) (value & 0xFF);
            value >>=8;
        }
    }

    public static void writeArray(byte[] buffer, long offset, long length , byte [] name) {
        /*
         * Offset : Here Offset will play role like Counter cause offset put the point at the place that i can store my value at
         * Length : How many byte i can use to store :D
         * Buffer : Where we save value.
         */
        int i =0 ;
        while(length-- > 0){
            buffer[(int) offset++] = (byte) (name[i++] & 0xff);
        }
    }

    public static long read(byte[] buffer, long offset, long length) {

        /*
        * Offset : set As Zero Cause We Will read data from 0 always Mean Byte Array Always Had One Value
        * Length : How much to read
        * buffer : where to read
        * result : has Data Type Value
         */
        offset =0;
        long result = 0 ;
        int shift_count = 0 ;

        while ( length -- > 0 ) {
            int temp = buffer[(int) offset++] &  0xFF;
            result |= temp << shift_count ;
            shift_count += 8;
        }
        return(result);
    }

    public static void nameToBytes(byte[] buf , String name) {
        /*
        * This Method Use To Store Name in Utf-8 As PkWare Says
        */
        int i;
        byte[] stringBuffer = name.getBytes(Charset.forName("UTF-8"));
        for (i = 0; i < stringBuffer.length && i < name.length(); ++i) {
            buf[i] = stringBuffer[i];
        }

    }

    public static StringBuffer parseName(byte[] header, long length) {
        /*
         * convert int number to String
         */
        int end = (int) length;
        int i;
        for (i = 0; i < end; ++i)
            if (header[i] == 0)
                break;
        byte[] stringBuffer = Arrays.copyOfRange(header, 0, (int)(length));
        StringBuffer result = new StringBuffer(new String(stringBuffer));
        return result;
    }

}
