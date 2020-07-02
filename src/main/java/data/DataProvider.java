package data;

import partition.Partition;
import util.*;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProvider implements KVOps {
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Key, Value>> slotConcurrentHashMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> syncSeqCommandHashMap = new ConcurrentHashMap<>();
    private AtomicInteger syncSeq = new AtomicInteger();

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

    public Set<Integer> getSlots() {
        return slotConcurrentHashMap.keySet();
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

    public Boolean tryPut(Key key, Value value, Integer syncSeq) {
        syncSeqCommandHashMap.put(syncSeq, "put " + key.toString() + " " + value.toString());
        return true;
    }

    public Boolean tryDelete(Key key, Integer syncSeq) {
        syncSeqCommandHashMap.put(syncSeq, "delete " + key.toString());
        return true;
    }

    public Set<Integer> allBufferSyncSeq(Integer syncSeq) {
        return syncSeqCommandHashMap.keySet();
    }

    public void commit(Integer syncSeq) {
        if (syncSeqCommandHashMap.containsKey(syncSeq)) {
            String command = syncSeqCommandHashMap.remove(syncSeq);
            String[] strings = command.split("\\s+");
            switch (strings[0]) {
                case "put":
                    put(new StringKey(strings[1]), new StringValue(strings[2]));
                    getNextSyncSeq();
                    break;
                case "delete":
                    delete(new StringKey(strings[1]));
                    getNextSyncSeq();
                    break;
            }
        }
    }

    public void rollback(int syncSeq) {
        if (syncSeqCommandHashMap.containsKey(syncSeq)) {
            syncSeqCommandHashMap.remove(syncSeq);
        }
    }

    public Integer getNextSyncSeq() {
        return syncSeq.incrementAndGet();
    }
}
