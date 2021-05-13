package it.archiver;

import it.archiver.RegMenus.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FakeMain {

    public static void main(String[] args) throws IOException {
        int flag = 0;
        String[] filesPath = null;
//        args = new String[]{"0", "C:\\Users\\lovef\\Desktop\\دراسة.docx"};
//        args = new String[]{"5", "C:\\Users\\lovef\\Desktop\\دراسة.tar"};
        if (args.length > 0) {
            filesPath = new String[args.length - 1];
            flag = Integer.parseInt(args[0]);
            for (int i = 1; i < args.length; i++)
                filesPath[i - 1] = args[i];
        }
        switch (flag) {
            case 0:
                Engine.main(filesPath);
                break;
            case 1:
                AddToArchiveMenu.main(filesPath);
                break;
            case 2:
                ArchiveHereTar.main(filesPath);
                break;
            case 3:
                ShareArchive.main(filesPath);
                break;
            case 4:
                ExtractHere.main(filesPath);
                break;
            case 5:
                ExtractFiles.main(filesPath);
                break;
            case 6:
                ExtractInHereNewFolder.main(filesPath);
                break;
            default:
                break;
        }
    }

    public static File[] parseFileFromArgs(String[] args) {
        ArrayList<File> files = new ArrayList<>();
        for (String arg : args) {
            files.add(new File(arg));
        }
        return files.toArray(new File[]{});
    }
}
