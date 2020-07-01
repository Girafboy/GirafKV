package server.metaserver;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GroupManager {
    private class Group {
        private String primary;
        private final ConcurrentHashMap<String, String> workers = new ConcurrentHashMap<>();

        public void addWorker(String workerName, String workerAddress) {
            workers.put(workerName, workerAddress);
        }

        public void removeWorker(String workerName) {
            // TODO
            workers.remove(workerName);
        }

        public Boolean upgradeAsPrimary(String workerName) {
            if (!workers.containsKey(workerName)) {
                return false;
            }
            this.primary = workerName;
            return true;
        }
    }
    private final ConcurrentHashMap<Integer, Group> groups = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> workerGroupIdMap = new ConcurrentHashMap<>();

    public void addWorker(String workerName, String workerAddress, Integer groupId){
        if (!groups.containsKey(groupId)){
            groups.put(groupId, new Group());
        }
        groups.get(groupId).addWorker(workerName, workerAddress);
        workerGroupIdMap.put(workerName, groupId);
    }

    public void removeWorker(String workerName){
        // TODO
        if (!workerGroupIdMap.containsKey(workerName)) {
            return;
        }
        groups.get(workerGroupIdMap.get(workerName)).removeWorker(workerName);
        workerGroupIdMap.remove(workerName);
    }

    public Boolean upgradeAsPrimary(Integer groupId, String workerName) {
        if (!groups.containsKey(groupId)) {
            return false;
        }
        return groups.get(groupId).upgradeAsPrimary(workerName);
    }
}
