package com.elong.test.japi.client;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileMessageClientTest {
    @Test
    public void testSend() throws Exception {
        Map<String, String> kvs = new HashMap<String, String>();
        kvs.put("http_url", "http://192.168.14.51/JsonService/Groupon.aspx");
        kvs.put("http_method", "POST");
        kvs.put("encryption", "clear text");
        kvs.put("action", "GetTuanBrand");
        kvs.put("req", "{\"CityId\":\"0101\",\"BizSectionID\":\"\",\"Star\":\"\","
                + "\"Latitude\":\"\",\"DistrictID\":\"\",\"Longitude\":\"\",\"Scope\":\"\"}");
        kvs.put("expected", "{\"VerifyClass\":\"com.elong.test.japi.verify.MobileVerify\","
                + "\"VerifySteps\":[[\"collectionSizeEqualTo\",\"$.TuanBrands\",\"30\"]"
                + ",[\"equalToString\",\"$.TuanBrands[0].BrandName\",\"汉庭\"]]}");
        kvs.put("type", "0");
        IMessageClient client = new MobileMessageClient();
        String request = "http://192.168.14.51/JsonService/Groupon.aspx";
        Assert.assertEquals(client.assemble(kvs), request);
        System.out.println(client.send());
        client.verify();
        client.close();
    }
}