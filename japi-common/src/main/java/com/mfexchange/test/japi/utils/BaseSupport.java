package com.elong.test.japi.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.testng.annotations.*;

public class BaseSupport {
    protected static Map<String, Object> context;

    @BeforeSuite(alwaysRun=true)
    public void setUpBeforeSuiteWrapper() throws Exception {
        setUpBeforeSuite();
    }

    @BeforeTest(alwaysRun=true)
    public void setUpBeforeTestWrapper() throws Exception {
        setUpBeforeTest();
    }

    @AfterTest(alwaysRun=true)
    public void tearDownAfterTestWrapper() throws Exception {
        tearDownAfterTest();
    }

    @AfterSuite(alwaysRun=true)
    public void tearDownAfterSuiteWrapper() throws Exception {
        tearDownAfterSuite();
    }

    protected void setUpBeforeSuite() throws Exception {
        context = new ConcurrentHashMap<String, Object>();
    }

    protected void setUpBeforeTest() throws Exception {
    }

    protected void tearDownAfterTest() throws Exception {
    }

    protected void tearDownAfterSuite() throws Exception {
        context.clear();
    }
}
