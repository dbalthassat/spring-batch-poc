package org.dbalthassat;

import org.dbalthassat.dto.FileMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class MetadataReader implements ItemReader<FileMetadata>, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(MetadataReader.class);
    private Path filepath;
    private boolean done;
    @Override
    public FileMetadata read() throws Exception {
        if(done) {
            return null;
        }
        done = true;
        FileMetadata metadata = new FileMetadata();
        metadata.setAbsolutePath(filepath.toAbsolutePath());
        return metadata;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        LOGGER.info(">>>>>>>>>>>>>> Read filepath key <<<<<<<<<<<<<<");
        this.filepath = (Path) stepExecution.getJobExecution().getExecutionContext().get("filepath");
        LOGGER.info(">>>>>>>>>>>>>> value={}) <<<<<<<<<<<<<<", filepath.toString());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
