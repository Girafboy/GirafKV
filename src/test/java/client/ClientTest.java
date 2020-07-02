package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Test;
import util.StringKey;
import util.StringValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class ClientTest {
    ManagedChannel channel;
    Client client;

    HashMap<String, String> answer = new HashMap<>();

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    void prepare () {
        channel = ManagedChannelBuilder.forTarget("127.0.0.1:23333")
                .usePlaintext()
                .build();
        try {
            client = new Client(channel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            String key = getRandomString(random.nextInt(100)+1);
            answer.put(key, key);
        }
    }

    void tearDown() {
        channel.shutdown();
    }

    @Test
    void main() {
        prepare();
        for (Map.Entry<String, String> entry:
                answer.entrySet()) {
            client.put(new StringKey(entry.getKey()), new StringValue(entry.getValue()));
        }
        for (Map.Entry<String, String> entry:
                answer.entrySet()) {
            assert client.get(new StringKey(entry.getKey())).toString().equals(answer.get(entry.getKey()));
        }
        for (Map.Entry<String, String> entry:
                answer.entrySet()) {
            client.delete(new StringKey(entry.getKey()));
        }
//        for (Map.Entry<String, String> entry:
//                answer.entrySet()) {
//            assert client.get(new StringKey(entry.getKey())).toString().equals("");
//        }

        tearDown();
    }
}