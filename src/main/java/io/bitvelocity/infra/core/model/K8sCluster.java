package io.bitvelocity.infra.core.model;

import com.pulumi.core.Output;

/**
 * Logical representation of a Kubernetes cluster (real or placeholder).
 */
public record K8sCluster(String name, Output<String> kubeconfig, Output<String> apiServer) {}