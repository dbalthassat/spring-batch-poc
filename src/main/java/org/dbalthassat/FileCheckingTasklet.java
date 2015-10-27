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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileCheckingTasklet implements Tasklet, StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMovingTasklet.class);

    private final EnvFolderProperty envFolderProperty;

    public FileCheckingTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            Path dir = Paths.get(envFolderProperty.getRead());
            List<Path> files = Files.list(dir).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
            if(files.isEmpty()) {
                return ExitStatus.STOPPED;
            }
            return ExitStatus.COMPLETED;
        } catch (IOException e) {
            return ExitStatus.STOPPED;
        }

    }
}
