package example.servicediscovery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxizhong on 2017/2/28.
 */
public class ChildrenCache {
    List<String> stringList = null;

    public ChildrenCache(List<String> stringList) {
        this.stringList = stringList;
    }

    List<String> removedAndSet(List<String> strings) {
        List<String> newStringList = new ArrayList<String>();
        if (!strings.contains(stringList)) {
            newStringList.addAll(strings);
        }
        return newStringList;
    }
}
