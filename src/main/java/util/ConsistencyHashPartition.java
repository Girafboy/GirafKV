package util;

import grpc.data.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import server.metaserver.Master;

import javax.lang.model.type.MirroredTypeException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class ConsistencyHashPartition implements Partition {
    private static final Logger logger = Logger.getLogger(Partition.class.getName());

    private final Integer[] slotRing;
    private final HashMap<Integer, ArrayList<Integer>> virtualPartition = new HashMap<>();
    private final HashMap<Integer, String> groupIdPrimaryMap = new HashMap<>();

    public ConsistencyHashPartition() {
        this.slotRing = new Integer[numOfVirtualNode];
    }

    @Override
    public String getPartition(Key key) {
        return groupIdPrimaryMap.get(slotRing[Math.abs(key.hashCode()) % numOfVirtualNode]);
    }

    @Override
    public Boolean addPartition(String address, Integer groupId) {
        logger.info("addPartition " + address + " " + groupId);

        // 记录Group中的Primary
        groupIdPrimaryMap.put(groupId, address);

        // 冷启动
        if(virtualPartition.size() == 0){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < numOfVirtualNode; i++) {
                slotRing[i] = groupId;
                list.add(i);
            }
            virtualPartition.put(groupId, list);

            MigrateResponse response = (MigrateResponse) RpcCall.oneTimeRpcCall(
                    address,
                    DataServicesGrpc.class,
                    (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, MigrateResponse>) stub -> {
                        MigrateRequest request = MigrateRequest.newBuilder().addMigrateSlices(
                                MigrateSlice.newBuilder().setAddress("").addAllSlotId(list).build()
                        ).build();
                        return stub.migrateFrom(request);
                    }
            );
            assert response != null;
            return response.getStatus() == 1;
        }

        // 平均持有slot数量
        int balancedNum = (numOfVirtualNode + virtualPartition.size()) / (virtualPartition.size() + 1);

        ArrayList<Integer> newPartitionSlots = new ArrayList<>(); // 新节点将持有的Slots
        HashMap<Integer, List<Integer>> tempPartition = new HashMap<>(); // 正在迁移的数据，完成后将被删除

        // RPC 数据迁移
        MigrateResponse response = (MigrateResponse) RpcCall.oneTimeRpcCall(
                address,
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, MigrateResponse>) stub -> {
                    MigrateRequest.Builder builder = MigrateRequest.newBuilder();
                    for (Map.Entry<Integer, ArrayList<Integer>> entry :
                            virtualPartition.entrySet()) {
                        List<Integer> surplusSlots = entry.getValue().subList(balancedNum, entry.getValue().size());
                        tempPartition.put(entry.getKey(), surplusSlots);
                        newPartitionSlots.addAll(surplusSlots);
                        builder.addMigrateSlices(
                                MigrateSlice.newBuilder().setAddress(groupIdPrimaryMap.get(entry.getKey())).addAllSlotId(surplusSlots).build()
                        );
                    }

                    MigrateRequest request = builder.build();
                    return stub.migrateFrom(request);
                }
        );

        assert response != null;
        if (response.getStatus() != 1) {
            return false;
        }

        // 重定向
        virtualPartition.put(groupId, newPartitionSlots);
        for (Integer i :
                newPartitionSlots) {
            slotRing[i] = groupId;
        }

        // 删除旧映射
        for (Map.Entry<Integer, List<Integer>> entry :
                tempPartition.entrySet()) {
            virtualPartition.get(entry.getKey()).removeAll(entry.getValue());
        }
        return true;
    }

    @Override
    public Boolean changePartition(String address, Integer groupId) {
        logger.info("changePartition " + address + " " + groupId);
        // 修改Group中的Primary
        if (groupIdPrimaryMap.containsKey(groupId)) {
            groupIdPrimaryMap.put(groupId, address);
            return true;
        }
        return false;
    }


    public Boolean containGroupId(Integer groupId) {
        return groupIdPrimaryMap.containsKey(groupId);
    }
}
