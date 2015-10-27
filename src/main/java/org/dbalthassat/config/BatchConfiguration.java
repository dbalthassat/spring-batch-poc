package org.dbalthassat.config;

import org.dbalthassat.FileCheckingTasklet;
import org.dbalthassat.FileMovingTasklet;
import org.dbalthassat.PrintFileProcessor;
import org.dbalthassat.dto.FileMetadata;
import org.dbalthassat.property.EnvFolderProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("SpringJavaAutowiringInspection")
public class BatchConfiguration {
    private final static Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    @Bean
    public Tasklet fileMovingTasklet(EnvFolderProperty envFolderProperty) {
        return new FileMovingTasklet(envFolderProperty);
    }

    @Bean
    public Tasklet fileCheckingTasklet(EnvFolderProperty envFolderProperty) {
        return new FileCheckingTasklet(envFolderProperty);
    }

    @Bean
    public ItemReader<FileMetadata> reader(EnvFolderProperty envFolderProperty) {
        FlatFileItemReader<FileMetadata> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(envFolderProperty.getProcess()));
        reader.setLineMapper(new DefaultLineMapper<FileMetadata>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "content" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<FileMetadata>() {{
                setTargetType(FileMetadata.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<FileMetadata, FileMetadata> processor() {
        return new PrintFileProcessor();
    }

    @Bean
    public Job importFilesJob(JobBuilderFactory jobs, JobExecutionListener listener,
                              Step fileCheck, Step moveFile, Step processFile) {
        return jobs.get("importFilesJob")
                .listener(listener)
                .start(fileCheck).on(ExitStatus.STOPPED.getExitCode()).end()
                .next(moveFile)
                .next(processFile).on(ExitStatus.COMPLETED.getExitCode()).to(fileCheck).end()
                .build();
    }

    @Bean
    public Step fileCheck(StepBuilderFactory stepBuilderFactory, Tasklet fileCheckingTasklet) {
        return stepBuilderFactory.get("fileCheck")
                .tasklet(fileCheckingTasklet)
                .build();
    }

    @Bean
    public Step moveFile(StepBuilderFactory stepBuilderFactory, Tasklet fileMovingTasklet) {
        return stepBuilderFactory.get("moveFile")
                .tasklet(fileMovingTasklet)
                .build();
    }

    @Bean
    public Step processFile(StepBuilderFactory stepBuilderFactory, ItemReader<FileMetadata> reader, ItemProcessor<FileMetadata, FileMetadata> processor) {
        return stepBuilderFactory.get("processFile")
                .<FileMetadata, FileMetadata>chunk(1)
                .reader(reader)
                .processor(processor)
                .build();
    }
}

