package org.springframework.cloud.stream.binder.azure.storagequeue.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "azure.storage")
@Data
@NoArgsConstructor
public class AzureStorageQueueConfigurationProperties {
    private String  connection;
}
