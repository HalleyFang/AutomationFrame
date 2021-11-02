package core.testplan;

public class TestPlan {

    /**
     * 测试计划
     *
     * @param getTestClasses
     * @return
     */
    public static Class[] getTestPlan(GetTestClasses getTestClasses) {
        return getTestClasses.getTestClasses();
    }

}
