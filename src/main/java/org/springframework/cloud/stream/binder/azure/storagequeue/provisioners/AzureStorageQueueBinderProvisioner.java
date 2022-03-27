package org.springframework.cloud.stream.binder.azure.storagequeue.provisioners;

import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningException;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;

public class AzureStorageQueueBinderProvisioner implements ProvisioningProvider<ConsumerProperties, ProducerProperties> {

    public ProducerDestination provisionProducerDestination(String name, ProducerProperties properties) throws ProvisioningException {
        return new AzureStorageQueueDestination(name);
    }

    public ConsumerDestination provisionConsumerDestination(String name, String group, ConsumerProperties properties) throws ProvisioningException {
        return new AzureStorageQueueDestination(name);
    }
}
