package core.utils;

import core.email.SendEmail;

public class GetBeanFactoryUtil {

    public static Object getBean(Class tClass){
        if (AppConf.isDebug()) {
            try {
                return tClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return ContextUtil.getBean(tClass);
    }
}
