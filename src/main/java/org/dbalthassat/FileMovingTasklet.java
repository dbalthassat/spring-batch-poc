package org.dbalthassat;

import org.dbalthassat.property.EnvFolderProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileMovingTasklet implements Tasklet, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMovingTasklet.class);
    private final EnvFolderProperty envFolderProperty;
    private Path filepath;

    public FileMovingTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Path dir = Paths.get(envFolderProperty.getRead());
        assert Files.isDirectory(dir);
        List<Path> files = Files.list(dir).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
        if(!files.isEmpty()) {
            Path file = files.get(0);
            Path dest = Paths.get(envFolderProperty.getProcess() + File.separator + file.getFileName());
            LOGGER.info("Moving {} to {}", file, dest);
            // StandardCopyOption.REPLACE_EXISTING because the working directory is not cleared but in a real application
            // we should.
            Files.move(file, dest, StandardCopyOption.REPLACE_EXISTING);
            filepath = dest;
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if(filepath != null) {
            stepExecution.getJobExecution().getExecutionContext().put("filepath", filepath);
        }
        return ExitStatus.COMPLETED;
    }
}
