package util;

public interface Partition {
    int numOfVirtualNode = 1024;

    public static int getSlotId(Key key) {
        return key.hashCode() % numOfVirtualNode;
    }

    String getPartition(Key key);
    Boolean addPartition(String address, Integer groupId);
    Boolean changePartition(String address, Integer groupId);
    Boolean containGroupId(Integer groupId);
}
