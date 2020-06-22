package util;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

public interface Coordinator {
    void connect(String address, Watcher watcher) throws IOException, KeeperException, InterruptedException;
}
