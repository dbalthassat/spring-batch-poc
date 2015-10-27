package org.dbalthassat.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@SuppressWarnings("SpringJavaAutowiringInspection")
public class JobConfiguration {
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
}
