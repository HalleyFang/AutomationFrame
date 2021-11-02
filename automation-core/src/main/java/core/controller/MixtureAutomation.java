package core.controller;

import core.data.TestPlanType;
import core.testplan.TestPlanExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 混合测试入口
 */
@RestController
@RequestMapping(path = "/mixture_automation")
public class MixtureAutomation {

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    TestPlanExec testPlanExec;

    @RequestMapping(path = "/start")
    public String start(@RequestParam String browser, @RequestParam String db, @RequestParam String tenant, @RequestParam Boolean isSaas) {
        executor.submit(() -> {
            try {
                testPlanExec.testPlanExec(browser, db, tenant, isSaas, TestPlanType.PLAN_ALL.getType());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return "开始执行Mix";
    }

}
