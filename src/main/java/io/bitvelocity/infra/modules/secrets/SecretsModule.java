package io.bitvelocity.infra.modules.secrets;

import io.bitvelocity.infra.core.CloudProvider;
import io.bitvelocity.infra.core.model.SecretStore;
import io.bitvelocity.infra.util.Naming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Secret management logical module.
 */
public class SecretsModule {

    private static final Logger log = LoggerFactory.getLogger(SecretsModule.class);
    private final SecretStore secretStore;

    public SecretsModule(CloudProvider provider) {
        String name = Naming.resource("secrets", "vault");
        log.info("Provisioning Secret Store logical resource: {}", name);
        this.secretStore = provider.createSecrets(name);
    }

    public SecretStore secretStore() {
        return secretStore;
    }
}