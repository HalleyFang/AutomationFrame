package core.utils;

import core.listener.SendResultEmailListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.ResourceBundle;

@Component
public class AppConf {

    public static String getReportUrl() {
        return reportUrl;
    }

    private static String reportUrl;

    public static String getRunType() {
        return runType;
    }

    private static String runType;

    private static Boolean isDebug = true;

    public static Boolean isDebug() {
        return isDebug;
    }

    public AppConf() {
        ResourceBundle config = ReadConfig.readConfig("application.properties");
        AppConf.runType = config.getString("auto.run-type");
        AppConf.isDebug =
                !StringUtils.isEmpty(AppConf.runType) && AppConf.runType.equalsIgnoreCase("debug");
        String toListStr = config.getString("auto.email-to-list");
        if (!StringUtils.isEmpty(toListStr)) {
            String[] to = toListStr.split(",");
            SendResultEmailListener.toList = Arrays.asList(to);
        }
        AppConf.reportUrl = config.getString("auto.report-url");
    }

}
