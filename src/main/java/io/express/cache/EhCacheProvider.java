package io.express.cache;

import java.util.Hashtable;

import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(EhCacheProvider.class);

    private CacheManager manager;
    private Hashtable<String, EhCache> _CacheManager;

    @Override
    public Cache buildCache(String regionName, boolean autoCreate) throws CacheException {
        EhCache ehcache = _CacheManager.get(regionName);
        if (ehcache == null && autoCreate) {
            try {
                net.sf.ehcache.Cache cache = manager.getCache(regionName);
                if (cache == null) {
                    log.warn("Could not find configuration [" + regionName + "]; using defaults.");
                    manager.addCache(regionName);
                    cache = manager.getCache(regionName);
                    log.debug("started EHCache region: " + regionName);
                }
                synchronized (_CacheManager) {
                    ehcache = new EhCache(cache);
                    _CacheManager.put(regionName, ehcache);
                    return ehcache;
                }
            } catch (net.sf.ehcache.CacheException e) {
                throw new CacheException(e);
            }
        }
        return ehcache;
    }

    @Override
    public void start() throws CacheException {
        if (manager != null) {
            log.warn("Attempt to restart an already started EhCacheProvider. Use sessionFactory.close() "
                    + " between repeated calls to buildSessionFactory. Using previously created EhCacheProvider."
                    + " If this behaviour is required, consider using net.sf.ehcache.hibernate.SingletonEhCacheProvider.");
            return;
        }
        manager = new CacheManager();
        _CacheManager = new Hashtable<String, EhCache>();
    }

    @Override
    public void stop() {
        if (manager != null) {
            manager.shutdown();
            manager = null;
        }
    }
}
