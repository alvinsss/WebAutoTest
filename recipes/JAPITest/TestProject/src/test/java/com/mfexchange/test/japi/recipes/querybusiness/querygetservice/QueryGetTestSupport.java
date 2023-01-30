package com.elong.test.japi.recipes.querybusiness.querygetservice;

import com.elong.test.japi.utils.Props;
import com.elong.test.japi.utils.BaseSupport;
import com.elong.test.japi.client.IMessageClient;

public class QueryGetTestSupport extends BaseSupport {
    protected Props props;
    protected IMessageClient client;

    public void setUpBeforeClass() throws Exception {
        props = new Props();
        props.loadFrom(getClass().getResourceAsStream("/global.properties"));
        props.loadFrom(getClass().getResourceAsStream("QueryGetTest.properties"));
    }

    public void setUpBeforeMethod() throws Exception {
        client = new com.elong.test.japi.client.EchoMessageClient();
        client.loadTestContext(context);
    }

    public void tearDownAfterMethod() throws Exception {
        context = client.dumpTestContext();
        client.close();
    }

    public void tearDownAfterClass() throws Exception {
    }
}
