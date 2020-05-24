package com.cache.interf;

import java.util.function.Supplier;

/**
 * 缓存加载数据接口
 */
public interface LocalCacheLoader<T> {
    //默认的本地缓存名称
    String DEFAULT_CACHE_NAME = "DEFAULT";
    //缓存分类
    String getDataCategory();
    //加载数据
    Supplier<T> getValueSupplier();
}
