package core.testplan;

import core.cache.InitCache;
import core.ui.utils.ScreenShot;
import core.utils.DbTestDru;
import core.utils.RunStatusUtil;
import core.utils.TenantConfig;
import core.utils.TestNGExec;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TestPlanExec {

    public static Map<Long, String> runStatus = new HashMap<>();
    private static ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    TestNGExec testNGExec;

    public static final ThreadLocal threadLocal = new ThreadLocal();

    public void testPlanExec(String browser, String db, String tenant, Boolean isSaas, GetTestClasses plan) {
        if (runStatus.containsValue(browser)) {
            return;
        }
        executor.submit(() -> {
            Long threadId = Thread.currentThread().getId();
            //记录同一种类型的测试，同一平台同一浏览器同时只能存在一个任务在执行
            runStatus.put(threadId, RunStatusUtil.runStr(browser,db,tenant));
            Map<String, Object> runParameters = new HashMap<>();
            try {
                InitCache.initCache();
                runParameters.put("browser", browser);
                threadLocal.set(runParameters);
                setDb(db, tenant, isSaas);//todo 数据库需要按照计划传入参数，数据库驱动需要工厂模式和单例模式重新设计
                ScreenShot.clearCaptcha();//todo 目录需按照执行计划分组
                ScreenShot.clearFailedImage();//todo 目录需按照执行计划分组
                testNGExec.testngExec(plan);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                runStatus.remove(threadId);
                threadLocal.remove();
            }
        });
    }

    public void testPlanExec(String browser, String db, String tenant, Boolean isSaas, Integer type) throws Exception {
        GetTestClasses plan = getPlan(type);
        if (plan instanceof GetTestClasses) {
            if (StringUtils.isEmpty(browser)) {
                if (type == 1) {
                    browser = "Interface";
                } else if (type == 2) {
                    browser = "FireFox";
                }
            }
            testPlanExec(browser, db, tenant, isSaas, plan);
        } else {
            throw new Exception("plan is not exist");
        }
    }

    private GetTestClasses getPlan(Integer type) throws Exception {
        List<GetTestClasses> planList = new ArrayList<>();
        applicationContext.getBeansOfType(GetTestClasses.class)
                .values()
                .forEach(plan -> planList.add(plan));

        if (planList.size() == 0) {
            throw new Exception("plan is not exist");
        }

        GetTestClasses plan = null;
        if (type == 3) {
            GetTestClasses plan1 = null;
            GetTestClasses plan2 = null;
            for (GetTestClasses g1 : planList) {
                if (g1.getType() == 1 && g1.isExecute()) {
                    plan1 = g1;
                    break;
                }
            }
            for (GetTestClasses g2 : planList) {
                if (g2.getType() == 2 && g2.isExecute()) {
                    plan2 = g2;
                    break;
                }
            }
            if (plan1 instanceof GetTestClasses && plan2 instanceof GetTestClasses) {
                GetTestClasses finalPlan1 = plan1;
                GetTestClasses finalPlan2 = plan2;
                return new GetTestClasses() {
                    @Override
                    public Class[] getTestClasses() {
                        return ArrayUtils.addAll(finalPlan1.getTestClasses(), finalPlan2.getTestClasses());
                    }
                };
            } else if (plan1 instanceof GetTestClasses) {
                return plan1;
            } else if (plan2 instanceof GetTestClasses) {
                return plan2;
            }
        }

        for (GetTestClasses g : planList) {
            if (g.getType() == type && g.isExecute()) {
                plan = g;
                break;
            }
        }
        return plan;
    }

    private void setDb(String db, String tenant, Boolean isSaas) {
        if (StringUtils.isBlank(db)) {
            db = "postgresql";
        }
        if (StringUtils.isBlank(tenant)) {
            tenant = "t";
        }
        switch (db.toLowerCase()) {
            case "mysql":
                DbTestDru.setDruidDataSource("mysql");
                break;
            case "oracle":
                DbTestDru.setDruidDataSource("oracle");
                break;
            case "sqlserver":
                DbTestDru.setDruidDataSource("sqlserver");
                break;
            default:
                DbTestDru.setDruidDataSource("postgresql");
                break;
        }
        TenantConfig.setTenantConfig(isSaas, tenant);
    }
}
