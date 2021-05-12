package it.archiver.PhotoCompression.EntropyCoding;


import it.archiver.PhotoCompression.Comment.comment;
import it.archiver.PhotoCompression.KnownPaths.KnowFolders;
import javafx.util.Pair;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@comment(
        id=1,
        name="jamal",
        Edit="research and developer",
        date="07/05/2020"
)
public class DcAndAc {

    private int width;
    private int height;
    private int blockSize;


    private int previousBlock;
    private int indexForDc;
    private int indexForAc;
    private int Diff;
    private FileOutputStream fileOutputStream;
    private BufferedOutputStream bufferedOutputStream;
    private StringBuffer stringBlock;

    private String fileName;

    private HashMap<Integer, HashMap<Integer, String>> DC;
    private HashMap<Integer, String> sizeDC;
    private HashMap<Integer, Integer> lengthDC;
    private HashMap<Integer, String> level0;
    private HashMap<Integer, String> level1;
    private HashMap<Integer, String> level2;
    private HashMap<Integer, String> level3;
    private HashMap<Integer, String> level4;
    private HashMap<Integer, String> level5;
    private HashMap<Integer, String> level6;
    private HashMap<Integer, String> level7;
    private HashMap<Pair<Integer, Integer>, String> luAcMab;
    private HashMap<Pair<Integer, Integer>, String> chAcMab;
    private ArrayList<Pair<Integer, Integer>> acList;



    public DcAndAc(int width, int height, int blockSize, int[][] image, String fileName) throws IOException {
        this.width = width;
        this.height = height;
        this.blockSize = blockSize;
        this.fileName = fileName;

        this.level0 = new HashMap<>();
        this.level1 = new HashMap<>();
        this.level2 = new HashMap<>();
        this.level3 = new HashMap<>();
        this.level4 = new HashMap<>();
        this.level5 = new HashMap<>();
        this.level6 = new HashMap<>();
        this.level7 = new HashMap<>();

        level0.put(0, "0");

        level1.put(-1, "0");
        level1.put(1, "1");

        level2.put(-3, "00");
        level2.put(-2, "01");
        level2.put(2, "10");
        level2.put(3, "11");

        level3.put(-7, "000");
        level3.put(-6, "001");
        level3.put(-5, "010");
        level3.put(-4, "011");
        level3.put(4, "100");
        level3.put(5, "101");
        level3.put(6, "110");
        level3.put(7, "111");


        level4.put(-15, "0000");
        level4.put(-14, "0001");
        level4.put(-13, "0010");
        level4.put(-12, "0011");
        level4.put(-11, "0100");
        level4.put(-10, "0101");
        level4.put(-9, "0110");
        level4.put(-8, "0111");
        level4.put(8, "1000");
        level4.put(9, "1001");
        level4.put(10, "1010");
        level4.put(11, "1011");
        level4.put(12, "1100");
        level4.put(13, "1101");
        level4.put(14, "1110");
        level4.put(15, "1111");

        level5.put(-31, "00000");
        level5.put(-30, "00001");
        level5.put(-29, "00010");
        level5.put(-28, "00011");
        level5.put(-27, "00100");
        level5.put(-26, "00101");
        level5.put(-25, "00110");
        level5.put(-24, "00111");
        level5.put(-23, "01000");
        level5.put(-22, "01001");
        level5.put(-21, "01010");
        level5.put(-20, "01011");
        level5.put(-19, "01100");
        level5.put(-18, "01101");
        level5.put(-17, "01110");
        level5.put(-16, "01111");
        level5.put(16, "10000");
        level5.put(17, "10001");
        level5.put(18, "10010");
        level5.put(19, "10011");
        level5.put(20, "10100");
        level5.put(21, "10101");
        level5.put(22, "10110");
        level5.put(23, "10111");
        level5.put(24, "11000");
        level5.put(25, "11001");
        level5.put(26, "11010");
        level5.put(27, "11011");
        level5.put(28, "11100");
        level5.put(29, "11101");
        level5.put(30, "11110");
        level5.put(31, "11111");

        level6.put(-63, "000000");
        level6.put(-62, "000001");
        level6.put(-61, "000010");
        level6.put(-60, "000011");
        level6.put(-59, "000100");
        level6.put(-58, "000101");
        level6.put(-57, "000110");
        level6.put(-56, "000111");
        level6.put(-55, "001000");
        level6.put(-54, "001001");
        level6.put(-53, "001010");
        level6.put(-52, "001011");
        level6.put(-51, "001100");
        level6.put(-50, "001101");
        level6.put(-49, "001110");
        level6.put(-48, "001111");
        level6.put(-47, "010000");
        level6.put(-46, "010001");
        level6.put(-45, "010010");
        level6.put(-44, "010011");
        level6.put(-43, "010100");
        level6.put(-42, "010101");
        level6.put(-41, "010110");
        level6.put(-40, "010111");
        level6.put(-39, "011000");
        level6.put(-38, "011001");
        level6.put(-37, "011010");
        level6.put(-36, "011011");
        level6.put(-35, "011100");
        level6.put(-34, "011101");
        level6.put(-33, "011110");
        level6.put(-32, "011111");
        level6.put(32, "100000");
        level6.put(33, "100001");
        level6.put(34, "100010");
        level6.put(35, "100011");
        level6.put(36, "100100");
        level6.put(37, "100101");
        level6.put(38, "100110");
        level6.put(39, "100111");
        level6.put(40, "101000");
        level6.put(41, "101001");
        level6.put(42, "101010");
        level6.put(43, "101011");
        level6.put(44, "101100");
        level6.put(45, "101101");
        level6.put(46, "101110");
        level6.put(47, "101111");
        level6.put(48, "110000");
        level6.put(49, "110001");
        level6.put(50, "110010");
        level6.put(51, "110011");
        level6.put(52, "110100");
        level6.put(53, "110101");
        level6.put(54, "110110");
        level6.put(55, "110111");
        level6.put(56, "111000");
        level6.put(57, "111001");
        level6.put(58, "111010");
        level6.put(59, "111011");
        level6.put(60, "111100");
        level6.put(61, "111101");
        level6.put(62, "111110");
        level6.put(63, "111111");

        level7.put(-127, "0000000");
        level7.put(-126, "0000001");
        level7.put(-125, "0000010");
        level7.put(-124, "0000011");
        level7.put(-123, "0000100");
        level7.put(-122, "0000101");
        level7.put(-121, "0000110");
        level7.put(-120, "0000111");
        level7.put(-119, "0001000");
        level7.put(-118, "0001001");
        level7.put(-117, "0001010");
        level7.put(-116, "0001011");
        level7.put(-115, "0001100");
        level7.put(-114, "0001101");
        level7.put(-113, "0001110");
        level7.put(-112, "0001111");
        level7.put(-111, "0010000");
        level7.put(-110, "0010001");
        level7.put(-109, "0010010");
        level7.put(-108, "0010011");
        level7.put(-107, "0010100");
        level7.put(-106, "0010101");
        level7.put(-105, "0010110");
        level7.put(-104, "0010111");
        level7.put(-103, "0011000");
        level7.put(-102, "0011001");
        level7.put(-101, "0011010");
        level7.put(-100, "0011011");
        level7.put(-99, "0011100");
        level7.put(-98, "0011101");
        level7.put(-97, "0011110");
        level7.put(-96, "0011111");
        level7.put(-95, "0100000");
        level7.put(-94, "0100001");
        level7.put(-93, "0100010");
        level7.put(-92, "0100011");
        level7.put(-91, "0100100");
        level7.put(-90, "0100101");
        level7.put(-89, "0100110");
        level7.put(-88, "0100111");
        level7.put(-87, "0101000");
        level7.put(-86, "0101001");
        level7.put(-85, "0101010");
        level7.put(-84, "0101011");
        level7.put(-83, "0101100");
        level7.put(-82, "0101101");
        level7.put(-81, "0101110");
        level7.put(-80, "0101111");
        level7.put(-79, "0110000");
        level7.put(-78, "0110001");
        level7.put(-77, "0110010");
        level7.put(-76, "0110011");
        level7.put(-75, "0110100");
        level7.put(-74, "0110101");
        level7.put(-73, "0110110");
        level7.put(-72, "0110111");
        level7.put(-71, "0111000");
        level7.put(-70, "0111001");
        level7.put(-69, "0111010");
        level7.put(-68, "0111011");
        level7.put(-67, "0111100");
        level7.put(-66, "0111101");
        level7.put(-65, "0111110");
        level7.put(-64, "0111111");
        level7.put(127, "1000000");
        level7.put(126, "1000001");
        level7.put(125, "1000010");
        level7.put(124, "1000011");
        level7.put(123, "1000100");
        level7.put(122, "1000101");
        level7.put(121, "1000110");
        level7.put(120, "1000111");
        level7.put(119, "1001000");
        level7.put(118, "1001001");
        level7.put(117, "1001010");
        level7.put(116, "1001011");
        level7.put(115, "1001100");
        level7.put(114, "1001101");
        level7.put(113, "1001110");
        level7.put(112, "1001111");
        level7.put(111, "1010000");
        level7.put(110, "1010001");
        level7.put(109, "1010010");
        level7.put(108, "1010011");
        level7.put(107, "1010100");
        level7.put(106, "1010101");
        level7.put(105, "1010110");
        level7.put(104, "1010111");
        level7.put(103, "1011000");
        level7.put(102, "1011001");
        level7.put(101, "1011010");
        level7.put(100, "1011011");
        level7.put(99, "1011100");
        level7.put(98, "1011101");
        level7.put(97, "1011110");
        level7.put(96, "1011111");
        level7.put(95, "1100000");
        level7.put(94, "1100001");
        level7.put(93, "1100010");
        level7.put(92, "1100011");
        level7.put(91, "1100100");
        level7.put(90, "1100101");
        level7.put(89, "1100110");
        level7.put(88, "1100111");
        level7.put(87, "1101000");
        level7.put(86, "1101001");
        level7.put(85, "1101010");
        level7.put(84, "1101011");
        level7.put(83, "1101100");
        level7.put(82, "1101101");
        level7.put(81, "1101110");
        level7.put(80, "1101111");
        level7.put(79, "1110000");
        level7.put(78, "1110001");
        level7.put(77, "1110010");
        level7.put(76, "1110011");
        level7.put(75, "1110100");
        level7.put(74, "1110101");
        level7.put(73, "1110110");
        level7.put(72, "1110111");
        level7.put(71, "1111000");
        level7.put(70, "1111001");
        level7.put(69, "1111010");
        level7.put(68, "1111011");
        level7.put(67, "1111100");
        level7.put(66, "1111101");
        level7.put(65, "1111110");
        level7.put(64, "1111111");

        this.DC = new HashMap<>();
        DC.put(0, level0);
        DC.put(1, level1);
        DC.put(2, level2);
        DC.put(3, level3);
        DC.put(4, level4);
        DC.put(5, level5);
        DC.put(6, level6);
        DC.put(7, level7);


        this.sizeDC = new HashMap<>();
        sizeDC.put(0, "00");
        sizeDC.put(1, "10"); // -1 and 1
        sizeDC.put(2, "11"); // -3 to 3
        sizeDC.put(3, "100"); // -7 to 7
        sizeDC.put(4, "101"); // -15 to 15
        sizeDC.put(5, "110"); // -31 to 31
        sizeDC.put(6, "1110"); // -64 to 64
        sizeDC.put(7, "11110"); // -127 to 127
        sizeDC.put(8, "111110");
        sizeDC.put(9, "1111110");
        sizeDC.put(10, "11111110");
        sizeDC.put(11, "111111110");

        Pair<Integer, Integer> S00 = new Pair<>(0, 0);
        Pair<Integer, Integer> S01 = new Pair<>(0, 1);
        Pair<Integer, Integer> S02 = new Pair<>(0, 2);
        Pair<Integer, Integer> S03 = new Pair<>(0, 3);
        Pair<Integer, Integer> S04 = new Pair<>(0, 4);
        Pair<Integer, Integer> S05 = new Pair<>(0, 5);
        Pair<Integer, Integer> S06 = new Pair<>(0, 6);
        Pair<Integer, Integer> S07 = new Pair<>(0, 7);
        Pair<Integer, Integer> S08 = new Pair<>(0, 8);
        Pair<Integer, Integer> S09 = new Pair<>(0, 9);
        Pair<Integer, Integer> S010 = new Pair<>(0, 10);


        Pair<Integer, Integer> S11 = new Pair<>(1, 1);
        Pair<Integer, Integer> S12 = new Pair<>(1, 2);
        Pair<Integer, Integer> S13 = new Pair<>(1, 3);
        Pair<Integer, Integer> S14 = new Pair<>(1, 4);
        Pair<Integer, Integer> S15 = new Pair<>(1, 5);
        Pair<Integer, Integer> S16 = new Pair<>(1, 6);
        Pair<Integer, Integer> S17 = new Pair<>(1, 7);
        Pair<Integer, Integer> S18 = new Pair<>(1, 8);
        Pair<Integer, Integer> S19 = new Pair<>(1, 9);
        Pair<Integer, Integer> S110 = new Pair<>(1, 10);

        Pair<Integer, Integer> S21 = new Pair<>(2, 1);
        Pair<Integer, Integer> S22 = new Pair<>(2, 2);
        Pair<Integer, Integer> S23 = new Pair<>(2, 3);
        Pair<Integer, Integer> S24 = new Pair<>(2, 4);
        Pair<Integer, Integer> S25 = new Pair<>(2, 5);
        Pair<Integer, Integer> S26 = new Pair<>(2, 6);
        Pair<Integer, Integer> S27 = new Pair<>(2, 7);
        Pair<Integer, Integer> S28 = new Pair<>(2, 8);
        Pair<Integer, Integer> S29 = new Pair<>(2, 9);
        Pair<Integer, Integer> S210 = new Pair<>(2, 10);

        Pair<Integer, Integer> S31 = new Pair<>(3, 1);
        Pair<Integer, Integer> S32 = new Pair<>(3, 2);
        Pair<Integer, Integer> S33 = new Pair<>(3, 3);
        Pair<Integer, Integer> S34 = new Pair<>(3, 4);
        Pair<Integer, Integer> S35 = new Pair<>(3, 5);
        Pair<Integer, Integer> S36 = new Pair<>(3, 6);
        Pair<Integer, Integer> S37 = new Pair<>(3, 7);
        Pair<Integer, Integer> S38 = new Pair<>(3, 8);
        Pair<Integer, Integer> S39 = new Pair<>(3, 9);
        Pair<Integer, Integer> S310 = new Pair<>(3, 10);


        Pair<Integer, Integer> S41 = new Pair<>(4, 1);
        Pair<Integer, Integer> S42 = new Pair<>(4, 2);
        Pair<Integer, Integer> S43 = new Pair<>(4, 3);
        Pair<Integer, Integer> S44 = new Pair<>(4, 4);
        Pair<Integer, Integer> S45 = new Pair<>(4, 5);
        Pair<Integer, Integer> S46 = new Pair<>(4, 6);
        Pair<Integer, Integer> S47 = new Pair<>(4, 7);
        Pair<Integer, Integer> S48 = new Pair<>(4, 8);
        Pair<Integer, Integer> S49 = new Pair<>(4, 9);
        Pair<Integer, Integer> S410 = new Pair<>(4, 10);

        Pair<Integer, Integer> S51 = new Pair<>(5, 1);
        Pair<Integer, Integer> S52 = new Pair<>(5, 2);
        Pair<Integer, Integer> S53 = new Pair<>(5, 3);
        Pair<Integer, Integer> S54 = new Pair<>(5, 4);
        Pair<Integer, Integer> S55 = new Pair<>(5, 5);
        Pair<Integer, Integer> S56 = new Pair<>(5, 6);
        Pair<Integer, Integer> S57 = new Pair<>(5, 7);
        Pair<Integer, Integer> S58 = new Pair<>(5, 8);
        Pair<Integer, Integer> S59 = new Pair<>(5, 9);
        Pair<Integer, Integer> S510 = new Pair<>(5, 10);

        Pair<Integer, Integer> S61 = new Pair<>(6, 1);
        Pair<Integer, Integer> S62 = new Pair<>(6, 2);
        Pair<Integer, Integer> S63 = new Pair<>(6, 3);
        Pair<Integer, Integer> S64 = new Pair<>(6, 4);
        Pair<Integer, Integer> S65 = new Pair<>(6, 5);
        Pair<Integer, Integer> S66 = new Pair<>(6, 6);
        Pair<Integer, Integer> S67 = new Pair<>(6, 7);
        Pair<Integer, Integer> S68 = new Pair<>(6, 8);
        Pair<Integer, Integer> S69 = new Pair<>(6, 9);
        Pair<Integer, Integer> S610 = new Pair<>(6, 10);

        Pair<Integer, Integer> S71 = new Pair<>(7, 1);
        Pair<Integer, Integer> S72 = new Pair<>(7, 2);
        Pair<Integer, Integer> S73 = new Pair<>(7, 3);
        Pair<Integer, Integer> S74 = new Pair<>(7, 4);
        Pair<Integer, Integer> S75 = new Pair<>(7, 5);
        Pair<Integer, Integer> S76 = new Pair<>(7, 6);
        Pair<Integer, Integer> S77 = new Pair<>(7, 7);
        Pair<Integer, Integer> S78 = new Pair<>(7, 8);
        Pair<Integer, Integer> S79 = new Pair<>(7, 9);
        Pair<Integer, Integer> S710 = new Pair<>(7, 10);

        Pair<Integer, Integer> S81 = new Pair<>(8, 1);
        Pair<Integer, Integer> S82 = new Pair<>(8, 2);
        Pair<Integer, Integer> S83 = new Pair<>(8, 3);
        Pair<Integer, Integer> S84 = new Pair<>(8, 4);
        Pair<Integer, Integer> S85 = new Pair<>(8, 5);
        Pair<Integer, Integer> S86 = new Pair<>(8, 6);
        Pair<Integer, Integer> S87 = new Pair<>(8, 7);
        Pair<Integer, Integer> S88 = new Pair<>(8, 8);
        Pair<Integer, Integer> S89 = new Pair<>(8, 9);
        Pair<Integer, Integer> S810 = new Pair<>(8, 10);

        Pair<Integer, Integer> S91 = new Pair<>(9, 1);
        Pair<Integer, Integer> S92 = new Pair<>(9, 2);
        Pair<Integer, Integer> S93 = new Pair<>(9, 3);
        Pair<Integer, Integer> S94 = new Pair<>(9, 4);
        Pair<Integer, Integer> S95 = new Pair<>(9, 5);
        Pair<Integer, Integer> S96 = new Pair<>(9, 6);
        Pair<Integer, Integer> S97 = new Pair<>(9, 7);
        Pair<Integer, Integer> S98 = new Pair<>(9, 8);
        Pair<Integer, Integer> S99 = new Pair<>(9, 9);
        Pair<Integer, Integer> S910 = new Pair<>(9, 10);


        Pair<Integer, Integer> S101 = new Pair<>(10, 1);
        Pair<Integer, Integer> S102 = new Pair<>(10, 2);
        Pair<Integer, Integer> S103 = new Pair<>(10, 3);
        Pair<Integer, Integer> S104 = new Pair<>(10, 4);
        Pair<Integer, Integer> S105 = new Pair<>(10, 5);
        Pair<Integer, Integer> S106 = new Pair<>(10, 6);
        Pair<Integer, Integer> S107 = new Pair<>(10, 7);
        Pair<Integer, Integer> S108 = new Pair<>(10, 8);
        Pair<Integer, Integer> S109 = new Pair<>(10, 9);
        Pair<Integer, Integer> S1010 = new Pair<>(10, 10);


        Pair<Integer, Integer> S111 = new Pair<>(11, 1);
        Pair<Integer, Integer> S112 = new Pair<>(11, 2);
        Pair<Integer, Integer> S113 = new Pair<>(11, 3);
        Pair<Integer, Integer> S114 = new Pair<>(11, 4);
        Pair<Integer, Integer> S115 = new Pair<>(11, 5);
        Pair<Integer, Integer> S116 = new Pair<>(11, 6);
        Pair<Integer, Integer> S117 = new Pair<>(11, 7);
        Pair<Integer, Integer> S118 = new Pair<>(11, 8);
        Pair<Integer, Integer> S119 = new Pair<>(11, 9);
        Pair<Integer, Integer> S1110 = new Pair<>(11, 10);

        Pair<Integer, Integer> S121 = new Pair<>(12, 1);
        Pair<Integer, Integer> S122 = new Pair<>(12, 2);
        Pair<Integer, Integer> S123 = new Pair<>(12, 3);
        Pair<Integer, Integer> S124 = new Pair<>(12, 4);
        Pair<Integer, Integer> S125 = new Pair<>(12, 5);
        Pair<Integer, Integer> S126 = new Pair<>(12, 6);
        Pair<Integer, Integer> S127 = new Pair<>(12, 7);
        Pair<Integer, Integer> S128 = new Pair<>(12, 8);
        Pair<Integer, Integer> S129 = new Pair<>(12, 9);
        Pair<Integer, Integer> S1210 = new Pair<>(12, 10);


        Pair<Integer, Integer> S131 = new Pair<>(13, 1);
        Pair<Integer, Integer> S132 = new Pair<>(13, 2);
        Pair<Integer, Integer> S133 = new Pair<>(13, 3);
        Pair<Integer, Integer> S134 = new Pair<>(13, 4);
        Pair<Integer, Integer> S135 = new Pair<>(13, 5);
        Pair<Integer, Integer> S136 = new Pair<>(13, 6);
        Pair<Integer, Integer> S137 = new Pair<>(13, 7);
        Pair<Integer, Integer> S138 = new Pair<>(13, 8);
        Pair<Integer, Integer> S139 = new Pair<>(13, 9);
        Pair<Integer, Integer> S1310 = new Pair<>(13, 10);


        Pair<Integer, Integer> S141 = new Pair<>(14, 1);
        Pair<Integer, Integer> S142 = new Pair<>(14, 2);
        Pair<Integer, Integer> S143 = new Pair<>(14, 3);
        Pair<Integer, Integer> S144 = new Pair<>(14, 4);
        Pair<Integer, Integer> S145 = new Pair<>(14, 5);
        Pair<Integer, Integer> S146 = new Pair<>(14, 6);
        Pair<Integer, Integer> S147 = new Pair<>(14, 7);
        Pair<Integer, Integer> S148 = new Pair<>(14, 8);
        Pair<Integer, Integer> S149 = new Pair<>(14, 9);
        Pair<Integer, Integer> S1410 = new Pair<>(14, 10);

        Pair<Integer, Integer> S150 = new Pair<>(15, 0);
        Pair<Integer, Integer> S151 = new Pair<>(15, 1);
        Pair<Integer, Integer> S152 = new Pair<>(15, 2);
        Pair<Integer, Integer> S153 = new Pair<>(15, 3);
        Pair<Integer, Integer> S154 = new Pair<>(15, 4);
        Pair<Integer, Integer> S155 = new Pair<>(15, 5);
        Pair<Integer, Integer> S156 = new Pair<>(15, 6);
        Pair<Integer, Integer> S157 = new Pair<>(15, 7);
        Pair<Integer, Integer> S158 = new Pair<>(15, 8);
        Pair<Integer, Integer> S159 = new Pair<>(15, 9);
        Pair<Integer, Integer> S1510 = new Pair<>(15, 10);
        this.luAcMab = new HashMap<>();

        luAcMab.put(S00, "1010");
        luAcMab.put(S01, "00");
        luAcMab.put(S02, "01");
        luAcMab.put(S03, "100");
        luAcMab.put(S04, "1011");
        luAcMab.put(S05, "11010");
        luAcMab.put(S06, "1111000");
        luAcMab.put(S07, "11111000");
        luAcMab.put(S08, "1111110110");
        luAcMab.put(S09, "1111111110000010");
        luAcMab.put(S010,"1111111110000011");

        luAcMab.put(S11, "1100");
        luAcMab.put(S12, "11011");
        luAcMab.put(S13, "1111001");
        luAcMab.put(S14, "111110110");
        luAcMab.put(S15, "11111110110");
        luAcMab.put(S16, "1111111110000100");
        luAcMab.put(S17, "1111111110000101");
        luAcMab.put(S18, "1111111110000110");
        luAcMab.put(S19, "1111111110000111");
        luAcMab.put(S110,"1111111110001000");


        luAcMab.put(S21, "11100");
        luAcMab.put(S22, "11111001");
        luAcMab.put(S23, "1111110111");
        luAcMab.put(S24, "111111110111");
        luAcMab.put(S25, "1111111110001001");
        luAcMab.put(S26, "1111111110001010");
        luAcMab.put(S27, "1111111110001011");
        luAcMab.put(S28, "1111111110001100");
        luAcMab.put(S29, "1111111110001101");
        luAcMab.put(S210,"1111111110001110");


        luAcMab.put(S31, "111010");
        luAcMab.put(S32, "111110111");
        luAcMab.put(S33, "111111110101");
        luAcMab.put(S34, "1111111110001111");
        luAcMab.put(S35, "1111111110010000");
        luAcMab.put(S36, "1111111110010001");
        luAcMab.put(S37, "1111111110010010");
        luAcMab.put(S38, "1111111110010011");
        luAcMab.put(S39, "1111111110010100");
        luAcMab.put(S310,"1111111110010101");

        luAcMab.put(S41, "111011");
        luAcMab.put(S42, "1111111000");
        luAcMab.put(S43, "1111111110010110");
        luAcMab.put(S44, "1111111110010111");
        luAcMab.put(S45, "1111111110011000");
        luAcMab.put(S46, "1111111110011001");
        luAcMab.put(S47, "1111111110011010");
        luAcMab.put(S48, "1111111110011011");
        luAcMab.put(S49, "1111111110011100");
        luAcMab.put(S410,"1111111110011101");

        luAcMab.put(S51, "1111010");
        luAcMab.put(S52, "11111110111");
        luAcMab.put(S53, "1111111110011110");
        luAcMab.put(S54, "1111111110011111");
        luAcMab.put(S55, "1111111110100000");
        luAcMab.put(S56, "1111111110100001");
        luAcMab.put(S57, "1111111110100010");
        luAcMab.put(S58, "1111111110100011");
        luAcMab.put(S59, "1111111110100100");
        luAcMab.put(S510,"1111111110100101");

        luAcMab.put(S61, "1111011");
        luAcMab.put(S62, "111111110110");
        luAcMab.put(S63, "1111111110100110");
        luAcMab.put(S64, "1111111110100111");
        luAcMab.put(S65, "1111111110101000");
        luAcMab.put(S66, "1111111110101001");
        luAcMab.put(S67, "1111111110101010");
        luAcMab.put(S68, "1111111110101011");
        luAcMab.put(S69, "1111111110101100");
        luAcMab.put(S610,"1111111110101101");

        luAcMab.put(S71, "11111010");
        luAcMab.put(S72, "111111110111");
        luAcMab.put(S73, "1111111110101110");
        luAcMab.put(S74, "1111111110101111");
        luAcMab.put(S75, "1111111110110000");
        luAcMab.put(S76, "1111111110110001");
        luAcMab.put(S77, "1111111110110010");
        luAcMab.put(S78, "1111111110110011");
        luAcMab.put(S79, "1111111110110100");
        luAcMab.put(S710,"1111111110110101");

        luAcMab.put(S81, "111111000");
        luAcMab.put(S82, "111111111000000");
        luAcMab.put(S83, "1111111110110110");
        luAcMab.put(S84, "1111111110110111");
        luAcMab.put(S85, "1111111110111000");
        luAcMab.put(S86, "1111111110111001");
        luAcMab.put(S87, "1111111110111010");
        luAcMab.put(S88, "1111111110111011");
        luAcMab.put(S89, "1111111110111100");
        luAcMab.put(S810,"1111111110111101");

        luAcMab.put(S91, "111111001");
        luAcMab.put(S92, "1111111110111110");
        luAcMab.put(S93, "1111111110111111");
        luAcMab.put(S94, "1111111111000000");
        luAcMab.put(S95, "1111111111000001");
        luAcMab.put(S96, "1111111111000010");
        luAcMab.put(S97, "1111111111000011");
        luAcMab.put(S98, "1111111111000100");
        luAcMab.put(S99, "1111111111000101");
        luAcMab.put(S910,"1111111111000110");

        luAcMab.put(S101, "111111010");
        luAcMab.put(S102, "1111111111000111");
        luAcMab.put(S103, "1111111111001000");
        luAcMab.put(S104, "1111111111001001");
        luAcMab.put(S105, "1111111111001010");
        luAcMab.put(S106, "1111111111001011");
        luAcMab.put(S107, "1111111111001100");
        luAcMab.put(S108, "1111111111001101");
        luAcMab.put(S109, "1111111111001110");
        luAcMab.put(S1010,"1111111111001111");

        luAcMab.put(S111, "1111111001");
        luAcMab.put(S112, "1111111111010000");
        luAcMab.put(S113, "1111111111010001");
        luAcMab.put(S114, "1111111111010010");
        luAcMab.put(S115, "1111111111010011");
        luAcMab.put(S116, "1111111111010100");
        luAcMab.put(S117, "1111111111010101");
        luAcMab.put(S118, "1111111111010110");
        luAcMab.put(S119, "1111111111010111");
        luAcMab.put(S1110,"1111111111011000");

        luAcMab.put(S121, "1111111010");
        luAcMab.put(S122, "1111111111011001");
        luAcMab.put(S123, "1111111111011010");
        luAcMab.put(S124, "1111111111011011");
        luAcMab.put(S125, "1111111111011100");
        luAcMab.put(S126, "1111111111011101");
        luAcMab.put(S127, "1111111111011110");
        luAcMab.put(S128, "1111111111011111");
        luAcMab.put(S129, "1111111111100000");
        luAcMab.put(S1210,"1111111111100001");

        luAcMab.put(S131, "11111111000");
        luAcMab.put(S132, "1111111111100010");
        luAcMab.put(S133, "1111111111100011");
        luAcMab.put(S134, "1111111111100100");
        luAcMab.put(S135, "1111111111100101");
        luAcMab.put(S136, "1111111111100110");
        luAcMab.put(S137, "1111111111100111");
        luAcMab.put(S138, "1111111111101000");
        luAcMab.put(S139, "1111111111101001");
        luAcMab.put(S1310,"1111111111101010");

        luAcMab.put(S141, "1111111111101011");
        luAcMab.put(S142, "1111111111101100");
        luAcMab.put(S143, "1111111111101101");
        luAcMab.put(S144, "1111111111101110");
        luAcMab.put(S145, "1111111111101111");
        luAcMab.put(S146, "1111111111110000");
        luAcMab.put(S147, "1111111111110001");
        luAcMab.put(S148, "1111111111110010");
        luAcMab.put(S149, "1111111111110011");
        luAcMab.put(S1410,"1111111111110100");

        luAcMab.put(S150, "11111111001");
        luAcMab.put(S151, "1111111111110101");
        luAcMab.put(S152, "1111111111110110");
        luAcMab.put(S153, "1111111111110111");
        luAcMab.put(S154, "1111111111111000");
        luAcMab.put(S155, "1111111111111001");
        luAcMab.put(S156, "1111111111111010");
        luAcMab.put(S157, "1111111111111011");
        luAcMab.put(S158, "1111111111111100");
        luAcMab.put(S159, "1111111111111101");
        luAcMab.put(S1510,"1111111111111110");

        this.chAcMab = new HashMap<>();

        chAcMab.put(S00, "00");
        chAcMab.put(S01, "01");
        chAcMab.put(S02, "100");
        chAcMab.put(S03, "1010");
        chAcMab.put(S04, "11000");
        chAcMab.put(S05, "110001");
        chAcMab.put(S06, "111000");
        chAcMab.put(S07, "1111000");
        chAcMab.put(S08, "111110100");
        chAcMab.put(S09, "1111110110");
        chAcMab.put(S010,"111111110100");

        chAcMab.put(S11, "1011");
        chAcMab.put(S12, "111001");
        chAcMab.put(S13, "11110110");
        chAcMab.put(S14, "111110101");
        chAcMab.put(S15, "11111110110");
        chAcMab.put(S16, "111111110101");
        chAcMab.put(S17, "1111111110001000");
        chAcMab.put(S18, "1111111110001001");
        chAcMab.put(S19, "1111111110001010");
        chAcMab.put(S110,"1111111110001011");


        chAcMab.put(S21, "11010");
        chAcMab.put(S22, "11110111");
        chAcMab.put(S23, "1111110111");
        chAcMab.put(S24, "111111110110");
        chAcMab.put(S25, "111111111000010");
        chAcMab.put(S26, "1111111110001100");
        chAcMab.put(S27, "1111111110001101");
        chAcMab.put(S28, "1111111110001110");
        chAcMab.put(S29, "1111111110001111");
        chAcMab.put(S210,"1111111110010000");


        chAcMab.put(S31, "11011");
        chAcMab.put(S32, "11111000");
        chAcMab.put(S33, "1111111000");
        chAcMab.put(S34, "111111110111");
        chAcMab.put(S35, "1111111110010001");
        chAcMab.put(S36, "1111111110010010");
        chAcMab.put(S37, "1111111110010011");
        chAcMab.put(S38, "1111111110010100");
        chAcMab.put(S39, "1111111110010101");
        chAcMab.put(S310,"1111111110010110");

        chAcMab.put(S41, "111010");
        chAcMab.put(S42, "111110110");
        chAcMab.put(S43, "1111111110010111");
        chAcMab.put(S44, "1111111110011000");
        chAcMab.put(S45, "1111111110011001");
        chAcMab.put(S46, "1111111110011010");
        chAcMab.put(S47, "1111111110011011");
        chAcMab.put(S48, "1111111110011100");
        chAcMab.put(S49, "1111111110011101");
        chAcMab.put(S410,"1111111110011110");

        chAcMab.put(S51, "111011");
        chAcMab.put(S52, "1111111001");
        chAcMab.put(S53, "1111111110011111");
        chAcMab.put(S54, "1111111110100000");
        chAcMab.put(S55, "1111111110100001");
        chAcMab.put(S56, "1111111110100010");
        chAcMab.put(S57, "1111111110100011");
        chAcMab.put(S58, "1111111110100100");
        chAcMab.put(S59, "1111111110100101");
        chAcMab.put(S510,"1111111110100110");

        chAcMab.put(S61, "1111001");
        chAcMab.put(S62, "11111110111");
        chAcMab.put(S63, "1111111110100111");
        chAcMab.put(S64, "1111111110101000");
        chAcMab.put(S65, "1111111110101001");
        chAcMab.put(S66, "1111111110101010");
        chAcMab.put(S67, "1111111110101011");
        chAcMab.put(S68, "1111111110101100");
        chAcMab.put(S69, "1111111110101101");
        chAcMab.put(S610,"1111111110101110");

        chAcMab.put(S71, "1111010");
        chAcMab.put(S72, "11111111000");
        chAcMab.put(S73, "1111111110101111");
        chAcMab.put(S74, "1111111110110000");
        chAcMab.put(S75, "1111111110110001");
        chAcMab.put(S76, "1111111110110010");
        chAcMab.put(S77, "1111111110110011");
        chAcMab.put(S78, "1111111110110100");
        chAcMab.put(S79, "1111111110110101");
        chAcMab.put(S710,"1111111110110110");

        chAcMab.put(S81, "11111001");
        chAcMab.put(S82, "1111111110110111");
        chAcMab.put(S83, "1111111110111000");
        chAcMab.put(S84, "1111111110111001");
        chAcMab.put(S85, "1111111110111010");
        chAcMab.put(S86, "1111111110111011");
        chAcMab.put(S87, "1111111110111100");
        chAcMab.put(S88, "1111111110111101");
        chAcMab.put(S89, "1111111110111110");
        chAcMab.put(S810,"1111111110111111");

        chAcMab.put(S91, "111110111");
        chAcMab.put(S92, "1111111111000000");
        chAcMab.put(S93, "1111111111000001");
        chAcMab.put(S94, "1111111111000010");
        chAcMab.put(S95, "1111111111000011");
        chAcMab.put(S96, "1111111111000100");
        chAcMab.put(S97, "1111111111000101");
        chAcMab.put(S98, "1111111111000110");
        chAcMab.put(S99, "1111111111000111");
        chAcMab.put(S910,"1111111111001000");

        chAcMab.put(S101, "111111000");
        chAcMab.put(S102, "1111111111001001");
        chAcMab.put(S103, "1111111111001010");
        chAcMab.put(S104, "1111111111001011");
        chAcMab.put(S105, "1111111111001100");
        chAcMab.put(S106, "1111111111001101");
        chAcMab.put(S107, "1111111111001110");
        chAcMab.put(S108, "1111111111001111");
        chAcMab.put(S109, "1111111111010000");
        chAcMab.put(S1010,"1111111111010001");

        chAcMab.put(S111, "111111001");
        chAcMab.put(S112, "1111111111010010");
        chAcMab.put(S113, "1111111111010011");
        chAcMab.put(S114, "1111111111010100");
        chAcMab.put(S115, "1111111111010101");
        chAcMab.put(S116, "1111111111010110");
        chAcMab.put(S117, "1111111111010111");
        chAcMab.put(S118, "1111111111011000");
        chAcMab.put(S119, "1111111111011001");
        chAcMab.put(S1110,"1111111111011010");

        chAcMab.put(S121, "111111010");
        chAcMab.put(S122, "1111111111011011");
        chAcMab.put(S123, "1111111111011100");
        chAcMab.put(S124, "1111111111011101");
        chAcMab.put(S125, "1111111111011110");
        chAcMab.put(S126, "1111111111011111");
        chAcMab.put(S127, "1111111111100000");
        chAcMab.put(S128, "1111111111100001");
        chAcMab.put(S129, "1111111111100010");
        chAcMab.put(S1210,"1111111111100011");

        chAcMab.put(S131, "11111111001");
        chAcMab.put(S132, "1111111111100100");
        chAcMab.put(S133, "1111111111100101");
        chAcMab.put(S134, "1111111111100110");
        chAcMab.put(S135, "1111111111100111");
        chAcMab.put(S136, "1111111111101000");
        chAcMab.put(S137, "1111111111101001");
        chAcMab.put(S138, "1111111111101010");
        chAcMab.put(S139, "1111111111101011");
        chAcMab.put(S1310,"1111111111101100");

        chAcMab.put(S141, "11111111100000");
        chAcMab.put(S142, "1111111111101101");
        chAcMab.put(S143, "1111111111101110");
        chAcMab.put(S144, "1111111111101111");
        chAcMab.put(S145, "1111111111110000");
        chAcMab.put(S146, "1111111111110001");
        chAcMab.put(S147, "1111111111110010");
        chAcMab.put(S148, "1111111111110011");
        chAcMab.put(S149, "1111111111110100");
        chAcMab.put(S1410,"1111111111110101");

        chAcMab.put(S150, "1111111010");
        chAcMab.put(S151, "111111111000011");
        chAcMab.put(S152, "1111111111110110");
        chAcMab.put(S153, "1111111111110111");
        chAcMab.put(S154, "1111111111111000");
        chAcMab.put(S155, "1111111111111001");
        chAcMab.put(S156, "1111111111111010");
        chAcMab.put(S157, "1111111111111011");
        chAcMab.put(S158, "1111111111111100");
        chAcMab.put(S159, "1111111111111101");
        chAcMab.put(S1510,"1111111111111110");

        /*this.lengthDC = new HashMap<>();
        lengthDC.put(0, 2);
        lengthDC.put(1, 3);
        lengthDC.put(2, 3);
        lengthDC.put(3, 3);
        lengthDC.put(4, 3);
        lengthDC.put(5, 3);
        lengthDC.put(6, 4);
        lengthDC.put(7, 5);
         */


        this.indexForDc = 0;
        this.indexForAc = 0;
        this.previousBlock = 0;
        this.stringBlock =new StringBuffer();
        this.Diff = 0;
        this.acList = new ArrayList<>();




        this.fileOutputStream = new FileOutputStream(KnowFolders.Desktop.getPath()+"\\fileName");
        this.bufferedOutputStream = new BufferedOutputStream(this.fileOutputStream);


        int numBlock = (int) (this.width * this.height / Math.pow(blockSize, 2)) + (int) ((this.width * this.height / Math.pow(blockSize, 2)) / 4) + (int) ((this.width * this.height / Math.pow(blockSize, 2)) / 4);
        for (int a = 0; a < numBlock; a++) {
            DcAndEntropyCoding(image[a][0]);
            AcAndEntropyCoding(image[a]);
        }

        bufferedOutputStream.close();



    }

    private byte[] parseByteFromString() {
        byte[] bytes = new byte[stringBlock.length() / 7];
        for (int i = 0; i < stringBlock.length() / 7; i++) {
            String Sub = stringBlock.substring(i * 7, (i * 7) + 7);
            bytes[i] = Byte.parseByte(Sub, 2);
        }
        return bytes;
    }

    private int foundString(int index) {
        for (int i = 0; i < DC.size(); i++) {
            if (DC.get(i).containsKey(index)) {
                return i;
            }
        }
        System.out.println("Error...!");
        return 0;
    }

    private void DcAndEntropyCoding(int currentBlock) throws IOException {
        Diff = currentBlock - previousBlock;
        int numOfExtra = 0;
        for (int i = 0; i < sizeDC.size(); i++) {
            if (DC.get(i).containsKey(Diff)) {
                StringBuffer StringBuffer1=new StringBuffer(sizeDC.get(i));
                if ( StringBuffer1.length() % 7 != 0) {
                    numOfExtra =  StringBuffer1.length() % 7;
                    numOfExtra = 7 - numOfExtra;
                }
                for (int j = 0; j < numOfExtra; j++) {
                    StringBuffer1.append("0");
                }

                stringBlock.append(new String( StringBuffer1));

                bufferedOutputStream.write(numOfExtra);
                bufferedOutputStream.write(parseByteFromString());

                stringBlock.delete(0,stringBlock.length());

                StringBuffer StringBuffer2=new StringBuffer(DC.get(i).get(Diff));
                if ( StringBuffer2.length() % 7 != 0) {
                    numOfExtra =  StringBuffer2.length() % 7;
                    numOfExtra = 7 - numOfExtra;
                }
                for (int j = 0; j < numOfExtra; j++) {
                    StringBuffer2.append("0");
                }
                stringBlock.append(new String(StringBuffer2));

                bufferedOutputStream.write(numOfExtra);
                bufferedOutputStream.write(parseByteFromString());

                stringBlock.delete(0,stringBlock.length());

                indexForDc++;
                previousBlock = currentBlock;
                break;
            }
        }
    }

    private void AcAndEntropyCoding(int[] array) throws IOException {
        int zeroCounter = 0;
        int numOfExtra = 0;

        for (int i = 1; i < array.length; i++) {
            if (array[i] == 0) {
                zeroCounter++;
            }

            if (array[i] != 0) {
                Pair<Integer, Integer> ac1 = new Pair<>(zeroCounter,foundString(array[i]));

                StringBuffer StringBuffer1=new StringBuffer(chAcMab.get(ac1));
                if ( StringBuffer1.length() % 7 != 0) {
                    numOfExtra =  StringBuffer1.length() % 7;
                    numOfExtra = 7 - numOfExtra;
                }
                for (int j = 0; j < numOfExtra; j++) {
                    StringBuffer1.append("0");
                }
                stringBlock.append(new String( StringBuffer1));

                bufferedOutputStream.write(numOfExtra);
                bufferedOutputStream.write(parseByteFromString());

                stringBlock.delete(0,stringBlock.length());

                StringBuffer StringBuffer2=new StringBuffer(DC.get(foundString(array[i])).get(array[i]));
                if ( StringBuffer2.length() % 7 != 0) {
                    numOfExtra =  StringBuffer2.length() % 7;
                    numOfExtra = 7 - numOfExtra;
                }
                for (int j = 0; j < numOfExtra; j++) {
                    StringBuffer2.append("0");
                }

                stringBlock.append(new String(StringBuffer2));


                bufferedOutputStream.write(numOfExtra);
                bufferedOutputStream.write(parseByteFromString());

                stringBlock.delete(0,stringBlock.length());

                zeroCounter = 0;
            }
            if (zeroCounter == 16) {
                Pair<Integer, Integer> ac2 = new Pair<>(zeroCounter-1,foundString(array[i]));

                StringBuffer StringBuffer1=new StringBuffer(chAcMab.get(ac2));
                if ( StringBuffer1.length() % 7 != 0) {
                    numOfExtra =  StringBuffer1.length() % 7;
                    numOfExtra = 7 - numOfExtra;
                }
                for (int j = 0; j < numOfExtra; j++) {
                    StringBuffer1.append("0");
                }
                stringBlock.append(new String(StringBuffer1));
                bufferedOutputStream.write(numOfExtra);
                bufferedOutputStream.write(parseByteFromString());

                stringBlock.delete(0,stringBlock.length());
                zeroCounter = 0;
            }
        }
        Pair<Integer, Integer> ac3 = new Pair<>(0, 0);
        StringBuffer StringBuffer1=new StringBuffer(luAcMab.get(ac3));
        if ( StringBuffer1.length() % 7 != 0) {
            numOfExtra =  StringBuffer1.length() % 7;
            numOfExtra = 7 - numOfExtra;
        }
        for (int j = 0; j < numOfExtra; j++) {
            StringBuffer1.append("0");
        }
        stringBlock.append(luAcMab.get(ac3));
        bufferedOutputStream.write(numOfExtra);
        bufferedOutputStream.write(parseByteFromString());

        stringBlock.delete(0,stringBlock.length());

    }
}



    /*private void foundString1(String e) {
        int numOfExtra = 0;
        StringBuffer b = new StringBuffer(e);
        if (b.length() % 7 != 0) {
            numOfExtra = b.length() % 7;
            numOfExtra = 7 - numOfExtra;
        }
        for (int i = 0; i < numOfExtra; i++) {
            b.append("0");
        }

        byte[] bytes = new byte[b.length() / 7];
        for (int i = 0; i < b.length() / 7; i++) {
            String Sub = b.substring(i * 7, (i * 7) + 7);
            bytes[i] = Byte.parseByte(Sub, 2);
        }

        String res = null;

        for (int j = 0; j < bytes.length; j += 2) {
            res = Integer.toBinaryString(bytes[j]);
            for (int i1 = 1; i1 < sizeDC.size(); i1++) {
                if (sizeDC.get(i1).contentEquals(res)) {
                    res = Integer.toBinaryString(bytes[j + 1]);
                    Set set = DC.get(i1).entrySet();
                    for (Object o : set) {
                        Map.Entry m = (Map.Entry) o;
                        if (m.getValue().equals(res)) {
                            System.out.println(m.getKey());
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

     */



