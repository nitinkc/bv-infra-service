package io.bitvelocity.infra.modules.cache;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.Cache;
import io.bitvelocity.infra.util.Naming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cache (Redis) logical module.
 */
public class CacheModule {

    private static final Logger log = LoggerFactory.getLogger(CacheModule.class);
    private final Cache cache;

    public CacheModule(CloudProvider provider, String region) {
        String name = Naming.resource("redis", "cache");
        log.info("Provisioning Cache (Redis) logical resource: name={} region={}", name, region);
        this.cache = provider.createRedis(name, region);
    }

    public Cache cache() {
        return cache;
    }
}

