package org.dbalthassat;

import org.dbalthassat.dto.FileMetadata;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

public class PrintFileProcessor implements ItemProcessor<FileMetadata, FileMetadata> {
    @Override
    public FileMetadata process(FileMetadata fileMetadata) throws Exception {
        System.out.println(fileMetadata.getContent());
        return fileMetadata;
    }


}
