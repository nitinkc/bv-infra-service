package io.bitvelocity.infra.core.model;

import com.pulumi.core.Output;

/**
 * Logical representation of an in-memory cache (Redis).
 */
public record Cache(String name, Output<String> host) {}