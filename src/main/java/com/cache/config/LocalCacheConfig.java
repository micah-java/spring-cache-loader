package com.cache.config;

import com.cache.interf.LocalCacheLoader;
import com.cache.local.LocalCacheHelper;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class LocalCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(LocalCacheConfig.class);

    @Configuration
    @Conditional(ModuleCondition.class)
    static class ModuleLocalCacheConfig{

        private final Map<String, LocalCacheLoader> localCacheLoaderMap;

        /**
         *  将数据载入localCacheLoaderMap
         */
        ModuleLocalCacheConfig(ObjectProvider<Map<String, LocalCacheLoader>> LocalCacheLoaderProvider) {
            this.localCacheLoaderMap = new HashMap<String, LocalCacheLoader>();
            Map<String, LocalCacheLoader> ifAvailableMap = LocalCacheLoaderProvider.getIfAvailable();
            if(!CollectionUtils.isEmpty(ifAvailableMap)){
                for(LocalCacheLoader localCacheLoader:ifAvailableMap.values()){
                    localCacheLoaderMap.put(localCacheLoader.getDataCategory(),localCacheLoader);
                }
            }
        }

        @Bean
        public LocalCacheHelper localCacheHelper(CacheManager cacheManager){
            return new LocalCacheHelper(cacheManager);
        }

        @Bean
        public CacheManager cacheManager(CacheLoader cacheLoader){
            CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
            caffeineCacheManager.setAllowNullValues(true);
            //caffeineCacheManager.setCacheNames(Arrays.asList(LocalCacheLoader.DEFAULT_CACHE_NAME));
            caffeineCacheManager.setCaffeine(buildCaffeine());
            caffeineCacheManager.setCacheLoader(cacheLoader);
            return caffeineCacheManager;
        }

        Caffeine<Object, Object> buildCaffeine(){
            return Caffeine.newBuilder()
                    .initialCapacity(100)
                    .maximumSize(200000)
                    .recordStats();
        }

        @Bean
        public CacheLoader<Object,Object> cacheLoader(){
            return new CacheLoader<Object, Object>() {
                @Nullable
                @Override
                public Object load(@NonNull Object dataCategory) {
                    LocalCacheLoader localCacheLoader = localCacheLoaderMap.get(dataCategory);
                    return localCacheLoader.getValueSupplier().get();
                }
            };
        }
    }
}
