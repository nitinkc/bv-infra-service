package io.bitvelocity.infra.core.model;

import com.pulumi.core.Output;

/**
 * Logical representation of a database (Postgres focus initially).
 */
public record Database(String name, Output<String> rwEndpoint, Output<String> mode) {}