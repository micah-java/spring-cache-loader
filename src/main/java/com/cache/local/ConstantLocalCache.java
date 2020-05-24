package com.cache.local;

import com.cache.interf.LocalCacheLoader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class ConstantLocalCache implements LocalCacheLoader {
    @Override
    public String getDataCategory() {
        return "constant";
    }

    @Override
    public Supplier<Map<String,Integer>> getValueSupplier() {
        return () -> {
            Map<String,Integer> xMap = new HashMap<>();
            xMap.put("xiaoxiao",18);
            xMap.put("qiqi",19);
            xMap.put("kele",20);
            return xMap;
        };
    }
}
