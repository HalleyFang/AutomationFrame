package android.config;

import core.utils.ReadConfig;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseConf {
    public static ResourceBundle config = ReadConfig.readConfig("android.properties");

    public static URL getAppiumUrl() throws MalformedURLException {
        URL url = new URL(config.getString("appium.url"));
        return url;
    }

    public static DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities des = new DesiredCapabilities();
        des.setCapability("platformName", config.getString("appium.platformName"));//平台名称
        des.setCapability("platformVersion", config.getString("appium.platformVersion"));//手机操作系统版本
        des.setCapability("udid", config.getString("appium.udid"));//连接的物理设备的唯一设备标识
        des.setCapability("deviceName", config.getString("appium.deviceName"));//使用的手机类型或模拟器类型  UDID
        des.setCapability("appPackage", config.getString("appium.appPackage"));//App安装后的包名,注意与原来的CalcTest.apk不一样
        des.setCapability("appActivity", config.getString("appium.appActivity"));//app测试人员常常要获取activity，进行相关测试
        des.setCapability("unicodeKeyboard", Boolean.parseBoolean(config.getString("appium.unicodeKeyboard")));//支持中文输入
        des.setCapability("resetKeyboard", Boolean.parseBoolean(config.getString("appium.resetKeyboard")));//支持中文输入
//        des.setCapability("newCommandTimeout", Integer.parseInt(config.getString("appium.newCommandTimeout")));//没有新命令时的超时时间设置
        des.setCapability("nosign", Boolean.parseBoolean(config.getString("appium.nosign")));//跳过检查和对应用进行 debug 签名的步骤
        des.setCapability("noReset", Boolean.parseBoolean(config.getString("appium.noReset")));//已经装了则不会重新安装apk
        return des;
    }
}
