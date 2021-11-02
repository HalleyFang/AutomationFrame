package core.testplan;


import java.util.ArrayList;
import java.util.List;

public class TestListUtils {

    public static List<Class> planUnion(List<Class> s1, List<Class> s2) {
        List<Class> c = new ArrayList<>();
        c.addAll(s1);
        c.addAll(s2);
        return c;
    }

    public static List<Class> planUnionAll(List<List<Class>> s) {
        List<Class> c = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            c.addAll(s.get(i));
        }
        return c;
    }

    public static Class[] planListToC(List<Class> cl) {
        return cl.toArray(new Class[cl.size()]);
    }
}
