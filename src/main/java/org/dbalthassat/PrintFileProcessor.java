package org.dbalthassat;

import org.dbalthassat.dto.FileMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PrintFileProcessor implements ItemProcessor<FileMetadata, FileMetadata> {
    private final static Logger LOGGER = LoggerFactory.getLogger(PrintFileProcessor.class);

    @Override
    public FileMetadata process(FileMetadata fileMetadata) throws Exception {
        LOGGER.info("Filename: {}", fileMetadata.getFilename());
        // Build your code to store some information ...
        return fileMetadata;
    }
}
