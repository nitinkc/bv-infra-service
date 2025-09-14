package io.bitvelocity.infra.modules.messaging;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.Messaging;
import io.bitvelocity.infra.util.Naming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kafka (or future messaging) logical module.
 */
public class MessagingModule {

    private static final Logger log = LoggerFactory.getLogger(MessagingModule.class);
    private final Messaging messaging;

    public MessagingModule(CloudProvider provider, String region, boolean replication) {
        String name = Naming.resource("kafka", "east");
        log.info("Provisioning Messaging (Kafka) logical resource: name={} region={} replication={}",
                name, region, replication);
        this.messaging = provider.createKafka(name, region, replication);
    }

    public Messaging messaging() {
        return messaging;
    }
}