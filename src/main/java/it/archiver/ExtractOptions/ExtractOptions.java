package it.archiver.ExtractOptions;

import it.archiver.ArchiveFormat.ArchiveFormat;

public abstract class ExtractOptions {
    private ArchiveFormat archiveFormat;
    public abstract void executeOrder();
}
