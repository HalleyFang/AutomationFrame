package core.listener;

import core.utils.AssertionRuntime;
import core.utils.ResultHandle;
import core.ui.utils.ScreenShot;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 断言监听，返回断言结果状态
 *
 * @author Halley.Fang
 */
public class AssertionListener extends TestListenerAdapter implements ResultHandle {
    private static Logger logger = Logger.getLogger(AssertionListener.class);
    public static ITestContext itx;

    @Override
    public void beforeConfiguration(ITestResult tr) {
        logger.info("this is :" + tr.getMethod().getRealClass().getName() + "." + tr.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        AssertionRuntime.flag = true;
        AssertionRuntime.errors.clear();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        this.handleAssertion(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        this.handleAssertion(tr);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        this.handleAssertion(tr);
    }

    private void screen(ITestResult tr) {
        if (tr.getMethod().getRealClass().getName().startsWith("myautomation.pagecases")) {
            logger.debug("failed need screen png ");
            try {
                WebDriver driver = (WebDriver) tr.getMethod().getRealClass().getField("driver").get(tr.getInstance());
                if (driver != null) {
                    String filename = tr.getMethod().getRealClass().getSimpleName() + "_" + tr.getMethod().getMethodName();
                    logger.debug("screen png " + filename);
                    ScreenShot sc = new ScreenShot();
                    sc.failedImageScreenShot(driver, filename);
                } else {
                    logger.debug("screen png driver is null");
                }
            } catch (Exception e) {
                logger.debug("screen png failed");
                e.printStackTrace();
            }
        }
    }

    private int index = 0;

    //处理失败异常信息
    private void handleAssertion(ITestResult tr) {
        if (!AssertionRuntime.flag) {
            screen(tr);
            Throwable throwable = tr.getThrowable();
            if (throwable == null) {
                throwable = new Throwable();
            }

            StackTraceElement[] traces = throwable.getStackTrace();
            StackTraceElement[] alltrace = new StackTraceElement[0];
            for (Error e : AssertionRuntime.errors) {
                StackTraceElement[] errorTraces = e.getStackTrace();
                StackTraceElement[] et = this.getKeyStackTrace(tr, errorTraces);
                StackTraceElement[] message = new StackTraceElement[]{new StackTraceElement("message : " + e.getMessage() + " in method : ", tr.getMethod().getMethodName(), tr.getTestClass().getRealClass().getSimpleName(), index)};
                index = 0;
                alltrace = this.merge(alltrace, message);
                alltrace = this.merge(alltrace, et);
            }
            if (traces != null) {
                traces = this.getKeyStackTrace(tr, traces);
                alltrace = this.merge(alltrace, traces);
            }
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);
            AssertionRuntime.flag = true;
            AssertionRuntime.errors.clear();
            tr.setStatus(ITestResult.FAILURE);
        }
    }

    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements) {
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().equals(tr.getTestClass().getName())) {
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();
            }
        }
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        for (int i = 0; i < et.length; i++) {
            et[i] = ets.get(i);
        }
        return et;
    }

    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2) {
        StackTraceElement[] ste = new StackTraceElement[traces1.length + traces2.length];
        for (int i = 0; i < traces1.length; i++) {
            ste[i] = traces1[i];
        }
        for (int i = 0; i < traces2.length; i++) {
            ste[traces1.length + i] = traces2[i];
        }
        return ste;
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        testContext = contextHandle(testContext);
            /*// List of test results which we will delete later
            ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
            // collect all id's from passed test
            Set<Integer> passedTestIds = new HashSet<Integer>();
            for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
                logger.info("PassedTests = " + passedTest.getName());
                passedTestIds.add(getId(passedTest));
            }

            Set<Integer> failedTestIds = new HashSet<Integer>();
            for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
                logger.info("failedTest = " + failedTest.getName());
                // id = class + method + dataprovider
                int failedTestId = getId(failedTest);

                // if we saw this test as a failed test before we mark as to be
                // deleted
                // or delete this failed test if there is at least one passed
                // version
                if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
                    testsToBeRemoved.add(failedTest);
                } else {
                    failedTestIds.add(failedTestId);
                }
            }

            // finally delete all tests that are marked
            for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
                    .hasNext(); ) {
                ITestResult testResult = iterator.next();
                if (testsToBeRemoved.contains(testResult)) {
                    logger.info("Remove repeat Fail Test: " + testResult.getName());
                    iterator.remove();
                }
            }
        }*/
        itx = testContext;
    }

    /*private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }*/
}
