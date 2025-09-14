package io.bitvelocity.infra.providers.local;

import com.pulumi.core.Output;
import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Local-only provider. Does not create real infra.
 * Useful for early application integration, CI previews, and cost avoidance.
 */
public class LocalCloudProvider implements CloudProvider {

    private static final Logger log = LoggerFactory.getLogger(LocalCloudProvider.class);

    @Override
    public K8sCluster createKubernetesCluster(String logicalName, String region) {
        log.info("Creating local logical Kubernetes cluster: {} in {}", logicalName, region);
        return new K8sCluster(
                logicalName,
                Output.of("kind-kubeconfig-placeholder"),
                Output.of("https://localhost:6443")
        );
    }

    @Override
    public Database createPostgres(String logicalName, String region, String mode) {
        log.info("Creating local logical Postgres: {} in {} mode={}", logicalName, region, mode);
        String endpoint = "localhost:5432/" + logicalName;
        return new Database(logicalName, Output.of(endpoint), Output.of(mode));
    }

    @Override
    public Messaging createKafka(String logicalName, String region, boolean replicationEnabled) {
        log.info("Creating local logical Kafka: {} in {} replicationEnabled={}", logicalName, region, replicationEnabled);
        return new Messaging(logicalName, Output.of("localhost:9092"), Output.of(replicationEnabled));
    }

    @Override
    public Cache createRedis(String logicalName, String region) {
        log.info("Creating local logical Redis: {} in {}", logicalName, region);
        return new Cache(logicalName, Output.of("localhost:6379"));
    }

    @Override
    public SecretStore createSecrets(String logicalName) {
        log.info("Referencing local logical Secret Store: {}", logicalName);
        return new SecretStore(logicalName, Output.of("http://localhost:8200"));
    }
}