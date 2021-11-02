package core.utils;

import org.apache.commons.lang3.StringUtils;

public class RunStatusUtil {

    public static String runStr(String browser, String db, String tenant) {
        if(StringUtils.isEmpty(browser)){
            browser = "";
        }
        if(StringUtils.isEmpty(db)){
            db = "";
        }
        if(StringUtils.isEmpty(tenant)){
            tenant = "";
        }
        String str = browser+db+tenant;
        if(StringUtils.isEmpty(str)){
            str = "unknown";
        }
        return str;
    }
}
