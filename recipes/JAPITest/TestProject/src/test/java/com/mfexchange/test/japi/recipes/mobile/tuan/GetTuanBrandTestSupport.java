package com.elong.test.japi.recipes.mobile.tuan;

import com.elong.test.japi.utils.Props;
import com.elong.test.japi.utils.BaseSupport;
import com.elong.test.japi.client.IMessageClient;

public class GetTuanBrandTestSupport extends BaseSupport {
    protected Props props;
    protected IMessageClient client;

    public void setUpBeforeClass() throws Exception {
        props = new Props();
        props.loadFrom(getClass().getResourceAsStream("/global.properties"));
        props.loadFrom(getClass().getResourceAsStream("GetTuanBrandTest.properties"));
    }

    public void setUpBeforeMethod() throws Exception {
        client = new com.elong.test.japi.client.MobileMessageClient();
        client.loadTestContext(context);
    }

    public void tearDownAfterMethod() throws Exception {
        context = client.dumpTestContext();
        client.close();
    }

    public void tearDownAfterClass() throws Exception {
    }
}
