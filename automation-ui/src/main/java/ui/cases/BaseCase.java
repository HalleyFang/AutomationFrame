package ui.cases;

import core.ui.pageoperates.OperatesMethods;
import core.ui.utils.GetWebDriver;
import core.utils.GetBeanFactoryUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ui.config.BaseData;

import java.util.Map;

public class BaseCase {

    Map<String, Object> driverMap;
    WebDriver driver;
    DriverService driverService;
    OperatesMethods operatesMethods;

    @BeforeClass
    public void globalSetup() {
        this.driverMap = GetWebDriver.getWebDriverMap(BaseData.config);
        this.driver = (WebDriver) driverMap.get("webDriver");
        this.driverService = (DriverService) driverMap.get("service");
        this.operatesMethods = (OperatesMethods) GetBeanFactoryUtil.getBean(OperatesMethods.class);
    }

    @AfterClass
    public void globalTearDown() {
        operatesMethods.exit(driverService);
    }

}
