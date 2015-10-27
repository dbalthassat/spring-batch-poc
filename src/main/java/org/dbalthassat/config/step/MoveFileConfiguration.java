package org.dbalthassat.config.step;

import org.dbalthassat.FileMovingTasklet;
import org.dbalthassat.property.EnvFolderProperty;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class MoveFileConfiguration {
    @Bean
    public Tasklet fileMovingTasklet(EnvFolderProperty envFolderProperty) {
        return new FileMovingTasklet(envFolderProperty);
    }

    @Bean
    public Step moveFile(StepBuilderFactory stepBuilderFactory, Tasklet fileMovingTasklet) {
        return stepBuilderFactory.get("moveFile")
                .tasklet(fileMovingTasklet)
                .build();
    }
}
