package org.example.StateMachine.model;

import java.util.HashMap;
import java.util.Map;

public class DataStorage {
    private final Map<String, Object> Dictionary;

    public DataStorage() {
        Dictionary = new HashMap<>();
    }

    public void add(String key, Object value) {
        Dictionary.put(key, value);
    }

    public void remove(String key) {
        Dictionary.remove(key);
    }

    public Object get(String key) {
        return Dictionary.get(key);
    }
}
