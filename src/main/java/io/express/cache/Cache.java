package io.express.cache;

import java.util.List;

public interface Cache {
    public Object get(Object key) throws CacheException;

    public void put(Object key, Object value) throws CacheException;

    public void update(Object key, Object value) throws CacheException;

    public List<Object> keys() throws CacheException;

    public void remove(Object key) throws CacheException;

    public void clear() throws CacheException;

    public void destroy() throws CacheException;
}
