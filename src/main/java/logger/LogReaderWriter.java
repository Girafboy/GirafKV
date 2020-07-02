package logger;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LogReaderWriter {
    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    static public void write(String filename, Object object) {
        readWriteLock.writeLock().lock();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    static public Object read(String filename) {
        readWriteLock.readLock().lock();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }
}
