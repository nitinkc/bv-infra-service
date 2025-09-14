package io.bitvelocity.infra.core;

import io.bitvelocity.infra.core.model.*;

 /**
  * Abstraction for provisioning (or logically defining) infrastructure resources.
  * Implementations may either:
  *  - Return real Pulumi-managed resources (cloud providers)
  *  - Return synthetic/local placeholders (local dev)
  *
  * Future: Add methods for observability, security policy, object storage, etc.
  */
public interface CloudProvider {

    K8sCluster createKubernetesCluster(String logicalName, String region);

    Database createPostgres(String logicalName, String region, String mode);

    Messaging createKafka(String logicalName, String region, boolean replicationEnabled);

    Cache createRedis(String logicalName, String region);

    SecretStore createSecrets(String logicalName);
}