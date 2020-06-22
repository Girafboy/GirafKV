package util;

import java.util.concurrent.ConcurrentHashMap;

public class DataProvider implements KVOps {
    private ConcurrentHashMap<Key, Value> concurrentHashMap;

    public DataProvider() {
        this.concurrentHashMap = new ConcurrentHashMap<Key, Value>();
    }

    public Value get(Key key) {
        return concurrentHashMap.get(key);
    }

    public Boolean put(Key key, Value value) {
        concurrentHashMap.put(key, value);
        return true;
    }

    public Boolean delete(Key key) {
        concurrentHashMap.remove(key);
        return true;
    }
}
