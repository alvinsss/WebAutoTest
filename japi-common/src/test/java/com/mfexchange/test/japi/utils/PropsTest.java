package com.mfexchange.test.japi.utils;

import java.io.IOException;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class PropsTest {
    @Test
    public void testLoadFromAndGet() throws IOException {
        Props props = new Props();
        props.loadFrom(getClass().getResourceAsStream("/Global.properties"));
        Assert.assertEquals("http://127.0.0.1", props.get("testServiceUrl"));
        Assert.assertEquals("LocalEchoProtocol", props.get("testServiceProtocol"));
        Assert.assertEquals("Axxxxxx=Bxxxx=cxxx=sxxx=sxxxxxxxxx", props.get("xxxx"));
    }
}
