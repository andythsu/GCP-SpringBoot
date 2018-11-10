package com.example.SpringBoot.Services.Definitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class KeyValuePair {
    private Map<String, Object> map = new HashMap<>();

    public Object get(String key) {
        if (hasKey(key)) return map.get(key);
        return null;
    }

    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }
    public Map<String, Object> getMap(){
        return map;
    }
    public Set<String> keySet(){
        return map.keySet();
    }
}
