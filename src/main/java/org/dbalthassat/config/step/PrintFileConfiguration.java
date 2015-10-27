package org.dbalthassat.config.step;

import org.dbalthassat.PrintFileProcessor;
import org.dbalthassat.dto.FileMetadata;
import org.dbalthassat.property.EnvFolderProperty;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
@SuppressWarnings("SpringJavaAutowiringInspection")
public class PrintFileConfiguration {
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
    public Step processFile(StepBuilderFactory stepBuilderFactory, ItemReader<FileMetadata> reader, ItemProcessor<FileMetadata, FileMetadata> processor) {
        return stepBuilderFactory.get("processFile")
                .<FileMetadata, FileMetadata>chunk(1)
                .reader(reader)
                .processor(processor)
                .build();
    }
}
