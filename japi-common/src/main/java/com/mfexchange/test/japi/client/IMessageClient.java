package com.elong.test.japi.client;

import java.util.Map;

public abstract class IMessageClient {
    protected String caseId;
    protected Map<String, Object> context;

    public abstract String assemble(Map<String, String> kvs) throws Exception;
    public abstract String send() throws Exception;
    public abstract void verify() throws Exception;
    public abstract void close() throws Exception;

    public void loadTestContext(Map<String, Object> context) throws Exception {
        this.context = context;
    }

    public Map<String, Object> dumpTestContext() throws Exception {
        return context;
    }

    public String getCaseId() throws Exception {
        return caseId;
    }

    public void setCaseId(String caseId) throws Exception {
        this.caseId = caseId;
    }
}
