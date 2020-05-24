package com.cache.controller;

import com.cache.until.ConfigVars;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cache")
public class CacheController {

    @RequestMapping("/constant/{name}")
    @ResponseBody
    public Map getConstant(@PathVariable String name){
        Integer value = ConfigVars.getConstant(name);
        Map map = new HashMap();
        map.put("age",value);
        return map;
    }

    @RequestMapping("/error/{code}")
    @ResponseBody
    public Map getError(@PathVariable String code){
        String errMsg = ConfigVars.getError(code);
        Map map = new HashMap();
        map.put(code,errMsg);
        return map;
    }
}
