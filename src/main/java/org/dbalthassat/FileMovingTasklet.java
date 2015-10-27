package org.dbalthassat;

import org.dbalthassat.property.EnvFolderProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class FileMovingTasklet implements Tasklet {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileMovingTasklet.class);

    private final EnvFolderProperty envFolderProperty;

    public FileMovingTasklet(EnvFolderProperty envFolderProperty) {
        this.envFolderProperty = envFolderProperty;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Path dir = Paths.get(envFolderProperty.getRead());
        Path dest = Paths.get(envFolderProperty.getProcess());
        assert Files.isDirectory(dir);
        List<Path> files = Files.list(dir).filter(p -> !Files.isDirectory(p)).collect(Collectors.toList());
        if(!files.isEmpty()) {
            Path file = files.get(0);
            // TODO build FileMetadata
            // TODO purge?
            LOGGER.info("Moving {} to {}", file, dest);
            Files.move(file, dest, StandardCopyOption.REPLACE_EXISTING);
        }
        return RepeatStatus.FINISHED;
    }
}
