package util;

public interface KVOps {
    Value get(Key key);
    Boolean put(Key key, Value value);
    Boolean delete(Key key);
}
