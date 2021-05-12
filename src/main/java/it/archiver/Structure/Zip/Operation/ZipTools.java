package it.archiver.Structure.Zip.Operation;

public class ZipTools {
    public static void size(byte [] bytes){
        System.out.println(bytes[0] + bytes[1]);
    }
    public static int mergeNumbers(int number1 , int number2 , int number3){
        String s1 = Integer.toBinaryString(number1);
        String s2 = Integer.toBinaryString(number2);
        String s3 = Integer.toBinaryString(number3);
        s1 += s2;
        s1 += s3;

        return 1;
    }
}
