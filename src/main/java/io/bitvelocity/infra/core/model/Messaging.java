package io.bitvelocity.infra.core.model;

import com.pulumi.core.Output;

/**
 * Logical representation of a messaging / streaming system (Kafka or equivalent).
 */
public record Messaging(String name, Output<String> brokers, Output<Boolean> replicationEnabled) {}