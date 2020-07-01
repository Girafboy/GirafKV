package util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZookeeperCoordinator implements Coordinator {
    public ZooKeeper zooKeeper;

    public ZookeeperCoordinator(String address, Watcher watcher) {
        try {
            zooKeeper = new ZooKeeper(address, 3000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkAndCreate(String path) {
        try {
            Stat stat = zooKeeper.exists(path, false);
            if(stat == null){
                zooKeeper.create(path, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getData(String path, Boolean watch, Stat stat) {
        try {
            return new String(zooKeeper.getData(path, watch, stat));
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Boolean exists(String path, Boolean watch) {
        try {
            return zooKeeper.exists(path, watch) != null;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete(String path) {
        try {
            zooKeeper.delete(path, -1);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public String create(String path, String data, CreateMode createMode) {
        try {
            return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String create(String path, String data, CreateMode createMode, Boolean printException) {
        try {
            return zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
        } catch (KeeperException | InterruptedException e) {
            if (printException){
                e.printStackTrace();
            }
        }
        return "";
    }

    public List<String> getChildren(String path, Boolean watch) {
        try {
            return zooKeeper.getChildren(path, watch);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public HashMap<String, String> getChildrenValues(String path, Boolean watch) {
        HashMap<String, String> childrenValues = new HashMap<>();
        for (String child :
                getChildren(path, watch)) {
            childrenValues.put(child, getData(path + "/" + child, false, null));
        }
        return childrenValues;
    }
}
