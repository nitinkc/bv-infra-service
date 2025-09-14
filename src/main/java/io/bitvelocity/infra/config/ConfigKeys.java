package io.bitvelocity.infra.config;

/**
 * Central list of Pulumi configuration keys to avoid string duplication.
 */
public final class ConfigKeys {
    private static final String PREFIX = "bitvelocity-infra:";

    public static final String CLOUD_PROVIDER = PREFIX + "cloudProvider";
    public static final String REGION_EAST = PREFIX + "regionEast";
    public static final String REGION_WEST = PREFIX + "regionWest";
    public static final String MESH_ENABLED = PREFIX + "meshEnabled";
    public static final String KAFKA_REPLICATION = PREFIX + "replication.kafka.enabled";
    public static final String DB_ORDERS_MODE = PREFIX + "db.orders.mode";

    private ConfigKeys() {}
}