package com.mfexchange.test.japi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.logging.Log;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.xml.XmlSuite;

public class TestProjectGen {
    private Log logger;
    private String messageClient;
    private String javaPath;
    private String resourcePath;
    private String testngPath;
    private String timeout;
    private GlobalSettings settings;
    private Set<String> skipPaths;
    private Map<String, String> classByMethod;
    private Map<String, List<String>> testSuitesByGroup;

    private static final String STATUS_SUCC = "SUCCESS";
    private static final String STATUS_FAIL = "FAILURE";

    private void genTestSource(File testCaseDir, String packageName, String methodPrefix)
            throws Exception {
        for (File file : testCaseDir.listFiles()) {
            if (file.isHidden() || skipPaths.contains(file.getAbsolutePath())) {
                continue;
            }

            String filename = file.getName();
            if (file.isDirectory()) {
                String newPackageName = String.format("%s.%s", packageName, filename);
                String newMethodPrefix = String.format("%s_%s", methodPrefix, filename);
                genTestSource(file, newPackageName, newMethodPrefix);
            } else if (filename.endsWith(".xlsx")) {
                filename = filename.substring(0, filename.lastIndexOf("."));
                if (!JAPIUtils.isClassFileName(filename)) {
                    continue;
                }

                if (null != logger) {
                    logger.info(String.format(
                            "Generate test cases from %s",
                            file.getAbsolutePath()));
                }

                // Generate test source
                String status = STATUS_SUCC;
                String className = JAPIUtils.getClassName(filename);
                String newMethodPrefix = String.format("%s_%s", methodPrefix, filename);
                try {
                    AtomicCases cases = new AtomicCases(file, settings);
                    TestSource source = new TestSource(javaPath,
                            resourcePath, packageName, className,
                            cases.getTestCases(), newMethodPrefix,
                            testngPath, classByMethod, messageClient,
                            timeout, testSuitesByGroup);
                    source.generate();
                } catch (Exception ex) {
                    status = STATUS_FAIL;
                    throw ex;
                } finally {
                    if (null != logger) {
                        logger.info(String.format("Status: %s", status));
                    }
                }
            }
        }
    }

    public void genTestProject(String groupId, String artifactId,
            String version, File testProjectDir, List<String> localJarPaths, File testCaseDir,
            File orderCaseExcel, File globalSettingsExcel, String messageClient, String timeout,
            Log logger) throws Exception {
        this.logger = logger;
        this.testSuitesByGroup = new HashMap<String, List<String>>();
        this.messageClient = messageClient;
        this.timeout = timeout;
        this.classByMethod = new HashMap<String, String>();
        TestProjectArch arch = new TestProjectArch(
                testProjectDir.getAbsolutePath());
        this.javaPath = arch.getJavaPath();
        this.resourcePath = arch.getResourcePath();
        this.testngPath = arch.getTestNGPath();
        this.skipPaths = new HashSet<String>();
        if (null != globalSettingsExcel && globalSettingsExcel.isFile()) {
            this.skipPaths.add(globalSettingsExcel.getAbsolutePath());
        }
        if (null != orderCaseExcel && orderCaseExcel.isFile()) {
            this.skipPaths.add(orderCaseExcel.getAbsolutePath());
        }
        /**
         * STEP1: Generate test project architecture:
         * <ROOT>
         *    |-pom.xml
         *    |-src 
         *    |  |-test
         *    |  |  |-assembly
         *    |  |  |     |-test-jar-with-dependencies.xml
         *    |  |  |-java
         *    |  |  |-resources
         *    |  |  |     |-global.properties
         *    |  |  |     |-testng
         */
        TestProject project = new TestProject(groupId, artifactId, version,
                localJarPaths);
        project.generateTo(testProjectDir);
        // STEP2: Load global settings' excel
        settings = new GlobalSettings();
        settings.loadFrom(globalSettingsExcel);
        // STEP3: dfs test case directory to generate test sources
        StringBuilder sb = new StringBuilder();
        sb.append(groupId);
        sb.append(".");
        sb.append(artifactId);
        String packageName = sb.toString();

        // Generate test sources
        if (testCaseDir.isDirectory() && !testCaseDir.isHidden()) {
            genTestSource(testCaseDir, packageName, "test");
        }
        // STEP4: Generate order case
        if (null != logger) {
            logger.info(String.format(
                    "Generate test cases from %s",
                    orderCaseExcel.getAbsolutePath()));
        }

        String status = STATUS_SUCC;
        try {
            if (null != orderCaseExcel && orderCaseExcel.exists()) {
                OrderCases orderCases = new OrderCases(orderCaseExcel, testngPath,
                        classByMethod, testSuitesByGroup);
                List<XmlSuite> xmlSuites = orderCases.getXmlSuites();
                for (int i = 0; i < xmlSuites.size(); ++i) {
                    XmlSuite suite = xmlSuites.get(i);
                    Writer writer = null;
                    try {
                        writer = new OutputStreamWriter(new FileOutputStream(suite.getFileName()), "UTF-8");
                        writer.write(suite.toXml());
                    } finally {
                        if (null != writer) {
                            writer.close();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            status = STATUS_FAIL;
            throw ex;
        } finally {
            if (null != logger) {
                logger.info(String.format("Status: %s", status));
            }
        }
        // STEP5: Update suiteXmlFiles in pom.xml
        List<String> fileNames = new ArrayList<String>();
        for (File f : new File(testngPath).listFiles()) {
            if (f.isFile() && f.getName().endsWith(".xml")) {
                fileNames.add(String.format("testng/%s", f.getName()));
            }
        }
        String pomPath = arch.getPomPath();
        XmlDocument doc = new XmlDocument(pomPath);
        doc.updateSuiteXmlFiles(fileNames);
        doc.dumpTo(pomPath);

        // STEP6: Generate CaseCategory.xlsx
        /*
        String caseCategoryPath = arch.getCaseCategoryPath();
        OutputStream os = null;
        try {
            Workbook workbook = new XSSFWorkbook();
            for (String group : testSuitesByGroup.keySet()) {
                Sheet sheet = workbook.createSheet(group);
                List<String> l = testSuitesByGroup.get(group);
                sheet.createRow(sheet.getLastRowNum()).createCell(0).setCellValue("用例名称");
                for (String s : l) {
                    int rowId = sheet.getLastRowNum() + 1;
                    Row row = sheet.createRow(rowId);
                    row.createCell(0).setCellValue(s);
                }
            }

            os = new FileOutputStream(caseCategoryPath);
            workbook.write(os);
        } finally {
            if (null != os) {
                os.close();
            }
        }*/
    }
}
