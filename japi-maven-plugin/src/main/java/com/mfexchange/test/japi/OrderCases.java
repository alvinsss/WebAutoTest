package com.mfexchange.test.japi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.xml.XmlSuite;

import com.mfexchange.test.japi.graph.Digraph;
import com.mfexchange.test.japi.graph.KahnTopological;

public class OrderCases {
    private List<XmlSuite> xmlSuites;

    public List<XmlSuite> getXmlSuites() {
        return xmlSuites;
    }

    public OrderCases(String orderCaseExcelPath, String testngPath,
            Map<String, String> classByMethod, Map<String, List<String>> testSuitesByGroup)
                    throws IOException {
        this(new File(orderCaseExcelPath), testngPath, classByMethod, testSuitesByGroup);
    }

    public OrderCases(File orderCaseExcelFile, String testngPath,
            Map<String, String> classByMethod, Map<String, List<String>> testSuitesByGroup)
                    throws IOException {
        this(new FileInputStream(orderCaseExcelFile), testngPath, classByMethod, testSuitesByGroup);
    }

    public OrderCases(InputStream is, String testngPath,
            Map<String, String> classByMethod, Map<String, List<String>> testSuitesByGroup)
                    throws IOException {
        xmlSuites = new ArrayList<XmlSuite>();
        try {
            parse(is, testngPath, classByMethod, testSuitesByGroup);
        } finally {
            if (null != is) {
                is.close();
            }
        }
    }

    private void parse(InputStream is, String testngPath,
            Map<String, String> classByMethod, Map<String, List<String>> testSuitesByGroup) 
                    throws IOException {
        Map<String, OrderCase> orderCases = new HashMap<String, OrderCase>();
        Digraph graph = new Digraph();
        Set<String> s1 = new HashSet<String>();
        Set<String> s2 = new HashSet<String>();

        Workbook workbook = new XSSFWorkbook(is);
        int sheetNum = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            // skip non-OrderList sheet
            if (!sheetName.startsWith("OrderList")) {
                continue;
            }

            Iterator<Row> rowIter = sheet.iterator();
            // skip headers
            if (!rowIter.hasNext()) {
                continue;
            }
            rowIter.next();

            // Traversal each row
            while (rowIter.hasNext()) {
                Row row = rowIter.next();
                Iterator<Cell> cellIter = row.cellIterator();
                // get case id
                if (!cellIter.hasNext()) {
                    continue;
                }
                Cell cell = cellIter.next();
                String id = ExcelUtils.getCellValue(cell).toString();
                if ("".equals(id)) {
                    continue;
                }

                // continue or skip after failure
                if (!cellIter.hasNext()) {
                    continue;
                }
                cell = cellIter.next();
                boolean continueAfterFailure = Boolean.valueOf(ExcelUtils.getCellValue(cell).toString());

                if (classByMethod.containsKey(id)) {
                    throw new IllegalArgumentException(String.format(
                            "Duplicated case '%s' found in class %s and order cases",
                            id, classByMethod.get(id)));
                }

                graph.addNode(id);
                if (s1.contains(id)) {
                    throw new IllegalArgumentException(String.format(
                            "Duplicated case '%s' found in order cases", id));
                }
                s1.add(id);

                // get groups
                Set<String> groups = new HashSet<String>();
                groups.add("ALL");
                groups.add("OrderCase");
                JAPIUtils.addGroup(testSuitesByGroup, groups, id);

                // get test steps
                List<String> steps = new ArrayList<String>();
                while (cellIter.hasNext()) {
                    cell = cellIter.next();
                    String step = ExcelUtils.getCellValue(cell).toString();
                    if (!classByMethod.containsKey(step)) {
                        graph.addEdge(step, id);
                        s2.add(step);
                    }
                    steps.add(step);
                }

                OrderCase orderCase = new OrderCase(id, continueAfterFailure, steps);
                orderCases.put(id, orderCase);
            }
        }

        for (String s : s2) {
            if (!s1.contains(s)) {
                throw new IllegalArgumentException(String.format(
                        "Step '%s' isn't a test case in order case", s));
            }
        }

        List<String> ids = KahnTopological.sort(graph);
        for (String id : ids) {
            OrderCase orderCase = orderCases.get(id);
            orderCase.expandSteps(orderCases);
        }

        for (String id : orderCases.keySet()) {
            OrderCase orderCase = orderCases.get(id);
            XmlSuite suite = orderCase.toXmlSuite(testngPath, classByMethod);
            xmlSuites.add(suite);
        }
    }
}
