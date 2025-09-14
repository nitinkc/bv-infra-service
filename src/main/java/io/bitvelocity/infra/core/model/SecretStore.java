package io.bitvelocity.infra.core.model;

import com.pulumi.core.Output;

/**
 * Logical representation of a secret management endpoint (Vault or Cloud Secret Manager).
 */
public record SecretStore(String name, Output<String> address) {}