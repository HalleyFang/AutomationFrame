package core.testmanagement;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import core.annotation.CaseAuthor;
import core.annotation.CaseId;
import core.api.apiutils.GsonChange;
import core.utils.ApiConf;
import core.utils.Authors;
import core.utils.GetTime;
import core.utils.ReadConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import jodd.http.HttpRequest;

import java.lang.reflect.Parameter;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AutoTestCaseListener implements ITestListener {

    private Logger logger = LoggerFactory.getLogger(AutoTestCaseListener.class);

    private ExecutorService executor;

    private ResourceBundle config = ReadConfig.readConfig("testmanagement.properties");

    private GsonChange gsonChange = new GsonChange();

    private String execId = GetTime.getCurrentTimeM();

    //tm中标识来源
    private Integer type = 2;

    private String getCaseId(ITestResult result) {
        String caseId = null;
        try {
            caseId = result.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(CaseId.class)
                    .caseId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseId;
    }

    private String getAuthor(ITestResult result) {
        Authors authors = null;
        try {
            authors = result.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(CaseAuthor.class)
                    .name();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (authors == null) {
            return null;
        }
        return authors.getAuthor();
    }

    private String getCreateDate(ITestResult result) {
        String createDate = null;
        try {
            createDate = result.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(CaseAuthor.class)
                    .createDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createDate;
    }

    @Cacheable("tm.ip")
    private String getIp() {
        String ip = config.getString("tm.host");
        if (StringUtils.isEmpty(ip)) {
            return null;
        }
        return ip;
    }

    @Cacheable("tm.port")
    private Integer getPort() {
        String portStr = config.getString("tm.port");
        if (StringUtils.isEmpty(portStr)) {
            return null;
        }
        Integer port = Integer.valueOf(portStr);
        return port;
    }


    @Cacheable("tm.path")
    private String getPath() {
        String path = config.getString("tm.path");
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        return path;
    }

    //todo 动态代理获取传入的值
    private JsonObject getMethodParameter(ITestResult result) {
        Parameter[] parameters = result.getMethod().getConstructorOrMethod().getMethod().getParameters();
        if (parameters == null || parameters.length == 0) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(parameters), JsonObject.class);
    }

    private void post(ITestResult result, Integer status) {
        String caseId = getCaseId(result);
        if (!StringUtils.isEmpty(caseId)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("execId", execId);
            jsonObject.addProperty("caseId", caseId);
            jsonObject.addProperty("author", getAuthor(result));
            jsonObject.addProperty("createDate", getCreateDate(result));
            jsonObject.addProperty("status", status);
            if (status == 5) {
                jsonObject.addProperty("startDate", GetTime.getCurrentTime());
            }
            if (status == 1 || status == 2 || status == 3) {
                jsonObject.addProperty("endDate", GetTime.getCurrentTime());
            }
            jsonObject.addProperty("type", type);
            jsonObject.addProperty("currentTime", GetTime.getTimeStamp());
//            jsonObject.add("methodParam", getMethodParameter(result));
            String body = gsonChange.jsonToString(jsonObject);
            //发送到test management
            executor.submit(() -> {
                try {
                    HttpRequest httpRequest = new HttpRequest();
                    httpRequest.method(ApiConf.POST.getValue())
                            .protocol(ApiConf.HTTP.getValue())
                            .host(getIp())
                            .port(getPort())
                            .path(getPath())
                            .contentType(ApiConf.CONTENT_TYPE.getValue())
                            .body(body)
                            .timeout(10000)
                            .send();
                    logger.debug("Auto Request to Test Management: " + body);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        } else {
            logger.error("caseId not fount" + result.getMethod().getMethodName());
        }
    }

    /**
     * 0-未执行
     * 1-成功
     * 2-失败
     * 3-跳过
     * 4-阻塞
     * 5-执行中
     *
     * @param result
     */
    @Override
    public void onTestStart(ITestResult result) {
        logger.debug("onTestStart: " + getCaseId(result));
        post(result, 5);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.debug("onTestSuccess: " + getCaseId(result));
        post(result, 1);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.debug("onTestFailure: " + getCaseId(result));
        post(result, 2);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.debug("onTestSkipped: " + getCaseId(result));
        post(result, 3);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
        executor = Executors.newCachedThreadPool();
        context.setAttribute("execId", execId);
        logger.debug("execId: " + context.getAttribute("execId") + " Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("execId: " + context.getAttribute("execId") + " Finished");
    }
}
