package com.mfexchange.test.japi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.DocumentException;

public class TestProject {
    private final String groupId;
    private final String artifactId;
    private final String version;
    private final List<String> localJarPaths;

    public TestProject(String groupId, String artifactId, String version, List<String> localJarPaths) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.localJarPaths = localJarPaths;
    }

    public void generateTo(File testProjectDir) throws DocumentException, IOException {
        TestProjectArch arch = new TestProjectArch(testProjectDir.getAbsolutePath());
        // STEP1: Generate test project directory
        testProjectDir.mkdirs();
        // STEP2: Generate pom.xml
        String pomPath = arch.getPomPath();
        XmlDocument doc = null;
        if (new File(pomPath).exists()) {
            doc = new XmlDocument(pomPath);
        } else {
            doc = new XmlDocument(getClass().getResourceAsStream("/pom.xml.tpl"));
        }
        // STEP2.1: Update groupId, artifactId and version in pom.xml
        Map<String, String> m = new HashMap<String, String>();
        m.put("/project/groupId", groupId);
        m.put("/project/artifactId", artifactId);
        m.put("/project/version", version);
        doc.updateText(m);
        // STEP2.2: Update local jar dependencies in pom.xml
        doc.updateDependencies(localJarPaths);
        // STEP2.3: Dump to pom.xml
        doc.dumpTo(pomPath);
        // STEP3: Generate src/test/assembly/test-jar-with-dependencies
        new File(arch.getAssemblyPath()).mkdirs();
        doc = new XmlDocument(getClass().getResourceAsStream("/test-jar-with-dependencies.xml"));
        doc.dumpTo(arch.getAssemblyFilePath());
        // STEP4: Generate src/test/java
        new File(arch.getJavaPath()).mkdirs();
        // STEP5: Generate src/test/resources/global.properties
        //                 src/test/resources/testng
        new File(arch.getResourcePath()).mkdirs();
        String testngPath = arch.getTestNGPath();
        JAPIUtils.delete(new File(testngPath));
        new File(testngPath).mkdirs();

        File globalPropertiesFile = new File(arch.getGlobalPropertiesPath());
        if (!globalPropertiesFile.exists()) {
            globalPropertiesFile.createNewFile();
        }
    }
}
