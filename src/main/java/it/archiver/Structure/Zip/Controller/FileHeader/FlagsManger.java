package it.archiver.Structure.Zip.Controller.FileHeader;

public class FlagsManger {
//    FileHeader fileHeader;
//
//    public FlagsManger(FileHeader fileHeader) {
//        this.fileHeader = fileHeader;
//    }
//
//    //we will know if the file is encrypted if the 1st bit is equal to 1
//    public boolean isEncrypted(){
//        //Check on first bit
//        String temp = Byte.toString((byte)fileHeader.getFlags().getValue());
//        int x = temp.charAt(0);
//        return (x%2)==1;
//    }
///*
//    //Mark As encrypted
//    public void encrypted(){
//        if(!isEncrypted())
//            fileHeader.getFlags().setValue( fileHeader.getFlags().getValue() + 1);
//    }
//*/
//    //we will know if the file is Descriptor if the 3rd bit is equal to 1
//    public boolean isDescriptor(){
//        //Check on 3rd bit of flags
////        String temp = Byte.toString((byte)fileHeader.getFlags().getValue());
////        int x = temp.charAt(3);
////        return (x%2)==1;
//    }
///*
//    //mark as descriptor(Data Structure)
//    public void markAsDescriptor(){
//        String temp = Byte.toString((byte)fileHeader.getFlags().getValue());
//        Character zero = '0';
//        if(zero.equals(temp.charAt(3)) && !isDescriptor()){
//            fileHeader.getFlags().setValue( fileHeader.getFlags().getValue() + 8);
//        }
//    }
//*/
//    //we will know if the file is Edited or repaired if the 5th bit is equal to 1
//    public boolean isPatched(){
//        //Check on 5th bit of flags
//        String temp =  Byte.toString((byte)fileHeader.getFlags().getValue());
//        int x = temp.charAt(5);
//        return (x%2)==1;
//    }
//
//    /*
//    //Mark As Modified
//    public void modified(){
//        // need to check on 5th bit and changed if it wasn't equal 1 by add 2^5 (for change exactly 5th bit :D)
//        if(!isPatched())
//            fileHeader.getFlags().setValue( fileHeader.getFlags().getValue() + 32);
//    }
//    */
//    public String getDeflateType(){
//        /*
//
//        (For Methods 8 and 9 - Deflating)
//        Bit 2  Bit 1
//          0      0    Normal (-en) compression option was used.
//          0      1    Maximum (-exx/-ex) compression option was used.
//          1      0    Fast (-ef) compression option was used.
//          1      1    Super Fast (-es) compression option was used.
//
//          Bit 11: Language encoding flag (EFS).  If this bit is set,
//                the filename and comment fields for this file
//                MUST be encoded using UTF-8. (see APPENDIX D)
//
//
//        Bit 3 : is set then Values of Size will be zero cause of encryption
//       */
//        return "" ;
//    }
}
