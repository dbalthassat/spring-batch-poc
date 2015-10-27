package org.dbalthassat.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "env.folder")
@SuppressWarnings("unused")
public class EnvFolderProperty {
    private String process;
    private String read;
    private String write;

    public String getProcess() {
        return process;
    }

    public String getRead() {
        return read;
    }

    public String getWrite() {
        return write;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public void setWrite(String write) {
        this.write = write;
    }
}
