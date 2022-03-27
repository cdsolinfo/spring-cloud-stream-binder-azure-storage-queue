package org.springframework.cloud.stream.binder.azure.storagequeue.provisioners;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;

@RequiredArgsConstructor
public class AzureStorageQueueDestination implements ConsumerDestination, ProducerDestination {

    private final String destinationName;

    public String getName() {
        return destinationName;
    }

    public String getNameForPartition(int partition) {
        throw new UnsupportedOperationException("Partitioning is not implemented for Azure Blob messaging.");
    }
}
