package io.bitvelocity.infra;

import com.pulumi.Pulumi;
import com.pulumi.core.Output;
import io.bitvelocity.infra.config.ConfigKeys;
import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.providers.gcp.GcpCloudProvider;
import io.bitvelocity.infra.providers.local.LocalCloudProvider;
import io.bitvelocity.infra.modules.networking.NetworkingModule;
import io.bitvelocity.infra.modules.kubernetes.KubernetesModule;
import io.bitvelocity.infra.modules.database.DatabaseModule;
import io.bitvelocity.infra.modules.messaging.MessagingModule;
import io.bitvelocity.infra.modules.cache.CacheModule;
import io.bitvelocity.infra.modules.secrets.SecretsModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point stack building logical infra modules and exporting endpoints.
 *
 * Sequence:
 *  1. Read config
 *  2. Select CloudProvider
 *  3. Instantiate modules (networking -> k8s -> db -> messaging -> secrets)
 *  4. Export outputs for application consumption
 */
public class AppStack {

    private static final Logger log = LoggerFactory.getLogger(AppStack.class);

    public static void main(String[] args) {
        Pulumi.run(ctx -> {
            var config = ctx.config();

            String providerName = config.get(ConfigKeys.CLOUD_PROVIDER).orElse("local");
            String regionEast = config.get(ConfigKeys.REGION_EAST).orElse("us-east1");
            String regionWest = config.get(ConfigKeys.REGION_WEST).orElse("us-west1");
            String ordersMode = config.get(ConfigKeys.DB_ORDERS_MODE).orElse("primary");
            boolean kafkaReplication = config.getBoolean(ConfigKeys.KAFKA_REPLICATION).orElse(false);

            log.info("Config resolved: provider={} regionEast={} regionWest={} ordersMode={} kafkaReplication={}",
                    providerName, regionEast, regionWest, ordersMode, kafkaReplication);

            CloudProvider provider = switch (providerName.toLowerCase()) {
                case "local" -> new LocalCloudProvider();
                case "gcp" -> new GcpCloudProvider(); // will throw until implemented
                default -> {
                    log.warn("Unsupported provider '{}', defaulting to local", providerName);
                    yield new LocalCloudProvider();
                }
            };

            // Modules
            new NetworkingModule(regionEast, regionWest);
            var k8s = new KubernetesModule(provider, regionEast);
            var db = new DatabaseModule(provider, regionEast, ordersMode);
            var messaging = new MessagingModule(provider, regionEast, kafkaReplication);
            var cache = new CacheModule(provider, regionEast);
            var secrets = new SecretsModule(provider);

            // Exports
            ctx.export("K8S_CLUSTER_NAME", Output.of(k8s.cluster().name()));
            ctx.export("POSTGRES_ORDERS_RW_ENDPOINT", db.ordersDb().rwEndpoint());
            ctx.export("KAFKA_BROKERS_EAST", messaging.messaging().brokers());
            ctx.export("REDIS_CACHE_HOST", cache.cache().host());
            ctx.export("VAULT_ADDR", secrets.secretStore().address());
        });
    }
}