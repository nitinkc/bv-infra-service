package io.bitvelocity.infra.providers.gcp;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.*;

/**
 * Stub for future GCP implementation. Will eventually:
 *  - Provision GKE cluster
 *  - Provision Cloud SQL (Postgres)
 *  - Integrate Pub/Sub or Kafka
 *  - Integrate Memorystore (Redis) or alternative
 *  - Integrate Secret Manager or Vault on GKE
 */
public class GcpCloudProvider implements CloudProvider {

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("GcpCloudProvider not yet implemented");
    }

    @Override
    public K8sCluster createKubernetesCluster(String logicalName, String region) {
        throw unsupported();
    }

    @Override
    public Database createPostgres(String logicalName, String region, String mode) {
        throw unsupported();
    }

    @Override
    public Messaging createKafka(String logicalName, String region, boolean replicationEnabled) {
        throw unsupported();
    }

    @Override
    public Cache createRedis(String logicalName, String region) {
        throw unsupported();
    }

    @Override
    public SecretStore createSecrets(String logicalName) {
        throw unsupported();
    }
}