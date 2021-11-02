package ui.config;

import core.utils.ReadConfig;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * 登陆数据
 */
public class BaseData {

    public static ResourceBundle config = ReadConfig.readConfig("ui.properties");

    private static String url;
    private static String username;
    private static String password;

    public static String getUsername() {
        username = config.getString("ui.username");
        return username;
    }

    public static String getPassword() {
        password = config.getString("ui.password");
        return password;
    }

    public static String getUrl() {
        url = config.getString("ui.url");
        return url;
    }
}
