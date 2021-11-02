package core.testplan;

/**
 * 测试类获取
 */
public interface GetTestClasses {

    Class[] getTestClasses();

    default Integer getType() {
        return 3;
    }

    default Boolean isExecute() {
        return true;
    }
}
