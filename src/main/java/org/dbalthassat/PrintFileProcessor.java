package org.dbalthassat;

import org.dbalthassat.dto.FileMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class PrintFileProcessor implements ItemProcessor<FileMetadata, FileMetadata>, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(PrintFileProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info("Begin processor PrintFileProcessor");
    }

    @Override
    public FileMetadata process(FileMetadata fileMetadata) throws Exception {
        System.out.println(fileMetadata.getFilename());
        return fileMetadata;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("End of processor PrintFileProcessor with status {}", ExitStatus.COMPLETED);
        return ExitStatus.COMPLETED;
    }
}
