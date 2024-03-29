package com.mfexchange.test.japi;

import java.io.File;

public class TestProjectArch {
    private final String testProjectOutputDir;

    public TestProjectArch(String testProjectOutputDir) {
        this.testProjectOutputDir = testProjectOutputDir;
    }

    public String getPomPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(testProjectOutputDir);
        sb.append(File.separator);
        sb.append("pom.xml");
        return sb.toString();
    }

    public String getAssemblyPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(testProjectOutputDir);
        sb.append(File.separator);
        sb.append("src");
        sb.append(File.separator);
        sb.append("test");
        sb.append(File.separator);
        sb.append("assembly");
        return sb.toString();
    }

    public String getAssemblyFilePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAssemblyPath());
        sb.append(File.separator);
        sb.append("test-jar-with-dependencies.xml");
        return sb.toString();
    }

    public String getJavaPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(testProjectOutputDir);
        sb.append(File.separator);
        sb.append("src");
        sb.append(File.separator);
        sb.append("test");
        sb.append(File.separator);
        sb.append("java");
        return sb.toString();
    }

    public String getResourcePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(testProjectOutputDir);
        sb.append(File.separator);
        sb.append("src");
        sb.append(File.separator);
        sb.append("test");
        sb.append(File.separator);
        sb.append("resources");
        return sb.toString();
    }

    public String getGlobalPropertiesPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getResourcePath());
        sb.append(File.separator);
        sb.append("global.properties");
        return sb.toString();
    }

    public String getTestNGPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getResourcePath());
        sb.append(File.separator);
        sb.append("testng");
        return sb.toString();
    }

    public String getCaseCategoryPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getResourcePath());
        sb.append(File.separator);
        sb.append("CaseCategory.xlsx");
        return sb.toString();
    }
}
