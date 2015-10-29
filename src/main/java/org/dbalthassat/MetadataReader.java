package org.dbalthassat;

import org.dbalthassat.dto.FileMetadata;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class MetadataReader implements ItemReader<FileMetadata>, StepExecutionListener {
    private Path filepath;
    private boolean done;

    @Override
    public FileMetadata read() throws Exception {
        if(done) {
            return null;
        }
        done = true;
        FileMetadata metadata = new FileMetadata();
        metadata.setFilename(filepath.getFileName());
        return metadata;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.filepath = (Path) stepExecution.getJobExecution().getExecutionContext().get("filepath");
        this.done = false;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}
