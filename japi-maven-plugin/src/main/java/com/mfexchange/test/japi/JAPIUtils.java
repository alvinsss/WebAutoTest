package com.mfexchange.test.japi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JAPIUtils {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String listener = "com.mfexchange.test.japi.testng.mfexchangeTestListener";

    public static void addGroup(Map<String, List<String>> testSuitesByGroup,
            Set<String> groups, String testSuiteId) {
        for (String group : groups) {
            if ("".equals(group.trim())) {
                continue;
            }
            if (!testSuitesByGroup.containsKey(group)) {
                List<String> testSuites = new ArrayList<String>();
                testSuitesByGroup.put(group, testSuites);
            }
            List<String> testSuites = testSuitesByGroup.get(group);
            testSuites.add(testSuiteId);
        }
    }

    public static String getRelativePath(String s1, String s2, String base) {
        String p1 = "";
        if (s1.length() > base.length()) {
            p1 = s1.substring(base.length() + 1);
        }
        String p2 = "";
        if (s2.length() > base.length()) {
            p2 = s2.substring(base.length() + 1);
        }
        String[] parts = splitByFileSeparator(p1);

        int dist = 0;
        for (String part : parts) {
            if ("..".equals(part)) {
                ++dist;
            } else if (!".".equals(part) && !"".equals(part)) {
                --dist;
            }
        }

        List<String> al = new ArrayList<String>();
        if (dist < 0) {
            for (int i = 0; i < -dist; ++i) {
                al.add("..");
            }
        } else if (dist > 0) {
            parts = splitByFileSeparator(base);
            int len = parts.length;
            for (int i = 0; i < dist; ++i) {
                al.add(parts[len - i - 1]);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = al.size() - 1; i >= 0; --i) {
            sb.append(al.get(i));
            if (i != 0) {
                sb.append(File.separatorChar);
            }
        }
        if (!"".equals(p2)) {
            if (0 != sb.length()) {
                sb.append(File.separatorChar);
            }
            sb.append(p2);
        }
        return sb.toString();
    }

    public static boolean isClassFileName(String filename) {
        if ('0' <= filename.charAt(0) && '9' >= filename.charAt(0)) {
            return false;
        }

        for (int i = 0; i < filename.length(); ++i) {
            if (!('a' <= filename.charAt(i) && 'z' >= filename.charAt(i))
                    && !('A' <= filename.charAt(i) && 'Z' >= filename.charAt(i))
                    && !('0' <= filename.charAt(i) && '9' >= filename.charAt(i))
                    && '_' != filename.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String getClassName(String classPrefixName) {
        StringBuilder sb = new StringBuilder();
        if ('a' <= classPrefixName.charAt(0) && 'z' >= classPrefixName.charAt(0)) {
            sb.append((char)(classPrefixName.charAt(0) - 'a' + 'A'));
            sb.append(classPrefixName.substring(1));
        } else if ('A' <= classPrefixName.charAt(0) && 'Z' >= classPrefixName.charAt(0)) {
            sb.append(classPrefixName);
        } else if ('_' == classPrefixName.charAt(0)) {
            sb.append(classPrefixName);
        }
        sb.append("Test");
        return sb.toString();
    }

    public static String getArtifactId(String jarPath) {
        int id = jarPath.lastIndexOf(File.separatorChar);
        String jarName = jarPath.substring(id + 1);
        id = jarName.lastIndexOf('.');
        jarName = jarName.substring(0, id);
        id = jarName.lastIndexOf('-');
        if (-1 == id) {
            return jarName;
        } else {
            return jarName.substring(0, id);
        }
    }

    public static String getVersion(String jarPath) {
        int id = jarPath.lastIndexOf(File.separatorChar);
        String jarName = jarPath.substring(id + 1);
        id = jarName.lastIndexOf('.');
        jarName = jarName.substring(0, id);
        id = jarName.lastIndexOf('-');
        if (-1 == id || id + 1 == jarName.length()) {
            return "SNAPSHOT";
        } else {
            return jarName.substring(id + 1);
        }
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            for (File f: file.listFiles()) {
                delete(f);
            }
        }
        file.delete();
    }

    public static String toLiteral(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\\\"", "\\\\\"")
                .replaceAll("\\t", "\\\\t")
                .replaceAll("\\r", "\\\\r")
                .replaceAll("\\n", "\\\\n"));
        return sb.toString();
    }

    public static String toLiteralString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(toLiteral(str));
        sb.append("\"");
        return sb.toString();
    }

    public static String toParam(String p) {
        StringBuilder sb = new StringBuilder();
        String param = p.toUpperCase();
        if (param.length() > 0) {
            if (param.charAt(0) >= '0' && param.charAt(0) <= '9') {
                sb.append("_");
            }
        }
        for (int i = 0; i < param.length(); ++i) {
            if ((param.charAt(i) >= '0' && param.charAt(i) <= '9')
                    || (param.charAt(i) >= 'A' && param.charAt(i) <= 'Z')
                    || (param.charAt(i) >= 'a' && param.charAt(i) <= 'z')) {
                sb.append(param.charAt(i));
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    public static String packageNameToPath(String packageName) {
        if ('\\' == File.separatorChar) {
            return packageName.replaceAll("\\.", "\\\\");
        } else {
            return packageName.replaceAll("\\.", "/");
        }
    }

    private static String[] splitByFileSeparator(String p1) {
        String[] parts = null;
        if ('\\' == File.separatorChar) {
            parts = p1.split("\\\\");
        } else {
            parts = p1.split("/");
        }
        return parts;
    }

    public static Set<String> stringToSet(String s) {
        Set<String> set = new HashSet<String>();
        String[] strs = s.split(",");
        for (String str : strs) {
            set.add(str.trim());
        }
        return set;
    }
}
