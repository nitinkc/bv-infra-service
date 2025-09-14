package io.bitvelocity.infra.modules.database;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.Database;
import io.bitvelocity.infra.util.Naming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Orders database (Postgres logical placeholder).
 */
public class DatabaseModule {

    private static final Logger log = LoggerFactory.getLogger(DatabaseModule.class);
    private final Database ordersDb;

    public DatabaseModule(CloudProvider provider, String region, String mode) {
        String name = Naming.resource("orders", "db");
        log.info("Provisioning Orders Database: name={} region={} mode={}", name, region, mode);
        this.ordersDb = provider.createPostgres(name, region, mode);
    }

    public Database ordersDb() {
        return ordersDb;
    }
}