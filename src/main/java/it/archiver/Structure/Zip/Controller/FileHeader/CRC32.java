package it.archiver.Structure.Zip.Controller.FileHeader;

import java.io.*;

public class CRC32 {
    private String crc;
    private StringBuffer divided;
    private static final String polynomial = "100000100110000010001110110110111";

    public CRC32() {

    }

    public CRC32(String message) {
        this.divided = new StringBuffer();
        for (int i = 0; i < message.length(); i++) {
            StringBuffer currChar = new StringBuffer();
            currChar.append(Integer.toBinaryString(message.charAt(i)));
            this.divided.append(fixLength(currChar));
        }
        ReverseBitsInEachByte();
        append32Zeros();
        XORTheFirst4Bytes();
        this.divided = fixLength(divided);
        crc = getCRCReminder(divided.toString(), polynomial);
        crc = reverseBits(crc);
    }

    public CRC32(long number) {
        divided = new StringBuffer(Long.toBinaryString(number));
        this.divided = fixLength(divided);
        ReverseBitsInEachByte();
        append32Zeros();
        XORTheFirst4Bytes();
        this.divided = fixLength(divided);
        crc = getCRCReminder(divided.toString(), polynomial);
        crc = reverseBits(crc);
    }

    public CRC32(File file) {
        try {
            int x;
            this.divided = new StringBuffer();
            FileInputStream fileReader = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileReader);
            while ((x = bufferedInputStream.read()) != -1) {
                StringBuffer currChar = new StringBuffer();
                currChar.append(Integer.toBinaryString((char) x));
                this.divided.append(fixLength(currChar));
            }
            if (divided.length() == 0)
                crc = "00000000000000000000000000000000";
            else {
            ReverseBitsInEachByte();
            append32Zeros();
            XORTheFirst4Bytes();
            this.divided = fixLength(divided);
            crc = getCRCReminder(divided.toString(), polynomial);
            crc = reverseBits(crc);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private StringBuffer fixLength(StringBuffer string) {
        while (string.length() % 8 != 0)
            string.insert(0, 0);
        return string;
    }

    public void ReverseBitsInEachByte() {
        StringBuffer current = divided;
        divided = new StringBuffer();
        for (int i = 7; i < current.length(); i += 8)
            for (int j = 0; j < 8; j++)
                divided.append(current.charAt(i - j));
    }

    private void append32Zeros() {
        for (int i = 0; i < 32; i++)
            divided.append(0);
    }

    private void XORTheFirst4Bytes() {
        StringBuffer after4Bytes = new StringBuffer(divided.substring(32));
        divided = new StringBuffer(Long.toBinaryString(Long.parseLong(divided.substring(0, 32), 2) ^ Long.parseLong("FFFFFFFF", 16)));
        divided.append(after4Bytes);
    }

    public String getCRCReminder(String dividend, String divisor) {
        long div = Long.parseLong(divisor, 2), xorResult = 0;
        int i;
        for (i = 0; i < dividend.length(); i++)
            if (dividend.charAt(i) == '1') {
                dividend = dividend.substring(i);
                break;
            }
        xorResult = Long.parseLong(dividend.substring(0, divisor.length()), 2);
        xorResult ^= div;
        i = divisor.length();
        while (i < dividend.length()) {
            if (Long.toBinaryString(xorResult).length() < divisor.length()) {
                if (dividend.charAt(i) == '0') {
                    xorResult <<= 1;
                } else if (dividend.charAt(i) == '1')
                    xorResult = Long.parseLong(Long.toBinaryString(xorResult) + "1", 2);
                i++;
            } else if (Long.toBinaryString(xorResult).length() == divisor.length())
                xorResult ^= div;
        }
        if (Long.toBinaryString(xorResult).length() == divisor.length())
            xorResult ^= div;
        return Long.toBinaryString(xorResult);
    }

    public String reverseBits(String bits) {
        StringBuffer XORBits = new StringBuffer(Long.toBinaryString(Long.parseLong(bits, 2) ^ Long.parseLong("FFFFFFFF", 16)));
        XORBits = fixLength(XORBits);
        StringBuffer CRC32 = new StringBuffer();
        for (int i = XORBits.length() - 1; i >= 0; i--)
            CRC32.append(XORBits.charAt(i));
        return CRC32.toString();
    }

    public String getBinaryCRC() {
        return crc;
    }

    public String getHexCRC() {
        return Long.toHexString(Long.parseLong(crc, 2));
    }

    public Long getLongCRC() {
        return Long.parseLong(crc, 2);
    }
}
