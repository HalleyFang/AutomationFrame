package auto.apitestplan;

import api.apicases.LoginInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例class维护类
 */
public class TestList {

    //interface
    public static List<Class> plan() {
        List<Class> c = new ArrayList<>();
        c.add(LoginInterface.class);
        return c;
    }
}