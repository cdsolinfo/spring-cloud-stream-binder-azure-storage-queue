package org.springframework.cloud.stream.binder.azure.storagequeue;

import com.azure.core.util.serializer.TypeReference;
import com.azure.storage.queue.QueueAsyncClient;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueServiceAsyncClient;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.models.QueueMessageItem;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.binder.azure.storagequeue.schema.StorageQueueMessage;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequiredArgsConstructor
public class AzureStorageQueueMessageProducer extends MessageProducerSupport {

    private final QueueServiceClient queueServiceClient;
    private final ConsumerDestination consumerDestination;

    @Override
    protected void doStart() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleWithFixedDelay(() -> {

            QueueClient queueClient =
                    queueServiceClient.getQueueClient(consumerDestination.getName());

            QueueMessageItem msg = queueClient.receiveMessage();
            if (msg != null) {
                StorageQueueMessage storageQueueBody =
                        msg.getBody().toObject(TypeReference.createInstance(StorageQueueMessage.class));

                Message<StorageQueueMessage> receivedMessage =
                        MessageBuilder.withPayload(storageQueueBody)
                                .setHeader("messageId", msg.getMessageId())
                                .build();
                sendMessage(receivedMessage);

                queueClient.deleteMessage(msg.getMessageId(), msg.getPopReceipt());
            }

        }, 0, 50, MILLISECONDS);

    }
}
