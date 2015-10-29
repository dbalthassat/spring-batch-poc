package org.dbalthassat.dto;

import java.nio.file.Path;

public class FileMetadata {
    private Path filename;

    public Path getFilename() {
        return filename;
    }

    public void setFilename(Path filename) {
        this.filename = filename;
    }
}
