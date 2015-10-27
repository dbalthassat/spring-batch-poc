package org.dbalthassat.config;

import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    public JobCompletionNotificationListener() {
    }
}
