package com.mfexchange.test.japi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlDocument {
    private final Document document;

    public XmlDocument(String path) throws DocumentException, IOException {
        this(new File(path));
    }

    public XmlDocument(File file) throws DocumentException, IOException {
        this(new FileInputStream(file));
    }

    public XmlDocument(InputStream is) throws DocumentException, IOException {
        SAXReader saxReader = new SAXReader();
        document = saxReader.read(is);
        is.close();
    }

    public void updateText(Map<String, String> m) {
        for (String key : m.keySet()) {
            Element element = (Element) document.selectSingleNode(key);
            element.setText(m.get(key));
        }
    }

    public void updateSuiteXmlFiles(List<String> fileNames) {
        Element suiteXmlFilesElement = (Element) document.selectSingleNode(
                "/project/build/plugins/plugin/configuration/suiteXmlFiles");
        // STEP1: Remove existed suiteXmlFile
        List<?> list = suiteXmlFilesElement.selectNodes("suiteXmlFile");
        Iterator<?> iter = list.iterator();
        while (iter.hasNext()) {
            Element suiteXmlFileElement = (Element) iter.next();
            suiteXmlFilesElement.remove(suiteXmlFileElement);
        }
        // STEP2: Add new suiteXmlFile element
        for (String fileName : fileNames) {
            Element suiteXmlFileElement = suiteXmlFilesElement.addElement("suiteXmlFile");
            suiteXmlFileElement.setText(String.format("${project.basedir}/src/test/resources/%s",
                    fileName));
        }
    }

    public void updateAttribute(Map<String, String> m) {
        for (String key : m.keySet()) {
            Attribute attribute = (Attribute) document.selectSingleNode(key);
            attribute.setValue(m.get(key));
        }
    }

    public void addElementAttr(String node, String key, String attr, String value) {
        Element element = (Element) document.selectSingleNode(node);
        Element subElement = element.addElement(key);
        subElement.addAttribute(attr, value);
    }

    public void updateDependencies(List<String> jarPaths) {
        Element dependenciesElement = (Element) document.selectSingleNode("/project/dependencies");
        // STEP1: Remove existed local jar dependencies
        List<?> list = dependenciesElement.selectNodes("dependency");
        Iterator<?> iter = list.iterator();
        while (iter.hasNext()) {
            Element dependencyElement = (Element) iter.next();
            Element scopeElement = (Element) dependencyElement.selectSingleNode("scope");
            if (null != scopeElement && "system".equals(scopeElement.getText())) {
                dependenciesElement.remove(dependencyElement);
            }
        }
        // STEP2: Add new local jar dependencies
        for (String jarPath : jarPaths) {
            Element dependencyElement = dependenciesElement.addElement("dependency");
            Element groupIdElement = dependencyElement.addElement("groupId");
            groupIdElement.setText("com.mfexchange.test.local");
            Element artifactIdElement = dependencyElement.addElement("artifactId");
            artifactIdElement.setText(JAPIUtils.getArtifactId(jarPath));
            Element versionElement = dependencyElement.addElement("version");
            versionElement.setText(JAPIUtils.getVersion(jarPath));
            Element scopeElement = dependencyElement.addElement("scope");
            scopeElement.setText("system");
            Element systemPathElement = dependencyElement.addElement("systemPath");
            systemPathElement.setText(("${project.basedir}" + File.separator + jarPath).replaceAll("\\\\", "/"));
        }
    }

    public void updateTestNGXml(String packageName, String className, Set<String> methods) {
        String xpath = "/suite/test/classes/class[@name='" + packageName + "." + className + "Impl']";
        Element element = (Element) document.selectSingleNode(xpath);
        Element methodsElement = element.addElement("methods");
        for (String method : methods) {
            Element includeElement = methodsElement.addElement("include");
            includeElement.addAttribute("name", method);
        }
    }

    public void dumpTo(String xmlPath) throws IOException {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setLineSeparator(JAPIUtils.LINE_SEPARATOR);
        XMLWriter writer = null;

        try {
            writer = new XMLWriter(new FileOutputStream(
                    new File(xmlPath)), format);
            writer.setEscapeText(true);
            writer.write(document);
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }
}
