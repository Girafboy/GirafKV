package util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataProvider implements KVOps {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Key, Value>> slotConcurrentHashMap = new ConcurrentHashMap<>();

    public void addSlot(Integer slotId) {
        if (!slotConcurrentHashMap.containsKey(slotId)){
            slotConcurrentHashMap.put(slotId, new ConcurrentHashMap<>());
        }
    }

    public void addSlot(Collection<Integer> slotIds) {
        for (Integer slotId:
             slotIds) {
            addSlot(slotId);
        }
    }

    public void removeSlot(Integer slotId) {
        if (slotConcurrentHashMap.containsKey(slotId)){
            slotConcurrentHashMap.get(slotId).clear();
            slotConcurrentHashMap.remove(slotId);
        }
    }

    public void removeSlot(Collection<Integer> slotIds) {
        for (Integer slotId:
                slotIds) {
            removeSlot(slotId);
        }
    }

    public ConcurrentHashMap<Key, Value> getSlotEntries(Integer slotId) {
        return slotConcurrentHashMap.get(slotId);
    }

    public Value get(Key key) {
        if (!slotConcurrentHashMap.containsKey(Partition.getSlotId(key)))
            return null;
        return slotConcurrentHashMap.get(Partition.getSlotId(key)).get(key);
    }

    public Boolean put(Key key, Value value) {
        if (!slotConcurrentHashMap.containsKey(Partition.getSlotId(key)))
            return false;
        slotConcurrentHashMap.get(Partition.getSlotId(key)).put(key, value);
        return true;
    }

    public Boolean delete(Key key) {
        if (!slotConcurrentHashMap.containsKey(Partition.getSlotId(key)))
            return false;
        slotConcurrentHashMap.get(Partition.getSlotId(key)).remove(key);
        return true;
    }
}
