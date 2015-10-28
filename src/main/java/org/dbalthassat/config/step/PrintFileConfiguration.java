package org.dbalthassat.config.step;

import org.dbalthassat.MetadataReader;
import org.dbalthassat.PrintFileProcessor;
import org.dbalthassat.dto.FileMetadata;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class PrintFileConfiguration {
    @Bean
    public ItemReader<FileMetadata> reader() {
        return new MetadataReader();
    }

    @Bean
    public ItemProcessor<FileMetadata, FileMetadata> processor() {
        return new PrintFileProcessor();
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
