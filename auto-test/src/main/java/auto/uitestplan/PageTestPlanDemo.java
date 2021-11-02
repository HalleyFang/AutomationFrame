package auto.uitestplan;

import core.testplan.GetTestClasses;
import core.testplan.TestListUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ui测试类
 */
@Component
public class PageTestPlanDemo implements GetTestClasses {

    @Override
    public Class[] getTestClasses() {
        List<List<Class>> s = new ArrayList<>();
        s.add(TestList.planA());
        s.add(TestList.planB());
        s.add(TestList.planC());
        List<Class> cl = TestListUtils.planUnionAll(s);
        return TestListUtils.planListToC(cl);
    }

    @Override
    public Integer getType() {
        return 2;
    }
}
