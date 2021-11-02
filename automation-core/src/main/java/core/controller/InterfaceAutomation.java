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
 * 接口测试入口
 */
@RestController
@RequestMapping(path = "/interface_automation")
public class InterfaceAutomation {

    private ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    TestPlanExec testPlanExec;

    @RequestMapping(path = "/start")
    public String start(@RequestParam String db, @RequestParam String tenant, @RequestParam Boolean isSaas) {
        executor.submit(() -> {
            try {
                testPlanExec.testPlanExec(null, db, tenant, isSaas, TestPlanType.INTERFACE_PLAN.getType());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return "开始执行Interface";
    }

}
