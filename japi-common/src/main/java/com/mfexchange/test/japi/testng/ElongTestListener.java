package com.elong.test.japi.testng;

import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

public class ElongTestListener extends TestListenerAdapter {
    private boolean skip = false;

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        if (skip) {
            throw new SkipException("skipping as previous test step failed");
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        skip = true;
    }
}
