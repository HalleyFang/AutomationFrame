package ui.businessservice;

import core.utils.AssertionRuntime;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.stereotype.Component;
import ui.pageobject.BasePage;
import ui.pageobject.LoginPage;
import ui.pageobject.LogoutPage;

import java.io.IOException;

/**
 * 公共操作：登出
 */

@Component
public class Logout {

    public void logout(BasePage basePage) throws IOException {
        LogoutPage logoutPage = basePage.logout();
        AssertionRuntime.verifyEquals(logoutPage.logoutMsg(), "您已成功登出");
        LoginPage loginPage = logoutPage.reLogin();
        AssertionRuntime.verifyEquals(loginPage.loginTitle(), "账号登录");
    }
}
