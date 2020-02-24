package io.express.cache;

public interface CacheProvider {
    public Cache buildCache(String regionName, boolean autoCreate) throws CacheException;

    public void start() throws CacheException;

    public void stop();
}
