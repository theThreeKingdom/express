package io.express.container;

import io.express.Config;

import java.util.List;


/**
 * Factory instance for creating IoC container.
 */
public interface ContainerFactory {
    /**
     * Init container factory.
     */
    void init(Config config);

    /**
     * Find all beans in container.
     */
    List<Object> findAllBeans();

    /**
     * When container destroyed.
     */
    void destroy();
}
