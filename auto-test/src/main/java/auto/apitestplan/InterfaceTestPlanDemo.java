package auto.apitestplan;

import core.testplan.GetTestClasses;
import core.testplan.TestListUtils;
import org.springframework.stereotype.Component;

/**
 * 接口测试类
 */
@Component
public class InterfaceTestPlanDemo implements GetTestClasses {
    @Override
    public Class[] getTestClasses() {
        return TestListUtils.planListToC(TestList.plan());
    }

    @Override
    public Integer getType() {
        return 1;
    }
}
