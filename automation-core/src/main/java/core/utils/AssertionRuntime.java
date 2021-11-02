package core.utils;

import core.ui.utils.ScreenShot;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * verify，封装断言，避免失败中断测试用例执行
 * 适用一个用例中需要验证多个相互独立的数据结果，即使之前的验证失败了后续的验证也需要执行
 * 最终在报告中显示该条用例状态为失败（在listener中处理），并通过日志进行定位失败原因
 * @author Halley.Fang
 */
public class AssertionRuntime {
    public static boolean flag = true;
    public static List<Error> errors = new ArrayList<Error>();

    public synchronized static void verifyEquals(String actual, String expected, WebDriver driver) throws IOException {
        if (StringUtils.isNotBlank(actual) && StringUtils.isNotBlank(expected)) {
            try {
                Assert.assertEquals(actual.replace(" ", ""), expected.replace(" ", ""));
            } catch (AssertionError e) {
                errors.add(e);
                flag = false;
            }
        } else if (StringUtils.isBlank(actual) && StringUtils.isBlank(expected)) {
            flag = true;
        } else {
            flag = false;
        }
        if (!flag) {
            StackTraceElement a = Thread.currentThread().getStackTrace()[2];
            String filename = a.getClassName() + "_" + a.getMethodName();
            ScreenShot sc = new ScreenShot();
            sc.failedImageScreenShot(driver, filename);
        }
    }

    public synchronized static void verifyEquals(String actual, String expected) {
        if (StringUtils.isNotBlank(actual) && StringUtils.isNotBlank(expected)) {
            try {
                Assert.assertEquals(actual.replace(" ", ""), expected.replace(" ", ""));
            } catch (AssertionError e) {
                errors.add(e);
                flag = false;
            }
        } else if (StringUtils.isBlank(actual) && StringUtils.isBlank(expected)) {
            flag = true;
        } else {
            flag = false;
        }
    }

    public synchronized static void verifyEqualsQuiet(String actual, String expected) throws Exception {
        verifyEquals(actual, expected);
        if (!flag) {
            throw new Exception("assert failed");
        }
    }

    public synchronized static void verifyEqualsLow(String actual, String expected) throws IOException {
        try {
            Assert.assertEquals(actual.replace(" ", "").toLowerCase(), expected.replace(" ", "").toLowerCase());
        } catch (AssertionError e) {
            errors.add(e);
            flag = false;
        }
    }

    public synchronized static void verifyEquals(Object actual, Object expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e2) {
            errors.add(e2);
            flag = false;
        }
    }

    public synchronized static void verifyEquals(int actual, int expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e2) {
            errors.add(e2);
            flag = false;
        }
    }

    public synchronized static void verifyEquals(Boolean actual, Boolean expected, String message) throws IOException {
        try {
            Assert.assertEquals(actual, expected, message);
        } catch (Error e) {
            errors.add(e);
            flag = false;
        }
    }

    public synchronized static void verifyContains(String actual, String expected) {
        try {
            if (expected.contains(actual)) {
                Assert.assertEquals(1, 1);
            } else {
                Assert.assertEquals(1, -1);
            }
        } catch (Error e2) {
            errors.add(e2);
            flag = false;
        }
    }

    public synchronized static void verifyEquals(Boolean actual, Boolean expected) {
        try {
            Assert.assertEquals(actual, expected);
        } catch (Error e) {
            errors.add(e);
            flag = false;
        }
    }
}
