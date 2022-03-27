package org.springframework.cloud.stream.binder.azure.storagequeue;

import com.azure.core.util.BinaryData;
import com.azure.storage.queue.QueueServiceClient;
import org.springframework.cloud.stream.binder.AbstractMessageChannelBinder;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.binder.azure.storagequeue.provisioners.AzureStorageQueueBinderProvisioner;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

public class AzureStorageQueueChannelBinder extends AbstractMessageChannelBinder<ConsumerProperties, ProducerProperties, AzureStorageQueueBinderProvisioner> {

    private QueueServiceClient queueServiceClient;


    public AzureStorageQueueChannelBinder(AzureStorageQueueBinderProvisioner provisioningProvider, QueueServiceClient queueServiceClient) {
        super(new String[]{}, provisioningProvider);
        this.queueServiceClient = queueServiceClient;
    }

    protected MessageHandler createProducerMessageHandler(final ProducerDestination destination, ProducerProperties producerProperties, MessageChannel errorChannel) throws Exception {
        return message -> {

            String destinationName = destination.getName();
            String payload = new String((byte[])message.getPayload());

            queueServiceClient.getQueueClient(destinationName).sendMessage(BinaryData.fromString(payload));
        };
    }

    protected MessageProducer createConsumerEndpoint(ConsumerDestination destination, String group, ConsumerProperties properties) throws Exception {
        return new AzureStorageQueueMessageProducer(queueServiceClient, destination);
    }
}
