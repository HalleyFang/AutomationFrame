package core.utils;

import core.listener.ReportListener;
import core.listener.SendResultEmailListener;
import core.testplan.GetTestClasses;
import core.testplan.TestPlan;
import core.listener.AssertionListener;
import core.listener.RePrioritizingListener;
import org.springframework.stereotype.Component;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

@Component
public class TestNGExec {

    public void testngExec(GetTestClasses classes) {
        TestNG tng = new TestNG();
        tng.setTestClasses(TestPlan.getTestPlan(classes));
        tng.setParallel(XmlSuite.ParallelMode.CLASSES);
        tng.setThreadCount(1);
        tng.addListener(new AssertionListener());
        tng.addListener(new TestListenerAdapter());
        tng.addListener(new RePrioritizingListener());
        tng.addListener(new ReportListener());
        tng.addListener(new SendResultEmailListener());
        tng.run();
    }
}
