package com.mfexchange.test.japi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.dom4j.DocumentException;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

public class TestSource {
    private final String javaSourcePath;
    private final String resourcePath;

    private final String packageName;
    private final String className;

    private final List<AtomicCase> testCases;
    private final String methodPrefix;
    private final String testngPath;

    private final Map<String, String> classByMethod;
    private final String messageClient;
    private final String timeout;
    private final Map<String, List<String>> testSuitesByGroup;

    public TestSource(String javaSourcePath, String resourcePath,
            String packageName, String className, List<AtomicCase> testCases,
            String methodPrefix, String testngPath, Map<String, String> classByMethod,
            String messageClient, String timeout, Map<String, List<String>> testSuitesByGroup)
            throws IOException {
        this.javaSourcePath = javaSourcePath;
        this.resourcePath = resourcePath;
        this.packageName = packageName.toLowerCase();
        this.className = className;
        this.testCases = testCases;
        this.methodPrefix = methodPrefix;
        this.testngPath = testngPath;
        this.classByMethod = classByMethod;
        this.messageClient = messageClient;
        this.timeout = timeout;
        this.testSuitesByGroup = testSuitesByGroup;
    }

    public void generate() throws IOException, DocumentException {
        new File(getTestPackagePath()).mkdirs();
        new File(getTestResourcePath()).mkdirs();
        generateResource();
        generateTestSourceParam();
        generateTestSourceSupport();
        generateTestSource();
        generateTestNGXml();
    }

    private void generateTestNGXml() throws IOException {
        for (int i = 0; i < testCases.size(); ++i) {
            AtomicCase testCase = testCases.get(i);
            String caseId = testCase.getId();

            String suiteName = String.format("%s_%s", methodPrefix, caseId);
            String testName = "step_001";
            String xmlClassName = String.format("%s.%s", packageName, className);
            String methodName = String.format("%s_%s", methodPrefix, caseId);
            String xmlPath = String.format("%s%stestng-%s.xml", testngPath, File.separator,
                    methodName);

            if (classByMethod.containsKey(methodName)) {
                throw new IllegalArgumentException(String.format("%s is duplicated in class %s and %s",
                        methodName, xmlClassName, classByMethod.get(methodName)));
            }
            classByMethod.put(methodName, xmlClassName);

            JAPIUtils.addGroup(testSuitesByGroup, testCase.getGroups(), suiteName);
            XmlSuite suite = new XmlSuite();
            suite.setName(suiteName);
            XmlTest test = new XmlTest(suite);
            test.setName(testName);
            List<XmlClass> classes = new ArrayList<XmlClass>();
            XmlClass xmlClass = new XmlClass(xmlClassName, false);
            List<XmlInclude> includedMethods = new ArrayList<XmlInclude>();
            includedMethods.add(new XmlInclude(methodName));
            xmlClass.setIncludedMethods(includedMethods);
            classes.add(xmlClass);
            test.setXmlClasses(classes);

            Writer writer = null;
            try {
                writer = new OutputStreamWriter(new FileOutputStream(xmlPath), "UTF-8");
                writer.write(suite.toXml());
            } finally {
                writer.close();
            }
        }
    }

    private void generateResource() throws IOException {
        String classTestResourcePath = getClassTestResourcePath();
        if (!new File(classTestResourcePath).exists()) {
            new File(classTestResourcePath).createNewFile();
        }
    }

    private void check(Set<String> paramSet) {
        Map<String, String> m = new HashMap<String, String>();
        for (String paramKey : paramSet) {
            String p = JAPIUtils.toParam(paramKey);
            if (m.containsKey(p)) {
                throw new IllegalArgumentException(String.format("Duplicated param key %s and %s found",
                        m.get(p), paramKey));
            }
            m.put(p, paramKey);
        }
    }

    private void generateTestSourceParam() throws IOException {
        Writer writer = null;
        String testSourceParamPath = getTestSourceParamPath();
        try {
            writer = new OutputStreamWriter(new FileOutputStream(testSourceParamPath), "UTF-8");
            writer.write(getSourceTemplateString("/source.tpl6")
                    .replace("@packageName@", packageName)
                    .replace("@className@", className));
            writer.write(JAPIUtils.LINE_SEPARATOR);
            Set<String> paramSet = new HashSet<String>();
            for (int i = 0; i < testCases.size(); ++i) {
                paramSet.addAll(Arrays.asList(testCases.get(i).getParamKeys()));
            }
            check(paramSet);
            String tpl = getSourceTemplateString("/source.tpl7");
            for (String paramKey : paramSet) {
                writer.write(tpl.replace("@param@", JAPIUtils.toLiteralString(paramKey))
                        .replace("@param_upper_case@", JAPIUtils.toParam(paramKey)));
                writer.write(JAPIUtils.LINE_SEPARATOR);
            }
            writer.write(getSourceTemplateString("/source.tpl8"));
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    private void generateTestSourceSupport() throws IOException {
        Writer writer = null;
        String testSourcePath = getTestSourceSupportPath();
        if (!new File(testSourcePath).exists()) {
            try {
                writer = new OutputStreamWriter(new FileOutputStream(testSourcePath), "UTF-8");
                writer.write(getSourceTemplateString("/source.tpl0")
                        .replaceAll("@packageName@", Matcher.quoteReplacement(packageName))
                        .replaceAll("@className@", Matcher.quoteReplacement(className))
                        .replaceAll("@message_client@", Matcher.quoteReplacement(messageClient)));
            } finally {
                if (null != writer) {
                    writer.close();
                }
            }
        }
    }

    private void generateTestSource() throws IOException {
        Writer writer = null;
        String testSourceImplPath = getTestSourcePath();
        try {
            writer = new OutputStreamWriter(new FileOutputStream(testSourceImplPath), "UTF-8");
            writer.write(getSourceTemplateString("/source.tpl1")
                    .replaceAll("@packageName@", Matcher.quoteReplacement(packageName))
                    .replaceAll("@className@", Matcher.quoteReplacement(className)));
            for (int i = 0; i < testCases.size(); ++i) {
                writer.write(getMethod(testCases.get(i)));
            }
            writer.write(getSourceTemplateString("/source.tpl5"));
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    private String getMethod(AtomicCase testCase) throws IOException {
        StringBuilder sb = new StringBuilder();
        String method = String.format("%s_%s", methodPrefix, testCase.getId());
        sb.append(getSourceTemplateString("/source.tpl2")
                .replaceAll("@timeout@", Matcher.quoteReplacement(timeout))
                .replaceAll("@packageName@", Matcher.quoteReplacement(packageName))
                .replaceAll("@className@", Matcher.quoteReplacement(className))
                .replaceAll("@method@", Matcher.quoteReplacement(method))
                .replaceAll("@caseName@", Matcher.quoteReplacement(
                        JAPIUtils.toLiteral(testCase.getName())))
                .replaceAll("@owner@", Matcher.quoteReplacement(testCase.getOwner()))
                .replace("@priority@", testCase.getPriority()));
        sb.append(getSourceTemplateString("/source.tpl3")
                .replace("@paramKeys@", join(className, testCase.getParamKeys()))
                .replace("@paramValues@", join(testCase.getParamValues())));
        sb.append(getSourceTemplateString("/source.tpl4"));
        return sb.toString();
    }

    private String join(String className, String[] paramKeys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramKeys.length; ++i) {
            if (i != 0) {
                sb.append(", ");
            }

            sb.append(className);
            sb.append("Param.");
            sb.append(JAPIUtils.toParam(paramKeys[i]));
        }
        return sb.toString();
    }

    private String join(Object[] objs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objs.length; ++i) {
            if (i != 0) {
                sb.append(", ");
            }

            Object obj = objs[i];
            if (null == obj) {
                sb.append("null");
            } else {
                sb.append(JAPIUtils.toLiteralString(obj.toString()));
            }
        }
        return sb.toString();
    }

    private String getSourceTemplateString(String template)
            throws IOException {
        BufferedReader reader = null;
        String line = null;

        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream(template)));
            while (null != (line = reader.readLine())) {
                sb.append(line);
                sb.append(JAPIUtils.LINE_SEPARATOR);
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }

        return sb.toString();
    }

    private String getTestPackagePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(javaSourcePath);
        sb.append(File.separator);
        sb.append(JAPIUtils.packageNameToPath(packageName));
        return sb.toString();
    }

    private String getTestResourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(resourcePath);
        sb.append(File.separator);
        sb.append(JAPIUtils.packageNameToPath(packageName));
        return sb.toString();
    }

    private String getClassTestResourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTestResourcePath());
        sb.append(File.separator);
        sb.append(className);
        sb.append(".properties");
        return sb.toString();
    }

    private String getTestSourceParamPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTestPackagePath());
        sb.append(File.separator);
        sb.append(className);
        sb.append("Param.java");
        return sb.toString();
    }

    private String getTestSourceSupportPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTestPackagePath());
        sb.append(File.separator);
        sb.append(className);
        sb.append("Support.java");
        return sb.toString();
    }

    private String getTestSourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTestPackagePath());
        sb.append(File.separator);
        sb.append(className);
        sb.append(".java");
        return sb.toString();
    }
}
