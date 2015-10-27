package org.dbalthassat.config.step;

import org.dbalthassat.FileCheckingTasklet;
import org.dbalthassat.property.EnvFolderProperty;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class FileCheckConfiguration {
    @Bean
    public Tasklet fileCheckingTasklet(EnvFolderProperty envFolderProperty) {
        return new FileCheckingTasklet(envFolderProperty);
    }

    @Bean
    public Step fileCheck(StepBuilderFactory stepBuilderFactory, Tasklet fileCheckingTasklet) {
        return stepBuilderFactory.get("fileCheck")
                .tasklet(fileCheckingTasklet)
                .build();
    }
}
