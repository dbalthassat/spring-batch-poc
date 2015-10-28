package org.dbalthassat.dto;

import java.nio.file.Path;

public class FileMetadata {
    private Path absolutePath;

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(Path absolutePath) {
        this.absolutePath = absolutePath;
    }
}
