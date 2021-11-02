package auto.uitestplan;

import ui.cases.LoginCases;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用例class维护类
 */
public class TestList {

    //ui
    public static List<Class> planA() {
        List<Class> c = new ArrayList<>();
        c.add(LoginCases.class);
        return c;
    }

    //designer
    public static List<Class> planB() {
        List<Class> c = new ArrayList<>();
        return c;
    }

    //book
    public static List<Class> planC() {
        List<Class> c = new ArrayList<>();
        return c;
    }

}