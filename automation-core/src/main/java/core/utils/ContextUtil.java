package core.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class ContextUtil {

    private static ConfigurableApplicationContext context;

    public static void setContext(ConfigurableApplicationContext context) {
        ContextUtil.context = context;
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    public static Object getBean(Class c) {
        return context.getBean(c);
    }
}
