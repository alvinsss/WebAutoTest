package com.elong.test.japi.recipes.querybusiness.querygetservice;

import java.util.Map;

import org.testng.annotations.*;
import com.elong.test.japi.utils.Logger;
import com.elong.test.japi.utils.Converter;

public class QueryGetTest extends QueryGetTestSupport {

    /**
     * 输入名称为yanshuai
     * @author shuai.yan
     */
    @Test(
        description="输入名称为yanshuai",
        dataProvider="test_QueryBusiness_QueryGetService_QueryGet_QueryGet_001",
        groups={
            "shuai.yan",
            "Priority1"
        },
        timeOut=300000
    )
    public void test_QueryBusiness_QueryGetService_QueryGet_QueryGet_001(Map<String, String> kvs) throws Exception {
        client.setCaseId("test_QueryBusiness_QueryGetService_QueryGet_QueryGet_001");
        kvs.putAll(props.toMap());
        Logger.info("assemble message");
        Logger.info(String.format("request: %s", client.assemble(kvs)));
        Logger.info("send request");
        Logger.info(String.format("response: %s", client.send()));
        Logger.info("verify response");
        client.verify();
    }

    @DataProvider(name="test_QueryBusiness_QueryGetService_QueryGet_QueryGet_001")
    private Object[][] test_QueryBusiness_QueryGetService_QueryGet_QueryGet_001DataProvider() throws Exception {
        return new Object[][] {
            {
                Converter.toMap(new String[] {QueryGetTestParam.NAME, QueryGetTestParam.EXPECTED},
                        new String[] {"yanshuai", "Hello, yanshuai!"})
            }
        };
    }

    /**
     * 输入名称为闫帅
     * @author shuai.yan
     */
    @Test(
        description="输入名称为闫帅",
        dataProvider="test_QueryBusiness_QueryGetService_QueryGet_QueryGet_002",
        groups={
            "shuai.yan",
            "Priority1"
        },
        timeOut=300000
    )
    public void test_QueryBusiness_QueryGetService_QueryGet_QueryGet_002(Map<String, String> kvs) throws Exception {
        client.setCaseId("test_QueryBusiness_QueryGetService_QueryGet_QueryGet_002");
        kvs.putAll(props.toMap());
        Logger.info("assemble message");
        Logger.info(String.format("request: %s", client.assemble(kvs)));
        Logger.info("send request");
        Logger.info(String.format("response: %s", client.send()));
        Logger.info("verify response");
        client.verify();
    }

    @DataProvider(name="test_QueryBusiness_QueryGetService_QueryGet_QueryGet_002")
    private Object[][] test_QueryBusiness_QueryGetService_QueryGet_QueryGet_002DataProvider() throws Exception {
        return new Object[][] {
            {
                Converter.toMap(new String[] {QueryGetTestParam.NAME, QueryGetTestParam.EXPECTED},
                        new String[] {"闫帅", "Hello, 闫帅!"})
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
