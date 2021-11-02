package core.ui.utils;

import core.testplan.TestPlanExec;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class GetWebDriver {

    /*public WebDriver getWebDriver(String nodeIP, String nodePort) {
        WebDriver remoteWebDriver = null;
        String gridUrl = String.format("http://%s:%s/wd/hub", nodeIP, nodePort);
        GetNetworkStatus netstatus = new GetNetworkStatus();
        netstatus.getNetworkStatus(nodeIP, nodePort);
        if (netstatus.netstatus) {
            try {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--lang=zh-cn");
                DesiredCapabilities capability = DesiredCapabilities.chrome();
                capability.setCapability(ChromeOptions.CAPABILITY, options);
                remoteWebDriver = new RemoteWebDriver(new URL(gridUrl), capability);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return remoteWebDriver;
    }*/


    /*public synchronized static WebDriver getWebDriver() {
        WebDriver webDriver = null;
        try {
            setPath();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("intl.accept_languages","zh-cn");
            webDriver = new FirefoxDriver(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return webDriver;
    }*/

    private ChromeDriverService service() throws IOException {
        ChromeDriverService service = new ChromeDriverService.Builder().build();
        return service;
    }

    public synchronized static Map<String, Object> getWebDriverMap(ResourceBundle config) {
//        ResourceBundle config = ReadConfig.readConfig("ui/ui.properties");
        //String driver_path = "E:\\develop\\botwire-demo\\webdriver\\chromedriver.exe";
        //String driver_name = "webdriver.chrome.driver";
        // 判断系统
/*        String os = System.getProperties().getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            driver_path = "";
        }*/
        Map<String, Object> driverMap = new HashMap<>();
        WebDriver webDriver = null;
        String remoteHost = config.getString("ui.remoteHost");
        String remotePort = config.getString("ui.remotePort");
        String driver_path = null;
        String driver_name = null;
        Map<String, Object> runParameters = (Map<String, Object>) TestPlanExec.threadLocal.get();
        switch (runParameters.get("browser").toString()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--lang=zh-cn");
                DesiredCapabilities capability = DesiredCapabilities.chrome();
                capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                if (StringUtils.isNotBlank(remoteHost) && StringUtils.isNotBlank(remotePort)) {
                    String gridUrl = String.format("http://%s:%s/wd/hub", remoteHost, remotePort);
                    try {
                        webDriver = new RemoteWebDriver(new URL(gridUrl), capability);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    driver_path = config.getString("ui.chromeDriverPath");
                    driver_name = "webdriver.chrome.driver";
                    System.setProperty(driver_name, driver_path);
                    ChromeDriverService chromeDriverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(driver_path)).usingAnyFreePort().build();
                    try {
                        chromeDriverService.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    webDriver = new RemoteWebDriver(chromeDriverService.getUrl(), capability);
                    driverMap.put("service", chromeDriverService);
                }
                driverMap.put("webDriver", webDriver);
                break;
            case "ie":
                driver_path = config.getString("ui.ieDriverPath");
                driver_name = "webdriver.ie.driver";
                System.setProperty(driver_name, driver_path);
                InternetExplorerDriverService internetExplorerDriverService = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(driver_path)).usingAnyFreePort().build();
                try {
                    internetExplorerDriverService.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InternetExplorerOptions internetExplorerOptions = new InternetExplorerOptions();
                DesiredCapabilities ieCapabilities = DesiredCapabilities.edge();
                webDriver = new RemoteWebDriver(internetExplorerDriverService.getUrl(), ieCapabilities);
                driverMap.put("service", internetExplorerDriverService);
                driverMap.put("webDriver", webDriver);
                break;
            case "edge":
                driver_path = config.getString("ui.edgeDriverPath");
                driver_name = "webdriver.edge.driver";
                System.setProperty(driver_name, driver_path);
                EdgeDriverService edgeDriverService = new EdgeDriverService.Builder().usingDriverExecutable(new File(driver_path)).usingAnyFreePort().build();
                try {
                    edgeDriverService.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EdgeOptions edgeOptions = new EdgeOptions();
                DesiredCapabilities desiredCapabilities = DesiredCapabilities.edge();
                webDriver = new RemoteWebDriver(edgeDriverService.getUrl(), desiredCapabilities);
                driverMap.put("service", edgeDriverService);
                driverMap.put("webDriver", webDriver);
                break;
            case "safari":
                driver_path = config.getString("ui.safariDriverPath");
                driver_name = "webdriver.safari.driver";
                System.setProperty(driver_name, driver_path);
                SafariDriverService safariDriverService = new SafariDriverService.Builder().usingDriverExecutable(new File(driver_path)).usingAnyFreePort().build();
                try {
                    safariDriverService.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SafariOptions safariOptions = new SafariOptions();
                DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
                webDriver = new RemoteWebDriver(safariDriverService.getUrl(), safariCapabilities);
                driverMap.put("service", safariDriverService);
                driverMap.put("webDriver", webDriver);
                break;
            default:
                driver_path = config.getString("ui.fireFoxDriverPath");
                driver_name = "webdriver.gecko.driver";
                System.setProperty(driver_name, driver_path);
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("intl.accept_languages", "zh-cn");
                DesiredCapabilities capabilityF = DesiredCapabilities.firefox();
                capabilityF.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
                if (StringUtils.isNotBlank(remoteHost) && StringUtils.isNotBlank(remotePort)) {
                    String gridUrl = String.format("http://%s:%s/wd/hub", remoteHost, remotePort);
                    try {
                        webDriver = new RemoteWebDriver(new URL(gridUrl), capabilityF);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                } else {
                    webDriver = new FirefoxDriver(options);
                }
                driverMap.put("webDriver", webDriver);
                break;
        }
        return driverMap;
    }


 /*   public static void main(String[] args) throws MalformedURLException, InterruptedException {
        // RemoteWebDriver的基本使用

        //第一个参数：表示服务器的地址。第二个参数：表示预期的执行对象，其他的浏览器都可以以此类推
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.32:4444/wd/hub/"), DesiredCapabilities.chrome());
        driver.manage().window().maximize();
        driver.get("http://www.baidu.com");
    }*/
}
