package com.cache.until;

import com.cache.local.ConstantLocalCache;
import com.cache.local.ErrorLocalCache;
import com.cache.local.LocalCacheHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ConfigVars {

    private static final Logger logger = LoggerFactory.getLogger(ConfigVars.class);

    private static LocalCacheHelper localCacheHelper;

    private static ConstantLocalCache constantLocalCache;

    private static ErrorLocalCache errorLocalCache;

    /**
     * bean初始化的时候赋值
     */
    public ConfigVars( LocalCacheHelper localCacheHelper,ConstantLocalCache constantLocalCache,ErrorLocalCache errorLocalCache) {
        ConfigVars.localCacheHelper = localCacheHelper;
        ConfigVars.constantLocalCache = constantLocalCache;
        ConfigVars.errorLocalCache = errorLocalCache;
    }

    public static Integer getConstant(String key){
        Map<String,Integer> xMap = localCacheHelper.getData(constantLocalCache.getDataCategory());
        return xMap.get(key);
    }

    public static String getError(String key){
        Map<String,String> xMap = localCacheHelper.getData(errorLocalCache.getDataCategory());
        return xMap.get(key);
    }
}
