package core.controller;

import core.testplan.TestPlanExec;
import core.utils.AppConf;
import core.utils.RunStatusUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 报告入口
 */
@RestController
@RequestMapping(path = "/report")
public class ReportCheck {

    @RequestMapping(path = "/check")
    public Boolean checkStatus(@RequestParam String browser, @RequestParam String db, @RequestParam String tenant, @RequestParam Boolean isSaas) {
        Map<Long, String> runStatus = TestPlanExec.runStatus;
        if (runStatus.containsValue(RunStatusUtil.runStr(browser, db, tenant))) {
            return true;
        }
        return false;
    }

    @RequestMapping(path = "url")
    public String getUrl() {
        return AppConf.getReportUrl();
    }

}
