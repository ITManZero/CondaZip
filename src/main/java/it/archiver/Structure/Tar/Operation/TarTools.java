package it.archiver.Structure.Tar.Operation;

import it.archiver.Exception.FilePathNotCorrectException;
import it.archiver.Structure.Tar.TarStructure.TarHeader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class TarTools {

    public static void nameToBytes(TarHeader.HeaderComponent name, byte[] buf) {
        int i;
        byte[] stringBuffer = ((StringBuffer) name.getValue()).toString().getBytes(Charset.forName("UTF-8"));
        for (i = 0; i < stringBuffer.length && i < name.getLength(); ++i) {
            buf[name.getOffset() + i] = stringBuffer[i];
        }
        for (; i < name.getLength(); ++i) {
            buf[name.getOffset() + i] = 0;
        }
    }

    public static void integerOctalToBytes(TarHeader.HeaderComponent value, byte[] buf) {
        octalToBytes(value, buf, false);
    }

    public static void longOctalToBytes(TarHeader.HeaderComponent value, byte[] buf) {
        octalToBytes(value, buf, true);
    }

    private static void octalToBytes(TarHeader.HeaderComponent value, byte[] buf, boolean isLongOctal) {
        int idx;
        long val;
        if (isLongOctal) {
            idx = value.getLength();
            val = (long) value.getValue();
        } else {
            idx = value.getLength() - 1;
            val = (int) value.getValue();
        }

        buf[value.getOffset() + idx] = 0;
        --idx;

        buf[value.getOffset() + idx] = (byte) ' ';
        --idx;
        if (val == 0) {
            buf[value.getOffset() + idx] = (byte) '0';
            --idx;
        } else {
            for (; idx >= 0 && val > 0; --idx) {
                buf[value.getOffset() + idx] = (byte) ((byte) '0' + (byte) (val & 7));
                val = val >> 3;
            }
        }
        for (; idx >= 0; --idx) {
            buf[value.getOffset() + idx] = (byte) ' ';
        }
    }

    public static long computeCheckSum(byte[] buf) {
        long sum = 0;
        for (int i = 0; i < buf.length; ++i)
            sum += 255 & buf[i];
        return sum;
    }

    public static void getCheckSumOctalBytes(TarHeader.HeaderComponent value, byte[] buf) {
        integerOctalToBytes(value, buf);
        buf[value.getOffset() + value.getLength() - 1] = (byte) ' ';
        buf[value.getOffset() + value.getLength() - 2] = 0;

    }


    public static StringBuffer parseName(byte[] header, int offset, int length) {
        int end = offset + length;
        int i;
        for (i = 0; i < end; ++i)
            if (header[offset + i] == 0)
                break;
        byte[] stringBuffer = Arrays.copyOfRange(header, 0, i);
        StringBuffer result = null;
        try {
            result = new StringBuffer(new String(stringBuffer, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static long parseOctal(byte[] header, int offset, int length) {
        long result = 0L;
        boolean numberNotFound = true;
        int end = offset + length;

        for (int i = offset; i < end && header[i] != 0; ++i) {
            if (header[i] == 32 || header[i] == 48) {
                if (numberNotFound) {
                    continue;
                }
                if (header[i] == 32) {
                    break;
                }
            }
            numberNotFound = false;
            result = (result << 3) + (long) (header[i] - 48);
        }
        return result;
    }


    public static boolean checkValidDestination(String outputFileDestination) throws FilePathNotCorrectException {
        boolean valid;
        if (!new File(outputFileDestination).exists())
            new File(outputFileDestination).mkdir();
        valid = new File(outputFileDestination).exists();
        if (!valid)
            throw new FilePathNotCorrectException("File path[" + outputFileDestination + "] not correct");
        return true;
    }

    public static String fixOutputFileName(String outputFileName) {
        if (outputFileName.contains("."))
            return outputFileName.substring(0, outputFileName.indexOf('.')).concat(".tar");
        else return outputFileName;
    }

    public static boolean renameTarFile(File tarFile, String destPath) {
        String fixedName = fixOutputFileName(tarFile.getName());
        return tarFile.renameTo(new File(destPath + fixedName));
    }

    public static String autoRename(File fileName, String destPath) {
        int copyNum = 1;
        String fixedName = fixOutputFileName(fileName.getName());
        boolean renamed = fileName.renameTo(new File(destPath + fixedName));
        String newName = fixedName;
        while (!renamed) {

            renamed = fileName.renameTo(new File(destPath + copyNum + "-" + fixedName));
            newName = copyNum + "-" + fixedName;
            copyNum++;
        }
        return newName;
    }

}
