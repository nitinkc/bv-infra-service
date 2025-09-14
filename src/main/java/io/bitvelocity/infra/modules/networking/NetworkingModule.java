package io.bitvelocity.infra.modules.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Placeholder networking module (VPCs, subnets, firewall rules in cloud scenario).
 * Local provider does not create real networking resources.
 */
public class NetworkingModule {

    private static final Logger log = LoggerFactory.getLogger(NetworkingModule.class);

    public NetworkingModule(String regionEast, String regionWest) {
        log.info("Networking module initialized (logical only). east={} west={}", regionEast, regionWest);
    }
}