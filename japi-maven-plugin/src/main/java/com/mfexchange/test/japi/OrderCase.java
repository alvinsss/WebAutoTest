package com.mfexchange.test.japi;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class OrderCase {
    private String id;
    private boolean continueAfterFailure;
    private List<String> steps;

    public OrderCase(String id, boolean continueAfterFailure,
            List<String> steps) {
        this.id = id;
        this.continueAfterFailure = continueAfterFailure;
        this.steps = steps;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void expandSteps(Map<String, OrderCase> orderCases) {
        List<String> newSteps = new ArrayList<String>();
        for (String step : steps) {
            if (orderCases.containsKey(step)) {
                OrderCase orderCase = orderCases.get(step);
                newSteps.addAll(orderCase.getSteps());
            } else {
                newSteps.add(step);
            }
        }
        steps = newSteps;
    }

    public XmlSuite toXmlSuite(String testngPath, Map<String, String> classByMethod) {
        String suiteName = id;
        String suitePath = String.format("%s%stestng-%s.xml",
                testngPath, File.separator, suiteName);

        XmlSuite suite = new XmlSuite();
        suite.setName(suiteName);
        suite.setFileName(suitePath);
        if (!continueAfterFailure) {
            suite.addListener(JAPIUtils.listener);
        }
        List<XmlTest> tests = new ArrayList<XmlTest>();
        for (int k = 0; k < steps.size(); ++k) {
            String testName = String.format("step_%03d", k + 1);
            String methodName = steps.get(k);
            String xmlClassName = classByMethod.get(methodName);
            if (null == xmlClassName) {
                xmlClassName = "default";
            }

            XmlTest test = new XmlTest();
            test.setName(testName);
            List<XmlClass> classes = new ArrayList<XmlClass>();
            XmlClass xmlClass = new XmlClass(xmlClassName, false);
            List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
            includedMethods.add(new XmlInclude(methodName));
            xmlClass.setIncludedMethods(includedMethods);
            classes.add(xmlClass);
            test.setClasses(classes);
            tests.add(test);
        }
        suite.setTests(tests);
        return suite;
    }
}
