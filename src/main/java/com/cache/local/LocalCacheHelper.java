package com.cache.local;

import com.cache.interf.LocalCacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class LocalCacheHelper {

    private final Logger logger = LoggerFactory.getLogger(LocalCacheHelper.class);

    private final CacheManager cacheManager;

    public LocalCacheHelper(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> T getData(String key){
        String cacheName = LocalCacheLoader.DEFAULT_CACHE_NAME;
        Cache cache = this.cacheManager.getCache(cacheName);
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if(valueWrapper == null){
            return null;
        }
        return (T)valueWrapper.get();
    }
}
