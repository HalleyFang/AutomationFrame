package core.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.report.OperationResult;
import core.report.TestCaseResult;
import core.report.TestPlanResult;
import core.utils.GetTime;
import core.utils.ResultHandle;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReportListener implements ITestListener, ResultHandle {

    public static TestPlanResult testPlanResult;
    private Logger logger = Logger.getLogger("ReportListener.class");
/*    private List<ITestResult> caseList = new ArrayList<>();
    private Map<ITestResult,TestCaseResult> testCaseResultsTmp = new HashMap<>();*/

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
//        caseList.add(result);
//        caseList.add(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
//        caseList.add(result);
//        caseList.add(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        caseList.add(result);
//        caseList.add(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        testPlanResult = new TestPlanResult();
        testPlanResult.setStartTime(System.currentTimeMillis());
    }

    @Override
    public void onFinish(ITestContext context) {
//        testCaseResultsTmp.clear();
        testPlanResult.setCompleteTime(System.currentTimeMillis());
        context = contextHandle(context);

        for (ITestResult passedTest : context.getPassedTests().getAllResults()) {
            successResult(passedTest);
        }
        for (ITestResult failedTest : context.getFailedTests().getAllResults()) {
            failedResult(failedTest);
        }
        for (ITestResult skipTest : context.getSkippedTests().getAllResults()) {
            skipResult(skipTest);
        }

        /*Set<String> set = new HashSet();
        List<String> newList = new ArrayList();
        for (Iterator iter = caseList.iterator(); iter.hasNext();) {
            String element = iter.next().toString();
            if (set.add(element))
                newList.add(element);
        }*/

  /*      for(int i=0;i<newList.size();i++){
            TestCaseResult testCaseResultTmp = testCaseResultsTmp.get(newList.get(i));
            if(testCaseResultTmp != null){
                testPlanResult.addCaseResult(testCaseResultTmp);
            }
        }*/

//        String outputDir="output/"+ GetTime.getCurrentTimeM()+"/";;
        String outputDir = "output/";
        String resultsDir = outputDir + "results/";
        mkDir(Paths.get(resultsDir));
        try {
            copyResources(outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            List<TestPlanResult> testPlanResults = new ArrayList<>();
            testPlanResults.add(testPlanResult);
            s = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testPlanResults);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Path resultPath = Paths.get(resultsDir, "_result.js");
        if (resultPath.toFile().exists()) {
            resultPath.toFile().delete();
        }
        try {
            logger.debug("==== write _result.js ====");
            Files.write(resultPath, ("var plans = " + s).getBytes("utf-8"), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        caseList.clear();
    }

    private static void mkDir(Path path) {
        File file = path.toFile();
        if (!file.exists()) {
            file.mkdirs();
        } else {
            file.delete();
            file.mkdir();
        }
    }

    private static void copyResources(String resourceDir) throws IOException {
        mkDir(Paths.get(resourceDir, "resources", "fonts"));
        copyFile("result-html/index.html", resourceDir);
        copyFile("result-html/resources/botwire.min.js", resourceDir);
        copyFile("result-html/resources/echarts.simple.min.js", resourceDir);
        copyFile("result-html/resources/bootstrap.min.css", resourceDir);
        copyFile("result-html/resources/fonts/glyphicons-halflings-regular.eot", resourceDir);
        copyFile("result-html/resources/fonts/glyphicons-halflings-regular.svg", resourceDir);
        copyFile("result-html/resources/fonts/glyphicons-halflings-regular.ttf", resourceDir);
        copyFile("result-html/resources/fonts/glyphicons-halflings-regular.woff", resourceDir);
        copyFile("result-html/resources/fonts/glyphicons-halflings-regular.woff2", resourceDir);
    }

    private static void copyFile(String source, String out) throws IOException {
        try (InputStream stream = ReportListener.class.getClassLoader().getResourceAsStream(source)) {
            Path path = Paths.get(out, source.substring("result-html/".length()));
            File file = path.toFile();
            if (file.exists()) {
                file.delete();
            }
            Files.copy(stream, path);
        }
    }

    private void failedResult(ITestResult result) {
        String crlf = " \r\n ";
        String methodName = result.getMethod().getMethodName();
        TestCaseResult testCaseResult = new TestCaseResult(methodName, "", result.getMethod().getDescription(), false, false);
        String errorMsg = "";
        Throwable th = result.getThrowable();
        errorMsg = th.toString() + crlf;
        StackTraceElement[] stackArray = th.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement stack = stackArray[i];
            errorMsg += stack.toString() + crlf;
        }
        OperationResult operationResult = new OperationResult(false, errorMsg);
        operationResult.setScreenshot(result.getMethod().getRealClass().getSimpleName() + "_" + methodName);
        testCaseResult.addStepResult(operationResult);
//        testCaseResultsTmp.put(result,testCaseResult);
//        testCaseResultsTmp.put(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName(),testCaseResult);
        testPlanResult.addCaseResult(testCaseResult);
    }

    private void successResult(ITestResult result) {
        TestCaseResult testCaseResult = new TestCaseResult(result.getMethod().getMethodName(), "", result.getMethod().getDescription(), false, true);
        OperationResult operationResult = new OperationResult(true, "success");
        testCaseResult.addStepResult(operationResult);
//        testCaseResultsTmp.put(result,testCaseResult);
//        testCaseResultsTmp.put(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName(),testCaseResult);
        testPlanResult.addCaseResult(testCaseResult);
    }

    private void skipResult(ITestResult result) {
        TestCaseResult testCaseResult = new TestCaseResult(result.getMethod().getMethodName(), "", result.getMethod().getDescription(), true, false);
//        testCaseResultsTmp.put(result,testCaseResult);
        //        testCaseResultsTmp.put(result.getMethod().getRealClass().getName()+"."+result.getMethod().getMethodName(),testCaseResult);
        testPlanResult.addCaseResult(testCaseResult);
    }
}
