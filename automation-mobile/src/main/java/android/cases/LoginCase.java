package android.cases;

import android.businessservice.PermissionAllow;
import android.businessservice.ServerConnect;
import android.config.BaseConf;
import android.pageobject.LoginPage;
import core.utils.GetBeanFactoryUtil;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LoginCase extends BaseCase {

    LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        PermissionAllow permissionAllow = (PermissionAllow) GetBeanFactoryUtil.getBean(PermissionAllow.class);
        permissionAllow.permissionAllow(driver);
        ServerConnect serverConnect = (ServerConnect) GetBeanFactoryUtil.getBean(ServerConnect.class);
        serverConnect.serverConnect(driver, BaseConf.config.getString("web.url"));
        serverConnect.chooseDiverMenu(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void login() {
        loginPage.login("aaa", "bbb");
    }

}
