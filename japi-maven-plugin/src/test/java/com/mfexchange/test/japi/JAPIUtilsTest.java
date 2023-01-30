package com.elong.test.japi;

import static org.testng.Assert.assertEquals;

import java.io.File;

import org.junit.Assert;
import org.testng.annotations.*;

public class JAPIUtilsTest {
    @Test(dataProvider="testGetRelativePathDataProvider")
    public void testGetRelativePath(String s1, String s2, String base, String expected) {
        String actual = JAPIUtils.getRelativePath(s1, s2, base);
        Assert.assertEquals(expected, actual);
    }

    @DataProvider(name="testGetRelativePathDataProvider")
    private Object[][] testGetRelativePathDataProvider() {
        return new Object[][] {
                {
                     toPath("@home@yanshuai@japi@testcases@..@TestProject"),
                     toPath("@home@yanshuai@japi@testcases@..@hello@lib"),
                     toPath("@home@yanshuai@japi@testcases"),
                     toPath("..@hello@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@..@TestProject"),
                    toPath("@home@yanshuai@japi@testcases@..@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases@..@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@ABC@TestProject"),
                    toPath("@home@yanshuai@japi@testcases@..@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@..@..@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@..@TestProject"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("")
                },
                {
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@ABC@TestProject"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@..")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@..@TestProject"),
                    toPath("@home@yanshuai@japi@testcases@ABC@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("ABC@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("@home@yanshuai@japi@testcases@ABC@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("ABC@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@ABC@TestProject"),
                    toPath("@home@yanshuai@japi@testcases@ABC@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@..@ABC@lib")
                },
                {
                    toPath("@home@yanshuai@japi@testcases@ABC@TestProject"),
                    toPath("@home@yanshuai@japi@testcases@ABCD@lib"),
                    toPath("@home@yanshuai@japi@testcases"),
                    toPath("..@..@ABCD@lib")
                }
        };
    }

    @Test(dataProvider="testToLiteralString")
    public void testToLiteralString(String str, String expected) {
        String actual = JAPIUtils.toLiteralString(str);
        Assert.assertEquals(expected, actual);
    }

    @DataProvider(name="testToLiteralString")
    private Object[][] testToLiteralStringDataProvider() {
        return new Object[][] {
                {"\\", "\"\\\\\""},
                {"\"", "\"\\\"\""},
                {"\t", "\"\\t\""},
                {"\r", "\"\\r\""},
                {"\n", "\"\\n\""}
        };
    }

    @Test
    public void testPackageNameToPath() {
        assertEquals("com\\elong\\Hotel", "com.elong.Hotel".replaceAll("\\.", "\\\\"));
        assertEquals("com/elong/Hotel", "com.elong.Hotel".replaceAll("\\.", "/"));
    }

    @Test
    public void testToParam() {
        String param = "abc D\te\nf\rAZ\r$9";
        String expected = "ABC_D_E_F_AZ__9";
        String actual = JAPIUtils.toParam(param);
        assertEquals(actual, expected);
    }

    private String toPath(String path) {
        if ('\\' == File.separatorChar) {
            return path.replaceAll("@", "\\\\");
        } else {
            return path.replaceAll("@", "/");
        }
    }
}
