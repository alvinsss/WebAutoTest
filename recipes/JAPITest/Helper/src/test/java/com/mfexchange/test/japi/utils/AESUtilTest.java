package com.elong.test.japi.utils;

import junit.framework.Assert;

import org.testng.annotations.Test;

public class AESUtilTest {
    @Test
    public void testEncryptByKey() {
        String aes = "{\"哈哈\"}";
        String res = AESUtil.encryptByKey(aes, "1234567890123456");
        Assert.assertEquals("b3mygTEPsFQr/svdNG+XEw==", res);
    }
}