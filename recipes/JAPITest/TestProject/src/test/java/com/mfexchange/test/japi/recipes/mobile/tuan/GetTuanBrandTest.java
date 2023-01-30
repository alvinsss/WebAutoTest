package com.elong.test.japi.recipes.mobile.tuan;

import java.util.Map;

import org.testng.annotations.*;
import com.elong.test.japi.utils.Logger;
import com.elong.test.japi.utils.Converter;

public class GetTuanBrandTest extends GetTuanBrandTestSupport {

    /**
     * 获取团购品牌列表
     * @author lei.zhu
     */
    @Test(
        description="获取团购品牌列表",
        dataProvider="test_mobile_tuan_GetTuanBrand_GetTuanBrand_1387507728346",
        groups={
            "lei.zhu",
            "Priority_None"
        },
        timeOut=300000
    )
    public void test_mobile_tuan_GetTuanBrand_GetTuanBrand_1387507728346(Map<String, String> kvs) throws Exception {
        client.setCaseId("test_mobile_tuan_GetTuanBrand_GetTuanBrand_1387507728346");
        kvs.putAll(props.toMap());
        Logger.info("assemble message");
        Logger.info(String.format("request: %s", client.assemble(kvs)));
        Logger.info("send request");
        Logger.info(String.format("response: %s", client.send()));
        Logger.info("verify response");
        client.verify();
    }

    @DataProvider(name="test_mobile_tuan_GetTuanBrand_GetTuanBrand_1387507728346")
    private Object[][] test_mobile_tuan_GetTuanBrand_GetTuanBrand_1387507728346DataProvider() throws Exception {
        return new Object[][] {
            {
                Converter.toMap(new String[] {GetTuanBrandTestParam.HTTP_URL, GetTuanBrandTestParam.HTTP_METHOD, GetTuanBrandTestParam.ENCRYPTION, GetTuanBrandTestParam.ACTION, GetTuanBrandTestParam.REQ, GetTuanBrandTestParam.EXPECTED, GetTuanBrandTestParam.TYPE},
                        new String[] {"http://192.168.14.51/JsonService/Groupon.aspx", "POST", "clear text", "GetTuanBrand", "{\"BizSectionID\":\"\",\"CityId\":\"0101\",\"DistrictID\":\"\",\"Latitude\":\"\",\"Longitude\":\"\",\"Scope\":\"\",\"Star\":\"\"}", "{\"VerifyClass\":\"com.elong.test.japi.verify.MobileVerify\",\"VerifySteps\":[[\"collectionSizeEqualTo\",\"$.TuanBrands\",\"30\"],[\"equalToString\",\"$.TuanBrands[0].BrandName\",\"汉庭\"]]}", "0"})
            }
        };
    }

    @BeforeClass(alwaysRun=true)
    public void setUpBeforeClassWrapper() throws Exception {
        setUpBeforeClass();
    }

    @BeforeMethod(alwaysRun=true)
    public void setUpBeforeMethodWrapper() throws Exception {
        setUpBeforeMethod();
    }

    @AfterMethod(alwaysRun=true)
    public void tearDownAfterMethodWrapper() throws Exception {
        tearDownAfterMethod();
    }

    @AfterClass(alwaysRun=true)
    public void tearDownAfterClassWrapper() throws Exception {
        tearDownAfterClass();
    }
}
