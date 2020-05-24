package com.cache.local;

import com.cache.interf.LocalCacheLoader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class ErrorLocalCache implements LocalCacheLoader {
    @Override
    public String getDataCategory() {
        return "error";
    }

    @Override
    public Supplier<Map<String,String>> getValueSupplier() {
        return () -> {
            Map<String,String> xMap = new HashMap<>();
            xMap.put("200","操作成功");
            xMap.put("404","路径错误");
            xMap.put("502","网关异常");
            return xMap;
        };
    }
}
