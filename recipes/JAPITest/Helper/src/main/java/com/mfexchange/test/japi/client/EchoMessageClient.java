package com.elong.test.japi.client;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yanshuai
 */
public class EchoMessageClient extends IMessageClient {
    public EchoMessageClient() {
        m = new HashMap<String, String>();
    }

    public String assemble(Map<String, String> kvs) {
        m.putAll(kvs);
        return m.toString();
    }

    public String send() {
        return m.toString();
    }

    public void verify() {
    }

    public void close() {
    }

    protected Map<String, String> m;
}
