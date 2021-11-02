package api.config;

import core.utils.ReadConfig;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * 接口基础数据
 */
@Component
public class InterfaceConfig {

    private String ip;
    private int port;
    private String contextPath = "";

    public String getContextPath() {
        return contextPath;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public InterfaceConfig(){
        ResourceBundle config = ReadConfig.readConfig("api.properties");
        this.ip=config.getString("interface.ip");
        this.port= Integer.parseInt(config.getString("interface.port"));
        this.contextPath=config.getString("interface.contextPath");
    }


}
