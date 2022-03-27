package org.springframework.cloud.stream.binder.azure.storagequeue.config;

import com.azure.storage.queue.QueueMessageEncoding;
import com.azure.storage.queue.QueueServiceAsyncClient;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.azure.storagequeue.provisioners.AzureStorageQueueBinderProvisioner;
import org.springframework.cloud.stream.binder.azure.storagequeue.AzureStorageQueueChannelBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AzureStorageQueueConfigurationProperties.class)
public class AzureStorageQueueBinderConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public QueueServiceClient storateQueueServiceAsyncClient(AzureStorageQueueConfigurationProperties configurationProperties) {
        return new QueueServiceClientBuilder()
                .connectionString(configurationProperties.getConnection())
                .messageEncoding(QueueMessageEncoding.BASE64)
                .buildClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public AzureStorageQueueBinderProvisioner storageQueueProvisioner() {
        return new AzureStorageQueueBinderProvisioner();
    }

    @Bean
    public AzureStorageQueueChannelBinder azureStorageQueueMessageBinder(AzureStorageQueueBinderProvisioner storageQueueProvisioner, QueueServiceClient queueServiceClient) {
        return new AzureStorageQueueChannelBinder(storageQueueProvisioner, queueServiceClient);
    }
}
