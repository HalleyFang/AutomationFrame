package core.utils;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;

public class AssertionCtl {

    private static String runType = StringUtils.isEmpty(AppConf.getRunType()) ? "debug" : AppConf.getRunType();

    public static void verifyEquals(String actual, String expected, WebDriver driver) throws IOException {
        if (runType.equalsIgnoreCase("debug")) {
            if (StringUtils.isNotBlank(actual) && StringUtils.isNotBlank(expected)) {
                Assert.assertEquals(actual.replace(" ", ""), expected.replace(" ", ""));
            }
        } else {
            AssertionRuntime.verifyEquals(actual, expected, driver);
        }
    }

    public static void verifyEquals(String actual, String expected) {
        if (runType.equalsIgnoreCase("debug")) {
            if (StringUtils.isNotBlank(actual) && StringUtils.isNotBlank(expected)) {
                Assert.assertEquals(actual.replace(" ", ""), expected.replace(" ", ""));
            }
        } else {
            AssertionRuntime.verifyEquals(actual, expected);
        }

    }

    public static void verifyEqualsQuiet(String actual, String expected) throws Exception {
        verifyEquals(actual, expected);
    }

    public static void verifyEqualsLow(String actual, String expected) throws IOException {
        if (runType.equalsIgnoreCase("debug")) {
            Assert.assertEquals(actual.replace(" ", "").toLowerCase(), expected.replace(" ", "").toLowerCase());
        } else {
            AssertionRuntime.verifyEqualsLow(actual, expected);
        }
    }

    public static void verifyEquals(Object actual, Object expected) {
        if (runType.equalsIgnoreCase("debug")) {
            Assert.assertEquals(actual, expected);
        } else {
            AssertionRuntime.verifyEquals(actual, expected);
        }

    }

    public static void verifyEquals(int actual, int expected) {
        if (runType.equalsIgnoreCase("debug")) {
            Assert.assertEquals(actual, expected);
        } else {
            AssertionRuntime.verifyEquals(actual, expected);
        }

    }

    public static void verifyEquals(Boolean actual, Boolean expected, String message) throws IOException {
        if (runType.equalsIgnoreCase("debug")) {
            Assert.assertEquals(actual, expected, message);
        } else {
            AssertionRuntime.verifyEquals(actual, expected, message);
        }

    }

    public static void verifyContains(String actual, String expected) {
        if (runType.equalsIgnoreCase("debug")) {
            if (expected.contains(actual)) {
                Assert.assertEquals(1, 1);
            } else {
                Assert.assertEquals(1, -1);
            }
        } else {
            AssertionRuntime.verifyContains(actual, expected);
        }

    }

    public static void verifyEquals(Boolean actual, Boolean expected) {
        if (runType.equalsIgnoreCase("debug")) {
            Assert.assertEquals(actual, expected);
        } else {
            AssertionRuntime.verifyEquals(actual, expected);
        }
    }
}
