package it.archiver.Exception;

import java.io.IOException;

public class FilePathNotCorrectException extends IOException {
    public FilePathNotCorrectException() {
        super();
    }

    public FilePathNotCorrectException(String s) {
        super(s);
    }
}
