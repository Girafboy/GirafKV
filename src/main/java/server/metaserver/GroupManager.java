package server.metaserver;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager {
    private final ConcurrentHashMap<Integer, Group> groups = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> workerGroupIdMap = new ConcurrentHashMap<>();

    public void addWorker(String workerName, String workerAddress, Integer groupId){
        if (!groups.containsKey(groupId)){
            groups.put(groupId, new Group());
        }
        groups.get(groupId).addWorker(workerName, workerAddress);
        workerGroupIdMap.put(workerName, groupId);
    }

    public Boolean upgradeAsPrimary(Integer groupId, String workerName) {
        if (!groups.containsKey(groupId)) {
            return false;
        }
        return groups.get(groupId).upgradeAsPrimary(workerName);
    }

    public void clear() {
        groups.clear();
        workerGroupIdMap.clear();
    }

    public String getReadAddress(Integer groupId) {
        return groups.get(groupId).getReadAddress();
    }

    public String getWriteAddress(Integer groupId) {
        return groups.get(groupId).getWriteAddress();
    }

    private class Group {
        private String primary;
        private final ConcurrentHashMap<String, String> workers = new ConcurrentHashMap<>();
        private int roundSeq = 0;

        public void addWorker(String workerName, String workerAddress) {
            workers.put(workerName, workerAddress);
        }

        public Boolean upgradeAsPrimary(String workerName) {
            if (!workers.containsKey(workerName)) {
                return false;
            }
            this.primary = workerName;
            return true;
        }

        public String getReadAddress() {
            Iterator<String> iterable = workers.values().iterator();
            for (int i = 0; i < roundSeq % workers.size(); i++) {
                iterable.next();
            }
            roundSeq++;
            return iterable.next();
        }

        public String getWriteAddress() {
            return workers.get(primary);
        }
    }
}
