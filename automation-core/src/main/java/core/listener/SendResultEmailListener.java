package core.listener;

import core.email.EmailModel;
import core.email.SendEmail;
import core.report.TestPlanResult;
import core.utils.*;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SendResultEmailListener implements ITestListener, ResultHandle {

    private static final Logger logger = LoggerFactory.getLogger(SendResultEmailListener.class);

    public static List<String> toList = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext itx) {
        String subject = "自动化测试-" + GetTime.getCurrentTimeM();
        //获取自动化结果
        Integer s = 0;
        Integer f = 0;
        Integer k = 0;
        if (itx != null) {
            Set<ITestResult> passedTest = itx.getPassedTests().getAllResults();
            s = passedTest.size();
            Set<ITestResult> failedTest = itx.getFailedTests().getAllResults();
            f = failedTest.size();
            Set<ITestResult> skippedTest = itx.getSkippedTests().getAllResults();
            k = skippedTest.size();
        }
        Integer total = s + f + k;

        //获取report
        TestPlanResult testPlanResult = ReportListener.testPlanResult;
        String startTime = GetTime.timeToDateStr(System.currentTimeMillis());
        String endTime = startTime;
        long time = 0;
        if (testPlanResult.getStartTime() > 0) {
            long startTime_tmp = testPlanResult.getStartTime();
            long endTime_tmp = testPlanResult.getCompleteTime();
            startTime = GetTime.timeToDateStr(startTime_tmp);
            endTime = GetTime.timeToDateStr(endTime_tmp);
            long time_tmp = GetTime.dateStrToTime(endTime) - GetTime.dateStrToTime(startTime);
            time = time_tmp / 1000;
        }
        long minute = 0;
        long milli = 0;
        long hour = 0;
        if (time > 0 && time < 60) {
            milli = time;
        } else if (time >= 60 && time < 3600) {
            minute = time / 60;
            milli = time % 60;
        } else if (time >= 3600) {
            hour = time / 3600;
            long m = time % 3600;
            minute = m / 60;
            milli = m % 60;
        }

        //生成html
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> \n" +
                "<meta charset=\"utf-8\"> \n" +
                "<title>report</title> \n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"4\"  class=\"tabtop13\" align=\"center\">\n" +
                "  <caption>自动化测试结果<br>" +
                "开始时间：" + startTime + "  结束时间：" + endTime + "  耗时：" + hour + "时" + minute + "分" + milli + "秒</caption>\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>总计</th>\n" +
                "\t<th>成功</th>\n" +
                "\t<th>失败</th>\n" +
                "\t<th>跳过</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <th>总计</th>\n" +
                "    <td align=\"center\" bgcolor=\"#20B2AA\">" + total + "</td>\n" +
                "\t<td align=\"center\" bgcolor=\"#00FF00\">" + s + "</td>\n" +
                "\t<td align=\"center\" bgcolor=\"#FF0000\">" + f + "</td>\n" +
                "\t<td align=\"center\" bgcolor=\"#FFFF00\">" + k + "</td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "<br>" + "<a href=\"" + AppConf.getReportUrl() + "\">报告详细</a>" +
                "\n" +
                "</body>\n" +
                "</html>";

        if (toList.size() > 0) {
            EmailModel emailModel = new EmailModel();
            emailModel.setToList(toList);
            emailModel.setSubject(subject);
            emailModel.setContentIsHtml(true);
            emailModel.setHasAttachments(false);
            emailModel.setContent(html);
            SendEmail sendEmail = (SendEmail) GetBeanFactoryUtil.getBean(SendEmail.class);
            try {
                sendEmail.sendEmail(emailModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.error("email to list is empty");
        }
    }

}
