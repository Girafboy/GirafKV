package util;

import grpc.data.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import javax.lang.model.type.MirroredTypeException;
import java.io.IOException;
import java.util.*;

public class ConsistencyHashPartition implements Partition {
    private final String[] slotRing;
    private final HashMap<String, ArrayList<Integer>> virtualPartition = new HashMap<>();

    public ConsistencyHashPartition() {
        this.slotRing = new String[numOfVirtualNode];
    }

    @Override
    public String getPartition(Key key) {
        return slotRing[Math.abs(key.hashCode()) % numOfVirtualNode];
    }

    @Override
    public Boolean addPartition(String address) {
        // 冷启动
        if(virtualPartition.size() == 0){
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < numOfVirtualNode; i++) {
                slotRing[i] = address;
                list.add(i);
            }
            virtualPartition.put(address, list);

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
        HashMap<String, List<Integer>> tempPartition = new HashMap<>(); // 正在迁移的数据，完成后将被删除

        // RPC 数据迁移
        MigrateResponse response = (MigrateResponse) RpcCall.oneTimeRpcCall(
                address,
                DataServicesGrpc.class,
                (RpcCallInterface<DataServicesGrpc.DataServicesBlockingStub, MigrateResponse>) stub -> {
                    MigrateRequest.Builder builder = MigrateRequest.newBuilder();
                    for (Map.Entry<String, ArrayList<Integer>> entry :
                            virtualPartition.entrySet()) {
                        List<Integer> surplusSlots = entry.getValue().subList(balancedNum, entry.getValue().size());
                        tempPartition.put(entry.getKey(), surplusSlots);
                        newPartitionSlots.addAll(surplusSlots);
                        builder.addMigrateSlices(
                                MigrateSlice.newBuilder().setAddress(entry.getKey()).addAllSlotId(surplusSlots).build()
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
        virtualPartition.put(address, newPartitionSlots);
        for (Integer i :
                newPartitionSlots) {
            slotRing[i] = address;
        }

        // 删除旧映射
        for (Map.Entry<String, List<Integer>> entry :
                tempPartition.entrySet()) {
            virtualPartition.get(entry.getKey()).removeAll(entry.getValue());
        }
        return true;
    }

    @Override
    public Boolean removePartition(String address) {
        // TODO
        return false;
    }
}
