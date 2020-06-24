package util;

import java.io.IOException;

public interface RpcCallInterface<Stub, Response> {
    Response doMethod(Stub stub) throws IOException;
}
