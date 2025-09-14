package io.bitvelocity.infra.modules.kubernetes;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.K8sCluster;
import io.bitvelocity.infra.util.Naming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles creation of a Kubernetes cluster (logical or real).
 */
public class KubernetesModule {

    private static final Logger log = LoggerFactory.getLogger(KubernetesModule.class);

    private final K8sCluster cluster;

    public KubernetesModule(CloudProvider provider, String region) {
        String name = Naming.resource("k8s", "primary");
        log.info("Provisioning Kubernetes module with name={} region={}", name, region);
        this.cluster = provider.createKubernetesCluster(name, region);
    }

    public K8sCluster cluster() {
        return cluster;
    }
}