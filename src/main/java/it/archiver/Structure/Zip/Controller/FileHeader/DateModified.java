package it.archiver.Structure.Zip.Controller.FileHeader;


import java.io.File;
import java.text.SimpleDateFormat;

public class DateModified {

    public static Long getLongTime(File file) {

        String time = new SimpleDateFormat("HHmmss").format(file.lastModified());

        long X = Integer.parseInt(time.substring(0, 2));
        X = X << 6;
        X = X | Integer.parseInt(time.substring(2, 4));
        X = X << 5;
        X = X | (Integer.parseInt(time.substring(4,6))) / 2;
        return X;
    }

    public static Long getLongDate(File file) {

        String date = new SimpleDateFormat("ddMMyyyy").format(file.lastModified());
        long X = Integer.parseInt(date.substring(4, 8)) - 1980;
        X = X << 4;
        X = X | Integer.parseInt(date.substring(2, 4));
        X = X << 5;
        X = X | Integer.parseInt(date.substring(0, 2));
        return X;
    }


}
