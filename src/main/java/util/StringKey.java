package util;

public class StringKey implements Key {
    private String key;

    public StringKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringKey stringKey = (StringKey) o;

        return key.equals(stringKey.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
